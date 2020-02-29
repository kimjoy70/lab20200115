package design.book;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JLabel;

public class TimeClient extends Thread {
	String timeStr = null;
	JLabel jlb_time = null;
	public TimeClient(JLabel jlb_time) {
		this.jlb_time = jlb_time;
	}
	public void run() {
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			//socket = new Socket("192.168.0.24",3000);
			socket = new Socket("192.168.0.70",3000);
			out = new PrintWriter(socket.getOutputStream(),true);
			in = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()));
			while(true) {
				while((timeStr = in.readLine()) != null)
					System.out.println(timeStr);
					jlb_time.setText(timeStr);
				try {
					Thread.sleep(1000);
				} catch(InterruptedException i) {}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void main(String[] args) {
		TimeClient tc = new TimeClient(null);
		tc.start();
	}

}
