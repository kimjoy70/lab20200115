package blockchain_Step1;

import java.io.IOException;

public class RuntimeTest {
	public static void main(String[] args) throws IOException, InterruptedException {
		Runtime rt = Runtime.getRuntime();
		Process pc = null;
		try {
			//외부 프로세스 실행
			pc = rt.exec("C:\\Program Files\\Microsoft Office\\root\\Office16\\Excel.exe");
			System.out.println("MicroSoft Excel Excute!!");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//명령어 종료시 까지 대기
			pc.waitFor(); 
			//명령어 종료시 하위 프로세스 제거
			pc.destroy();
		}
	}
}
