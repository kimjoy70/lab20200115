package blockchain.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import blockchain.BlockChain;

public class Base64Conversion implements Serializable {
	private static final long serialVersionUID = 1510962164517038337L;
	ByteArrayInputStream bais = null;
	ObjectInputStream ois = null;

	public Base64Conversion() {
	}


	public static Object decodeBase64(String base64Str) throws Exception {
		Object objectMember = null;
		byte[] serializedMember = Base64.getDecoder().decode(base64Str);
		try (ByteArrayInputStream bais = new ByteArrayInputStream(serializedMember)) {
			try (ObjectInputStream ois = new ObjectInputStream(bais)) {
				// 역직렬화된 Member 객체를 읽어온다.
				objectMember = ois.readObject();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return objectMember;
	}
	
	// 인코딩
	public static String encodeChain(BlockChain target) throws IOException {
		BlockChain blockChain = null;
		blockChain = target;
		byte[] serializedMember = null;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
				oos.writeObject(blockChain);
				// serializedMember -> 직렬화된 member 객체
				serializedMember = baos.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String outt = Base64.getEncoder().encodeToString(serializedMember);
		return outt;
	}

	public static String encodePublicKey(PublicKey target) throws IOException {
		PublicKey publicKey = null;
		publicKey = target;
		byte[] serializedMember = null;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
				oos.writeObject(publicKey);
				serializedMember = baos.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String outt = Base64.getEncoder().encodeToString(serializedMember);
		return outt;
	}

	public static String encodePrivateKey(PrivateKey target) throws IOException {
		PrivateKey privateKey = null;
		privateKey = target;
		byte[] serializedMember = null;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
				oos.writeObject(privateKey);
				serializedMember = baos.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String outt = Base64.getEncoder().encodeToString(serializedMember);
		return outt;
	}

	public static void saveChain(String targetStr, String path, String loginID) throws IOException {
		// 문자열 txt파일로 저장
		BufferedOutputStream bufferedOutputStream = null;
		try {
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path + loginID + "Chain.ftbc"));
			bufferedOutputStream.write(targetStr.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bufferedOutputStream.close();
		}
	}

	public static void savePublicKey(String targetStr, String path, String loginID) throws IOException {
		// 문자열 txt파일로 저장
		BufferedOutputStream bufferedOutputStream = null;
		try {
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path + loginID + ".puk"));
			bufferedOutputStream.write(targetStr.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bufferedOutputStream.close();
		}
	}

	public static void savePrivateKey(String targetStr, String path, String loginID) throws IOException {
		// 문자열 txt파일로 저장
		BufferedOutputStream bufferedOutputStream = null;
		try {
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path + loginID + ".prk"));
			bufferedOutputStream.write(targetStr.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bufferedOutputStream.close();
		}
	}

	/*****************
	 * 클라이언트의 txt인 base64코드를 String 타입으로 읽어오기 위해 사용하는 메소드
	 * 
	 * @param chainID
	 * @return 해당 파일이 있을 경우 String인 base64 리턴, 없을 경우 null 반환
	 * @throws IOException
	 */
	public static String importChain(String chainID, String path) throws IOException {
		FileInputStream fileStream = null;
		try {
			fileStream = new FileInputStream(path + chainID + "Chain.ftbc");
			byte[] readBuffer = new byte[fileStream.available()];
			while (fileStream.read(readBuffer) != -1) {
			}
			String base64 = new String(readBuffer);
			return base64;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fileStream.close();
		}
		return null;
	}

	public static String importPublicKey(String path, String userID) throws IOException {
		FileInputStream fileStream = null;
		try {
			fileStream = new FileInputStream(path + userID + ".puk");
			byte[] readBuffer = new byte[fileStream.available()];
			while (fileStream.read(readBuffer) != -1) {
			}
			String base64 = new String(readBuffer);
			return base64;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fileStream.close();
		}
		return null;
	}

	public static String importPrivateKey(String path, String userID) throws IOException {
		FileInputStream fileStream = null;
		try {
			fileStream = new FileInputStream(path + userID + ".prk");
			byte[] readBuffer = new byte[fileStream.available()];
			while (fileStream.read(readBuffer) != -1) {
			}
			String base64 = new String(readBuffer);
			return base64;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fileStream.close();
		}
		return null;
	}
}
