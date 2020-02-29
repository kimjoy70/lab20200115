package com.network2;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TalkClientViewTest extends JFrame {
	ImageIcon ig = new ImageIcon("main.png");
	class MyPanal2 extends JPanel {
		public void paintComponent(Graphics g) {
			System.out.println("MyPanal2");
			g.drawImage(ig.getImage(), 800, 400, this);
			setOpaque(false);
			super.paintComponents(g);
		}
	}	
	public TalkClientViewTest() {
		setContentPane(new MyPanal2());
		this.setLayout(null);
		this.setTitle("자바채팅 ver.1");
		this.setSize(350, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}


	public static void main(String[] args) {
		new TalkClientViewTest();
	}

}
