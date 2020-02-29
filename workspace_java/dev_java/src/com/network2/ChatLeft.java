package com.network2;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.net.MalformedURLException;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;



public class ChatLeft extends JPanel{
	public JLabel jlb_left;
	JLabel jlb_right;
	public JLabel jlb_leftimg;
	JLabel jlb_rightimg;
	JPanel chat_left;
	JPanel chat_right;
	public JLabel jlb_time;
	JLabel jlb_check;
	public JLabel jlb_fid;
	public String pic;
	public String friend_id;
	public String yourprofile = "./image/";
	//TalkMainController tmCtrl = new TalkMainController();
	Font f = new Font("맑은 고딕",Font.PLAIN,10);
	public ImageIcon youricon;// = new ImageIcon(yourprofile+pic);
	public List<ChatMemberVO> mvo = null;
	 
	public ChatLeft(String id, List<ChatMemberVO> mvo) throws MalformedURLException {
			//ts.private_idList
			this.mvo = mvo;
			for(int i=0; i<mvo.size();i++) {
				if(mvo.get(i).getMem_id().equals(id))
				this.friend_id = mvo.get(i).getMem_id();				
			}
			
			ChatMemberVO rmvo = new ChatMemberVO();
		     rmvo.setCommand("select");
		     //rmvo = tmCtrl.sendMyInfo(friend_id);
		     jlb_fid=new JLabel();
//		     jlb_fid.setText(" "+rmvo.getNickname());
		     jlb_fid.setText(" "+"무사");
//		     this.pic = rmvo.getPro_picture();
		     this.pic = "lion22.png";
		  //  this.pic = mvo.get(i)
		    youricon = new ImageIcon(yourprofile+pic);
			jlb_time = new JLabel();//시간
			jlb_leftimg = new JLabel();
			jlb_rightimg = new JLabel();
			chat_left = new ChatLeftBubble();
			
			jlb_left = new JLabel();
			jlb_check = new JLabel();
			Image originImg = youricon.getImage();
			Image resizedImg = originImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			ImageIcon resizedIcon = new ImageIcon(resizedImg);
			jlb_leftimg.setIcon(resizedIcon);
	        this.setBackground(new Color(254,231,134));
	        this.setAlignmentX(SwingConstants.LEFT);
	        /////////////////LEFT BUBBLE/////////////////////////////
	        jlb_left.setText("Hi, How are you?");
	        jlb_time.setText("13:00");
	        jlb_check.setText("");
	        jlb_check.setFont(f);
	        jlb_check.setForeground(Color.red);
	 
	        GroupLayout chat_leftLayout = new GroupLayout(chat_left);
	        chat_left.setLayout(chat_leftLayout);
	        chat_leftLayout.setHorizontalGroup(
	            chat_leftLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(chat_leftLayout.createSequentialGroup()
	            .addGap(31, 31, 31)//말풍선 안에서 말풍선과 문장 시작부분 gap
	            .addComponent(jlb_left)
	            .addGap(25, 25, 25)	 
	           )//말풍선 안에서 말풍선끝부분 gap
	                       
	        );
	        chat_leftLayout.setVerticalGroup(
	            chat_leftLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(chat_leftLayout.createSequentialGroup()
	            		.addGap(6, 6, 6) //말풍선 안에서 말풍선위쪽라인과 텍스트사이 gap 
	            		.addComponent(jlb_left)
	                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE+10, GroupLayout.DEFAULT_SIZE+10)
	                .addGap(6, 6, 6)
	               ) //말풍선 안에서 말풍선위쪽라인과 텍스트사이 gap )
	            //.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	            
	        		);
	        GroupLayout layout = new GroupLayout(this);
	        this.setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addComponent(jlb_fid)    
	            .addGroup(layout.createSequentialGroup()
	                .addGap(10,10,10)
	                .addComponent(jlb_leftimg)
	                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
	                .addComponent(chat_left, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
	            .addContainerGap()
	            .addGap(10,10,10)
	            .addComponent(jlb_time)
	            .addContainerGap()
	            .addGap(6,6,6)
	            .addComponent(jlb_check))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addComponent(jlb_fid)	
	            .addGroup(layout.createSequentialGroup()
	            	.addGap(20,20,20)
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                .addComponent(jlb_leftimg)
	                .addComponent(chat_left, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
	                .addComponent(jlb_time)
		            .addComponent(jlb_check))   
	             .addContainerGap()
	             .addGap(18, 18, 18))
	        );
	    }
	    public static void main(String[] args) {
 }
	}
