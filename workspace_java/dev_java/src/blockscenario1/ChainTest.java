package blockscenario1;

import java.security.Security;

public class ChainTest {
	
	// 관리자 
	// 회원 가입
	
	public static void main(String[] args) {
		MoneyChain moneyChain = new MoneyChain(); 
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		// 관리자 지갑 생성
		Wallet managerWallet = new Wallet();
		// 3명 충전 : 고명석, 정지우, 이상만
		Wallet koWallet      = new Wallet();
		Wallet jungWallet    = new Wallet();
		Wallet leeWallet     = new Wallet();
		// 보안 제공자로 Bouncey Castle 설정
		// 관리자가 충전 
		Transaction mTransaction = moneyChain.genesisTransaction;
		mTransaction = new Transaction(managerWallet.publicKey, koWallet.publicKey, 10000, null);
		mTransaction.generateSignature(managerWallet.privateKey);
		mTransaction.transactionId = "0";
		mTransaction.outputs.add(new Output(mTransaction.reciepient, mTransaction.value, mTransaction.transactionId));
		System.out.println("Creating and Mining Genesis block..");
		// 고명석씨에게 만원
		Block genesis = new Block("0");
		genesis.addTransaction(mTransaction);
		moneyChain.addBlock(genesis);
		// 정지우씨에게 천원
		Block block1 = new Block(genesis.hash);
		block1.addTransaction(managerWallet.sendFunds(jungWallet.publicKey, 1000));
		moneyChain.addBlock(block1);
		// 이상만씨에게 오만원
		Block block2 = new Block(block1.hash);
		block2.addTransaction(managerWallet.sendFunds(leeWallet.publicKey, 50000));
		moneyChain.addBlock(block2);
		
		moneyChain.isChainValid();
		System.out.println("고명석 잔액 : "+koWallet.getBalance());
		System.out.println("정지우 잔액 : "+jungWallet.getBalance());
		System.out.println("이상만 잔액 : "+leeWallet.getBalance());
		
		
	}
}
