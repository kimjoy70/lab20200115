package com.network2;

import java.awt.BorderLayout;
//from  ww  w.j  a va  2  s  . c o  m
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.DefaultCaret;

public class Main2 extends JFrame {
JPanel panel = new JPanel();
public static void main(String[] args) {
  Main2 frame = new Main2();
  frame.setVisible(true);
}

public Main2() {
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  panel.setLayout(new BorderLayout());
  setContentPane(panel);
  JTextPane textA = new JTextPane();
  textA.setName("text");
  textA.setContentType("text/html");
  DefaultCaret caret = (DefaultCaret) textA.getCaret();
  caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
  JScrollPane filler = new JScrollPane(textA,
      JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

  JTextPane textB = new JTextPane();
  textB.setName("text" + "_T");
  textB.setFont(textA.getFont());
  DefaultCaret caret_T = (DefaultCaret) textB.getCaret();
  caret_T.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
  JScrollPane filler_T = new JScrollPane(textB,
      JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
  panel.add(filler, BorderLayout.NORTH);
  panel.add(filler_T, BorderLayout.CENTER);
  pack();
}
}
