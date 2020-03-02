package thread.bank;

import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class CustomerBankTimeThread extends Thread {
	CustomerBank cus = null;
	public CustomerBankTimeThread(CustomerBank cus) {
		this.cus = cus;
	}

	public void run() {
		String msg = null;
		boolean isStop = false;
		while(!isStop) {//무한루프 방지코드를 꼭 추가하자 - 변수처리하자, 조건식을 활용하자
			try {
				//100|나초보
				msg = (String)cus.tois.readObject();
				//cus.jta_cus.append(msg+"\n");
				if(msg!=null) {
					cus.jlb_time.setText(msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}/////////////////end of run
}
