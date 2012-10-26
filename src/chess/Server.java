
package chess;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import ChessProtocol.ChessMessage;

public class Server implements Runnable {

  public static void main(String[] args) {
    ServerSocket s = null;
    try {
      s = new ServerSocket(6969);
      while (true) {
        new Thread(new Server(s.accept(), s.accept())).start();
        System.out.println("created new thread for a game");
      }
    }
    catch (Exception e) {
      System.out.println("Couldn't make socket");
    }
  }

  InputStream  whiteIn;

  OutputStream whiteOut;

  InputStream  blackIn;

  OutputStream blackOut;

  Socket       white;

  Socket       black;
  
  byte[] bytes;

  Server(Socket white, Socket black) {
    this.white = white;
    this.black = black;
    try {
      whiteIn = white.getInputStream();
      whiteOut = white.getOutputStream();
      blackIn = black.getInputStream();
      blackOut = black.getOutputStream();
      System.out.println("Done with game constructor");
    }
    catch (Exception e) {
    }
  }

  public void run() {
    try {
      ChessMessage.Info info = ChessMessage.Info.newBuilder().setColor(true).build();
      whiteOut.write(info.getSerializedSize());
      whiteOut.write(info.toByteArray());
      info = ChessMessage.Info.newBuilder().setColor(false).build();
      blackOut.write(info.getSerializedSize());
      blackOut.write(info.toByteArray());
      System.out.println("Sent color info");
      while (true) {
        bytes = new byte[whiteIn.read()];
        whiteIn.read(bytes);
        info = ChessMessage.Info.parseFrom(bytes);
        System.out.println("Received white's move");
        if (info.hasEndGame()) {
          break;
        }
        blackOut.write(info.getSerializedSize());
        blackOut.write(info.toByteArray());
        System.out.println("Relayed to black");
        bytes = new byte[blackIn.read()];
        blackIn.read(bytes);
        info = ChessMessage.Info.parseFrom(bytes);
        System.out.println("Received black's move");
        if (info.hasEndGame()) {
          break;
        }
        whiteOut.write(info.getSerializedSize());
        whiteOut.write(info.toByteArray());
        System.out.println("Relayed to white");
      }
      whiteIn.close();
      whiteOut.close();
      blackIn.close();
      blackOut.close();
      white.close();
      black.close();
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
