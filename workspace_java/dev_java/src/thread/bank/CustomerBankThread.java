package thread.bank;

import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class CustomerBankThread extends Thread {
	CustomerBank cus = null;
	public CustomerBankThread(CustomerBank cus) {
		this.cus = cus;
	}

	public void run() {
		String msg = null;
		boolean isStop = false;
		while(!isStop) {//무한루프 방지코드를 꼭 추가하자 - 변수처리하자, 조건식을 활용하자
			try {
				//100|나초보
				msg = (String)cus.ois.readObject();
				cus.jta_cus.append(msg+"\n");
				StringTokenizer st  = null;
				int protocol = 0;
				if(msg!=null) {
					st = new StringTokenizer(msg,"#");
					protocol = Integer.parseInt(st.nextToken());
				}
				switch(protocol) {
					case Protocol.ROOM_NO:{
						String nickName = st.nextToken();
						cus.jta_cus.append(nickName+"\n");
					}break;
					case Protocol.ROOM_IN:{
						String nickName = st.nextToken();
						cus.jta_cus.append(nickName+"님이 입장하였습니다.\n");
						cus.setAccountBank();
					}break;
				}//////////////end of switch
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
