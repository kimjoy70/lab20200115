package blockscenario1;
/*
 * 충전 용 체인
 * 관리자 용 체인
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MoneyChain {
	public static List<Block> projectChain = new ArrayList<Block>();
	public static int difficulty = 3;
	public static List<Map<String, Wallet>> totalWallet = new ArrayList<>();
	public static Transaction genesisTransaction;
	
	// 블록의 무결성을 점검하는 코드
	public static Boolean isChainValid() {
		System.out.println("\n[NoobChain] isChainValid: boolean 호출");
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		//* HashMap<String, Output> tempUTXOs = new HashMap<>();
		//* tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
		// 블록체인이 변경되었을 때 false 반환
		for(int i=1;i<projectChain.size();i++) {
			currentBlock = projectChain.get(i);
			previousBlock = projectChain.get(i-1);
			// System.out.println("currentBlock.hash : \n"+currentBlock.hash);
			// System.out.println("currentBlock.calculateHash() : \n"+currentBlock.calculateHash());
			// 등록 된 해시와 계산 된 해시를 비교
			if(!currentBlock.hash.equals(currentBlock.calculateHash())) {
				System.out.println("Current Hashes not equal");
				return false;
			}
			// System.out.println("previousBlock.hash : \n"+previousBlock.hash);
			// System.out.println("currentBlock.previousHash : \n"+currentBlock.previousHash);
			// 이전 해시와 등록 된 이전 해시 비교
			if(!previousBlock.hash.equals(currentBlock.previousHash)) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			// 해시가 해결 되었는지 확인
			if(!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
			//
			Output tempOutput;
			for(int t=0; t<currentBlock.transactions.size();t++) {
				Transaction currentTransaction = currentBlock.transactions.get(t);
				if(!currentTransaction.verifiySignature()) {
					System.out.println("#Signature on Transaction("+t+") is Invalid");
					return false;
				}
				if(currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
					System.out.println("#Inputs are note equal to outputs on Transaction(" + t + ")");
					return false;
				}
				if( currentTransaction.outputs.get(0).reciepient != currentTransaction.reciepient) {
					System.out.println("#Transaction(" + t + ") output reciepient is not who it should be");
					return false;
				}
				if( currentTransaction.outputs.get(1).reciepient != currentTransaction.sender) {
					System.out.println("#Transaction(" + t + ") output 'change' is not sender.");
					return false;
				}
			}
		}
		System.out.println("projectChain is valid");
		return true;
	}
	
	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		projectChain.add(newBlock);
		System.out.println("[NoobChain] addBlock: void >> 블록 입력 성공");
	}
}
