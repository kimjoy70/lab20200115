
package com.network2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.StyledDocument;


import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
public class TalkClient extends JFrame implements ActionListener, Serializable{
	Socket mySocket = null;
	ObjectInputStream ois  = null;
	ObjectOutputStream oos = null;	
	//JTextArea jta_display 	= new JTextArea();
	//JTextPane에 스타일을 적용하기 위해서는 스타일을 지원하는 클래스를 추가로 매핑하여
	//사용해야 한다.
	//왜냐하면 문자의 경우도 그리는 개념으로 이해해야 하므로 글꼴을 변경시키거나 글크기를 변경하는
	//부분도 반영하려면 꼭 필요
	StyledDocument sd_display = 
						new DefaultStyledDocument(
								new StyleContext());
	Image i;
	String imgPath = "C:\\dev_kosmo20181101\\dev_java\\com\\book\\image\\";
	ImageIcon icon = new ImageIcon(imgPath+"booklogo.jpg");
	JLabel jlb_bg = new JLabel(icon);
	JTextPane   jtp_display     = new JTextPane(sd_display);
	JScrollPane jsp_display 
				= new JScrollPane(jtp_display,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
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
	//색상 정보를 넘길 때 Color객체를 사용해야하지만 통신시 Object를 넘길 수 없어서
	//String 타입으로 색상값을 넘겨야 함.
	String fontColor = "0";	
	JFrame jf_fontColor = null;
	JTableHeader jth_nick = jtb_nick.getTableHeader();
	PictureMessage  pm = new PictureMessage(this);
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
			//100|나초보
			oos.writeObject(Protocol.ROOM_IN
					       +Protocol.seperator+nickName
                		   +Protocol.seperator+fontColor);
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
		//이모티콘 
		jbtn_emoticon.addActionListener(this);
		//대화명 변경 버튼 이벤트 매핑 - 콜백함수(actionPerformed)가 호출됨
		jbtn_change.addActionListener(this);
		jbtn_send.setBorderPainted(false);
		jbtn_send.setFocusPainted(false);
		i = Toolkit.getDefaultToolkit().getImage(imgPath+"googlelogo.png");
		//JTable 헤더 변경
		jth_nick.setBackground(Color.red);
		jth_nick.setForeground(Color.WHITE);
		jtb_nick.setGridColor(Color.red);
		jtp_display.setEditable(false);
		jtp_display.setFocusable(false);
		//글자색 변경버튼
		jbtn_fontColor.addActionListener(this);
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
	public void message_process(String msg) {
		if(msg==null ||msg.length()==0){
			msg = "이모티콘";
			try {
				oos.writeObject(Protocol.MESSAGE
						+Protocol.seperator+nickName
						+Protocol.seperator+msg
						+Protocol.seperator+fontColor
						+Protocol.seperator+pm.imgChoice);
			} catch (Exception e2) {
				System.out.println(e2.toString());//힌트문 출력하기
			}
		}
		else {//메시지가 있을 때
			try {
				oos.writeObject(Protocol.MESSAGE
						+Protocol.seperator+nickName
						+Protocol.seperator+msg
						+Protocol.seperator+fontColor
						+Protocol.seperator+"default");
			} catch (Exception e2) {
				System.out.println(e2.toString());//힌트문 출력하기
			}
		}
		jtf_msg.setText("");		
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
		if(obj == jbtn_emoticon) {
			pm.setVisible(true);
		}
		else if(obj == jbtn_change) {//너 대화명 변경할려구?
			//누구님의 대화창에 대한 변경은 run메소드(4번:this.setTitle(afterName+"님의 대화창"))에서 처리하자.
			String afterName = 
					JOptionPane.showInputDialog("변경할 대화명을 입력하세요.");
			//만일 사용자가 스페이스바만 입력하였다. -다시 입력하도록 요구하기 - return
			if(afterName == null || afterName.trim().length()<3) {
				JOptionPane.showMessageDialog
				(this, "변경할 대화명을 똑바로 입력하세요.", "INFO"
			   , JOptionPane.INFORMATION_MESSAGE);
				return;//actionPerformed 탈출, break-반복문을 탈출
			}
			//메시지 전송
			//주의사항-StringTokenizer으로 끊어서 처리할 때 갯수 맞추기
			//발생상황:서버측 로그에는 출력이 되었는데 클라이언트측에는 메시지가 출력되지 않음.
			try {
				oos.writeObject(Protocol.CHANGE+Protocol.seperator
						       +nickName+Protocol.seperator
						       +afterName+Protocol.seperator
						       +nickName+"님의 대화명이 "+afterName+"으로 변경");
			} catch (Exception e2) {
			//에러메시지도 JVM이 관리한다.-stack관리(배낭)
			//stack에 쌓인 에러메시지를 출력함.- Line Number출력 - 힌트	
				e2.printStackTrace();//기억해요..........
			}
		}///////////////end of 대화명 변경
		else if(obj==msg || obj==jtf_msg) {
			message_process(msg);
/*			if(msg==null ||msg.length()==0){
				msg = "이모티콘";
			}	
			try {
				oos.writeObject(Protocol.MESSAGE
						       +Protocol.seperator+nickName
						       +Protocol.seperator+msg
						       +Protocol.seperator+fontColor
				               +Protocol.seperator+pm.imgChoice);
			} catch (Exception e2) {
				System.out.println(e2.toString());//힌트문 출력하기
			}
			jtf_msg.setText("");*/
		}
		else if(obj==jbtn_exit) {
			exitChat();
			//상수 0을 주면 자바가상머신과의 연결고리를 끊게됨.
			//어플 종료 - 메모리가 모두 회수됨.
			System.exit(0);
		}
		else if(obj == jbtn_fontColor) {
			jf_fontColor = new JFrame();
			jf_fontColor.setSize(600, 500);
			//색상정보 담긴 팔레트 역할 - JTable
			JColorChooser jc_color = new JColorChooser();
			//색상정보값을 Object화
			ColorSelectionModel model = jc_color.getSelectionModel();
			ChangeListener listener = new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					Color c_fontColor = jc_color.getColor();
					fontColor=String.valueOf(c_fontColor.getRGB());
				}
			};
			//색상 팔레트에서 선택한 색상이 변경될때 마다 이벤트를 감지해서 색상정보를 변경해줌.
			model.addChangeListener(listener);
			jf_fontColor.add(jc_color);
			jf_fontColor.setVisible(true);
		}//////////////////end of 글자색
	}

}









