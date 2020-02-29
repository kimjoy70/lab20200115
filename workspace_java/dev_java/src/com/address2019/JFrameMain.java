package com.address2019;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class JFrameMain extends JFrame implements ActionListener {
    JPanel jp = new JPanel();
    JTextArea jta = new JTextArea();
    JPanel  jp_btn	= new JPanel();
    JButton jbtn_change = new JButton("화면변경1");
    JButton jbtn_change2 = new JButton("화면변경2");
    public JFrameMain() {
        initDisplay();
    }
    public void initDisplay() {
        jbtn_change.addActionListener(this);
        jbtn_change2.addActionListener(this);
        jp.setLayout(new BorderLayout());
        jp.add("Center",jta);
        this.add("Center",jp);
        jp_btn.add(jbtn_change);
        jp_btn.add(jbtn_change2);
        this.add("South",jp_btn);
        this.setSize(500, 400);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        new JFrameMain();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if(obj==jbtn_change) {
            System.out.println("변경");
            Container cont=this.getContentPane();
            if(jp!=null) {
                cont.remove(jp);
                cont.remove(jbtn_change);
            }
            JPanel1 jp1 = null;
            jp1 = new JPanel1();
            this.add("Center",jp1);
            cont.revalidate();
        }
        if(obj==jbtn_change2) {
        	System.out.println("변경2");
        	Container cont=this.getContentPane();
        	if(jp!=null) {
        		cont.remove(jp);
        		cont.remove(jbtn_change);
        	}
        	JPanel2 jp2 = null;
        	jp2 = new JPanel2();
        	this.add("Center",jp2);
        	cont.revalidate();
        }
    }

}
