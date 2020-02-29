package UI.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRootPane;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.*;

class Excel_Test2 extends JInternalFrame
{
	private JRootPane jrp;
	private Container con;
	private DefaultTableModel dtm = new DefaultTableModel(100,27);//100행 27열
	private DefaultTableColumnModel dtcm = new DefaultTableColumnModel();
	private DefaultListSelectionModel dlsm = new DefaultListSelectionModel();
	private JTable jt = new JTable(dtm,dtcm,dlsm);
	private JScrollPane jsp = new JScrollPane(jt);
	private TableColumn[] tc = new TableColumn[27];
	//첫번째 로우에는 값을 입력못하도록 그리고 색상을 지정해준다.
	private DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
	private JTextField jtf = new JTextField();
	private DefaultCellEditor dce = new DefaultCellEditor(jtf);
	private JTableHeader jth = new JTableHeader();
	
	public Excel_Test2(String str,boolean a, boolean b, boolean c, boolean d){
		super(str,a,b,c,d);
		this.init();
		this.start();
		this.setSize(200,150);
		this.setVisible(true);
			
	}
	public void init(){
		jrp = this.getRootPane();
		con = jrp.getContentPane();
		con.setLayout(new BorderLayout());
		jtf.setEnabled(false);
		tc[0] = new TableColumn(0,75,dtcr,dce);
		tc[0].setHeaderValue("번호");
		dtcr.setBackground(Color.lightGray);
		//헤더에 출력하는 문자열 중앙정렬
		dtcr.setHorizontalAlignment(JLabel.CENTER);
		dtcm.addColumn(tc[0]);
		
		//가로줄의 맨상단 헤더값 설정
		for(int i =1; i< 27;i++){
			tc[i] = new TableColumn(i,75);
			tc[i].setHeaderValue((char)(i+64)+"");
			dtcm.addColumn(tc[i]);
		}
		jth = new JTableHeader(dtcm);
		jth.setReorderingAllowed(false);//컬럼들이 이동할 수 없게함.
		jt.setTableHeader(jth);
		//이 설정을 주어야 한 화면에 보여주는 셀의 갯수설정
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jt.setCellSelectionEnabled(true);
		//세로줄의 맨앞쪽 로우수 설정
		for(int i=0;i<100;i++){
			jt.setValueAt(String.valueOf(i+1), i, 0);
		}
		con.add("Center",jsp);
		
	}
	public void start(){
		
	}
}

public class Excel_Test1 extends JFrame implements ActionListener{
	private JRootPane jrp;
	private Container con;
	//메뉴바생성
	private JMenuBar jmb = new JMenuBar();
	private JMenu file = new JMenu("파일");
	private JMenuItem fnew = new JMenuItem("New");
	private JMenuItem fexit = new JMenuItem("Exit");
	private JToolBar jtb = new JToolBar();
	private JButton jbt = new JButton("새창");
	private JButton jbtl = new JButton("종료");
	private JLabel jlb = new JLabel("Status : ");
	private JDesktopPane jdp = new JDesktopPane();
	private static int num = 0;
	Excel_Test1()
	{
		super("Excel Form");
		this.init();
		this.start();
		this.setSize(600,450);
		Dimension di = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dil = this.getSize();
		this.setLocation((int)(di.getWidth() / 2 - dil.getWidth() /2),
				         (int)(di.getHeight() / 2 - dil.getHeight() / 2));
		this.setVisible(true);
		
	}
	public void init(){
		jrp = this.getRootPane();
		con = jrp.getContentPane();
		con.setLayout(new BorderLayout());
		jrp.setJMenuBar(jmb);
		jmb.add(file);
		file.add(fnew);
		file.add(fexit);
		jtb.add(jbt);
		jtb.add(jbtl);
		con.add("North",jtb);
		jlb.setBorder(new BevelBorder(BevelBorder.RAISED));
		con.add("South",jlb);
		con.add("Center",jdp);
		
	}
	public void start(){
		fnew.addActionListener(this);
		jbt.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == fnew || e.getSource() == jbt){
			Excel_Test2 et2 = new Excel_Test2("제목"+ ++num,true,true,true,true);
			jdp.add(et2,new Integer(0));
			
		}
	}
	public static void main(String[] args) {
		Excel_Test1 et1 = new Excel_Test1();

	}

}
