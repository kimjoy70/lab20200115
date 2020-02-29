package UI.swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.*;

public class Test_Exam extends JFrame implements ActionListener{

	private JRootPane jrp;
	private Container con;
	private JLabel jlb = new JLabel("성적처리 인원수:" , JLabel.RIGHT);
	private JLabel jlb1 = new JLabel("명",JLabel.LEFT);
	private JTextField jtf = new JTextField();
	private JButton jbt = new JButton("만들기");
	private JButton jbt1 = new JButton("연산");
	private JButton jbt2 = new JButton("종료");
	private JButton jbtn_add = new JButton("3");
	private JButton jbtn_clear = new JButton("지우기");
	private JOptionPane optionDlg = null;
	//center영역에 들어갈 정보. center영역에 테이블이 생성되는 시점은 학생수를 입력하고 나서 엔터키를 
	//누르거나 만들기 버튼을 클릭했을 때 생성되어야 하므로 선언만 한다.
	/*
	 * TableModel을 구현한 것들은 이러한 데이터가 어떻게 저장되었는지 뿐만 아니라 어떻게 데이터를 덧붙이고
	 * ,조작하고
	 * 얻어내는지에 대해서도 명시한다. 
	 * 또한 TableModel은 특정셀이 편집될 수 있는지, 각 열의 데이터 타입은 무엇인지를 알아내는 역할도 한다.
	 * JTable의 TableModel에 있는 데이터의 위치는 JTable이 화면에 디스플레이 되었을 때의 위치와 일치하지는 않는다.
	 * DefaultTableModel은 JTable의 생성자에서 아무런 모델도 지정해 주지 않았을 때 쓰이는 클래스이다.
	 */
	private DefaultTableModel dtm;
	/*
	 * TableColumnModel은 TableColumn의 인스턴스를 관리하기 위해 디자인 되었다.
	 * TableColumn은 TableModel 데이터의 한 열을 표현하여, 실제 JTable GUI에 보여지는 열을 관리하는데
	 * 필요한 것이다.
	 */
	private DefaultTableColumnModel dtcm;
	/*
	 * TableColumnModel은 열의 순서, 선택, 여백 등 TableColumn들을 관리한다. 열을 선택하는 방법을
	 * 여러 가지로 지원하기 위해, TableColumnModel은 ListSelectionModel을 유지한다.
	 */
	private DefaultListSelectionModel dlsm;
	private JTable jt; // = new JTable();
	private JScrollPane jsp; // = new JScrollPane(jt);
	
	//학생수를 입력 받을 변수 선언
	private int num = 0;
	/*
	 * TableColumn은 JTable 에서 화면에 보여지는 것의 기본적인 단위로 데이블의 모델과 JTable GUI 사이를 
	 * 연결해주는
	 * 가장 중요한 부분이다.
	 * 이것은 JTable에 보여지는 열의 프로퍼티들을 관리하는 모델과 같다.
	 * JTable에서 TableColumn의 위치와 TableModel의 열 인덱스가 항상 일치하지는 않는다것을 명심해야 한다.
	 */
	private TableColumn tc,tc1,tc2,tc3,tc4,tc5,tc6,tc7,tc8;
	/*
	 * 이 클래스는 TableCellRender 인터페이스를 구현한 디폴트 클래스 이다.
	 * DefaultTableCellRender는 JLabel을 상속받고 Number, Icon, Object 데이터 타입들을 위한 디폴트 클래스
	 * 기반 렌더러로 사용된다.
	 */
	private DefaultTableCellRenderer dtcr,dtcr1,dtcr2,dtcr3,dtcr4,dtcr5,dtcr6,dtcr7,dtcr8;
	private JTextField jtf0,jtf1,jtf2,jtf3,jtf4,jtf5,jtf6,jtf7;
	private JCheckBox[] jcbs;
	/*
	 * DefaultCellEditor는 TableCellEditor 인터페이스와 TreeCellEditor 인터페이스를 모두 구현한다.
	 * 이 에디터는 셀을 편집하기 위해 JTextField, JComboBox, 또는 JCheckBox를 반환하도록 디자인 되었다.
	 */
	private DefaultCellEditor dce,dce1,dce2,dce3,dce4,dce5,dce6,dce7,dce8;
	/*
	 * 테이블의 열 헤더를 표시하기 위해 사용한다.
	 * 각각의 JTable은 JTableHeader 인스턴스를 1개씩 갖는다.
	 */
	private JTableHeader jth;
	int order=0;
	Test_Exam(){
		super("성적계산");
		jtf.setHorizontalAlignment(JTextField.RIGHT);
		this.init();
		this.start();
		this.setSize(300,200);
		optionDlg = new JOptionPane();
		Dimension di = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension di1 = this.getSize();
		this.setLocation((int)(di.getWidth() /2 - di1.getWidth() /2),
				        (int)(di.getHeight() /2 - di1.getHeight() /2));
		this.setVisible(true);
	}
	
	public void init(){
		jrp = this.getRootPane();
		con = jrp.getContentPane();
		con.setLayout(new BorderLayout());
		dtcr8 = new DefaultTableCellRenderer()
		{
			public Component getTableCellRendererComponent  // 셀렌더러
			(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
			{
				JCheckBox box= new JCheckBox();
				box.setSelected(((Boolean)value).booleanValue());   
				box.setHorizontalAlignment(JLabel.CENTER);
				return box;
			}
		};
		JPanel jp = new JPanel(new BorderLayout());
		jp.add("West",jlb);
		jp.add("Center",jtf);
		JPanel jpl = new JPanel(new BorderLayout());
		jpl.add("West",jlb1);
		jpl.add("Center",jbt);
		jp.add("East",jpl);
		con.add("North",jp);
		JPanel jp2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jp2.add(jbt1);//연산버튼
		jp2.add(jbt2);//종료버튼
		jp2.add(jbtn_add);
		jp2.add(jbtn_clear);
		con.add("South",jp2);
		//con.add("Center",jsp);
	}
	public void start(){
		jtf.addActionListener(this);//텍스트 필드에 숫자를 입력했을 때
		jbt.addActionListener(this);//만들기 버튼을 눌렀을 때
		jbt1.addActionListener(this);//연산버튼 
		jbt2.addActionListener(this);//종료버튼
		jbtn_add.addActionListener(this);//샘플추가
		jbtn_clear.addActionListener(this);//지우기
	}
		
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == jtf || e.getSource() == jbt){
			//테이블을 구성하고 화면에 뿌려준다.
			try{
				num = Integer.parseInt(jtf.getText().trim());
			}catch(NumberFormatException ee){
				optionDlg.showMessageDialog(this, "숫자만 입력하세요", "ERROR", JOptionPane.ERROR_MESSAGE);
				jtf.setText("");
				jtf.requestFocus();
				return;
			}
			jtf.setEnabled(false);//숫자를 더 이상 입력하지 못하게
			jbt.setEnabled(false);//만들기 버튼도 더이상 클릭하지 못하게
			//체크박스 추가하기
			jcbs = new JCheckBox[num];
			for(int i=0;i<jcbs.length;i++){
				jcbs[i] = new JCheckBox();
			}
			dtm = new DefaultTableModel(num,9);//이름, 국어,영어,수학,총점,평균,학점,순위
			dtcm = new DefaultTableColumnModel();
			dlsm = new DefaultListSelectionModel();
			jt = new JTable(dtm,dtcm,dlsm);
		    //jt.getColumn("선택").setCellRenderer(dtcr8);
		    JCheckBox box = new JCheckBox(); 
		    box.setHorizontalAlignment(JLabel.CENTER);
		    //jt.getColumn("선택").setCellEditor(new DefaultCellEditor(box));
			jsp = new JScrollPane(jt);
			//dce8.isCellEditable(e);
			//tc8 = new TableColumn(0, 60, dtcr8, dce8);
			//tc8.setHeaderValue("선택");
			//dtcm.addColumn(tc8);
			//이름
			dtcr = new DefaultTableCellRenderer();
			jtf0 = new JTextField();
			jtf0.setEditable(true);
			DefaultCellEditor dce = new MyCellEditor(jtf);
			tc = new TableColumn(1, 120, dtcr, dce);
			tc.setHeaderValue("이름");
			dtcm.addColumn(tc);
			//국어
			dtcr1 = new DefaultTableCellRenderer();
			jtf1 = new JTextField();
			jtf1.setEditable(true);
			dce1 = new MyCellEditor(jtf1);
			tc1 = new TableColumn(2, 75, dtcr1, dce1);
			tc1.setHeaderValue("국어");
			dtcm.addColumn(tc1);
			//영어
			dtcr2 = new DefaultTableCellRenderer();
			jtf2 = new JTextField();
			jtf2.setEditable(true);
			dce2 = new MyCellEditor(jtf2);
			tc2 = new TableColumn(3, 75, dtcr2, dce2);
			tc2.setHeaderValue("영어");
			dtcm.addColumn(tc2);
			//수학
			dtcr3 = new DefaultTableCellRenderer();
			jtf3 = new JTextField();
			jtf3.setEditable(true);
			dce3 = new MyCellEditor(jtf3);
			tc3 = new TableColumn(4, 75, dtcr3, dce3);
			tc3.setHeaderValue("수학");
			dtcm.addColumn(tc3);
			//총점
			dtcr4 = new DefaultTableCellRenderer();
			jtf4 = new JTextField();
            jtf4.setEditable(false);
            dce4 = new MyCellEditor(jtf4);
			tc4 = new TableColumn(5, 75, dtcr4, dce4);
			tc4.setHeaderValue("총점");
			dtcm.addColumn(tc4);
			//평균
			dtcr5 = new DefaultTableCellRenderer();
			jtf5 = new JTextField();
			jtf5.setEditable(false);
			dce5 = new MyCellEditor(jtf5);
			tc5 = new TableColumn(6, 100, dtcr5, dce5);
			tc5.setHeaderValue("평균");
			dtcm.addColumn(tc5);
			//학점
			dtcr6 = new DefaultTableCellRenderer();
			jtf6 = new JTextField();
			jtf6.setEditable(false);
			dce6 = new MyCellEditor(jtf6);
			tc6 = new TableColumn(7, 50, dtcr6, dce6);
			tc6.setHeaderValue("학점");
			dtcm.addColumn(tc6);
			//석차
			dtcr7 = new DefaultTableCellRenderer();
			jtf7 = new JTextField();
			jtf7.setEditable(false);
			dce7 = new MyCellEditor(jtf7);
			tc7 = new TableColumn(8, 50, dtcr7, dce7);
			tc7.setHeaderValue("석차");
			dtcm.addColumn(tc7);			
			jth = new JTableHeader(dtcm);
			jth .setReorderingAllowed(false);
			jt.setTableHeader(jth);
			
			con.add("Center",jsp);
			con.validate();
			this.pack();
			Dimension di = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension di1 = this.getSize();
			this.setLocation((int)(di.getWidth() /2 - di1.getWidth() /2),
					           (int)(di.getHeight() /2 - di1.getHeight() /2));
		}else if(e.getSource() == jbt1){
			//성적을 처리해서 결과를 뿌려준다.
			jt.clearSelection();
			int[][] imsi = new int[num][2];//총점과 석차가 들어갈 공간
			for(int i=0;i<num;i++){
				int tot = Integer.parseInt((String)dtm.getValueAt(i, 1)) 
				         + Integer.parseInt((String)dtm.getValueAt(i, 2))
				         + Integer.parseInt((String)dtm.getValueAt(i, 3));
				float avg = tot / 3.0f;
				char ch = 0 ;//학점
				switch((int)(avg / 10)){
					case 10:
					case 9:  ch = 'A';  break;
					case 8:  ch = 'B';  break;
					case 7:  ch = 'C';  break;
					case 6:  ch = 'D';  break;
					default: ch = 'F';
				}
				dtm.setValueAt(String.valueOf(tot), i, 4);
				dtm.setValueAt(String.valueOf(avg), i, 5);
				dtm.setValueAt(ch + "", i, 6);
				imsi[i][0] = tot;
				imsi[i][1] = 1;//석차를 1로 설정
			}
			//석차를 매겨줌
			for(int i=0;i<num; i++){
				for(int j=0;j<num;j++){
					if(imsi[i][0] < imsi[j][0]){
						imsi[i][1]++;
					}
				}
			}
			for(int i=0;i<num;i++){
				dtm.setValueAt(String.valueOf(imsi[i][1]), i, 7);
			}
		}else if(e.getSource() == jbt2){
			System.exit(0);
		}else if(e.getSource() == jbtn_add){
			String[][] data = {
					 {"홍길동","80","75","85"}
					,{"이성계","90","85","80"}
					,{"강감찬","70","75","70"}
			};
			for(int i=0;i<3;i++){
				for(int j=0;j<4;j++){
					dtm.setValueAt(data[i][j], i, j);
				}
			}
		}else if(e.getSource() == jbtn_clear){
			for(int i=0;i<num;i++){
				for(int j=0;j<=8;j++){
					dtm.setValueAt("", i, j);
				}
			}
		}
	}
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		Test_Exam te = new Test_Exam();
	}

}
class MyCellEditor extends DefaultCellEditor{

	public MyCellEditor(JTextField jtf) {
		super(jtf);
	}
	public MyCellEditor(JCheckBox jcb) {
		super(jcb);
	}
	
}