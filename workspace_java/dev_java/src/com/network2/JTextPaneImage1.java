package com.network2;

/*
 *         button2.setHorizontalTextPosition(SwingConstants.RIGHT); // Text 위치 지정.
        button2.setVerticalTextPosition(SwingConstants.TOP); // Text 위치 지정.
        button2.setMnemonic('b'); // alt + b 단축키 지정.
        button2.setPressedIcon(img2); // 마우스 클릭시 이미지 변화.
        button2.setRolloverIcon(img3); // 마우스 올렸을 경우 이미지 변화.
[출처] 22. JFC 구성 및 일반 클래스 활용|작성자 외계인셩


 */
import javax.swing.*;
import javax.swing.text.DefaultCaret;


import java.awt.*; 
public class JTextPaneImage1 extends JFrame
{
	String imgPath="C:\\workspace_java\\dev_java\\src\\image\\";
	ImageIcon iicon = new ImageIcon(imgPath+"main.png");
	Container con = this.getContentPane();
	public JTextPaneImage1() {
		//setUndecorated(true); 
	    //AWTUtilities.setWindowOpaque(this, false);
		//this.setContentPane(mp);
		final JTextPane textPane = new JTextPane();
		textPane.setOpaque(false);
		JScrollPane jsp = new JScrollPane();

	    JViewport viewport = new JViewport() {
	        @Override
	        protected void paintComponent(Graphics g) {
				Image img = iicon.getImage(); // 
				//Image grayImage = GrayFilter.createDisabledImage(img);
				setOpaque(false);
				Graphics2D gd = (Graphics2D)g;
				gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
				g.drawImage(img, 0, 0, this); 
				super.paintComponent(g); 
	        }
	      };
	      //viewport.setOpaque(false);
	      jsp.setOpaque(true);
	      jsp.setViewport(viewport);
	      jsp.setViewportView(textPane); 
		
		//mp.add("Center",jsp);
		con.add(jsp);
		//frame.pack();
		this.setSize(350, 600);
		//con.setLocationRelativeTo(null); 
		this.setVisible(true); 		
	}

	public static void main(String[] args) { 
		new JTextPaneImage1();
	} 
}