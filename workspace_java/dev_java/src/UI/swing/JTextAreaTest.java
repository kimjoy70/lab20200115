package UI.swing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class JTextAreaTest extends JFrame implements KeyListener {
	JTextArea jta = new JTextArea();
	JScrollPane jsp = new JScrollPane(jta);
	String key = null;
	public JTextAreaTest() {
		jta.addKeyListener(this);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add("Center",jsp);
		this.setSize(500, 400);
		this.setVisible(true);		
	}
	public static void main(String[] args) {
		new JTextAreaTest();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("keyPressed 호출 성공");
		key = "Key Pressed : "+e.getKeyChar();
		System.out.println(key+" : "+e.getKeyCode());
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
