
package chess;

import java.awt.Color;
import ChessProtocol.ChessMessage;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
  PLAYINGNETWORK {

    Coord        from = null;
    Coord        to = null;

    Socket       s;

    InputStream  serverIn;

    OutputStream serverOut;
    
    byte[] bytes;
    
    public void setup(ChessPanel cp)
    {
      try {
        s = new Socket("192.168.0.75", 6969);
        System.out.println("Opened socket");
        serverIn = s.getInputStream();
        serverOut = s.getOutputStream();
        System.out.println("waiting for color info");
        bytes = new byte[serverIn.read()];
        serverIn.read(bytes);
        ChessMessage.Info info = ChessMessage.Info.parseFrom(bytes);
        System.out.println("got color info");
        if (!info.getColor())
        {
          bytes = new byte[serverIn.read()];
          serverIn.read(bytes);
          info = ChessMessage.Info.parseFrom(bytes);
          Move enemy = new Move(
              new Coord(info.getMove().getFrom().getCol(), info.getMove().getFrom().getRow()),
              new Coord(info.getMove().getTo().getCol(), info.getMove().getTo().getRow()));
          cp.b.move(enemy.getFrom(), enemy.getTo(), false);
          System.out.println("Am black, and moved");
        }
        
      }
      catch (Exception e) {
        System.out.println("couldn't open socket");
      }
    }

    @Override
    public GameState mouseReleased(ChessPanel cp, MouseEvent e) {
      if (from == null || (to = getCoord(e)) == null) {
        from = null;
        to = null;
        return this;
      }
      return move(cp, from, to);
    }

    @Override
    public GameState mousePressed(ChessPanel cp, MouseEvent e) {
      from = getCoord(e);
      return this;
    }

    @Override
    protected GameState continueGame(final ChessPanel cp) {
      new SwingWorker<GameState, Void>() {

        @Override
        protected GameState doInBackground() throws Exception {
          ChessMessage.Info info = ChessMessage.Info.newBuilder()
          .setMove(ChessMessage.Move.newBuilder()
              .setFrom(ChessMessage.Position.newBuilder()
                  .setCol(from.getCol())
                  .setRow(from.getRow()))
              .setTo(ChessMessage.Position.newBuilder()
                  .setCol(to.getCol())
                  .setRow(to.getRow()))).build();
          serverOut.write(info.getSerializedSize());
          serverOut.write(info.toByteArray());
          bytes = new byte[serverIn.read()];
          serverIn.read(bytes);
          info = ChessMessage.Info.parseFrom(bytes);
          Move enemy = new Move(
              new Coord(info.getMove().getFrom().getCol(), info.getMove().getFrom().getRow()),
              new Coord(info.getMove().getTo().getCol(), info.getMove().getTo().getRow()));
          cp.b.move(enemy.getFrom(), enemy.getTo(), false);
                  
          switch (cp.b.getState()) {
            case ONGOING:
              return PLAYINGAI;
            case CHECKMATE:
              return VICTORY;
            case DRAW:
              return STALE;
          }
          return PLAYINGAI;
        }

        protected void done() {
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

    protected GameState move(ChessPanel cp, Coord from, Coord to) {
      if (cp.b.move(from, to, true)) {
        cp.repaint();
        switch (cp.b.getState()) {
          case ONGOING:
            return continueGame(cp);
          case CHECKMATE:
            sendEnd();
            return VICTORY;
          case DRAW:
            sendEnd();
            return STALE;
        }
      }
      return this;
    }
    
    private void sendEnd()
    {
      new SwingWorker<GameState, Void>() {

        @Override
        protected GameState doInBackground() throws Exception {
          ChessMessage.Info info = ChessMessage.Info.newBuilder().setEndGame(true).build();
          serverOut.write(info.getSerializedSize());
          serverOut.write(info.toByteArray());
          return null;
        }

        protected void done() {
          try {
          serverIn.close();
          serverOut.close();
          s.close();
          } catch(Exception e) {}
        }
      }.execute();
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
      new SwingWorker<GameState, Void>() {

        @Override
        protected GameState doInBackground() throws Exception {
          Move bestMove = Minimax.bestMove(cp.b);
          cp.b.move(bestMove.getFrom(), bestMove.getTo(), false);
          switch (cp.b.getState()) {
            case ONGOING:
              return PLAYINGAI;
            case CHECKMATE:
              return VICTORY;
            case DRAW:
              return STALE;
          }
          return PLAYINGAI;
        }

        protected void done() {
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
      setup(cp);
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
      setup(cp);
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
  public void setup(ChessPanel cp)
  {
    
  }
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
    state = GameState.PLAYINGNETWORK;
    state.setup(this);
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
