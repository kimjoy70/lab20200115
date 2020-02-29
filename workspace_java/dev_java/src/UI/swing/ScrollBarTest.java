package UI.swing;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JScrollBar;

public class ScrollBarTest extends JFrame{
	private static final String SwingConstants = null;
	private JRootPane jrp;
	private Container con;
	//private JScrollBar jsb = new JScrollBar(SwingConstants.HORIZONTAL), 50 ,30, 0, 100);
	ScrollBarTest(){
		this.init();
		this.start();
		this.setSize(500,400);
		this.setVisible(true);
	}
	public void init(){
		jrp = this.getRootPane();
		con = jrp.getContentPane();
		con.setLayout(new BorderLayout());
		//con.add("North",jsb);
	}
	public void start(){
		
	}
	public static void main(String[] args) {
		ScrollBarTest sbt = new ScrollBarTest();
	}

}
