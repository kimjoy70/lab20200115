package com.instance;

import javax.swing.JFrame;

public class JFrameTest {
	JFrame jf = null;
	public JFrameTest() {
		JFrameTest1 jft1 = new JFrameTest1(this);
		initDisplay();
	}
	public void initDisplay() {
		jf = new JFrame();
		jf.setSize(500, 400);
		jf.setVisible(true);
	}
	public static void main(String[] args) {
		new JFrameTest();
	}

}
