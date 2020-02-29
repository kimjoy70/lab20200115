package com.network2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.StringTokenizer;


public class TalkServerThread extends Thread {
	TalkServer ts = null;
	ObjectInputStream ois  = null;
	ObjectOutputStream oos = null;
	String nickName = null;//톡방에 입장한 사람의 대화명 담기
	public TalkServerThread(TalkServer ts) {
		this.ts = ts;
		try {
			oos = new ObjectOutputStream
					(ts.client.getOutputStream());			
			ois = new ObjectInputStream
						(ts.client.getInputStream());
			String msg = (String)ois.readObject();
			ts.jta_log.append(msg+"\n");
			ts.jta_log.setCaretPosition(ts.jta_log.getDocument().getLength());
			StringTokenizer st = null;
			if(msg!=null) {
				 st = new StringTokenizer(msg,"|");
			}
			st.nextToken();//100
			nickName = st.nextToken();//닉네임
			String fontColor = st.nextToken();
			for(TalkServerThread tst:ts.chatList) {
				String currentName = tst.nickName;
			//this를 사용할 때와 tst를 사용할 때 차이점에 대해서 생각해 보세요.	
				this.send(Protocol.ROOM_IN
						 +Protocol.seperator+currentName
				         +Protocol.seperator+fontColor);
			}
			ts.chatList.add(this);
			this.broadCasting(msg);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	public void broadCasting(String msg) {
		for(TalkServerThread tst:ts.chatList) {
			tst.send(msg);
		}
	}
	private void send(String msg) {
		try {
			oos.writeObject(msg);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	public void run() {
		boolean isStop = false;
		try {
			run_start://while문 같은 반복문 전체를 빠져 나가도록 처리할때
			while(!isStop) {
				String msg = (String)ois.readObject();
				ts.jta_log.append(msg+"\n");
				ts.jta_log.setCaretPosition(ts.jta_log.getDocument().getLength());
				int protocol = 0;
				//토큰(|)을 기준으로 문자열 잘라서 카운트 한다.
				//갯수를 셀때 protocol은 먼저 썰어내므로 아래서 처리할 때 1을 빼고 계산함.
				//switch문에 조건값으로 활용하기 위해 먼저 잘라낸다.
				StringTokenizer st = null;
				if(msg!=null) {
					st = new StringTokenizer(msg,Protocol.seperator);
					protocol = Integer.parseInt(st.nextToken());
				}
				//msg==> 200|누가|뭐라고?
				switch(protocol) {
				case Protocol.MESSAGE:{//200
					String nickName = st.nextToken();
					String message = st.nextToken();
					String fontColor = st.nextToken();
					String imgChoice = "";
					while(st.hasMoreTokens()){
						imgChoice = st.nextToken();
					}					
					this.broadCasting(Protocol.MESSAGE
							         +Protocol.seperator+nickName
							         +Protocol.seperator+message
							         +Protocol.seperator+fontColor
							         +Protocol.seperator+imgChoice);
				}break;
				//300|nickName|afterName|msg
				case Protocol.CHANGE:{//case문안에 {}넣은 이유는 변수중복선언을 피하기 위해서.
					String nickName = st.nextToken();
					String afterName = st.nextToken();
					String message = st.nextToken();
					//클라이언트측에서 대화명을 변경 신청하였다.
					//서측측에서 기억하고 있는 이름정보를 변경해야할까? 안해도 되나?
					//서버측에서 기억하고 있는 이름은 nickName일까? 아님 afterName일까?
					//insert here
					this.nickName = afterName;//대화명을 afterName으로 변경해서 저장함.
					//방송내보내기(run() - 2번작업완료, 3번 작업완료:broadCasting
					broadCasting(Protocol.CHANGE+Protocol.seperator
							    +nickName+Protocol.seperator
							    +afterName+Protocol.seperator
							    +message);
				}break;
				//종료하기에 대한 처리구현
				case Protocol.ROOM_OUT:{//500
					String nickName = st.nextToken();
					//500 메시지를 전송한 스레드를 chatList에서 제거 한다.
					ts.chatList.remove(this);//tst
					String message = Protocol.ROOM_OUT
							        +Protocol.seperator+nickName;
					this.broadCasting(message);
				}break run_start;//break뒤에 라벨문을 사용하면 while문 블럭전체를 빠져 나옴.
				}
			}/////////end of while
		} catch (Exception e) {
			// TODO: handle exception
		}
	}/////////////////end of run
}




