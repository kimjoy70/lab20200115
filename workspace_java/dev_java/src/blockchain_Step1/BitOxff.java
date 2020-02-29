package blockchain_Step1;

import java.security.MessageDigest;

public class BitOxff {
	public static void hexValue() {
		// 10진수를 16진수로 출력: 가장 간단한 방법 ㅎㅎ
	    System.out.format("%02X%n", 255);  // FF
	    System.out.format("%02x%n", 255);  // ff
	    System.out.format("%X%n"  , 10);   // A

	    //%X : 헥사를 대문자로
	    //%x : 헥사를 소문자로
	    //%02X : 2자리 헥사를 대문자로, 그리고 1자리 헥사는 앞에 0을 붙임
	    //%n : 다음줄로 줄바꿈하기
	    String s = String.format("%02X%n", 10); // 16진수 문자열로 변환
	    System.out.println("문자열로 만들어서 출력: " + s); // 문자열로 만들어서 출력: 0A


	    // 10진수를 16진수로: 불편한 방법
	    System.out.println(Integer.toHexString(255)); // ff
	    System.out.println(Integer.toHexString(255).toUpperCase()); // 대문자로: FF
	    System.out.println(Integer.toHexString(10).toUpperCase()); // A
	}
	public static String applySha256(String input){		

		try {

			MessageDigest digest = MessageDigest.getInstance("SHA-256");	        

			//Applies sha256 to our input, 

			byte[] hash = digest.digest(input.getBytes("UTF-8"));	        
			for(int x=0;x<hash.length;x++) {
				//System.out.println("x : "+x);
			}
			//여기에는 16 진수로 해시가 포함됩니다.
			StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal

			for (int i = 0; i < hash.length; i++) {
				//10진수를 16진수로 바꿔주는 함수 - toHexString
				String hex = Integer.toHexString(0xff & hash[i]);
				//System.out.println("hex:"+hex);
				if(hex.length() == 1) hexString.append('0');

				hexString.append(hex);

			}

			return hexString.toString();

		}
		catch(Exception e) {

			throw new RuntimeException(e);

		}
	}	
	void go() {
		//Runtime.getRuntime.exec("taskkill /f /im");
	}
	public static void main(String[] args) {
		//taskkill /f /im filename.exe
        //taskkill /f /pid 00000
		//taskkill /f /pid 248
		/*
		여기서 /f 는 강제종료, /im은 이미지이름을 사용한다는것, /pid는 프로세스 pid 값을 사용하여 종료하겠다는 의미이다.
		추가적으로 실행중인 프로세스 리스트를 보고싶을때는
		tasklist 명령어를 이용한다.
		중복된 프로세스일 경우 해당 프로세스가 사용중인 서비스 명을 확인해서 어떤 녀석인지 찾을 수 있는데
		이때는 /svc 옵션을 추가로 주면 된다.
		tasklist /svc
*/

		//Runtime.getRuntime.exec("taskkill //f //im");
		hexValue();
		/*
		 * byte자료형 범위가 음수,양수 모두 표현하려다 보니 127을 넘어서는 숫자부터 음수로 인식함.
		 * 150이라는 int형 숫자(=10010110)에서 보듯 맨앞의 비트가 1이므로 음수로 인식해
		 * -106이라는 숫자가 리턴된다.
		 */
		int n = 150;
		System.out.println(Integer.toBinaryString(n));
		byte b = (byte)n;
		System.out.println(b);
		//byte를 150으로 보이게 하는 방법이 아래 이다.
		//0xff는 표현식을 16진수로 했을 뿐 십진수 255이다.
		System.out.println(b & 0xff);
		
		String input = "1";
		//String result = BitOxff.applySha256(input);
		//System.out.println(result);
		
	}

}
