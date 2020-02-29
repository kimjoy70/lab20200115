package blockscenario1;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Wallet {
	public PrivateKey privateKey;
	public PublicKey publicKey;
	// 소유자의 잔고 관리
	public HashMap<String, Output> UTXOs = new HashMap<>();
	
	public Wallet() {
		generateKeyPair();
	}
	
	public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// 키 제너레이터 와 키페어 생성
			keyGen.initialize(ecSpec, random);
			KeyPair keyPair = keyGen.generateKeyPair();
			// 공개키와 개인키를 키페어에서 가져온다.
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
			System.out.println("[Wallet] generateKeyPair:void_privateKey");
			System.out.println(privateKey);
			System.out.println("[Wallet] generateKeyPair:void_publicKey");
			System.out.println(publicKey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	// 잔액을 반환하고 이 지갑의 소유한 UTXO를 여기 저장한다.
	public int getBalance() {
		int total = 0;
		for(Map.Entry<String, Output> item : ProjectChain.UTXOs.entrySet()) {
			Output UTXO = item.getValue();
			if(UTXO.isMine(publicKey)) { // 내 것일 경우
				UTXOs.put(UTXO.id, UTXO); // 사용하지 않은 거래 목록에 추가
				total += UTXO.value;
			}
		}
		return total;
	}
	
	// 새 거래를 생성하고 반환함
	public Transaction sendFunds(PublicKey _recipient, int value) {
		if(getBalance() < value) { // 잔액을 모으고 자금을 확인하십시오 
			System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
			return null;
		}
		// 입력 배열 목록을 만든다.
		ArrayList<Input> inputs = new ArrayList<>();
		int total = 0;
		for(Map.Entry<String, Output> item : UTXOs.entrySet()) {
			Output UTXO = item.getValue();
			total += UTXO.value;
			inputs.add(new Input(UTXO.id));
			if(total>value) break;
		}
		Transaction newTransaction = new Transaction(publicKey, _recipient, value, inputs);
		newTransaction.generateSignature(privateKey);
		for(Input input : inputs) {
			UTXOs.remove(input.OutputId);
		}
		return newTransaction;
	}
}
