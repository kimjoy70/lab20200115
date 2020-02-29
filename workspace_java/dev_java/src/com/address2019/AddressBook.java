package com.address2019;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import java.util.*;
import java.util.List;

public class AddressBook extends JFrame {
	String imgPath = "C:\\workspace_java\\dev_java\\src\\com\\images\\adress2019\\";
	ImageIcon titleIcon = new ImageIcon(imgPath+"memo.png");
	// 메인화면에 사용할 컴포넌트들을 선언합니다.
    private JMenuBar menuBar;
    private JMenu menuMenu;
    private JMenu menuAbout;
	private JMenuItem menuItemConnect;
    private JMenuItem menuItemInsert;
    private JMenuItem menuItemUpdate;
    private JMenuItem menuItemDelete;
    private JMenuItem menuItemDetail;
    private JMenuItem menuItemAbout;
    private JSeparator menuSeparator1;
    private JSeparator menuSeparator2;
    private JMenuItem menuItemExit;
    private JToolBar toolbar;
    private JButton btnInsert;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnDetail;
    private JScrollPane jsp_address;
    private JTable jt_address;
	private DefaultTableModel dtm_address;
	private String cols[] = {"아이디","이름","주소","전화번호"};
	private String data[][] = new String[0][4];
	private JTableHeader jth_address;
	private JPanel panelTimer;
	private JLabel labelTimer;

	private JOptionPane optionDlg;
	private ModifyDialog mDialog;
	private Font font;
	private String path;

	// DB작업을 중개해줄 Controller 클래스
	private AddressCtrl ctrl;
	public static AddressBook abook = null;

	// 메인 메쏘드는 AddressBook의 인스턴스를 생성하고 보여주는 일만 합니다.
    public static void main(String args[]) {
        abook = new AddressBook();
        abook.setVisible(true);
    }

    // 생성자는 컴포넌트들을 초기화합니다.
    public AddressBook() {
        initComponents();
    }

	// 초기화 작업은 컴포넌트들의 값을 셋팅하고 배치합니다.
    private void initComponents() {
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	// DefaultTableModel을 상속받은 MyTableModel 클래스가 테이블의
    	// 데이타를 담당합니다.
		font= new Font("굴림",Font.BOLD, 14);
		// 메뉴바, 메뉴, 메뉴 아이템을 정의합니다.
        menuBar = new JMenuBar();
        menuMenu = new JMenu();
		menuAbout= new JMenu();
		menuItemConnect= new JMenuItem();
        menuItemInsert = new JMenuItem();
        menuItemUpdate = new JMenuItem();
        menuItemDelete = new JMenuItem();
        menuItemDetail= new JMenuItem();
		menuItemAbout = new JMenuItem();
        menuSeparator1 = new JSeparator();
        menuSeparator2 = new JSeparator();
        menuItemExit = new JMenuItem();
        menuMenu.setText("메뉴");
        menuMenu.setFont(font);

        // 툴바와 이미지 버튼을 정의합니다.
        toolbar = new JToolBar();
        btnInsert = new JButton();
        btnInsert.setToolTipText("입력");
        btnUpdate = new JButton();
        btnUpdate.setToolTipText("수정");
        btnDelete = new JButton();
        btnDelete.setToolTipText("삭제");
        btnDetail = new JButton();
        btnDetail.setToolTipText("조회");
        jsp_address = new JScrollPane();
        dtm_address = new DefaultTableModel(data,cols);
        jt_address = new JTable(dtm_address);
        JTableHeader jth = new JTableHeader();
        jth_address = jt_address.getTableHeader();
        jsp_address.getViewport().setBackground(Color.black);

		labelTimer = new JLabel("현재 시간");
		labelTimer.setFont(font);
		panelTimer = new JPanel();
		path = "C:\\workspace_java\\dev_java\\src\\com\\images\\adress2019\\";
		// 타임 클라이언트 시작
		Thread t = new TimeClient(labelTimer);
		t.start();

		// About 화면을 출력할 대화상자 정의
		optionDlg = new JOptionPane();
		// 입력, 수정, 조회에 사용할 화면정의
        mDialog = new ModifyDialog(this);
        mDialog.setVisible(false);

        // DB연결 메뉴아이템
		menuItemConnect.setFont(font);
		menuItemConnect.setText("DB 연결");
		menuItemConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
                System.out.println("DB 연결 메뉴");
                connectActionPerformed(evt);
			}
		});

		// 조회 메뉴아이템
        menuItemDetail.setFont(font);
        menuItemDetail.setText("조회");
        menuItemDetail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.out.println("조회 메뉴");
                detailActionPerformed();
            }
        });

		// 입력 메뉴아이템
	    menuItemInsert.setFont(font);
        menuItemInsert.setText("입력");
        menuItemInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.out.println("입력 메뉴");
                addActionPerformed(evt);
            }
        });

		// 수정 메뉴아이템
        menuItemUpdate.setFont(font);
        menuItemUpdate.setText("수정");
        menuItemUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.out.println("수정 메뉴");
            	AddressVO vo = new AddressVO();
            	updateActionPerformed(vo);
            }
        });

		// 삭제 메뉴아이템
        menuItemDelete.setFont(font);
        menuItemDelete.setText("삭제");
        menuItemDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	System.out.println("삭제 메뉴");
            	deleteActionPerformed();
            }
        });

		// 종료 메뉴아이템
        menuItemExit.setFont(font);
        menuItemExit.setText("종료");
        menuItemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	System.out.println("종료 메뉴");
            	exitActionPerformed(evt);
            }
        });

        // 메뉴 아이템을 메뉴에 붙입니다.
		menuMenu.add(menuItemConnect);
        menuMenu.add(menuSeparator1);
		menuMenu.add(menuItemDetail);
		menuMenu.add(menuItemInsert);
		menuMenu.add(menuItemUpdate);
		menuMenu.add(menuItemDelete);
		menuMenu.add(menuSeparator2);
        menuMenu.add(menuItemExit);

 		// 첫번째 메뉴를 메뉴바에 붙입니다.
        menuBar.add(menuMenu);

		// About 메뉴
		menuAbout.setFont(font);
		menuAbout.setText("About");

		// About 메뉴아이템
		menuItemAbout.setFont(font);
		menuItemAbout.setText("About");
		menuItemAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.out.println("About 메뉴");
				aboutActionPerformed(evt);
			}
		});
		menuAbout.add(menuItemAbout);

		// 두번째 메뉴를 메뉴바에 붙입니다.
		menuBar.add(menuAbout);
		// 완성된 메뉴바를 프레임과 연결합니다.
		setJMenuBar(menuBar);

        // 프레임 관련 설정을 합니다.
		setTitle("주소록 데모 프로그램");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFont(font);

		// 윈도우 리스너 설정
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
            	System.out.println("윈도우 종료");
            	System.exit(0);
            }
        });

		// 조회 아이콘
		btnDetail.setIcon(new ImageIcon(path+"detail.gif"));
        btnDetail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	System.out.println("조회 아이콘");
            	detailActionPerformed();
            }
        });
        toolbar.add(btnDetail);
		toolbar.add(new JToolBar.Separator());

		// 입력 아이콘
        btnInsert.setIcon(new ImageIcon(path+"new.gif"));
        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	System.out.println("입력 아이콘");
            	addActionPerformed(evt);
            }
        });
		toolbar.add(btnInsert);

        // 수정 아이콘
        btnUpdate.setIcon(new ImageIcon(path+"update.gif"));
		btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	System.out.println("수정 아이콘");
            	AddressVO vo = new AddressVO();
            	updateActionPerformed(vo);
            }
            
        });
        toolbar.add(btnUpdate);

        // 삭제 아이콘
        btnDelete.setIcon(new ImageIcon(path+"delete.gif"));
		btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	System.out.println("삭제 아이콘");
            	deleteActionPerformed();
            }
        });
        toolbar.add(btnDelete);

        // 툴바를 컨테이너에 붙입니다.
        getContentPane().add(toolbar, java.awt.BorderLayout.NORTH);

        // 데이터 리스트가 조회될 테이블을 설정합니다.
        jt_address.setFont(font);
		jt_address.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() >= 2) {
					System.out.println("데이타 더블클릭");
					detailActionPerformed();
				}
			}
		});

		// 테이블을 스크롤 페널에 연결하여 붙입니다.
        jsp_address.setViewportView(jt_address);
        getContentPane().add(jsp_address, BorderLayout.CENTER);

		// 서버로부터 받아온 시간을 출력할 레이블을 붙입니다.
		panelTimer.add(labelTimer);
		getContentPane().add(panelTimer, BorderLayout.SOUTH);
		Dimension di = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension di2 = this.getSize();
		this.setLocation((int)(di.getWidth()/3-di2.getWidth()/2)
				          , (int)(di.getHeight()/4-di2.getHeight()/4));
        //pack();
        this.setSize(700, 500);
		// 데이터가 표시될 테이블의 칼럼을 설정합니다.
        jth_address.setFont(new Font("맑은고딕",Font.BOLD,18));
        jth_address.setBackground(new Color(22,22,100));
        jth_address.setForeground(Color.white);
        jth_address.setReorderingAllowed(false);
        jth_address.setResizingAllowed(false);
        jt_address.setFont(new Font("맑은고딕",Font.BOLD,16));
        jt_address.setBackground(Color.white);
        jt_address.setForeground(Color.blue);
        jt_address.setRowHeight(25);
		jt_address.setSelectionForeground(new Color(22,22,100));
		jt_address.setSelectionBackground(new Color(186,186,241));
		jt_address.setGridColor(Color.blue);
		jt_address.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		/////////////// 컬럼 너비 조정하기
		jt_address.getColumnModel().getColumn(0).setPreferredWidth(80);
		jt_address.getColumnModel().getColumn(1).setPreferredWidth(100);
		jt_address.getColumnModel().getColumn(2).setPreferredWidth(390);
		jt_address.getColumnModel().getColumn(3).setPreferredWidth(130);
		jt_address.repaint();
		// 모든 GUI 컴포넌트의 설정이 완료되면 전체 데이터를 조회합니다.
		try {
			refreshData();
		} catch (Exception e) {
			optionDlg.showMessageDialog(this, "DB 연결에 실패했습니다.\n" + e,
				"Error", JOptionPane.ERROR_MESSAGE);
		}
    }

	// DB연결 메뉴 선택시 작업을 정의합니다.
	private void connectActionPerformed(ActionEvent evt) {

		try {
			refreshData();
		} catch (Exception e) {
			optionDlg.showMessageDialog(this, "DB 연결에 실패했습니다.\n" + e,
				"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// 조회 메뉴나 조회 아이콘 선택시 작업을 정의합니다.
	private void detailActionPerformed() {
		int index[]= jt_address.getSelectedRows();
		// 테이블의 데이터를 선택하지 않은 경우
		if(index.length == 0) {
			optionDlg.showMessageDialog(this, "조회할 데이터를 선택하세요...", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		// 테이블의 데이터를 한건이상 선택한 경우
		} else if(index.length > 1) {
			optionDlg.showMessageDialog(this, "데이터를 한건만 선택하세요...", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			
			try {
				AddressVO vo = new AddressVO();
				jt_address.clearSelection();
				// 선택된 데이터의 PK인 id를 읽어옵니다.
//				Integer id= (Integer)myTableModel.getValueAt(index[0], 0);
				Integer id = Integer.parseInt
				(dtm_address.getValueAt(index[0], 0).toString());
				// Value Object에 id와 command를 설정합니다.
				vo.setId(id.intValue());
				vo.setCommand("select");
				// Controller 역할을 할 AddressCtrl을 생성합니다.
				ctrl= new AddressCtrl(vo);
				AddressVO newVo= ctrl.send(vo);
				// 조회된 데이터를 보여줄 윈도우를 설정합니다.
				mDialog.set("조회", false, newVo, abook);
				mDialog.setVisible(true);
			} catch (Exception e) {
				optionDlg.showMessageDialog(this, "조회중 에러가 발생했습니다.\n" + e,
					"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// 입력 메뉴나 입력 아이콘 선택시 작업을 정의합니다.
	private void addActionPerformed(ActionEvent evt) {
		mDialog.set("입력", true, null,abook);
		mDialog.setVisible(true);
		// 다이얼로그에서 입력된 값이 없을 경우는 이벤트를 처리하지 않고 버립니다.
		mDialog.dispose();
	}

	// 수정 메뉴나 수정 아이콘 선택시 작업을 정의합니다.
	public void updateActionPerformed(AddressVO pvo) {
		int index[] = jt_address.getSelectedRows();
		AddressVO vo = new AddressVO();
		//pvo = new AddressVO();
		if(index.length == 0) {
			optionDlg.showMessageDialog(this, "수정할 데이터를 선택하세요...", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} else if(index.length > 1) {
			optionDlg.showMessageDialog(this, "데이터를 한건만 선택하세요...", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			try {
				// 테이블에서 선택된 컬럼의 id를 읽어옵니다.
//				Integer id= (Integer)dtm_address.getValueAt(index[0], 0);
				Integer id = Integer.parseInt
				(dtm_address.getValueAt(index[0], 0).toString());				
				vo.setId(id.intValue());
				vo.setCommand("select");
				ctrl= new AddressCtrl(vo);
				AddressVO newVo= ctrl.send(vo);
				mDialog.set("수정", true, newVo, abook);
				mDialog.setVisible(true);
			} catch (Exception e) {
				optionDlg.showMessageDialog(this, "수정중 에러가 발생했습니다111." + e,
					"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// 삭제 메뉴나 삭제 아이콘 선택시 작업을 정의합니다.
	private void deleteActionPerformed() {
		int index[]= jt_address.getSelectedRows();
		if (index.length == 0) {
			optionDlg.showMessageDialog(this, "삭제할 데이터를 선택하세요..."
					, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			try {
				AddressVO vo = new AddressVO();
				// 테이블에서 선택된 row의 id에 대해 삭제 요청을 합니다.
				for(int i = 0; i < dtm_address.getRowCount(); i++) {
					if(jt_address.isRowSelected(i)) {
						System.out.println("ddd : "+dtm_address.getValueAt(i, 0).getClass().toString());
						// 테이블에서 선택된 컬럼의 ID를 읽어온다.
//						Integer id = (Integer)dtm_address.getValueAt(i, 0);
						Integer id = Integer.parseInt((String)dtm_address.getValueAt(i, 0));
						System.out.println("id : "+id.intValue());
						vo.setCommand("delete");
						vo.setId(id.intValue());
						ctrl= new AddressCtrl(vo);
						ctrl.send(vo);
					}
				}
				refreshData();
			} catch (Exception e) {
				optionDlg.showMessageDialog(this, "삭제중 에러가 발생했습니다.\n" + e,
					"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// 종료 메뉴 선택시 작업을 정의합니다.
	private void exitActionPerformed(ActionEvent evt) {
		System.exit(0);
	}

	// About 메뉴 선택시 작업을 정의합니다.
	private void aboutActionPerformed(ActionEvent evt) {
		optionDlg.setFont(font);
		optionDlg.showMessageDialog(this, "주소록 데모 프로그램 1.0",
			"About", JOptionPane.INFORMATION_MESSAGE);
	}

	// 전체 데이터를 다시 조회합니다.
	public void 
() throws Exception {

		// 이미 테이블에 보여지는 데이터가 있는 경우 모두 삭제합니다.
		while(dtm_address.getRowCount() > 0)
			dtm_address.removeRow(0);

		AddressVO vo = new AddressVO();
		vo.setCommand("selectall");
		ctrl = new AddressCtrl(vo);

		// Controller에서 넘겨 받은 전체 데이터를 테이블에 셋팅합니다.
		AddressVO [] vos = ctrl.send();
		List list = ctrl.sendAll();
        Iterator<HashMap> iter = list.iterator();
        Object obj[] = null;		
//		if ((vos == null)||(vos.length < 1)) {
		if ((list == null)||(list.size() < 1)) {
			optionDlg.showMessageDialog(this, "데이터가 없습니다.", "Warning", JOptionPane.ERROR_MESSAGE);
		} 
//		else {
//			for (int i = 0; i < vos.length; i++) {
//				Vector oneRow = new Vector();
//				oneRow.addElement(new Integer(vos[i].getId()));
//				oneRow.addElement(vos[i].getName());
//				oneRow.addElement(vos[i].getAddress());
//				oneRow.addElement(vos[i].getTelephone());			
//				dtm_address.addRow(oneRow);
//			}
		
//		}
		 else {
				while (iter.hasNext()) {
		        	HashMap data = iter.next();
		        	obj = data.keySet().toArray();				
					Vector oneRow = new Vector();
					oneRow.addElement(data.get(obj[2]).toString());
					oneRow.addElement(data.get(obj[3]));
					oneRow.addElement(data.get(obj[1]));
					oneRow.addElement((String)data.get(obj[0]));
					dtm_address.addRow(oneRow);
				}//while ended	
			}
		
	}

}
