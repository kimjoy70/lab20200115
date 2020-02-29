package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import blockchain.util.StringUtil;


public class Block implements Serializable {
	private static final long serialVersionUID = 3789218647314831245L;
	public String hash;
	public String previousHash;
	public String merkleRoot;
	public List<Transaction> transactions = new ArrayList<>();
	public long timeStamp;
	public int nonce;
	
	public Block(String previousHash) {
		this.previousHash = previousHash;
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
		while(!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : "+hash);
	}
	
	// 이 블록에 거래 추가
	public boolean addTransaction(BlockChain blockChain, Transaction transaction) {
		// 블록이 첫블록이 아니라면 트랜잭션을 처리하고 유효한지 확인
		System.out.println("\n[Block] addTransaction: boolean 호출");
		if(transaction == null) return false;
		if((previousHash != "0")) {
			if((transaction.processTransaction(blockChain) != true)) {
				System.out.println("Transaction failed to process. Discarded.");
				return false;
			}
		} else {
			transaction.txId = transaction.calculateHash();
		}
		transactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
		return true;
	}
}
