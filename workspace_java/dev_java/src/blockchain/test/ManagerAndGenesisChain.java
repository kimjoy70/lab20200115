package blockchain.test;

import java.security.PrivateKey;
import java.security.PublicKey;

import blockchain.Block;
import blockchain.BlockChain;
import blockchain.Output;
import blockchain.Transaction;
import blockchain.Wallet;
import blockchain.util.Base64Conversion;
import blockchain.util.CommonSet;
import exe.util.Path;

public class ManagerAndGenesisChain {
	private void createManagerWallet() {
		try {
			Wallet managerWallet = new Wallet();
			managerWallet.generateKeyPair();
			String publicBase64 = Base64Conversion.encodePublicKey(managerWallet.getPublicKey());
			String privateBase64 = Base64Conversion.encodePrivateKey(managerWallet.getPrivateKey());
			Base64Conversion.savePublicKey(publicBase64, Path.MANAGER_WALLET_PATH, "manager");
			Base64Conversion.savePrivateKey(privateBase64, Path.MANAGER_WALLET_PATH, "manager");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addGenesisBlock() {
		CommonSet commonSet = CommonSet.getInstance();
		Wallet managerWallet = commonSet.getManagerWallet();
		PublicKey managerPuk = managerWallet.getPublicKey();
		PrivateKey managerPrk = managerWallet.getPrivateKey();
		BlockChain FTBCChain = new BlockChain();
		// 마르지 않는 샘물 추가
		FTBCChain.genesisTransaction = new Transaction(managerPuk, managerPuk, null, 1000000000000000000L, null);
		FTBCChain.genesisTransaction.generateSignature(managerPrk);
		FTBCChain.genesisTransaction.txId = "0";
		FTBCChain.genesisTransaction.outputs.add(new Output(FTBCChain.genesisTransaction.reciepient,
															FTBCChain.genesisTransaction.value,
															FTBCChain.genesisTransaction.txId));
		FTBCChain.UTXOs.put(FTBCChain.genesisTransaction.outputs.get(0).id, FTBCChain.genesisTransaction.outputs.get(0));
		Block genesis = new Block("0");
		genesis.addTransaction(FTBCChain, FTBCChain.genesisTransaction);
		FTBCChain.addBlock(genesis);
		try {
			String baseStr = Base64Conversion.encodeChain(FTBCChain);
			Base64Conversion.saveChain(baseStr, "C:\\FTBC_server\\chain\\backup\\genesis_chain\\", "FTBC");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		ManagerAndGenesisChain magc = new ManagerAndGenesisChain();
		magc.addGenesisBlock();
		try {
			String str = Base64Conversion.importChain("FTBC", "C:\\FTBC_server\\chain\\backup\\genesis_chain\\");
			BlockChain blockChain = (BlockChain) Base64Conversion.decodeBase64(str);
			System.out.println(blockChain.blockChain.size());
			CommonSet commonSet = CommonSet.getInstance();
			Wallet wallet = commonSet.getManagerWallet();
			System.out.println(wallet.getBalance(blockChain));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
