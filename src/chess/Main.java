
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
      System.out.println("It is now " + b.getCurrentPlayer()
          + "'s turn to play!");
      try {
        System.out
            .println("Please enter the coordinates of the piece you would like to move:");
        cmd = in.readLine();
        while (selectedPiece == null) {
          try {
            selectedPiece = new Coord(cmd);
          }
          catch (InvalidFormatException e) {
            System.out.println(e.getMessage());
          }
        }
        System.out
            .println("Please enter the coordinates of the destination you would like to move to:");
        cmd = in.readLine();
        while (selectedDestination == null) {
          try {
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
      if (!b.move(selectedPiece, selectedDestination)) {
        System.out.println("Incorrect move!");
      }
    }
  }
}
