package thread.bank;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import design.book.TimeServerVer2;

public class BankTimeServer extends JFrame implements Runnable{
	//선언부
	static Thread th = null;
	ServerSocket server = null;
	Socket client = null;
	JTextArea jta_log = new JTextArea();
	JScrollPane jsp_log = new JScrollPane(jta_log
			,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	BankTimeServerThread btst = null;
	List<BankTimeServerThread> globalList = null;
	public BankTimeServer() {
		initDisplay();
		th = new Thread(this);
		th.start();		
	}
	public void initDisplay() {
			this.setTitle("BankTimeServer 로그");
			this.add("Center",jsp_log);
			this.setSize(500, 400);
			this.setVisible(true);
	}
	@Override
	public void run() {
		globalList = new Vector<BankTimeServerThread>();
		boolean isStop = false;
		try {
			server = new ServerSocket(3005);
			while(!isStop) {
				client = server.accept();
				jta_log.append(client.toString()+"\n");
				btst = new BankTimeServerThread(this);
				btst.start();//run메소드를 자동(JVM)으로 호출함. - 콜백메소드		
				/*
				ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
				while(true) {
					if(oos!=null) {
						oos.writeObject(getTimeStr());
					}
					try {
						th.sleep(1000);
					} catch(InterruptedException i) {}
				}
				*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		BankTimeServer bts = new BankTimeServer();
		/*
		bts.initDisplay();
		th = new Thread(bts);
		th.start();
		*/
	}
	// getTimeStr() 시작
	public String getTimeStr() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);

		return (hour < 10 ? "0" + hour : "" + hour) + ":" +
				(min < 10 ? "0" + min : "" + min)  +	":" +
				(sec < 10 ? "0" + sec : "" + sec) ;
	}
}
