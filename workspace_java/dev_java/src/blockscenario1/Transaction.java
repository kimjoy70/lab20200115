package blockscenario1;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;



public class Transaction {
	public String transactionId; // 거래의 해시이기도 하다.
	public PublicKey sender; // 발신자 주소
	public PublicKey reciepient; // 수신자 주소
	public int value; // 돈!
	public byte[] signature; // 제 3자가 우리 지갑에 돈을 쓰지 못하게 위한 것.
	// 입금
	public List<Input> inputs = new ArrayList<>();
	// 출금
	public List<Output> outputs = new ArrayList<>();
	private static double sequence = 0; // 생성된 트랜잭션의 대략적 수
	
	//* 생성자
	public Transaction(PublicKey from, PublicKey to, int value, ArrayList<Input> inputs) {
		this.sender = from;
		this.reciepient = to;
		this.value = value;
		this.inputs = inputs;
	}
	
	//* 트랜잭션 해시(ID로 사용될)을 계산함.
	private String caluateHash() {
		sequence++; // 동일한 해시를 갖는 2개의 동일한 트랜잭션을 피하기위해 1씩 증가
		return StringUtil.applySha256(
							StringUtil.getStringFromKey(sender)
							+ StringUtil.getStringFromKey(reciepient)
							+ Integer.toString(value)
							+ sequence
							);
	}
	// 변조하고 싶지 않은 모든 데이터에 서명함.
	public void generateSignature(PrivateKey privateKey) {
		String data = StringUtil.getStringFromKey(sender) 
					+ StringUtil.getStringFromKey(reciepient)
					+ Integer.toString(value);
		signature = StringUtil.applyECDSASig(privateKey, data);
	}
	// 서명 한 데이터가 변조되지 않았는지 확인
	public boolean verifiySignature() {
		String data = StringUtil.getStringFromKey(sender) 
				+ StringUtil.getStringFromKey(reciepient)
				+ Integer.toString(value);
		return StringUtil.verifyECDSASig(sender, data, signature);
	}
	
	// !!!!!!!!!!!!!!!!!!!!!!!충전 용!!!!!!!!!!!!!!! 트랜잭션 process
	public boolean chargeProcessTransaction() {
		if(verifiySignature() == false) {
			System.out.println("#트랜잭션 서명 증명 실패");
			return false;
		}
		transactionId = caluateHash();
		// 수신자에게 값을 보냄
		outputs.add(new Output(this.reciepient, value, transactionId));
		return true;
	}
	
	// 잔액이 검사해야되는거 프로젝트에 트랜잭션
	// 새 트랜잭션을 만들 수 있는 경우 true를 반환
	public boolean projectProcessTransaction() {
		if(verifiySignature() == false) {
			System.out.println("#트랜잭션 서명 증명 실패");
			return false;
		}
		// 트랜잭션 입력을 수집한다.(사용하지 않았는지 확인)
		for(Input i : inputs) {
			i.UTXO = ProjectChain.UTXOs.get(i.OutputId);
		}
		// 거래가 최소 금액과 유효한지 확인
		if(getInputsValue() < ProjectChain.minimumTransaction) {
			System.out.println("#Transaction Inputs to small : "+getInputsValue());
			return false;
		}
		// 트랜잭션 출력을 생성
		int leftOver = getInputsValue() - value; // 인풋한 값을 얻어오고 잔액을 남긴다.
		transactionId = caluateHash();
		// 수신자에게 값을 보냄
		outputs.add(new Output(this.reciepient, value, transactionId));
		// 남은 잔액을 발신자에게 다시 보낸다.
		outputs.add(new Output(this.sender, leftOver, transactionId));
		// 미사용 목록에 출력 추가
		for(Output o : outputs) {
			ProjectChain.UTXOs.put(o.id, o);
		}
		// 지출 한대로 UTXO 목록에서 트랜잭션 입력을 제거 한다.
		for(Input i : inputs) {
			if(i.UTXO == null) continue; // 거래를 찾을 수 없으면 건너 뛰라
			ProjectChain.UTXOs.remove(i.UTXO.id);
		}
		return true;
	}
	// 입력 합계(UTXO) 값을 반환한다.
	public int getInputsValue() {
		int total = 0;
		for(Input i : inputs) {
			if(i.UTXO == null) continue; // 거래를 찾을 수 없으면 건너 뛰어라.
			total += i.UTXO.value;
		}
		return total;
	}
	// 출력 합계를 반환합니다.
	public int getOutputsValue() {
		int total = 0;
		for(Output o : outputs) {
			total += o.value;
		}
		return total;
	}
}
