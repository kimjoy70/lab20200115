package blockchain2019;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/*
 * 지갑은 주소(개인키,공개키)를 가지고 있으며 개인키와 공개키의 존재 의미는 존재증명과 식별을 위해 존재함
 * 넓은 의미로 디지털 서명 이라고 함.
 * 블록체인(비트코인)에서 의미하는 지갑은 주소를 저장하고 트랜잭션을 생성할 수 있는 소프트웨어임
 * 
 * 아래는 공개키와 개인키를 가지는 지갑을 만들어 보았다.
 * 
 * 블록체인에서 공객키, 개인키의 의미는 트랜잭션의 검증 수단이다.
 * 트랜잭션이 생성되고 처리되는 과정에서 개인키로 트랜잭션에 서명을 하고 공개키로 올바른 서명인지 판단하게 된다.
 * 
 */
public class Wallet {
	public PrivateKey privateKey;
	public PublicKey publicKey;
	public HashMap<String,TransactionOutput> UTXOs = 
			new HashMap<String,TransactionOutput>(); //only UTXOs owned by this wallet.
	public Wallet(){
		generateKeyPair();	
	}
		
	/*
	 * 공개키와 개인키를 생성하는 메소드 구현
	 * 공개키와 개인키 쌍을 가리켜  KeyPair 라고 한다.
	 * 이 키 쌍을 만드는데는 타원 곡선 암호(Elliptic-curve cryptography) 알고리즘이 쓰인다.
	 * 이 알고리즘을 이용하여 키페어를 만드는 코드를 구현해 보자.
	 * 
	 */
	public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
	        	KeyPair keyPair = keyGen.generateKeyPair();
	        	// Set the public and private keys from the keyPair
	        	privateKey = keyPair.getPrivate();
	        	publicKey = keyPair.getPublic();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
/*
 * 	 * 보통 우리가 거래를 할때는 수중에 돈이 얼마 있는지 확인하고 계산할 수 있는 돈이 있을 때 거래를 한다.
	 * 이 과정은 나의 UTXO들을 확인하는 절차이며 UTXOs(코드의 HashMap)의  list를 보면서 나의  UTXO가 있는지 확인하는 과정이 필요함.
	 * 코드로 되어 있을 뿐 실세계에서 거래를 하는 방식과 동일하다.
	 * getBalance 메소드로 쓸수 있는 돈이 얼마 있는지 확인한다.
	 * 그리고 나서 sendFunds 메소드로 거래를 일으킨다.
 */
	  //returns balance and stores the UTXO's owned by this wallet in this.UTXOs
		public float getBalance() {
			float total = 0;	
	        for (Map.Entry<String, TransactionOutput> item: Step4_NoobChain.UTXOs.entrySet()){
	        	TransactionOutput UTXO = item.getValue();
	            if(UTXO.isMine(publicKey)) { //if output belongs to me ( if coins belong to me )
	            	UTXOs.put(UTXO.id,UTXO); //add it to our list of unspent transactions.
	            	total += UTXO.value ; 
	            }
	        }  
			return total;
		}
		//Generates and returns a new transaction from this wallet.
		public Transaction sendFunds(PublicKey _recipient,float value ) {
			if(getBalance() < value) { //gather balance and check funds.
				System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
				return null;
			}
	    //create array list of inputs
			ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	    
			float total = 0;
			for (Map.Entry<String, TransactionOutput> item: UTXOs.entrySet()){
				TransactionOutput UTXO = item.getValue();
				total += UTXO.value;
				inputs.add(new TransactionInput(UTXO.id));
				if(total > value) break;
			}
			
			Transaction newTransaction = new Transaction(publicKey, _recipient , value, inputs);
			newTransaction.generateSignature(privateKey);
			
			for(TransactionInput input: inputs){
				UTXOs.remove(input.transactionOutputId);
			}
			return newTransaction;
		}	
}
