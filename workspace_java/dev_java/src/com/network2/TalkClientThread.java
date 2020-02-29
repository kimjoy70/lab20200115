package com.network2;

import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class TalkClientThread extends Thread {
	TalkClient tc = null;
	String path = "C:\\Java\\dev_javaB\\dev_java\\src\\image\\";
	ChatRight cr;//말하는 사람이 나일 때 오른쪽 말풍선
	ChatLeft cl; //말하는 사람이 상대방일 때 왼쪽 말풍선
	String tch_nickName = null;
	public TalkClientThread(TalkClient tc) {
		this.tc = tc;
		this.tch_nickName = tc.nickName;
	}
	//글자색상이나 글꼴, 폰트 사이즈 등을 적용하고 싶을 때
	public SimpleAttributeSet makeAttribute(String fontColor) {
		SimpleAttributeSet sas = new SimpleAttributeSet();
		sas.addAttribute(StyleConstants.ColorConstants.Foreground
				       , new Color(Integer.parseInt(fontColor)));
		return sas;
	}
	public void run() {
		String msg = null;
		boolean isStop = false;
		while(!isStop) {//무한루프 방지코드를 꼭 추가하자 - 변수처리하자, 조건식을 활용하자
			try {
				//100|나초보
				msg = (String)tc.ois.readObject();
				StringTokenizer st  = null;
				int protocol = 0;
				if(msg!=null) {
					st = new StringTokenizer(msg,"|");
					protocol = Integer.parseInt(st.nextToken());
				}
				switch(protocol) {
					case Protocol.ROOM_IN:{
						String nickName = st.nextToken();
						//입장하였습니다 글자색을 초록색으로 적용하기
						String fontColor = st.nextToken();
						SimpleAttributeSet sas = makeAttribute(fontColor);
						Vector<String> v_nick = new Vector<String>();
						v_nick.add(nickName);
						tc.dtm_nick.addRow(v_nick);
						tc.jsp_nick.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
							public void adjustmentValueChanged(AdjustmentEvent e) {
								JScrollBar jsb = (JScrollBar)e.getSource();
								jsb.setValue(jsb.getMaximum());
							}
						});
						//tc.jtp_display.append(nickName+"님이 입장하였습니다.\n");
						//예외처리대상 - JDBC API, Network API, Thread, IO
						try {
							//offset-위치, str-메시지, a-attribute속성부여
							//tc.sd_display.insertString(offset, str, a);
							tc.sd_display.insertString(tc.sd_display.getLength()
									                 , nickName+"님이 입장하였습니다.\n"
									                 , sas);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}break;
					case Protocol.MESSAGE:{//200
						String nickName = st.nextToken();
						String message = st.nextToken();
						//메시지 뒤에 Vector와 같은 Object를 넘길 수 있나?
						String fontColor = st.nextToken();
						String imgChoice = st.nextToken();
					    MutableAttributeSet  attr1 = new SimpleAttributeSet();
					    MutableAttributeSet  attr2 = new SimpleAttributeSet();
					    if(!imgChoice.equals("default")){
						    int i=0;
						    for(i=0;i<tc.pm.imgfile.length;i++){
						    	if(tc.pm.imgfile[i].equals(imgChoice)){
						    		JOptionPane.showMessageDialog(tc, "이미지이름:"+tc.pm.imgfile[i]);
						    		StyleConstants.setIcon(attr2,
						    				new ImageIcon(path + tc.pm.imgfile[i]));
						    		try{
						    			tc.sd_display.insertString(tc.sd_display.getLength() , "\n" , attr2);
						    		}catch(Exception ex){}
						    	}
						    }
					    }//////////////////////end of emoticon
						//JOptionPane.showMessageDialog(tc, "style:"+style.length);
						//if(!message.equals("이모티콘")){
						else if(imgChoice.equals("default")){	
							//SimpleAttributeSet sas = makeAttribute(style);
							//tc.jta_display.setLineWrap(true);
							try {
								//JOptionPane.showMessageDialog(tc, "insertString:"+nickName);
								for(int i=0;i<tc.dtm_nick.getRowCount();i++) {
									
									if(tc.dtm_nick.getValueAt(i, 0).equals(nickName)) {//말하는 사람이 나일 때
										cr = new ChatRight(tc.nickName);
										cr.jlb_rightimg.setText(message);
										cr.jlb_time.setText("12:25");
										tc.sd_display.insertString(tc.sd_display.getLength(),"\n", null);
										tc.jtp_display.insertComponent(cr);
										tc.sd_display.insertString(tc.sd_display.getLength(),"\n", null);
									}else {//말하는 사람이 상대일 때
										cl = new ChatLeft(nickName, null);
										cl.jlb_rightimg.setText(message);
										cl.jlb_time.setText("14:55");
										tc.sd_display.insertString(tc.sd_display.getLength(),"\n", null);
										tc.jtp_display.insertComponent(cl);
										tc.sd_display.insertString(tc.sd_display.getLength(),"\n", null);
									}
								}
								//tc.sd_display.insertString(tc.sd_display.getLength(),"["+nickName+"]"+message+"\n", null);
								//tc.textPane.insertEmoText(nickName+ " : "+msg+"\n", sas);					
							} catch (Exception e) {
								JOptionPane.showMessageDialog(tc, "Excepton:"+e.toString());
							}
							tc.jtp_display.setCaretPosition(tc.sd_display.getLength());					
							//tc.jta_display.append("["+nickName+"] "+msg+"\n");
							//tc.jta_display.setCaretPosition(tc.jta_display.getDocument().getLength());
						}//////////////////////end of 이모티콘						
						//tc.jtp_display.append("["+nickName+"]"+message+"\n");
						tc.jtp_display.setCaretPosition(tc.sd_display.getLength());
					}break;	
					//대화명 변경처리 - 클라이언트측 처리 - 변경된 대화명을 dtm_nick변경처리- dtm_nick.setValueAt(what,row,cols);
					case Protocol.CHANGE:{
						String nickName = st.nextToken();
						String afterName = st.nextToken();
						String message = st.nextToken();
						//테이블에 대화명 변경하기 - 여기서 setValueAt()활용할것.
						for(int i=0;i<tc.dtm_nick.getRowCount();i++) {
							//대화명을 변경전에 현재 dtm_nick에서 가져온 대화명을 담는다.
							//왜냐하면 이름을 비교해서 그 값을 afterName으로 바꿔줘야 하니깐.....
							String currentName = (String)tc.dtm_nick.getValueAt(i, 0);
							if(nickName.equals(currentName)) {//서버에서 받아온 nickName과 dtm_nick에 있는 nickName이 같은게 있니?
								tc.dtm_nick.setValueAt(afterName, i, 0);
								break;
							}
							//변경된 대화명 메시지를 출력하기
							try {
								tc.sd_display.insertString
										(tc.sd_display.getLength(), message, null);
							} catch (Exception e) {
								// TODO: handle exception
							}
							//메시지가 추가될때 스크롤바 자동 위치 변경
							tc.jtp_display.setCaretPosition(tc.sd_display.getLength());
							//채팅창의 타이틀도 변경하자.
							if(nickName.equals(tc.nickName)) {
								tc.setTitle(afterName+"님의 대화창");
								tc.nickName = afterName;
							}
						}
					}break;
					case Protocol.ROOM_OUT:{//500
						String nickName = st.nextToken();
						//tc.jtp_display.append(nickName+"님이 퇴장 하였습니다.\n");
						//DefaultTableModel에서 사용자 정보 삭제하기
						for(int i=0;i<tc.dtm_nick.getRowCount();i++) {
							String n1 = (String)tc.dtm_nick.getValueAt(i,0);
							if(n1.equals(nickName)) {
								tc.dtm_nick.removeRow(i);
							}
						}
					}break;
				}//////////////end of switch
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
