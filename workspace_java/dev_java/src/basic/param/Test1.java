package basic.param;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Test1 {

	public static void main(String[] args) {
		JFrame jf = new JFrame();
		//jf.setSize(400, 300);
		//jf.setVisible(true);
		String u_id = JOptionPane.showInputDialog("아이디를 입력하세요.");
		String u_pw = JOptionPane.showInputDialog("비번을 입력하세요.");
		System.out.println("입력받은 아이디 "+u_id);
		System.out.println("입력받은 비밀번호 "+u_pw);
	}

}
