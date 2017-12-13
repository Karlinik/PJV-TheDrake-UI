package ovoce.thedrake.ui;

import ovoce.thedrake.game.Move;

public interface TileContext {
  public void tileSelected(TileView view);
  public void execute(Move move);
}
