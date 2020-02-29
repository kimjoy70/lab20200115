package com.network3;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class TalkClient extends JFrame implements ActionListener{
	Socket mySocket = null;
	ObjectInputStream ois  = null;
	ObjectOutputStream oos = null;	
	JTextArea jta_display 	= new JTextArea();
	JScrollPane jsp_display 
				= new JScrollPane(jta_display,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
						        , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	JPanel jp_first 		= new JPanel();//기존 배치한 컴포넌트 담기
	JPanel jp_first_south   = new JPanel();//BorderLayout
	JTextField jtf_msg 		= new JTextField();//Center
	JButton jbtn_send 		= new JButton("전송");//East
	JPanel jp_second 		= new JPanel();//새로 추가하는 컴포넌트 담기-JTable - Center:JTable, South:버튼 6개
	JPanel jp_second_south 	= new JPanel();//새로 추가하는 컴포넌트 담기-JButton 6개 - GridLayout(3,2)
	JButton jbtn_whisper	= new JButton("1:1");//East
	JButton jbtn_change		= new JButton("대화명변경");//East
	JButton jbtn_fontColor	= new JButton("글자색");//East
	JButton jbtn_emoticon	= new JButton("이모티콘");//East
	JButton jbtn_blank 		= new JButton("");//East
	JButton jbtn_exit 		= new JButton("종료");//East
	String cols[] = {"대화명"};
	String data[][] = new String[0][1];
	//DataSet의 역할을 수행하는 DefaultTableModel을 먼저 선언하고 초기화 하기
	DefaultTableModel dtm_nick = new DefaultTableModel(data,cols); 
	JTable 			  jtb_nick = new JTable(dtm_nick);
	JScrollPane 	  jsp_nick = new JScrollPane(jtb_nick
			                                    ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			                                    ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	JOptionPane jop = null;
	String ip = "127.0.0.1";
	int port = 3001;
	String nickName = null;
	public TalkClient() {
		nickName = JOptionPane.showInputDialog("너의 대화명은?");
		initDisplay();
		try {
			//통신은 지연될 수 있다.-wait - try...catch해야함.
			mySocket = new Socket(ip,port);
			oos = new ObjectOutputStream
					(mySocket.getOutputStream());			
			ois = new ObjectInputStream
						(mySocket.getInputStream());
			//톡방 정보 담기
			Room room = new Room();
			room.setTitle("스마트웹모바일 응용SW엔지니어");
			room.current = 10;
			room.state = "대기";
			//100|나초보
//			oos.writeObject(Protocol.ROOM_IN+Protocol.seperator+nickName+Protocol.seperator+room.getTitle());
			oos.writeObject(Protocol.ROOM_IN
					       +Protocol.seperator+nickName
					       +Protocol.seperator+room.state);
			TalkClientThread tct = new TalkClientThread(this);
			tct.start();//TalkClientThread의 run호출됨.-콜백함수
		} catch (Exception e) {
			System.out.println(e.toString());//에러 힌트문 출력.
		}
	}
	//종료의 경우 여러 이벤트 에서 재사용할 수 있으므로 재사용 가능하도록 메소드로 구현해 볼것.
	public void exitChat() {
		try {
			oos.writeObject(500+"|"+this.nickName);
		} catch (Exception e) {
			//stack메모리 영역에 쌓여있는 에러 메시지들을 순차적으로 출력해주고 라인번호와 에러 메시지 출력
			e.printStackTrace();//꼭 기억하자.- 힌트를 출력하는 메소드인데 이력까지도 출력, 라인번호도 같이 출력
		}
	}
	public void initDisplay() {
		jbtn_exit.addActionListener(this);
		jtf_msg.addActionListener(this);
		jbtn_send.addActionListener(this);
		//JFrame의 레이아웃을 GridLayout변경
		this.setLayout(new GridLayout(1,2));
		//첫번째 섹션에 기존 컴포넌트 배치
		jp_first.setLayout(new BorderLayout());
		jp_first_south.setLayout(new BorderLayout());
		jp_first.add("Center",jsp_display);
		jp_first_south.add("Center",jtf_msg);
		jp_first_south.add("East",jbtn_send);
		jp_first.add("South",jp_first_south);
		//두번째 섹션에 추가  컴포넌트 배치
		jp_second.setLayout(new BorderLayout());
		jp_second_south.setLayout(new GridLayout(3,2));
		jp_second.add("Center", jsp_nick);
		jp_second_south.add(jbtn_whisper);//1:1
		jp_second_south.add(jbtn_change);//대화명변경
		jp_second_south.add(jbtn_fontColor);//글자색
		jp_second_south.add(jbtn_emoticon);//이모티콘
		jp_second_south.add(jbtn_blank);//블랭크
		jp_second_south.add(jbtn_exit);//종료
		jp_second.add("South",jp_second_south);
		this.add(jp_first);//첫번째 섹션
		this.add(jp_second);//두번째 섹션
		this.setTitle(nickName+"님의 대화창");
		this.setSize(500, 400);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		TalkClient tc = new TalkClient();
	}
	/*******************************************************************
	 * TalkClient의 이벤트 처리에서는 말하기만을 전담하게 됩니다.
	 * 여기서 말한 내용은 반드시 서버를 경유합니다. - TalkServerThread의 run()에서 듣고 말하기 처리함. 
	 * @param e
	 ******************************************************************/
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();//이벤트가 감지된 버튼이나 엔터키에대한 컴포넌트의 주소번지
		String msg = jtf_msg.getText();
		if(obj==msg || obj==jtf_msg) {
			try {
				oos.writeObject(Protocol.MESSAGE
						       +Protocol.seperator+nickName
						       +Protocol.seperator+msg);
			} catch (Exception e2) {
				System.out.println(e2.toString());//힌트문 출력하기
			}
			jtf_msg.setText("");
		}
		else if(obj==jbtn_exit) {
			exitChat();
			//상수 0을 주면 자바가상머신과의 연결고리를 끊게됨.
			//어플 종료 - 메모리가 모두 회수됨.
			System.exit(0);
		}
	}

}









