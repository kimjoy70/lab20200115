package design.book;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BookDialog extends JDialog implements ActionListener {
/*
 * 자녀창에서 저장(입력) 성공했을때 부모창의 refreshData를 호출해야 한다.
 * 그런데 원본을 재사용해야 하므로 set메소드의 파라미터로 받아서 사용할 것이다.
 * 다른 메소드에서 ba를 사용해야 하니까 전역변수로 선언한 다음 초기화를 반드시 할것.	
 */
	BookApp ba = null;
	BookApp bookApp = null;
	boolean isView = false;
	String title = null;//입력
	//인스턴스화를 하면 생성자 호출이 일어남.
	JLabel 		jlb_title = new JLabel("책제목");
	JTextField 	jtf_title = new JTextField(30);
	JLabel 		jlb_author = new JLabel("저자");
	JTextField 	jtf_author = new JTextField(40);
	JLabel 		jlb_publish = new JLabel("출판사");
	JTextField 	jtf_publish = new JTextField(50);
	JLabel 		jlb_info = new JLabel("도서소개");
	JTextArea 	jta_info = new JTextArea(8,25);
	JLabel		jlb_img	 = new JLabel("이미지");
	JPanel jp_center = new JPanel();
	JPanel jp_south  = new JPanel();
	JButton jbtn_save 	= new JButton("저장");
	JButton jbtn_close 	= new JButton("닫기");
	JScrollPane jsp = new JScrollPane(jp_center);
	JScrollPane jsp_info = new JScrollPane(jta_info);
	String path = "C:\\workspace_java\\dev_java\\src\\design\\book\\";
	ImageIcon Icon = null;
	Map<String,Object> rMap = null;
	public BookDialog() {
	}
	public BookDialog(BookApp bookApp) {
		jbtn_save.addActionListener(this);
		jbtn_close.addActionListener(this);		
		this.bookApp = bookApp;
	}
	//입력과 수정시에는 컬럼값을 수정 가능하도록 하고
	//조회시에는 불가능하게 하는 메소드를 선언해 봐요.
	public void setEditable(boolean isOk) {
		jtf_title.setEditable(isOk);
		jtf_author.setEditable(isOk);
		jtf_publish.setEditable(isOk);
		jta_info.setEditable(isOk);
	}
	/****************************************************************
	 * 
	 * @param title 다이얼로그창 제목
	 * @param isView 다이얼로그창 뷰 여부
	 * @param editable 입력 컴포넌트 수정 여부
	 * @param rMap 조회결과를 담은 주소번지
	 ***************************************************************/
	public void set(String title, boolean isView, boolean editable, Map<String,Object> rMap, BookApp ba)
	{
		this.rMap = rMap;
		this.ba = ba;
		setValue(rMap);
		setEditable(editable);
		this.setTitle(title);
		initDisplay();
		this.setVisible(isView);
	}	
	public void initDisplay() {
		jta_info.setLineWrap(true);
		jp_center.setLayout(null);
		jp_south.setLayout(new FlowLayout(FlowLayout.CENTER));
		jp_south.add(jbtn_save);
		jp_south.add(jbtn_close);
		jlb_title.setBounds(20, 20, 100, 20);
		jtf_title.setBounds(120, 20, 300, 20);
		jlb_author.setBounds(20, 45, 100, 20);
		jtf_author.setBounds(120, 45, 150, 20);
		jlb_publish.setBounds(20, 70, 100, 20);
		jtf_publish.setBounds(120, 70, 200, 20);
		jlb_info.setBounds(20, 95, 100, 20);
		jsp_info.setBounds(120, 95, 300, 120);
		jlb_img.setBorder(BorderFactory.createEtchedBorder());
		jlb_img.setBounds(120, 220, 300, 400);
		//jlb_img.setIcon(new ImageIcon(path+"1.jpg"));
		jp_center.add(jlb_title);
		jp_center.add(jtf_title);
		jp_center.add(jlb_author);
		jp_center.add(jtf_author);
		jp_center.add(jlb_publish);
		jp_center.add(jtf_publish);
		jp_center.add(jlb_info);
		jp_center.add(jsp_info);
		jp_center.add(jlb_img);
		this.add("Center",jsp);
		this.add("South",jp_south);
		this.setSize(500, 720);
		//부모창에서 선택한 버튼에 따라 화면을 제어한다.- 변수
	}
	public void setValue(Map<String,Object> rmap) {
	//입력을 위한 화면 설정 - 모든값을 빈문자열로 셋팅한다.
		if(rmap == null) {
			setB_title("");
			setAuthor("");
			setPublish("");
			setInfo("");
		}
	//상세조회와 수정시는  파라미터로 받은 값으로 셋팅한다.
		else {
			setB_title(rmap.get("b_name").toString());
			setAuthor(rmap.get("author").toString());
			setPublish(rmap.get("publish").toString());
			setInfo(rmap.get("b_info").toString());
			setImg(rmap.get("b_img").toString());
		}
	}
	//각 컬럼의 값들을 설정하거나 읽어오는 getter/setter메소드 입니다.
	public String getB_title() {	return jtf_title.getText();}
	public void setB_title(String title) { jtf_title.setText(title);}
	public String getAuthor() {	return jtf_author.getText();}
	public void setAuthor(String author) { jtf_author.setText(author);}
	public String getPublish() {	return jtf_publish.getText();}
	public void setPublish(String publish) { jtf_publish.setText(publish);}
	public String getInfo() {	return jta_info.getText();}
	public void setInfo(String info) { jta_info.setText(info);}
	public void setImg(String img) { 
		ImageIcon originIcon = new ImageIcon(path+img);  
		//ImageIcon에서 Image를 추출
		Image originImg = originIcon.getImage(); 
		//추출된 Image의 크기를 조절하여 새로운 Image객체 생성
		Image changedImg= originImg.getScaledInstance(300, 400, Image.SCALE_SMOOTH );
		//새로운 Image로 ImageIcon객체를 생성
		Icon = new ImageIcon(changedImg);
//		image.setImage(new ImageIcon(path+img).getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT));
		jlb_img.setIcon(Icon);
	}
	/*			 */
	public static void main(String[] args) {
		BookDialog bd = new BookDialog();
		bd.set("입력",true,true,null,null);
		//bd.initDisplay();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("bd event호출");
		String command = e.getActionCommand();//이벤트 소스의 라벨
		//JOptionPane.showMessageDialog(ba, "이벤트 소스 라벨:"+command);
		//저장버튼을 누른거니?
		if("저장".equals(command)) {
			this.dispose();
			if(rMap!=null) {//수정처리
				
			}
			else {//입력처리
				Map<String,Object> pMap = new HashMap<>();
				pMap.put("b_name", getB_title());
				pMap.put("author", getAuthor());
				pMap.put("publish", getPublish());
				pMap.put("b_info", getInfo());
				bookApp.bDao.insertBook(pMap);
			}
			//insert here - 입력 인지 수정인지 어떻게 구분하지?
			ba.refreshData(null);
		}
		//닫기 버튼을 눌렀니?
		else if("닫기".equals(command)) {
			this.dispose();
		}
	}
}






