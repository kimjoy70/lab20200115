package com.address2019;
// TimeClient.java: 서버에 접속해서 1초마다 시간 문자열을 받아서 출력하는 프로그램
import java.net.*;
import java.io.*;

import javax.swing.*;

public class TimeClient extends Thread {

	private JLabel label;
	public TimeClient(){}
	public TimeClient(JLabel label) {
		this.label = label;
	}

	// run() 시작
	public void run() {

		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		String timeStr = null;
   
		try {
			socket = new Socket("localhost", 2008);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while(true) {
				System.out.println("while(true)");
				while((timeStr = in.readLine()) != null)
					System.out.println(timeStr);
					label.setText(timeStr);
					Thread.yield();
				try {
					Thread.sleep(1000);
				} catch(InterruptedException i) {}
			}

		} catch(IOException i) {
			label.setText("타임서버에 접속할 수 없습니다.");
		} finally {
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {}
		}
	}
	// run() 종료
	public static void main(String args[]){
		TimeClient tc = new TimeClient();
		tc.start();
	}

}