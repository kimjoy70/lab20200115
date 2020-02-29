package blockchain2019;

/*
 * 트랜잭션의 output은 전송된 최종 금액이 표시된다.
 * 이것은 새로운 거래를 생성할 때 input이 참조하게 되면서 보낼 수 있는 코인이 있다는 것을 증명하게 된다.
 * 
 * 한 블록에 여러 개의 트랜잭션이 모이게 되고 시간이 지날 수록 블록체인의 길이는 점점 길어지게 된다.
 * 길어지면 길어질 수록 UTXO를 찾는데 걸리는 시간 또한 길어진다.
 * 
 * 이 문제를 해결하기 위해서 UTXO의 collection을 만들어 주자.
 * 
 * 비트코인 상에는  UTXO pool이 따로 있다.
 */
public class TransactionInput {
	public String transactionOutputId; //Reference to TransactionOutputs -> transactionId
	public TransactionOutput UTXO; //Contains the Unspent transaction output
	
	public TransactionInput(String transactionOutputId) {
		this.transactionOutputId = transactionOutputId;
	}
}
