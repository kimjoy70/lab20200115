package UI.swing;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ClickJList extends JFrame implements ListSelectionListener{
	JList jList = new JList();
	JList jList2 = new JList();
	String imgPath = "C:\\workspace_java\\dev_java\\src\\image\\";
	ImageIcon imo1 = new ImageIcon(imgPath+"imo.png");
	ImageIcon imo2 = new ImageIcon(imgPath+"imo.png");
	ImageIcon imo3 = new ImageIcon(imgPath+"imo.png");
	ImageIcon imo4 = new ImageIcon(imgPath+"imo.png");
	DefaultListModel dlm = new DefaultListModel();
	DefaultListModel dlm2 = new DefaultListModel();
	JScrollPane jsp = new JScrollPane();
	Container ct = this.getContentPane();
	ClickJList(){
		jList.addListSelectionListener(this);
		ct.setLayout(new GridLayout(1,2));
		dlm.addElement("이순신");
		dlm.addElement("이성계");
		dlm.addElement("김유신");
		dlm2.addElement(imo1);
		dlm2.addElement(imo2);
		dlm2.addElement(imo3);
		dlm2.addElement(imo4);
		jList.setModel(dlm);
		jList2.setModel(dlm2);
		jList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ct.add(jList);
		ct.add(jsp);
		jsp.getViewport().add(jList2);
		this.setSize(500, 300);
		this.setVisible(true);
	}
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()) {
			System.out.println("호출 성공");
			return;
		}
		if(!jList.isSelectionEmpty()) {
			Object[] values = jList.getSelectedValues();
			for(int i=0;i<values.length;i++) {
				System.out.println(values[i]+"가 선택되었습니다.");
			}
		}
	}
	public static void main(String[] args) {
		new ClickJList();

	}

}
