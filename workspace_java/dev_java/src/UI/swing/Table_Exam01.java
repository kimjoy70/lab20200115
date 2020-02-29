package UI.swing;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

class Table_Exam01_UI extends JFrame
{
	private JRootPane jrp = null;
	private Container con = null;
	private JPanel jp = null;
	private JTable jt = null;
	private JScrollPane jsp = null;
	private String[][] str = {{"1_1", "1_2", "1_3", "1_4"}, {"2_1", "2_2", "2_3", "2_4"}, {"3_1", "3_2", "3_3", "3_4"}};
	private String[] str1 = {"1번", "2번", "3번", "4번"};
	public Table_Exam01_UI(){
		super("Table Exam!!!");
		jrp = this.getRootPane();
		con = jrp.getContentPane();
//		jt = new JTable();
//		jt = new JTable(3,4);
		jt = new JTable(str,str1);
		jsp = new JScrollPane(jt);
		con.setLayout(new BorderLayout(5,5));
		con.add("North",new JLabel("JTable 입니다.",JLabel.CENTER));
		jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jp.add(new JButton("확인"));
		jp.add(new JButton("취소"));
		con.add("South",jp);
		con.add("Center",jsp);
		this.setSize(300,200);
		this.setVisible(true);
	}
}
public class Table_Exam01 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Table_Exam01_UI teu = new Table_Exam01_UI();
	}

}

