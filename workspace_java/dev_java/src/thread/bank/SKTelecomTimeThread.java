package thread.bank;

import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class SKTelecomTimeThread extends Thread {
	SKTelecom sk = null;
	public SKTelecomTimeThread(SKTelecom sk) {
		this.sk = sk;
	}

	public void run() {
		String msg = null;
		boolean isStop = false;
		while(!isStop) {//무한루프 방지코드를 꼭 추가하자 - 변수처리하자, 조건식을 활용하자
			try {
				//100|나초보
				msg = (String)sk.tois.readObject();
				//sk.jta_cus.append(msg+"\n");
				if(msg!=null) {
					sk.jlb_time.setText(msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}/////////////////end of run
}
