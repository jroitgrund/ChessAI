
package chess;

public enum pieceColor {
  B, W;

  pieceColor opposite() {
    if (this == B) {
      return W;
    }
    return B;
  }
}
