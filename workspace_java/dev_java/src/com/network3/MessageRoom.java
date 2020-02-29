package com.network3;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MessageRoom extends JPanel implements ActionListener{
	TalkClientVer2 tc2 = null;
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
	public MessageRoom() {
	}

	public MessageRoom(TalkClientVer2 tc2) {
		this.tc2 = tc2;
		initDisplay();
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
		this.setSize(500, 400);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
