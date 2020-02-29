package blockchain.util;

import java.io.Serializable;
import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import blockchain.Transaction;



public class StringUtil implements Serializable {
	private static final long serialVersionUID = -968927731054828615L;
	// 해쉬 해주는 곳
	public static String applySha256(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
			StringBuilder hexString = new StringBuilder();
			for(int i=0;i<hash.length;i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	// ECDSA Signature를 적용하고 결과를 byte로 반환한다.
	public static byte[] applyECDSASig(PrivateKey privateKey, String input) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		Signature dsa;
		byte[] output = new byte[0];
		try {
			dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initSign(privateKey);
			byte[] strByte = input.getBytes();
			dsa.update(strByte);
			byte[] realSig = dsa.sign();
			output = realSig;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return output;
	}
	// 문자열 서명을 확인
	public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
			ecdsaVerify.initVerify(publicKey);
			ecdsaVerify.update(data.getBytes());
			return ecdsaVerify.verify(signature);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
	// 머클 루트
	public static String getMerkleRoot(List<Transaction> transactions) {
		int count = transactions.size();
		ArrayList<String> previousTreeLayer = new ArrayList<String>();
		for(Transaction transaction : transactions) {
			previousTreeLayer.add(transaction.txId);
		}
		ArrayList<String> treeLayer = previousTreeLayer;
		while(count>1) {
			treeLayer = new ArrayList<String>();
			for(int i=1;i<previousTreeLayer.size();i++) {
				treeLayer.add(applySha256(previousTreeLayer.get(i-1)
						+ previousTreeLayer.get(i)));
			}
			count = treeLayer.size();
			previousTreeLayer = treeLayer;
		}
		String merkleRoot = (treeLayer.size() == 1) ? treeLayer.get(0) : "";
		return merkleRoot;
	}
	//# 추가
	public static boolean isValidKeys(PublicKey publicKey, PrivateKey privateKey) {
		byte[] tempByte = StringUtil.applyECDSASig(privateKey, "검증용");
		boolean isValid = StringUtil.verifyECDSASig(publicKey, "검증용", tempByte);
		return isValid;
	}
}
