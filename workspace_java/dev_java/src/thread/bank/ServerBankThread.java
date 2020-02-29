package thread.bank;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;

public class ServerBankThread extends Thread {
	ServerBank sBank = null;
	ObjectInputStream ois  = null;
	ObjectOutputStream oos = null;	
	String nickName = null;
	String msg = null;
	public ServerBankThread(ServerBank sBank) {
		this.sBank = sBank;
		try {
			oos = new ObjectOutputStream
					(sBank.client.getOutputStream());			
			ois = new ObjectInputStream
						(sBank.client.getInputStream());
			msg = (String)ois.readObject();
			//JOptionPane.showMessageDialog(sBank, "msg:"+msg);
			sBank.jta_log.append(msg+"\n");
			sBank.jta_log.setCaretPosition(sBank.jta_log.getDocument().getLength());
			StringTokenizer st = null;
			if(msg!=null) {
				 st = new StringTokenizer(msg,Protocol.seperator);
			}
			st.nextToken();//100
			nickName = st.nextToken();//닉네임
			login(nickName);
			/*
			//JOptionPane.showMessageDialog(sBank, "nickName:"+nickName);
			for(ServerBankThread sbt:sBank.globalList) {
				String currentName = sbt.nickName;
				//ts.jta_log.append("for문 스레드 tst"+ ":"+tst+", this:"+this+"\n");
			    //this를 사용할 때와 tst를 사용할 때 차이점에 대해서 생각해 보세요.	
				this.send(Protocol.ROOM_IN
						 +Protocol.seperator+currentName);
			}
			sBank.globalList.add(this);
			this.broadCasting(msg);		
			*/
		} catch (Exception e) {
			System.out.println("ServerBankThread:"+e.getMessage()+","+e.toString());
		}		
	}//////////////end of ServerBankThread
	public void broadCasting(String msg) {
		//JOptionPane.showMessageDialog(ts, "서버:사람수:"+ts.globalList.size());
		synchronized(this) {
			for(ServerBankThread tst:sBank.globalList) {
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
	public void login(String mem_id) {
		Map<String,Object> rMap = null; 
		rMap = sBank.cusDao.login(mem_id);
		if(rMap !=null) {
			//JOptionPane.showMessageDialog(sBank, "nickName:"+nickName);
			String currentName = null;
			for(ServerBankThread sbt:sBank.globalList) {
				currentName = sbt.nickName;
				//ts.jta_log.append("for문 스레드 tst"+ ":"+tst+", this:"+this+"\n");
				//this를 사용할 때와 tst를 사용할 때 차이점에 대해서 생각해 보세요.	
				this.send(Protocol.ROOM_IN
						+Protocol.seperator+currentName);
			}
			Vector<Object> v = new Vector<>();
			v.add(getTimeStr());
			v.add(nickName);
			v.add(msg);
			v.add("로그인");
			sBank.dtm_history.addRow(v);
			sBank.globalList.add(this);
			this.broadCasting(msg);	
			
		}
		else {
			String currentName = null;
			for(ServerBankThread sbt:sBank.globalList) {
				currentName = sbt.nickName;
				//ts.jta_log.append("for문 스레드 tst"+ ":"+tst+", this:"+this+"\n");
				//this를 사용할 때와 tst를 사용할 때 차이점에 대해서 생각해 보세요.	
				this.send(Protocol.ROOM_NO
						+Protocol.seperator+currentName+"님은 로그인 실패입니다.");
			}
			sBank.globalList.add(this);
			this.broadCasting(Protocol.ROOM_NO
					+Protocol.seperator+nickName+"님은 로그인 실패입니다.");			
		}
	}
	@Override
	public void run() {
		boolean isStop = false;
		try {
			run_start://while문 같은 반복문 전체를 빠져 나가도록 처리할때
			while(!isStop) {
				String msg = (String)ois.readObject();
				sBank.jta_log.append(msg+"\n");
				sBank.jta_log.setCaretPosition(sBank.jta_log.getDocument().getLength());
				int protocol = 0;
				StringTokenizer st = null;
				if(msg!=null) {
					st = new StringTokenizer(msg,Protocol.seperator);
					protocol = Integer.parseInt(st.nextToken());
				}
				//msg==> 200|누가|뭐라고?
				switch(protocol) {
				
				//종료하기에 대한 처리구현
				case Protocol.ROOM_OUT:{
					String nickName = st.nextToken();
					//500 메시지를 전송한 스레드를 globalList에서 제거 한다.
					sBank.globalList.remove(this);//tst
					String message = Protocol.ROOM_OUT
							        +Protocol.seperator+nickName;
					this.broadCasting(message);
				}break run_start;//break뒤에 라벨문을 사용하면 while문 블럭전체를 빠져 나옴.
				}
			}/////////end of while
		} catch (Exception e) {
			// TODO: handle exception
		}
	}/////////////////end of run	
	private String getTimeStr() {
		Calendar cal = Calendar.getInstance();
		int yyyy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);

		return yyyy+"-"+mm+"-"+day+" "+
		        (hour < 10 ? "0" + hour : "" + hour) + ":" +
				(min < 10 ? "0" + min : "" + min)  +	":" +
				(sec < 10 ? "0" + sec : "" + sec) ;
	}	
}
