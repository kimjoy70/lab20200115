package blockchain2019;

import java.security.*;
import java.util.ArrayList;
/*
 * Transaction & Signatures
 * 트랜잭션과 서명을 구현해 본다.
 * 그전에 트랜잭션에 어떤 데이터가 들어갈 지 알아보자.
 * 1) 송신자의 공개키(주소)
 * 2) 수신자의 공개키(주소)
 * 3) 전달할 금액
 * 
 * Input(수신자가 보낼 돈을 가지고 있다는 것을 증명할 이전 트랜잭션 참조값)
 * Output(거래에서 받은 관련 주소 금액 -> 다음에 일어날 새 트랜잭션의  Input으로 사용됨.)
 * 암호화된 서명(Cryptographic signature):주소의 소유자(송신자)가 데이터가 변경되지 않았음을 증명하는 서명
 * (제 3자가 임의로 금액을 바꾸지 못하게)
 * 
 * 이해를 돕기 위한 간단한 예시를 소개해 본다.
 * 1. bob은 sally에게 2개의 Noob코인을 보내고 싶어 한다.
 * 2. 지갑은 이 트랜잭션을 생성하고 그 채굴자에게 트랜잭션을 다음 불록에 붙여 달라고 한다.
 * 3. 악의적인 채굴자는 sally가 아닌  john에게 2개의 Noob코인을 보내는 걸 수취인을 바꿔 버린다.
 * 4. 하지만 bob은 자신의 개인키로 데이터를 서명했고 그 데이터는  bob의 공개키로 데이터의 위, 변조를 식별할 수 있다.
 * 5. 다른 사람의 공개키로는  bob의 개인키로 암호화 된 데이터를 확인할 수 없고 거래는 무사히 완료된다.
 * 
 * Signatures는 채굴자에 의해서 검증된다.
 */
public class Transaction {
	
	public String transactionId; // this is also the hash of the transaction.
	//송신자의 공개키
	public PublicKey sender; // senders address/public key.
	//수신자의 공객키
	public PublicKey reciepient; // Recipients address/public key.
	//송신자가 수신자에게 보낼 코인 값
	public float value;
	//이전에는 signatures는 byte형의 배열이었다.
	//SIGNATURE = createSignature(Private Key, From[HASH]+To[HASH]+value)
	public byte[] signature; // this is to prevent anybody else from spending funds in our wallet.
	
	/*
	 * 우리가 지갑이라고 하지만 실제로는 지갑에 코인을 직접적으로 더하거나 빼면서 돈을 쌓아 놓지는 않는다.
	 * 트랜잭션의 결과로 생성된 특정 output을 추적해서 본인이 쓸 수 있는 코인을 알아낸다.
	 * 
	 * bob은 sally에게 2Noob코인을 보낼 때 bob의 지갑에는 2코인이 있는 것이 아니라 bob 이전 트랜젹션의 output이 쌓여 있었던 것이다.
	 * bob의 지갑은 트랜잭션의 output들을 추적해서 bob이 2코인 만큼 보낼 코인이 있는지 확인하게 된다.
	 * 
	 * 비트 코인에서는 이 특수한 output을 가리켜 사용하지 않은 거래의 출력(Upspent Transaction Outputs)라고 하며
	 * UTXO라고 줄여 쓴다.
	 * 결국 새로운 트랜잭션을 일으킨다는 것은 UTXO를 추적하여 이전 거래으 output값을 새로운 트랜잭션의 input으로 사용하겠다는 말이다.
	 * 
	 */
	public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
	//얼마나 많은 트랜잭션이 생성되었는지 대략적인 수치
	private static int sequence = 0; // a rough count of how many transactions have been generated. 
	
	// Constructor: 
	public Transaction(PublicKey from, PublicKey to, float value,  ArrayList<TransactionInput> inputs) {
		this.sender = from;//송신자의 공개키
		this.reciepient = to;//수신자의 공개키
		this.value = value;//전달할 값
		this.inputs = inputs;//이전 트랜잭션의 참조값
	}
	
	// This Calculates the transaction hash (which will be used as its Id)
	private String calulateHash() {
		//동일한 해쉬를 갖는 2 개의 동일한 트랜잭션을 피하기 위해 시퀀스를 증가시킨다.
		sequence++; //increase the sequence to avoid 2 identical transactions having the same hash
		return StringUtil.applySha256(
				StringUtil.getStringFromKey(sender) +
				StringUtil.getStringFromKey(reciepient) +
				Float.toString(value) + sequence
				);
	}
	
/*
 * 서명은 우리가 구현하고 있는 블록체인에서 두 가지 중요한 역할을 하게 된다.
 * 1. 돈의 주인만이 돈(코인)을 보낼 수 있다.
 * 2. 공격자로 부터 새로운 블록이 채굴되기 전에 트랜잭션이 위변조 되는 것을 막는다.
 * 
 * 	
 */
	//Signs all the data we dont wish to be tampered with.
	//개인키는 데이터를 서명하는데 쓰이고 
	//공개키는 데이터의 무결성을 확인하는데 쓰인다.
	public void generateSignature(PrivateKey privateKey) {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value)	;
		signature = StringUtil.applyECDSASig(privateKey,data);		
	}
	/*
	 * 트랜잭션은 거래 송신자가 본인의 개인키를 가지고 데이터를 암호화하고 송신자의 공개키로만 풀수 있는 구조다.
	 * 이 역할은 verifySignature메소드가 하고 있다.
	 */
	//Verifies the data we signed hasnt been tampered with
	public boolean verifiySignature() {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value)	;
		return StringUtil.verifyECDSASig(sender, data, signature);
	}	
	/*
	 * 다음은 트랜잭션을 처리하는 방식이다.
	 * 특정 트랜잭션이 유효한 트랜잭션인지 무결한 트랜잭션인지 검증하는 과정이 있어야 한다.
	 * 앞서 공부했듯이 트랜잭션은 거래 송신자가 본인의 개인키를 가지고 데이터를 암호화하고 송신자의 공개키로만 풀수 있는 구조이다.
	 * 
	 * 이제 거래에 필요한 다른 몇 가지 과정을 넣어 줄 차례이다.
	 * 이 과정을 processTransaction이라는 메소드를 통해서 이루어진다.
	 * 
	 * processTransaction메소드에서 트랜잭션을 처리하는 순서는 다음과 같다.
	 * 
	 * 트랜잭션 유효성 검사 -> 거래 할 UTXO가 충분한지 -> 거래금과 잔금 반환 -> 쓰인 UTXO삭제, 새로운  output 생성
	 * 
	 * 트랜잭션을 처리했으니 이제 지갑에서 트랜잭션을 만들어 낼 차례이다.
	 * 보통 우리가 거래를 할때는 수중에 돈이 얼마 있는지 확인하고 계산할 수 있는 돈이 있을 때 거래를 한다.
	 * 이 과정은 나의 UTXO들을 확인하는 절차이며 UTXOs(코드의 HashMap)의  list를 보면서 나의  UTXO가 있는지 확인하는 과정이 필요함.
	 * 
	 */
	//Returns true if new transaction could be created.	
	public boolean processTransaction() {
			
			if(verifiySignature() == false) {
				System.out.println("#Transaction Signature failed to verify");
				return false;
			}
					
			//gather transaction inputs (Make sure they are unspent):
			for(TransactionInput i : inputs) {
				i.UTXO = Step4_NoobChain.UTXOs.get(i.transactionOutputId);
			}

			//check if transaction is valid:
			if(getInputsValue() < Step4_NoobChain.minimumTransaction) {
				System.out.println("#Transaction Inputs to small: " + getInputsValue());
				return false;
			}
			
			//generate transaction outputs:
			float leftOver = getInputsValue() - value; //get value of inputs then the left over change:
			transactionId = calulateHash();
			outputs.add(new TransactionOutput( this.reciepient, value,transactionId)); //send value to recipient
			outputs.add(new TransactionOutput( this.sender, leftOver,transactionId)); //send the left over 'change' back to sender		
					
			//add outputs to Unspent list
			for(TransactionOutput o : outputs) {
				Step4_NoobChain.UTXOs.put(o.id , o);
			}
			
			//remove transaction inputs from UTXO lists as spent:
			for(TransactionInput i : inputs) {
				if(i.UTXO == null) continue; //if Transaction can't be found skip it 
				Step4_NoobChain.UTXOs.remove(i.UTXO.id);
			}
			
			return true;
		}
		
	//returns sum of inputs(UTXOs) values
		public float getInputsValue() {
			float total = 0;
			for(TransactionInput i : inputs) {
				if(i.UTXO == null) continue; //if Transaction can't be found skip it 
				total += i.UTXO.value;
			}
			return total;
		}

	//returns sum of outputs:
		public float getOutputsValue() {
			float total = 0;
			for(TransactionOutput o : outputs) {
				total += o.value;
			}
			return total;
		}	
}
