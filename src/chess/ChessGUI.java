
package chess;

import java.awt.Color;
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

enum GameState implements State {
  PLAYING {

    Coord from = null;

    @Override
    public void paint(ChessPanel cp, Graphics g) {
      g.drawImage(cp.bg, 0, 0, null);
      for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
          Piece p = cp.b.getPiece(new Coord(i, j));
          if (p != null) {
            g.drawImage(
                cp.pieces[p.getColor().ordinal()][p.getType().ordinal()],
                8 + i * 28, 240 - 36 - j * 28, null);
          }
        }
      }
    }

    @Override
    public GameState mouseReleased(ChessPanel cp, MouseEvent e) {
      Coord b = from;
      Coord c = getCoord(e);
      from = null;
      if (b == null || !c.inBoard()) {
        return this;
      }
      if (cp.b.move(b, c)) {
        switch (cp.b.getState()) {
          case ONGOING:
            return this;
          case CHECKMATE:
            return VICTORY;
          case DRAW:
            return STALE;
        }
      }
      return this;
    }

    @Override
    public GameState mousePressed(ChessPanel cp, MouseEvent e) {
      Coord c = getCoord(e);
      if (!c.inBoard()) {
        from = null;
      }
      else {
        from = c;
      }
      return this;
    }

    private Coord getCoord(MouseEvent e) {
      int x = e.getX() - 8;
      int y = 240 - e.getY() - 8;
      return new Coord(x / 28, y / 28);
    }
  },
  VICTORY {

    @Override
    public void paint(ChessPanel cp, Graphics g) {
      PLAYING.paint(cp, g);
      g.setColor(Color.BLACK);
      StringBuilder s = new StringBuilder(11);
      switch (cp.b.getCurrentPlayer()) {
        case W:
          s.append("Black");
          break;
        case B:
          s.append("White");
          break;
      }
      s.append(" wins!");
      g.drawString(s.toString(), 120, 120);
      g.drawString("Click to restart", 120, 160);
    }

    @Override
    public GameState mouseReleased(ChessPanel cp, MouseEvent e) {
      cp.b = new Board();
      return PLAYING;
    }

    @Override
    public GameState mousePressed(ChessPanel cp, MouseEvent e) {
      return this;
    }
  },
  STALE {

    @Override
    public void paint(ChessPanel cp, Graphics g) {
      PLAYING.paint(cp, g);
      g.setColor(Color.BLACK);
      g.drawString("Stalemate", 120, 120);
      g.drawString("Click to restart", 120, 160);
    }

    @Override
    public GameState mouseReleased(ChessPanel cp, MouseEvent e) {
      cp.b = new Board();
      return PLAYING;
    }

    @Override
    public GameState mousePressed(ChessPanel cp, MouseEvent e) {
      return this;
    }
  };
}

interface State {

  void paint(ChessPanel cp, Graphics g);

  GameState mouseReleased(ChessPanel cp, MouseEvent e);

  GameState mousePressed(ChessPanel cp, MouseEvent e);
}

@SuppressWarnings("serial")
class ChessPanel extends JPanel implements MouseListener {

  BufferedImage[][] pieces;

  BufferedImage     bg;

  Board             b;

  private GameState state;

  public ChessPanel() {
    state = GameState.PLAYING;
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
    state.paint(this, g);
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
    state = state.mousePressed(this, e);
    repaint();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    state = state.mouseReleased(this, e);
    repaint();
    System.out.println("Done with outer mouseReleased");
  }
}
