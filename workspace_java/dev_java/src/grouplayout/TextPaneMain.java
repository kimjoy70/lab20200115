package grouplayout;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class TextPaneMain extends JFrame implements ActionListener {
	JPanel 		jp_first 		= new JPanel();
	//메시지 전송할 때 - 이벤트처리 필요함
	JButton  jbtn_test = new JButton("테스트");
	ChatRight cr = null;
	/*
	 * JTextPane에 스타일을 적용하기 위해서는 스타일을 지원하는 클래스를 추가로 매핑해야함.
	 * 왜냐하면 문자도 그리는 개념으로 이해해야 하므로 글꼴을 변경하거나 글크기를 변경하는
	 * 부분도 반영하려면 필요함
	 */
	StyledDocument sd_display = 
			new DefaultStyledDocument(new StyleContext());
	JTextPane jtp_display = new JTextPane(sd_display);
	//메세지 내역 출력  - 비활성함. -이벤트처리 필요없음
	JScrollPane jsp_display		= new JScrollPane(jtp_display
			,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	String path = "C:\\workspace_java\\dev_java\\src\\grouplayout\\";
    private ImageIcon icon1 = new ImageIcon(path+"jbtn1.jpg");
    private ImageIcon icon2 = new ImageIcon(path+"jbtn1_1.jpg");

    private JToggleButton jtb1 = new JToggleButton(icon1);
    private ButtonGroup bg = new ButtonGroup();
    //private JToggleButton jtb2 = new JToggleButton("2",icon2);
    Container con = this.getContentPane();
	public void initDisplay() {
		
		jtb1.setSelectedIcon(icon2); 
		bg.add(jtb1);
		jtb1.addActionListener(this);
		jp_first.setLayout(new BorderLayout());
		jp_first.add("Center",jsp_display);
		con.add("Center",jp_first);
		con.add("South",jtb1);
		//jtb1.setRolloverIcon(icon1);
		this.setSize(500, 600);
		this.setVisible(true);		
	}
	public static void main(String[] args) {
		TextPaneMain tpm = new TextPaneMain();
		tpm.initDisplay();

	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==jtb1) {
			System.out.println("토글버튼");
			try {
				cr = new ChatRight("test");
				cr.jlb_right.setText("메세지");
				jtp_display.insertComponent(cr);
				//jtb1.setSelectedIcon(icon1);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

}
