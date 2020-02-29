package design.book;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TimeClientVer3 extends JFrame implements Runnable, ActionListener {
	String timeStr = null;
	JPanel jp_south = new JPanel();
	JButton jbtn_cut = new JButton("접속끊기");
	JButton jbtn_con = new JButton("연결");
	JTextField jtf_port = new JTextField(10);
	JLabel jlb_time = null;
	Socket socket = null;
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	static Thread th = null;
	TimeClientVer3(JLabel jlb_time){
		this.jlb_time = jlb_time;
	}
	public void initDisplay() {
		System.out.println("client initDisplay");
		jbtn_cut.addActionListener(this);
		jbtn_con.addActionListener(this);
		jp_south.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp_south.add(jtf_port);
		jp_south.add(jbtn_con);
		jp_south.add(jbtn_cut);
		this.setTitle("TimeClientVer2");
		this.add("North",jlb_time);
		this.add("South",jp_south);
		this.setSize(500, 400);
		this.setVisible(true);
	}
	public void run() {
		System.out.println("client run");
		try {
			socket = new Socket("192.168.0.70",3000);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			while(true) {
				while((timeStr = ois.readObject().toString()) != null) {
					System.out.println(timeStr);
					jlb_time.setText(timeStr);
					try {
						Thread.sleep(1000);
					} catch(InterruptedException i) {}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void main(String[] args) {
		TimeClientVer3 tc3 = new TimeClientVer3(null);
		tc3.initDisplay();
		th = new Thread(tc3);
		th.start();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == jbtn_con) {
			th = null;
			th = new Thread(this);
			th.start();
		}
		else if(obj == jbtn_cut) {
			try {
				if(ois!=null) {
					ois.close();
				}
				if(oos!=null) {
					oos.close();
				}
				if(socket!=null) {
					socket.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

}
