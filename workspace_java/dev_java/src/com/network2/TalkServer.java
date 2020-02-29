
package com.network2;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/*
 * 서버는 24시간 가동되어야 하고 여러 사용자가 동시에 접속할 수  있다.
 * 이 때 경합이 벌어지므로 스레드가 첨가되면 안정적으로 사용자를 받을 수 있을 것이다.
 * 
 * 자바는 단일 상속만 지원한다.
 * 만일 다른 클래스를 재사용하고 싶을 땐 인터페이스로 보완하도록 지원
 * implements Serializable
 */
public class TalkServer extends JFrame implements Runnable, Serializable {
	private static final long serialVersionUID = -4865946674835353945L;

	ServerSocket server = null;
	Socket       client = null;
	JTextArea 	jta_log = new JTextArea();
	JScrollPane jsp_log = new JScrollPane(jta_log
			                             ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			                             ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//일종의 속지임
	TalkServerThread tst = null;
	List<TalkServerThread> chatList = null;
	public TalkServer() {
		
	}
	public void initDisplay() {
		//클래스에 implements하지 않고 익명클래스로 처리해보기
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				try {
					if(server!=null) server.close();
					//자바가상머신과의 연결고리 끊기
					System.exit(0);
					//가비지컬렉터 호출 System.gc();
					//쓰레기값 대상이면 Candidate상태라 함.
					//Candidate상태가 되는건 언제?
					// A a = new A();  A a = null; 이때 인터셉트 a = new A();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		this.add("Center",jsp_log);
		this.setTitle("서버 로그 출력....");
		this.setSize(500, 400);
		this.setVisible(true);
	}//////////////////end of initDisplay
	//main가 가장 먼저 실행된다.- main메소드도 메인스레드임.
	public static void main(String args[]) {
		TalkServer ts = new TalkServer();
		ts.initDisplay();
		new Thread(ts).start();//내[TalkServer]안에 있는 run메소드 호출됨.
	}/////////////////end of main
	@Override
	public void run() {
		//List는 인터페이스, Vector는 List를 구현하는 구현체 클래스임.
		chatList = new Vector<TalkServerThread>();
		boolean isStop = false;
		try {
			server = new ServerSocket(3001);
			while(!isStop) {
				client = server.accept();//은수소켓저장(은수컴에 포트번호 할당)
				jta_log.append(client.toString()+"\n");//은수포트번호 출력테스트
				//스레드 생성하기
				//파라미터로 TalkServer를 넘기는 건 소켓은 TalkServer에 선언했는데
				//사용은 TalkServerThread에서도 사용가능해야 하니깐......
				tst = new TalkServerThread(this);
				tst.start();//run메소드를 자동(JVM)으로 호출함. - 콜백메소드
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}/////////////////end of run
}







