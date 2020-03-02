package thread.bank;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ServerBank extends JFrame implements Runnable{
	ServerSocket server = null;
	Socket       client = null;
	JTextArea 	jta_log = new JTextArea(12,30);
	JScrollPane jsp_log = new JScrollPane(jta_log
			                             ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			                             ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//일종의 속지임
	String cols[] = {"접속시간","접속자","메시지","상태"};
	String data[][] = new String[0][4];
	DefaultTableModel dtm_history = new DefaultTableModel(data,cols);
	JTable jtb_history = new JTable(dtm_history);
	JTableHeader jth_history = jtb_history.getTableHeader();
	JScrollPane jsp_history = new JScrollPane(jtb_history);
	ServerBankThread sbt = null;
	List<ServerBankThread> globalList = null;
	CustomerDao cusDao = new CustomerDao(this);
	public void initDisplay() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				try {
					server.close();
					client.close();
					System.exit(0);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		this.add("West",jsp_log);
		jtb_history.getColumnModel().getColumn(0).setPreferredWidth(180);
		jtb_history.getColumnModel().getColumn(1).setPreferredWidth(90);
		jtb_history.getColumnModel().getColumn(2).setPreferredWidth(300);
		jtb_history.getColumnModel().getColumn(3).setPreferredWidth(80);
		this.add("Center",jsp_history);
		this.setTitle("서버 로그 출력....");
		this.setSize(1100, 500);
		this.setVisible(true);		
	}
	public static void main(String[] args) {
		new BankTimeServer();
		ServerBank sb = new ServerBank();
		sb.initDisplay();
		new Thread(sb).start();
	}

	@Override
	public void run() {
		globalList = new Vector<ServerBankThread>();
		boolean isStop = false;
		try {
			server = new ServerSocket(3002);
			while(!isStop) {
				client = server.accept();//은수소켓저장(은수컴에 포트번호 할당)
				jta_log.append(client.toString()+"\n");//은수포트번호 출력테스트
				//스레드 생성하기
				//파라미터로 TalkServer를 넘기는 건 소켓은 TalkServer에 선언했는데
				//사용은 TalkServerThread에서도 사용가능해야 하니깐......
				sbt = new ServerBankThread(this);
				sbt.start();//run메소드를 자동(JVM)으로 호출함. - 콜백메소드
			}
		} catch (Exception e) {
			System.out.println("[[ServerBank]]"+e.toString());
		}		
	}

}
