package com.network2;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Background {
	public static void main(String args[]) {
		JFrame frame = new JFrame("Background Example");
		String imgPath = "C:\\workspace_java\\dev_java\\src\\image\\";
		JTextArea textArea = new JTextArea() {
			final ImageIcon imageIcon = new ImageIcon(imgPath + "main.png");
			Image image = imageIcon.getImage();
			Image grayImage = GrayFilter.createDisabledImage(image);
			{
				setOpaque(false);
			}

			public void paintComponent(Graphics g) {
				g.drawImage(grayImage, 0, 0, this);
				super.paintComponent(g);
			}
		};

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(textArea);
		Container content = frame.getContentPane();
		content.add(scrollPane, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(3);
		frame.setSize(250, 250);
		frame.setVisible(true);
	}
}
