package com.network2;


import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.awt.*; 
public class JTextPaneImage 
{ 
	public static void main(String[] args) { 
		JFrame frame = new JFrame(""); 
		final MyTextPane textPane = new MyTextPane(); 
		//DefaultCaret caret = (DefaultCaret)textPane.getCaret();
		//caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
//		JScrollPane jsp = new JScrollPane(textPane,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollPane jsp = new JScrollPane();
		jsp.setViewportView(textPane); 
		jsp.setOpaque(false);
		frame.add(jsp); 
		//frame.pack();
		frame.setSize(350, 600);
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true); 
	} 
	private static class MyTextPane extends JTextPane { 
		String imgPath="C:\\workspace_java\\dev_java\\src\\image\\";
		ImageIcon iicon = new ImageIcon(imgPath+"main.png");
		public MyTextPane() { 
			super(); 
			setText("Hello World"); 
			setOpaque(false); // this is needed if using Nimbus L&F - see http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6687960 
			setBackground(new Color(0,0,0,0)); 
		} 
		@Override 
		protected void paintComponent(Graphics g) { 
			// set background green - but can draw image here too 
			g.setColor(Color.GREEN); 
			//g.fillRect(0, 0, getWidth(), getHeight()); 
			// uncomment the following to draw an image // 
			Image img = iicon.getImage(); // 
			g.drawImage(img, 0, 0, this); 
			setOpaque(false);
			super.paintComponent(g); 
		}
	}
}