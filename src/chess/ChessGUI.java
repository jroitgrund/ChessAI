package chess;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class ChessGUI {
  
  private Board b;
  
  public ChessGUI()
  {
    b = new Board();
  }
  
  private void init() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run()
      {
        initGUI();
      }
    });
  }
  
  public static void main(String[] args)
  {
    ChessGUI g = new ChessGUI();
    g.init();
  }
  
  private static void initGUI()
  {
    JFrame f = new JFrame("Chess");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.add(new ChessPanel());
    f.pack();
    f.setVisible(true);
  }
  
}

class ChessPanel extends JPanel
{
  public Dimension getPreferredSize() {
    return new Dimension(240, 240);
  }
  
  protected void paintComponent(Graphics g) {
    
  }
}
