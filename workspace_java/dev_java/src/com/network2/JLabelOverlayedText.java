package com.network2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JLabelOverlayedText extends JLabel implements MouseListener{
      private String myText;
      private boolean transparentImage = true;
      
      public JLabelOverlayedText(ImageIcon i){
            super(i);
            addMouseListener(this);
      }
      
      public JLabelOverlayedText(ImageIcon i, String text)
      {
            this(i);
            myText = text;
      }
      
      public JLabelOverlayedText(ImageIcon i, String text, int fontSize)
      {
            this(i, text);
            setFont(new Font("Serif", 1, fontSize));
      }
      
      public void paintComponent(Graphics g)
      {
            super.paintComponent(g);
            ImageIcon imageicon = (ImageIcon)this.getIcon();
            Image image = imageicon.getImage();
            
            FontMetrics fm = this.getFontMetrics(getFont());
            Point p = new Point();
            p.setLocation((getWidth() - fm.getStringBounds(myText, g).getWidth())/2 , (getHeight()/2)+5);
            g.setFont(getFont());
            g.setColor(Color.WHITE);
                        
            Graphics2D g2 = (Graphics2D)g;
            if (image != null)
            {
                  Composite alpha;
                  if (transparentImage)
                        alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
                  else
                        alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
                  g2.setComposite(alpha);
                  g2.drawImage(image, 0, 0, null);
                  g2.drawString(myText, p.x, p.y);
            }
      }
      
      public void mouseClicked(MouseEvent e) {}
      public void mouseEntered(MouseEvent e) {
            transparentImage = false;
      }
      public void mouseExited(MouseEvent e)      {
                  transparentImage = true;
            }
            public void mousePressed(MouseEvent e)      {}
            public void mouseReleased(MouseEvent e)      {}
      
            public void actionPerformed(ActionEvent e) {}
      
            public void mouseDragged(MouseEvent e) {}
            public void mouseMoved(MouseEvent e) {
                  synchronized(this) {
                  if (contains(e.getPoint()))
                        transparentImage = true;
                  else
                        transparentImage = false;
                  }
            }

}

