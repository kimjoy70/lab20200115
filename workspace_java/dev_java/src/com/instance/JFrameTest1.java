package com.instance;

public class JFrameTest1 {
	JFrameTest jft = null;
	public JFrameTest1(JFrameTest jft) {
		this.jft = jft;
		System.out.println("jft:"+jft);
	}
	public JFrameTest1() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrameTest1 jft1 = new JFrameTest1();
		jft1.jft.initDisplay();
		
	}

}
