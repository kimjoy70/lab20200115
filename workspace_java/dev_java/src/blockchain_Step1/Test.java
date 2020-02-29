package blockchain_Step1;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;
import java.security.Timestamp;
import java.util.Date;



public class Test{

	public static void main(String[] args) {
		long timestamp = new Date().getTime();
		System.out.println(timestamp);
		if (args == null) {
			System.out.println("\n\n #### RunVertx(Main) class arguement(args) is null, error -1");
			System.exit(-1);
		}

                //OS 종류 확인
		String osName = System.getProperty("os.name");
		System.out.println(" - OS Name: " + osName);
		int length = args.length;
		System.out.println(" - Arguement length: " + length);
		StringBuilder sb = new StringBuilder();
		System.out.println(" - StringBuilder capacity: " + sb.capacity());
		System.out.println(" - StringBuilder length: " + sb.length());

		for (String str: args) {
			sb.append(str);
			sb.append(" ");
		}

		System.out.println(" - StringBuilder length: " + sb.length());
		System.out.println(" - Command: " + sb.toString());
		Process process = null;
		BufferedReader br = null;
		try {
			String[] cmd = null;
			if (osName.toLowerCase().startsWith("window")) {
                                // OS 가  Windows 일때 명령어 라인 생성
				cmd = new String[]{"cmd.exe", "/y", "/c", sb.toString()};

			} else {
				cmd = new String[]{"/bin/sh", "-c", sb.toString()};
			}

                        // 콘솔 명령 실행
			process = Runtime.getRuntime().exec(cmd);

                        // 실행 결과 확인 (에러) 
			br = new BufferedReader(new InputStreamReader(process.getErrorStream(),"EUC-KR"));

			System.out.print("\n ## ERROR : ");
			String line = null;
			while ((line = br.readLine())!= null) {
				System.out.println(line);
			}
			br = null;

                        // 실행 결과 확인

			br = new BufferedReader(new InputStreamReader(process.getInputStream()));

				

			System.out.print("\n ## RESULT: ");

			line = null;

			while ((line = br.readLine())!= null) {

				System.out.println(line);

			}
                        // 프로세스의 수행이 끝날때까지 대기

                       process.waitFor();

		} catch (IOException e) {

			e.printStackTrace();

		} catch(Exception e2) {
			
		}

		

		sb.delete(0, sb.length());

		sb.setLength(0);

		sb = null;



	}

} 



