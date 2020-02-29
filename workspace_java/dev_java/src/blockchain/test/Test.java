package blockchain.test;


import java.security.PublicKey;

import blockchain.Wallet;
import blockchain.util.Base64Conversion;

public class Test {
	public static void main(String[] args) throws Exception {
		/*
		Wallet wallet = new Wallet();
		wallet.generateKeyPair();
		System.out.println(wallet.getPublicKey());
		try {
			String str = Base64Conversion.encodePublicKey(wallet.getPublicKey());
			Base64Conversion.savePublicKey(str, "C:\\FTBC_server\\test\\", "test");
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		Wallet wallet = new Wallet();
		String str = Base64Conversion.importPublicKey("C:\\FTBC_server\\test\\", "test");
		PublicKey key = (PublicKey) Base64Conversion.decodeBase64(str);
		System.out.println(key);
	}
}
