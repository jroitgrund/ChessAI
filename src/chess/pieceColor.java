
package chess;

public enum pieceColor {
  W, B;

  pieceColor opposite() {
    if (this == B) {
      return W;
    }
    return B;
  }
}
