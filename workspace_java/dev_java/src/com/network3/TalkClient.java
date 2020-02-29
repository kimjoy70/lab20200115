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
	JPanel jp_first 		= new JPanel();//���� ��ġ�� ������Ʈ ���
	JPanel jp_first_south   = new JPanel();//BorderLayout
	JTextField jtf_msg 		= new JTextField();//Center
	JButton jbtn_send 		= new JButton("����");//East
	JPanel jp_second 		= new JPanel();//���� �߰��ϴ� ������Ʈ ���-JTable - Center:JTable, South:��ư 6��
	JPanel jp_second_south 	= new JPanel();//���� �߰��ϴ� ������Ʈ ���-JButton 6�� - GridLayout(3,2)
	JButton jbtn_whisper	= new JButton("1:1");//East
	JButton jbtn_change		= new JButton("��ȭ����");//East
	JButton jbtn_fontColor	= new JButton("���ڻ�");//East
	JButton jbtn_emoticon	= new JButton("�̸�Ƽ��");//East
	JButton jbtn_blank 		= new JButton("");//East
	JButton jbtn_exit 		= new JButton("����");//East
	String cols[] = {"��ȭ��"};
	String data[][] = new String[0][1];
	//DataSet�� ������ �����ϴ� DefaultTableModel�� ���� �����ϰ� �ʱ�ȭ �ϱ�
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
		nickName = JOptionPane.showInputDialog("���� ��ȭ����?");
		initDisplay();
		try {
			//����� ������ �� �ִ�.-wait - try...catch�ؾ���.
			mySocket = new Socket(ip,port);
			oos = new ObjectOutputStream
					(mySocket.getOutputStream());			
			ois = new ObjectInputStream
						(mySocket.getInputStream());
			//��� ���� ���
			Room room = new Room();
			room.setTitle("����Ʈ������� ����SW�����Ͼ�");
			room.current = 10;
			room.state = "���";
			//100|���ʺ�
//			oos.writeObject(Protocol.ROOM_IN+Protocol.seperator+nickName+Protocol.seperator+room.getTitle());
			oos.writeObject(Protocol.ROOM_IN
					       +Protocol.seperator+nickName
					       +Protocol.seperator+room.state);
			TalkClientThread tct = new TalkClientThread(this);
			tct.start();//TalkClientThread�� runȣ���.-�ݹ��Լ�
		} catch (Exception e) {
			System.out.println(e.toString());//���� ��Ʈ�� ���.
		}
	}
	//������ ��� ���� �̺�Ʈ ���� ������ �� �����Ƿ� ���� �����ϵ��� �޼ҵ�� ������ ����.
	public void exitChat() {
		try {
			oos.writeObject(500+"|"+this.nickName);
		} catch (Exception e) {
			//stack�޸� ������ �׿��ִ� ���� �޽������� ���������� ������ְ� ���ι�ȣ�� ���� �޽��� ���
			e.printStackTrace();//�� �������.- ��Ʈ�� ����ϴ� �޼ҵ��ε� �̷±����� ���, ���ι�ȣ�� ���� ���
		}
	}
	public void initDisplay() {
		jbtn_exit.addActionListener(this);
		jtf_msg.addActionListener(this);
		jbtn_send.addActionListener(this);
		//JFrame�� ���̾ƿ��� GridLayout����
		this.setLayout(new GridLayout(1,2));
		//ù��° ���ǿ� ���� ������Ʈ ��ġ
		jp_first.setLayout(new BorderLayout());
		jp_first_south.setLayout(new BorderLayout());
		jp_first.add("Center",jsp_display);
		jp_first_south.add("Center",jtf_msg);
		jp_first_south.add("East",jbtn_send);
		jp_first.add("South",jp_first_south);
		//�ι�° ���ǿ� �߰�  ������Ʈ ��ġ
		jp_second.setLayout(new BorderLayout());
		jp_second_south.setLayout(new GridLayout(3,2));
		jp_second.add("Center", jsp_nick);
		jp_second_south.add(jbtn_whisper);//1:1
		jp_second_south.add(jbtn_change);//��ȭ����
		jp_second_south.add(jbtn_fontColor);//���ڻ�
		jp_second_south.add(jbtn_emoticon);//�̸�Ƽ��
		jp_second_south.add(jbtn_blank);//��ũ
		jp_second_south.add(jbtn_exit);//����
		jp_second.add("South",jp_second_south);
		this.add(jp_first);//ù��° ����
		this.add(jp_second);//�ι�° ����
		this.setTitle(nickName+"���� ��ȭâ");
		this.setSize(500, 400);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		TalkClient tc = new TalkClient();
	}
	/*******************************************************************
	 * TalkClient�� �̺�Ʈ ó�������� ���ϱ⸸�� �����ϰ� �˴ϴ�.
	 * ���⼭ ���� ������ �ݵ�� ������ �����մϴ�. - TalkServerThread�� run()���� ��� ���ϱ� ó����. 
	 * @param e
	 ******************************************************************/
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();//�̺�Ʈ�� ������ ��ư�̳� ����Ű������ ������Ʈ�� �ּҹ���
		String msg = jtf_msg.getText();
		if(obj==msg || obj==jtf_msg) {
			try {
				oos.writeObject(Protocol.MESSAGE
						       +Protocol.seperator+nickName
						       +Protocol.seperator+msg);
			} catch (Exception e2) {
				System.out.println(e2.toString());//��Ʈ�� ����ϱ�
			}
			jtf_msg.setText("");
		}
		else if(obj==jbtn_exit) {
			exitChat();
			//��� 0�� �ָ� �ڹٰ���ӽŰ��� ������� ���Ե�.
			//���� ���� - �޸𸮰� ��� ȸ����.
			System.exit(0);
		}
	}

}









