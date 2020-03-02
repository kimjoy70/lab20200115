package thread.bank;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;

public class BankTimeServerThread extends Thread {
	BankTimeServer bts = null;
	ObjectInputStream ois  = null;
	ObjectOutputStream oos = null;	
	String msg = null;
	public BankTimeServerThread(BankTimeServer bts) {
		this.bts = bts;
		try {
			oos = new ObjectOutputStream
					(bts.client.getOutputStream());			
			ois = new ObjectInputStream
						(bts.client.getInputStream());
			//while(true) {
				msg = bts.getTimeStr();
				oos.writeObject(msg);
				
				for(BankTimeServerThread btst:bts.globalList) {
					//JOptionPane.showMessageDialog(bts, "34:msg:"+msg);
					this.send(msg);
				}
				//JOptionPane.showMessageDialog(bts, "38:msg:"+msg);
				bts.globalList.add(this);
				//JOptionPane.showMessageDialog(bts, "40:msg:"+msg);
				this.broadCasting(msg);		

			//}
			
		} catch (Exception e) {
			System.out.println("BankTimeServerThread:"+e.getMessage()+","+e.toString());
		}		
	}//////////////end of ServerBankThread
	public void broadCasting(String msg) {
		JOptionPane.showMessageDialog(bts, "서버:사람수:"+bts.globalList.size());
		synchronized(this) {
			for(BankTimeServerThread tst:bts.globalList) {
				tst.send(msg);
			}
		}
	}//////////////end of broadCasting 전체에 방송
	private void send(String msg) {
		try {
			oos.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	public void run() {
		while(true) {
			try {
				msg = bts.getTimeStr();
				oos.writeObject(msg);
				try {
					sleep(1000);
					//bts.jta_log.append(msg+"\n");
				} catch(InterruptedException i) {}				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
