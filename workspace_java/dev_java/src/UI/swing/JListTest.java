package UI.swing;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JListTest extends JFrame implements ListSelectionListener{
	String listName[] = {"빨강","노랑","파랑"};
	JList list = new JList(listName);
	public JListTest() {
		list.addListSelectionListener(this);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.add("North",list);
		this.setSize(500, 400);
		this.setVisible(true);			
	}
	public static void main(String[] args) {
		new JListTest();
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		System.out.println(list.getSelectedValue());
		
	}

}
