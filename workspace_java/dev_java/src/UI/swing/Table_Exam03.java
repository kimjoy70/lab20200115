package UI.swing;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.text.NumberFormat;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

class Table_Exam03_UI extends JFrame
{
	private JRootPane jrp = null;
	private Container con = null;
	public JPanel jp = null;
	private JTable jt = null;
	private JScrollPane jsp = null;
	String imgPath = "C:\\workspace_java\\dev_java\\src\\image\\";
	ImageIcon imo1 = new ImageIcon(imgPath+"imo.png");
	ImageIcon imo2 = new ImageIcon(imgPath+"imo.png");
	ImageIcon imo3 = new ImageIcon(imgPath+"imo.png");
	ImageIcon imo4 = new ImageIcon(imgPath+"imo.png");
	private Object[][] str = {
								{"김유신", new Integer(4500000), imo1, new Boolean(true)},
								{"이순신", new Integer(80000000), imo2, new Boolean(true)},
								{"홍길동", new Integer(6700000), imo3, new Boolean(false)}
							 };
	private String[] str1 = {"1번", "2번", "3번", "4번"};
	JComboBox jcb = new JComboBox();
	JCheckBox jce = new JCheckBox("결혼여부",true);
	public Table_Exam03_UI(){
		jrp = this.getRootPane();
		con = jrp.getContentPane();
		con.setLayout(new BorderLayout());
		DefaultTableModel dtm = new DefaultTableModel(str,str1);
		jt = new JTable(dtm);
		jsp = new JScrollPane();
		jt.setRowHeight(30);
		jcb.addItem(imo1);
		jcb.addItem(imo2);
		jcb.addItem(imo3);
		
		jt.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(jcb));
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer() {
			public void setValue(Object value) {
				if(value instanceof Integer)
				{
					this.setText(NumberFormat.getNumberInstance().format(value));
				}
			}
		};
		dtcr.setHorizontalAlignment(JLabel.RIGHT);
//		jt.getColumnModel().getColumn(3).setCellRenderer(dtcr);
		jt.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(jce));
		con.add("North",new JLabel("JTable 입니다.",JLabel.CENTER));
		jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jp.add(new JButton("확인"));
		jp.add(new JButton("취소"));
		con.add("South",jp);
		con.add("Center",jsp);
		jsp.getViewport().add(jt);
		
		this.setSize(300,200);
		this.setVisible(true);
	}
}
public class Table_Exam03 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Table_Exam03_UI teu = new Table_Exam03_UI();
	}

}

