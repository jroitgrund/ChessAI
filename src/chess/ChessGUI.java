
package chess;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ChessGUI {

  private void init() {
    SwingUtilities.invokeLater(new Runnable() {

      public void run() {
        initGUI();
      }
    });
  }

  public static void main(String[] args) {
    ChessGUI g = new ChessGUI();
    g.init();
  }

  private static void initGUI() {
    JFrame f = new JFrame("Chess");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.add(new ChessPanel());
    f.pack();
    f.setVisible(true);
  }
}

class ChessPanel extends JPanel implements MouseListener {

  private BufferedImage[][] pieces;

  private BufferedImage     bg;

  private Board             b;

  private Coord             from;

  private Coord             to;

  private int               state = 0;

  public ChessPanel() {
    pieces = new BufferedImage[2][6];
    try {
      pieces[0][0] = ImageIO.read(new File("img/whites/white_p.png"));
      pieces[0][1] = ImageIO.read(new File("img/whites/white_k.png"));
      pieces[0][2] = ImageIO.read(new File("img/whites/white_n.png"));
      pieces[0][3] = ImageIO.read(new File("img/whites/white_q.png"));
      pieces[0][4] = ImageIO.read(new File("img/whites/white_r.png"));
      pieces[0][5] = ImageIO.read(new File("img/whites/white_b.png"));
      pieces[1][0] = ImageIO.read(new File("img/blacks/black_p.png"));
      pieces[1][1] = ImageIO.read(new File("img/blacks/black_k.png"));
      pieces[1][2] = ImageIO.read(new File("img/blacks/black_n.png"));
      pieces[1][3] = ImageIO.read(new File("img/blacks/black_q.png"));
      pieces[1][4] = ImageIO.read(new File("img/blacks/black_r.png"));
      pieces[1][5] = ImageIO.read(new File("img/blacks/black_b.png"));
      bg = ImageIO.read(new File("img/board/board_black.png"));
    }
    catch (Exception e) {
      System.out.println("couldn't load images");
    }
    addMouseListener(this);
    b = new Board();
  }

  public Dimension getPreferredSize() {
    return new Dimension(240, 240);
  }

  protected void paintComponent(Graphics g) {
    g.drawImage(bg, 0, 0, null);
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Piece p = b.getPiece(new Coord(i, j));
        if (p != null) {
          g.drawImage(pieces[p.getColor().ordinal()][p.getType().ordinal()],
              8 + i * 28, 240 - 36 - j * 28, null);
        }
      }
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    System.out.println("clicked coord (" + e.getX() + ", " + e.getY());
    int x = e.getX() - 8;
    int y = 240 - e.getY() - 8;
    Coord c = new Coord(x / 28, y / 28);
    switch (state) {
      case 0:
        from = c;
        System.out.println("Set from to [" + c.getCol() + ", " + c.getRow()
            + "]");
        state++;
        break;
      case 1:
        to = c;
        System.out
            .println("Set to to [" + c.getCol() + ", " + c.getRow() + "]");
        if (!b.move(from, to)) {
          System.out.println("illegal move");
        }
        state = 0;
        repaint();
        break;
    }
  }
}
