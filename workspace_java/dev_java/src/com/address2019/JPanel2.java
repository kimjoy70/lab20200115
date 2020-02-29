package com.address2019;


import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class JPanel2 extends JPanel {
//public class JPanel1 extends JFrame {
    String cols[] = {"도서명"};
    String data[][] = new String[0][1];
    //DataSet의 역할을 수행하는 DefaultTableModel을 먼저 선언하고 초기화 하기
    DefaultTableModel dtm_nick = new DefaultTableModel(data,cols);
    JTable               jtb_nick = new JTable(dtm_nick);
    JScrollPane       jsp_nick = new JScrollPane(jtb_nick
                                                ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
                                                ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    public JPanel2() {
        initDisplay();
    }
    public void initDisplay() {
        this.setLayout(new BorderLayout());
        this.add("Center",jsp_nick);
        this.setSize(400, 300);
        this.setVisible(true);
    }
    /*
     * public static void main(String args[]) { new JPanel1(); }
     */
}
