package UI.swing;

import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JTextFieldCursor extends JFrame implements TextListener,ActionListener,FocusListener{
	JLabel jlb = new JLabel("주민등록번호");
	TextField jtf1 	= new TextField(6);
	TextField jtf2 	= new TextField(7);
	JTextField jtf3 = new JTextField("안녕");
	int count = 0;
	public JTextFieldCursor() {
		jtf1.addTextListener(this);
		jtf1.addActionListener(this);
		jtf3.addFocusListener(this);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(jlb);
		this.add(jtf1);
		this.add(jtf2);
		this.add(jtf3);
		this.setSize(500, 300);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new JTextFieldCursor();
	}

	@Override
	public void textValueChanged(TextEvent e) {
		count +=1;
		if(count == 6) {
			jtf2.requestFocus();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==jtf1) {
			System.out.println("onclick");
		}
	}
	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource()==jtf3) {
			jtf3.setText("");
		}
	}
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

}
