package com.network3;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;


public class TalkServerThread extends Thread {
	TalkServer ts = null;
	ObjectInputStream ois  = null;
	ObjectOutputStream oos = null;
	String nickName = null;//톡방에 입장한 사람의 대화명 담기
	String g_title = null;
	int g_current = 0;//현재인원수 담기
	//Room room = new Room();
	public TalkServerThread(TalkServer ts) {
		this.ts = ts;
		try {
			oos = new ObjectOutputStream
					(ts.client.getOutputStream());			
			ois = new ObjectInputStream
						(ts.client.getInputStream());
			String msg = (String)ois.readObject();
			ts.jta_log.append(msg+"\n");
			ts.jta_log.setCaretPosition(ts.jta_log.getDocument().getLength());
			StringTokenizer st = null;
			if(msg!=null) {
				 st = new StringTokenizer(msg,"|");
			}
			if(st.hasMoreTokens()) {
				st.nextToken();//100
				nickName = st.nextToken();//닉네임
				g_title =st.nextToken();
				ts.jta_log.append("위치:"+g_title+"\n");
			}
			for(TalkServerThread tst:ts.globalList) {
				String currentName = tst.nickName;
				String currentState = tst.g_title;
			//this를 사용할 때와 tst를 사용할 때 차이점에 대해서 생각해 보세요.	
				this.send(Protocol.WAIT
						 +Protocol.seperator+currentName
				         +Protocol.seperator+currentState);
			}///////////end of 대화목록관리
			//for(TalkServerThread tst:ts.globalList) {
			for(int i=0;i<ts.roomList.size();i++) {//방갯수
				Room room = ts.roomList.get(i);
				String title = room.title;
				g_title = title;
				int current = 0;
				if(room.userList!=null && room.userList.size()!=0) {
					current = room.userList.size();
				}
				g_current = current;
				this.send(Protocol.ROOM_LIST
						 +Protocol.seperator+g_title
				         +Protocol.seperator+g_current);
			}///////////end of 대화방관리
			//}///////////end of 사용자 수만큼 반복해줌.
			ts.globalList.add(this);
			this.broadCasting(msg);
		} catch (Exception e) {
			System.out.println("TalkServerThread:"+e.getMessage()+","+e.toString());
		}
	}
	public void broadCasting(String msg) {
		//JOptionPane.showMessageDialog(ts, "서버:사람수:"+ts.globalList.size());
		synchronized(this) {
			for(TalkServerThread tst:ts.globalList) {
				tst.send(msg);
			}
		}
	}//////////////end of broadCasting 전체에 방송
	public void roomCasting(String msg) {
        synchronized(this) {
            for(int i = 0; i < ts.roomList.size(); i++ ) {
               Room  room  = (Room)ts.roomList.get(i);
               if(g_title.equals(room.title)) {
                  for(int j = 0; j < room.userList.size(); j++ ){
                     TalkServerThread  tst = (TalkServerThread)room.userList.get(j);
                     try{
                          tst.send(msg);
                     }catch(Exception ex) {
                         room.userList.remove(j--);
                     }
                   }//for j ended
                   break;
                }
             }//for i ended
            }
	}//////////////end of broadCasting 전체에 방송
    protected void broadcast(String msg) {
        synchronized(this) {
         for(int i = 0; i < ts.roomList.size(); i++ ) {
            Room  room  = (Room)ts.roomList.get(i);
            if(g_title.equals(room.title)) {
               for(int j = 0; j < room.userList.size(); j++ ){
                  TalkServerThread  tst = (TalkServerThread)room.userList.get(j);
                  try{
                       tst.send(msg);
                  }catch(Exception ex) {
                      room.userList.remove(j--);
                  }
                }//for j ended
                break;
             }
          }//for i ended
         }
       }//broadcast method ended	
	private void send(String msg) {
		try {
			oos.writeObject(msg);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	public void run() {
		boolean isStop = false;
		try {
			run_start://while문 같은 반복문 전체를 빠져 나가도록 처리할때
			while(!isStop) {
				String msg = (String)ois.readObject();
				ts.jta_log.append(msg+"\n");
				ts.jta_log.setCaretPosition(ts.jta_log.getDocument().getLength());
				int protocol = 0;
				StringTokenizer st = null;
				if(msg!=null) {
					st = new StringTokenizer(msg,"|");
					protocol = Integer.parseInt(st.nextToken());
				}
				//msg==> 200|누가|뭐라고?
				switch(protocol) {
				case Protocol.ROOM_CREATE:{
					String roomTitle = st.nextToken();
					String currentNum = st.nextToken();
					Room room = new Room(roomTitle,Integer.parseInt(currentNum));
//					room.setTitle(roomTitle);
//					room.setCurrent(Integer.parseInt(currentNum));
					ts.roomList.add(room);
					this.broadCasting(Protocol.ROOM_CREATE
					         +Protocol.seperator+roomTitle
					         +Protocol.seperator+currentNum);
				}break;
				case Protocol.ROOM_IN:{
					String roomTitle = st.nextToken();
					String nickName = st.nextToken();
					StringBuffer names = new StringBuffer();
					String temp = null;
					for(int i=0;i<ts.roomList.size();i++) {
						Room room = ts.roomList.get(i);
						if(roomTitle.equals(room.title)) {
							g_title = roomTitle;
							g_current =room.current+1;
							room.setCurrent(g_current);
							
							room.userList.add(this);
							room.nameList.add(nickName);
							//JOptionPane.showMessageDialog
							//(ts, "S-ROOM_IN 내가 추가된 스레드수:"+room.userList.size()+",이름수:"+room.nameList.size());
							for(int j=0;j<room.nameList.size();j++) {
								names.append(room.nameList.get(j)+"#");
								JOptionPane.showMessageDialog(ts, "S-ROOM_IN 내가 추가된 이름들1:"+names.toString());
								if(j==room.nameList.size()-1) {
									temp = names.toString();
									temp= temp.substring(0, temp.length()-1);
								}
							}
							//JOptionPane.showMessageDialog(ts, "S-ROOM_IN 내가 추가된 이름들2:"+temp);
/*							broadCasting(Protocol.ROOM_IN
									    +Protocol.seperator+g_title
									    +Protocol.seperator+g_current
									    +Protocol.seperator+this.nickName
							            +Protocol.seperator+temp);
							
							for(TalkServerThread tst:ts.globalList) {
								String currentName = tst.nickName;
								String currentState = tst.g_title;
							//this를 사용할 때와 tst를 사용할 때 차이점에 대해서 생각해 보세요.	
								this.send(Protocol.WAIT
										 +Protocol.seperator+currentName
								         +Protocol.seperator+currentState);
							}///////////end of 대화목록관리
							ts.globalList.add(this);
							this.broadCasting(msg);	*/						
						}////////////end of if
					}////////////////end of for
					for(int i=0;i<ts.roomList.size();i++) {//방갯수
						Room room = ts.roomList.get(i);
						String title = room.title;
						g_title = title;
						int current = 0;
						if(room.userList!=null && room.userList.size()!=0) {
							current = room.userList.size();
						}
						JOptionPane.showMessageDialog(ts, "S-ROOM_IN 이전에 있던 친구들한테:"+nickName+", this.nickName:"+this.nickName);
						for(int j=0;j<room.nameList.size();j++) {
							if(!nickName.equals(room.nameList.get(j))) {
								TalkServerThread tst = room.userList.get(j);
								tst.send(Protocol.ROOM_INLIST
										+Protocol.seperator+g_title
										+Protocol.seperator+g_current
										+Protocol.seperator+nickName);
							}
						}
					}///////////end of 대화방관리
					broadcast(Protocol.ROOM_IN
				    +Protocol.seperator+g_title
				    +Protocol.seperator+g_current
				    +Protocol.seperator+this.nickName
		            +Protocol.seperator+temp);
				}break;
				case Protocol.MESSAGE:{
					String nickName = st.nextToken();
					String message = st.nextToken();
					this.broadCasting(Protocol.MESSAGE
							         +Protocol.seperator+nickName
							         +Protocol.seperator+message);
				}break;
				//종료하기에 대한 처리구현
				case Protocol.ROOM_OUT:{
					String nickName = st.nextToken();
					//500 메시지를 전송한 스레드를 globalList에서 제거 한다.
					ts.globalList.remove(this);//tst
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
}




