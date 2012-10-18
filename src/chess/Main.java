
package chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sun.media.sound.InvalidFormatException;

public class Main {

  public static void main(String[] args) {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    Board b = new Board();
    String cmd = "";
    System.out
        .println("Welcome to this game of chess! Move commands take the following format:");
    System.out.println("<row> <column>, e.g >b 2");
    while (!b.isFinished()) {
      Coord selectedPiece = null;
      Coord selectedDestination = null;
      String currentPlayer = "";
      if (b.getCurrentPlayer() == pieceColor.B) {
        currentPlayer = "black";
      }
      else {
        currentPlayer = "white";
      }
      System.out.println("It is now " + currentPlayer + "'s turn to play!");
      System.out.println(b.display());
      try {
        System.out
            .println("Please enter the coordinates of the piece you would like to move:");
        while (selectedPiece == null) {
          try {
            cmd = in.readLine();
            selectedPiece = new Coord(cmd);
          }
          catch (InvalidFormatException e) {
            System.out.println(e.getMessage());
          }
        }
        System.out
            .println("Please enter the coordinates of the destination you would like to move to:");
        while (selectedDestination == null) {
          try {
            cmd = in.readLine();
            selectedDestination = new Coord(cmd);
          }
          catch (InvalidFormatException e) {
            System.out.println(e.getMessage());
          }
        }
      }
      catch (IOException e) {
        System.out.println("Error while getting command.");
      }
      System.out.println("Trying to move [" + selectedPiece.getCol() + ", " + selectedPiece.getRow() + "] to [" + selectedDestination.getCol() + ", " + selectedDestination.getRow() + "]");
      if (!b.move(selectedPiece, selectedDestination)) {
        System.out.println("Incorrect move!");
      }
    }
  }
}
