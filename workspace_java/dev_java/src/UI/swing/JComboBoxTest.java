package UI.swing;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import com.jdbc.DBConnectionMgr2;

public class JComboBoxTest extends JFrame implements ItemListener{
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	DBConnectionMgr2 dbMgr = new DBConnectionMgr2();
	String depts[] = null;
//	String depts[] = {"영업부","총무부","개발부"};
//	JComboBox jcb_dept = new JComboBox(depts);
	JComboBox jcb_dept = null;
	public JComboBoxTest() {
		depts = getDeptList();
		jcb_dept = new JComboBox(depts);
		jcb_dept.addItemListener(this);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add("North",jcb_dept);
		this.setSize(500, 400);
		this.setVisible(true);
	}
	public String[] getDeptList() {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("SELECT deptno, dname FROM dept");
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			Vector<String> v = new Vector<>();
			for(int i=0;rs.next();i++) {
				String dept = rs.getString("dname");
				v.add(dept);
			}
			depts = new String[v.size()];
			v.copyInto(depts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return depts;
	}
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		new JComboBoxTest();
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		Object obj = e.getSource();
		if(obj==jcb_dept) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				System.out.println(""+jcb_dept.getSelectedIndex()+","+depts[jcb_dept.getSelectedIndex()]);
			}
		}
	}

}
