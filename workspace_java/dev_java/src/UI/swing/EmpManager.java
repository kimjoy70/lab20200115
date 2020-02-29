package UI.swing;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class EmpManager extends JFrame implements ActionListener{
	JRootPane jrp = this.getRootPane();
	Container jcp = this.getContentPane();
	JDesktopPane jdp = new JDesktopPane();	
	JPanel jp_north = new JPanel();
	JButton jbtn_emp = new JButton("사원목록");
	JButton jbtn_dept = new JButton("부서목록");
	JButton jbtn_sal = new JButton("급여목록");
	JButton jbtn_diligence = new JButton("근태목록");
	public EmpManager() {
		jbtn_emp.addActionListener(this);
		jbtn_dept.addActionListener(this);
		jbtn_sal.addActionListener(this);
		jbtn_diligence.addActionListener(this);
		jp_north.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp_north.add(jbtn_emp);
		jp_north.add(jbtn_dept);
		jp_north.add(jbtn_sal);
		jp_north.add(jbtn_diligence);
		this.add("North",jp_north);
		this.add("Center",jdp);
		this.setSize(600, 500);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new EmpManager();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == jbtn_emp) {
			InnerFrame inn = new InnerFrame("사원목록",true,true,true,true);
			jdp.add(inn);			
		}
		else if(obj == jbtn_dept) {
			InnerFrame inn = new InnerFrame("부서목록",true,true,true,true);
			jdp.add(inn);			
		}
		else if(obj == jbtn_sal) {
			InnerFrame inn = new InnerFrame("급여목록",true,true,true,true);
			jdp.add(inn);			
		}
		else if(obj == jbtn_diligence) {
			InnerFrame inn = new InnerFrame("근태목록",true,true,true,true);
			jdp.add(inn);			
		}
	}

}
class InnerFrame extends JInternalFrame{
	private String[][] str = {{"1_1", "1_2", "1_3", "1_4"}, {"2_1", "2_2", "2_3", "2_4"}, {"3_1", "3_2", "3_3", "3_4"}};
	private String[] str1 = {"1번", "2번", "3번", "4번"};
	private JTable jt = new JTable(str,str1);
	private JScrollPane jsp = new JScrollPane(jt);
	InnerFrame(String str,boolean a, boolean b, boolean c, boolean d){
		super(str,a, b, c, d);
		this.setTitle(str);
		this.setSize(300,200);
		this.setVisible(true);
	}
}