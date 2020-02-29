package blockscenario1;

import java.security.PublicKey;


public class Output {
	public String id;
	public PublicKey reciepient; // 이 동전의 새로운 소유자
	public int value; // 그들이 소유 한 동전의 양
	public String parentTransactionId; // 이 출력이 작성된 트랜잭션의 ID
	
	public Output(PublicKey reciepient, int value, String parentTransactionId) {
		this.reciepient = reciepient;
		this.value = value;
		this.parentTransactionId = parentTransactionId;
		this.id = StringUtil.applySha256(StringUtil.getStringFromKey(reciepient)
										+ Integer.toString(value)
										+ parentTransactionId
										);
		// System.out.println("[TransactionOutput] reciepient : "+reciepient);
		// System.out.println("[TransactionOutput] parentTransactionId : "+parentTransactionId+"\n");
	}
	
	// 동전이 당신의 소유인지 확인
	public boolean isMine(PublicKey publicKey) {
		return (publicKey == reciepient);
	}
}
