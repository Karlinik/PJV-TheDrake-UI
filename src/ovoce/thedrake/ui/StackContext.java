package ovoce.thedrake.ui;

import ovoce.thedrake.game.Troop;

public interface StackContext {
    public void removeBorder();
    public void changeTroop(Troop troopOnTop);
}
