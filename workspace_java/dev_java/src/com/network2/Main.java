package com.network2;

import java.awt.Color;
import java.awt.Graphics;
// w  w w .  j a v  a  2s.  com
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

public class Main {
  public static void main(String[] args) {
    JTextArea textArea = new JTextArea(5, 30);
    textArea.setOpaque(false);

    JViewport viewport = new JViewport() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = this.getWidth();
        int h = this.getHeight();
        g.setColor(Color.RED);
        g.fillRect(0, 0, w / 2, h / 2);
      }
    };

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setViewport(viewport);
    scrollPane.setViewportView(textArea);

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(scrollPane);
    frame.setLocationByPlatform(true);
    frame.pack();
    frame.setVisible(true);
  }
}
