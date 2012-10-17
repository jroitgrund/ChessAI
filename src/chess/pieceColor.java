package chess;

public enum pieceColor {
  B, W;

  public pieceColor opposite() {
    if (this == B) {
      return W;
    }

    return B;
  }
}
