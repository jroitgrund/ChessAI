
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
import javax.swing.SwingWorker;

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
  WAITING {

    @Override
    public GameState mouseReleased(ChessPanel cp, MouseEvent e) {
      return WAITING;
    }

    @Override
    public GameState mousePressed(ChessPanel cp, MouseEvent e) {
      return WAITING;
    }

    @Override
    protected GameState continueGame(ChessPanel cp) {
      return null;
    }
    
  },
  PLAYINGAI {

    Coord from = null;

    @Override
    public GameState mouseReleased(ChessPanel cp, MouseEvent e) {
      Coord b = from;
      Coord c = null;
      from = null;
      if (b == null || (c = getCoord(e)) == null) {
        return this;
      }
      return move(cp, b, c);
    }

    @Override
    public GameState mousePressed(ChessPanel cp, MouseEvent e) {
      from = getCoord(e);
      return this;
    }

    @Override
    protected GameState continueGame(final ChessPanel cp) {
      System.out.println("Creating new SwingWorker for w");
      new SwingWorker<GameState, Void>() {

        @Override
        protected GameState doInBackground() throws Exception {
          System.out.println("worker starting");
          Move bestMove = Minimax.bestMove(cp.b);
          cp.b.move(bestMove.getFrom(), bestMove.getTo(), false);
          System.out.println("worker done moving");
          switch (cp.b.getState()) {
            case ONGOING:
              System.out.println("worker returning PLAYINGAI");
              return PLAYINGAI;
            case CHECKMATE:
              return VICTORY;
            case DRAW:
              return STALE;
          }
          return PLAYINGAI;
        }
        
        protected void done()
        {
          try {
            cp.setState(get());
            cp.repaint();
          }
          catch (Exception e) {
          }
        }

      }.execute();
      return WAITING;
    }
  },
  PLAYING {

    Coord from = null;

    @Override
    public GameState mouseReleased(ChessPanel cp, MouseEvent e) {
      Coord b = from;
      Coord c = null;
      from = null;
      if (b == null || (c = getCoord(e)) == null) {
        return this;
      }
      return move(cp, b, c);
    }

    @Override
    public GameState mousePressed(ChessPanel cp, MouseEvent e) {
      from = getCoord(e);
      return this;
    }

    @Override
    protected GameState continueGame(ChessPanel cp) {
      return this;
    }
  },
  VICTORY {

    @Override
    public void paint(ChessPanel cp, Graphics g) {
      super.paint(cp, g);
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

    @Override
    protected GameState continueGame(ChessPanel cp) {
      return null;
    }
  },
  STALE {

    @Override
    public void paint(ChessPanel cp, Graphics g) {
      super.paint(cp, g);
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

    @Override
    protected GameState continueGame(ChessPanel cp) {
      return null;
    }
  };

  protected Coord getCoord(MouseEvent e) {
    int x = e.getX() - 8;
    int y = 240 - e.getY() - 8;
    Coord c = new Coord(x / 28, y / 28);
    return c.inBoard() ? c : null;
  }

  public void paint(ChessPanel cp, Graphics g) {
    g.drawImage(cp.bg, 0, 0, null);
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Piece p = cp.b.getPiece(new Coord(i, j));
        if (p != null) {
          g.drawImage(cp.pieces[p.getColor().ordinal()][p.getType().ordinal()],
              8 + i * 28, 240 - 36 - j * 28, null);
        }
      }
    }
  }

  protected GameState move(ChessPanel cp, Coord from, Coord to) {
    if (cp.b.move(from, to, true)) {
      cp.repaint();
      switch (cp.b.getState()) {
        case ONGOING:
          return continueGame(cp);
        case CHECKMATE:
          return VICTORY;
        case DRAW:
          return STALE;
      }
    }
    return this;
  }

  abstract protected GameState continueGame(ChessPanel cp);
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
    state = GameState.PLAYINGAI;
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
    }
    addMouseListener(this);
    b = new Board();
  }
  
  public void setState(GameState gameState) {
    state = gameState;
    
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
  }
}
