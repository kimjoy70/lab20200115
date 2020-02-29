package blockscenario1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;




public class Block {
	public String hash;
	public String previousHash;
	public String merkleRoot;
	
	public List<Transaction> transactions = new ArrayList<>(); //
	// private String data;
	private long timeStamp;
	private int nonce;
	
	// *생성자
	public Block(String previousHash) {
		// this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
	}
	// *해쉬 값 계산
	public String calculateHash() {
		String calculatehash = StringUtil.applySha256(
								previousHash
								+ Long.toString(timeStamp)
								+ Integer.toString(nonce)
								+ merkleRoot
								);
		return calculatehash;
	}
	// * 데이터 추출 - 마이닝
	public void mineBlock(int difficulty) {
		//Create a string with difficulty * "0" 
		System.out.println("\n[Block] mineBlock: void 호출");
		merkleRoot = StringUtil.getMerkleRoot(transactions);
		String target = new String(new char[difficulty]).replace('\0', '0');
		System.out.println("target : "+target);
		while(!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : "+hash);
	}
	
	//****************************변경 예측 부분**********************************
	//****************************변경 예측 부분**********************************
	// 이 블록에 거래 추가
	public boolean addTransaction(Transaction transaction) {
		// 블록이 첫블록이 아니라면 트랜잭션을 처리하고 유효한지 확인
		System.out.println("\n[Block] addTransaction: boolean 호출");
		if(transaction == null) return false;
		if((previousHash != "0")) {
			if((transaction.projectProcessTransaction() != true)) {
				System.out.println("Transaction failed to process. Discarded.");
				return false;
			}
		}
		transactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
		return true;
	}
	//****************************변경 예측 부분**********************************
	//****************************변경 예측 부분**********************************
}
