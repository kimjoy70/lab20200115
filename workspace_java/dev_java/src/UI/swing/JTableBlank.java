package UI.swing;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


public class JTableBlank extends JFrame {
	//DataSet의 역할을 수행하는 DefaultTableModel을 먼저 선언하고 초기화 하기
	private DefaultTableColumnModel dtcm;
	private DefaultListSelectionModel dlsm;
	private TableColumn tc;
	DefaultTableModel dtm_nick = new DefaultTableModel(1,2); 
	JTable 			  jtb_nick = new JTable(dtm_nick);
	JScrollPane 	  jsp_nick = new JScrollPane(jtb_nick
			                                    ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			                                    ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);	
	public JTableBlank() {
		dtcm = new DefaultTableColumnModel();
		tc = new TableColumn();
		tc.setHeaderValue("이름");
		dtcm.addColumn(tc);
		this.setLayout(null);
		this.setTitle("자바채팅 ver.1");
		this.setSize(350, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add("Center",jsp_nick);
		this.setVisible(true);
		
	}
	public static void main(String[] args) {
		new JTableBlank();
	}

}
