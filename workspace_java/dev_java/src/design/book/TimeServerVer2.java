package design.book;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TimeServerVer2 extends JFrame implements Runnable {
	//선언부
	static Thread th = null;
	ServerSocket server = null;
	Socket client = null;
	JTextArea jta_log = new JTextArea();
	JScrollPane jsp_log = new JScrollPane(jta_log
			,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	public void initDisplay() {
			this.add("Center",jsp_log);
			this.setSize(500, 400);
			this.setVisible(true);
	}
	@Override
	public void run() {
		boolean isStop = false;
		try {
			server = new ServerSocket(3000);
			while(!isStop) {
				client = server.accept();
				jta_log.append(client.toString()+"\n");
				ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
				while(true) {
					if(oos!=null) {
						oos.writeObject(getTimeStr());
					}
					try {
						th.sleep(1000);
					} catch(InterruptedException i) {}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		TimeServerVer2 tsv = new TimeServerVer2();
		tsv.initDisplay();
		th = new Thread(tsv);
		th.start();
	}
	// getTimeStr() 시작
	private String getTimeStr() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);

		return (hour < 10 ? "0" + hour : "" + hour) + ":" +
				(min < 10 ? "0" + min : "" + min)  +	":" +
				(sec < 10 ? "0" + sec : "" + sec) ;
	}
}
