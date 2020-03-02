package thread.bank;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
//출금하기 구현
public class WithDrawDlg extends JDialog implements ActionListener{
	JLabel 		jlb_waccNum = new JLabel("출금 계좌번호");
	JTextField 	jtf_waccNum = new JTextField(20);
	JLabel 		jlb_daccNum = new JLabel("입금 계좌번호");
	JTextField 	jtf_daccNum = new JTextField(20);
	JLabel 		jlb_wMsg = new JLabel("출금메시지");
	JTextField 	jtf_wMsg = new JTextField(30);
	JLabel 		jlb_dMsg = new JLabel("입금메시지");
	JTextField 	jtf_dMsg = new JTextField(30);
	JLabel 		jlb_transfer = new JLabel("이체금액");
	JTextField 	jtf_transfer = new JTextField(30);
	JPanel jp_center = new JPanel();
	JPanel jp_south  = new JPanel();
	JButton jbtn_transfer = new JButton("이체");
	JButton jbtn_close 	  = new JButton("닫기");
	JScrollPane jsp = new JScrollPane(jp_center);	
	CustomerBank cBank = null;
	WithDrawDlg(){
	}
	public WithDrawDlg(CustomerBank cBank) {
		this.cBank = cBank;
		initDisplay();
		jtf_daccNum.setText(cBank.dnum);
		jtf_waccNum.setText(cBank.wnum);
	}
	public void initDisplay() {
		jbtn_transfer.addActionListener(this);
		jtf_transfer.setHorizontalAlignment(JTextField.RIGHT);
		jp_center.setLayout(null);
		jp_south.setLayout(new FlowLayout(FlowLayout.CENTER));
		jp_south.add(jbtn_transfer);
		jp_south.add(jbtn_close);
		jlb_waccNum.setBounds(20, 20, 100, 20);
		jtf_waccNum.setBounds(120, 20, 200, 20);
		jlb_daccNum.setBounds(20, 45, 100, 20);
		jtf_daccNum.setBounds(120, 45, 200, 20);
		jlb_transfer.setBounds(20, 70, 100, 20);
		jtf_transfer.setBounds(120, 70, 150, 20);
		jlb_wMsg.setBounds(20, 95, 100, 20);
		jtf_wMsg.setBounds(120, 95, 200, 20);
		jlb_dMsg.setBounds(20, 120, 100, 20);
		jtf_dMsg.setBounds(120, 120, 200, 20);
		jp_center.add(jlb_waccNum);
		jp_center.add(jtf_waccNum);
		jp_center.add(jlb_daccNum);
		jp_center.add(jtf_daccNum);
		jp_center.add(jlb_wMsg);
		jp_center.add(jtf_wMsg);
		jp_center.add(jlb_dMsg);
		jp_center.add(jtf_dMsg);
		jp_center.add(jlb_transfer);
		jp_center.add(jtf_transfer);
		this.setTitle("출금관리");
		this.add("Center",jsp);
		this.add("South",jp_south);
		this.setSize(500, 420);
		this.setVisible(false);
	}	
	public static void main(String[] args) {
		WithDrawDlg wd = new WithDrawDlg();
		wd.initDisplay();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(jbtn_transfer == obj) {
			Map<String,Object> pMap = new HashMap<>();
			pMap.put("waccountnumber", getWaccNum());
			pMap.put("daccountnumber", getDaccNum());
			pMap.put("withdrawmsg", getWMsg());
			pMap.put("depositmsg", getDMsg());
			pMap.put("transfer", getTransfer());
			pMap.put("mem_id",cBank.mem_id);
			cBank.cusDao.dealInsert(pMap);
		}
	}
	public String getWaccNum() {	return jtf_waccNum.getText();}
	public void setWaccNum(String wanum) { jtf_waccNum.setText(wanum);}
	public String getDaccNum() {	return jtf_daccNum.getText();}
	public void setDaccNum(String danum) { jtf_daccNum.setText(danum);}
	public String getWMsg() {	return jtf_wMsg.getText();}
	public void setWMsg(String wmsg) { jtf_waccNum.setText(wmsg);}
	public String getDMsg() {	return jtf_dMsg.getText();}
	public void setDMsg(String dmsg) { jtf_daccNum.setText(dmsg);}
	public String getTransfer() {	return jtf_transfer.getText();}
	public void setTransfer(String transfer) { jtf_transfer.setText(transfer);}
	

}
