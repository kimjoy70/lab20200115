package blockchain;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import blockchain.util.Base64Conversion;
import blockchain.util.StringUtil;

public class Test {
	private static final Logger logger = LoggerFactory.getLogger(Test.class);
	public PublicKey pukTest(String a) throws Exception {
		logger.info("TEST puk  " + a);
		PublicKey puk = (PublicKey) Base64Conversion.decodeBase64(a);
		System.out.println("puk");
		System.out.println(puk);
		return puk;
	}
	public PrivateKey prkTest(String a) throws Exception {
		logger.info("TEST prk  " + a);
		PrivateKey pri = (PrivateKey) Base64Conversion.decodeBase64(a);
		System.out.println("pri");
		System.out.println(pri);
		return pri;
	}
	public static void main(String[] args) {
		try {
			String a = Base64Conversion.importPublicKey("C:\\Users\\kosmo_03\\Desktop\\FTBC\\test (1)\\", "park");
			String b = Base64Conversion.importPrivateKey("C:\\Users\\kosmo_03\\Desktop\\FTBC\\test (1)\\", "park");
			PublicKey pub = (PublicKey) Base64Conversion.decodeBase64(a);
			PrivateKey pri = (PrivateKey) Base64Conversion.decodeBase64(b);
			StringUtil stringUtil = new StringUtil();
			if(stringUtil.isValidKeys(pub, pri)) {
				System.out.println("맞음");
			} else {
				System.out.println("틀림");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
