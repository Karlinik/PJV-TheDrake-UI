package ovoce.thedrake.ui;

import javafx.util.Builder;
import ovoce.thedrake.game.*;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class BoardView extends GridPane implements TileContext, Builder {
    public boolean stackSelected;
    private StackContext stackContextBlue;
    private StackContext stackContextOrange;
    private LabelContext labelContext;
    public GameState state;
    public TileView selected;

    public BoardView() {
        super();
    }

    public BoardView(GameState state) {
        this.state = state;

        this.setHgap(5);
        this.setVgap(5);
        this.setPadding(new Insets(5));
        this.setAlignment(Pos.CENTER);

        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                int i = x;
                int j = 3-y;
                TilePosition pos = new TilePosition(i, j);
                this.add(new TileView(this, state.board().tileAt(pos)), x, y);
            }
        }
    }

    public TileView tileViewAt(TilePosition pos) {
        int index = (3-pos.j)*4+pos.i;
        return (TileView)getChildren().get(index);
    }

    private void clearMoves() {
        for(Node n : getChildren()) {
            TileView view = (TileView)n;
            view.clearMove();
        }
    }

    @Override
    public void tileSelected(TileView view) {
        stackSelected = false;
        stackContextOrange.removeBorder();
        stackContextBlue.removeBorder();

        if(selected != null && selected != view) {
            selected.unselect();
        }

        selected = view;
        clearMoves();
        List<Move> moves = state.boardMoves(view.position());
        for(Move move : moves) {
            tileViewAt(move.target()).setMove(move);
        }
    }

    @Override
    public void execute(Move move) {
        changeState(move);

        clearMoves();
        stackSelected = false;
        stackContextOrange.removeBorder();
        stackContextBlue.removeBorder();

        if (state.isVictory()) labelContext.setMainLabel(state.sideOnTurn().opposite() + " vyhrává!");

            for(Node n : getChildren()) {
            TileView view = (TileView)n;
            view.setTile(this.state.board().tileAt(view.position()));
            view.update();
        }
    }

    private void changeState(Move move) {
        StandardDrakeSetup setup = new StandardDrakeSetup();

        if (state instanceof PlacingLeadersGameState) {
            if (state.sideOnTurn() == PlayingSide.BLUE) {
                state = ((PlacingLeadersGameState) state).placeBlueLeader(move.target());
                stackContextBlue.removeBorder();
                stackContextBlue.changeTroop(state.troopStacks().peek(PlayingSide.BLUE));
            }
            else {
                state = ((PlacingLeadersGameState) state).placeOrangeLeader(move.target());
                stackContextOrange.removeBorder();
                stackContextOrange.changeTroop(state.troopStacks().peek(PlayingSide.ORANGE));
            }
        }
        else if (state instanceof PlacingGuardsGameState) {
            state = ((PlacingGuardsGameState) state).placeGuard(move.target());
            if (state.sideOnTurn().opposite() == PlayingSide.BLUE) {
                stackContextBlue.removeBorder();
                stackContextBlue.changeTroop(state.troopStacks().peek(PlayingSide.BLUE));
            }
            else {
                stackContextOrange.removeBorder();
                stackContextOrange.changeTroop(state.troopStacks().peek(PlayingSide.ORANGE));
            }
        }
        else {
            if (stackSelected) {
                state = ((MiddleGameState) state).placeFromStack(move.target());
                if (state.sideOnTurn().opposite() == PlayingSide.BLUE) {
                    stackContextBlue.removeBorder();
                    if (!state.troopStacks().troops(PlayingSide.BLUE).isEmpty()) stackContextBlue.changeTroop(state.troopStacks().peek(PlayingSide.BLUE));
                    else stackContextBlue.lastTroop();
                }
                else {
                    stackContextOrange.removeBorder();
                    if (!state.troopStacks().troops(PlayingSide.ORANGE).isEmpty()) stackContextOrange.changeTroop(state.troopStacks().peek(PlayingSide.ORANGE));
                    else stackContextOrange.lastTroop();
                }
            }
            else {
                if (state.board().tileAt(move.target()).hasTroop())
                    if (state.sideOnTurn() == PlayingSide.BLUE) labelContext.addCapturedBlue();
                    else labelContext.addCapturedOrange();
                selected.unselect();
                selected = null;
                this.state = move.resultState();
            }
        }

    }

    @Override
    public Object build() {
        StandardDrakeSetup setup = new StandardDrakeSetup();
        return new BoardView(new PlacingLeadersGameState(new BasicTroopStacks(setup.DRAKE, setup.CLUBMAN, setup.CLUBMAN, setup.ARCHER, setup.SPEARMAN, setup.MONK, setup.SWORDSMAN)));
    }

    public void showMoves(List<Move> moves) {
        for(Move move : moves) {
            tileViewAt(move.target()).setMove(move);
        }
    }

    public void setStackContexts(StackContext stackContextBlue, StackContext stackContextOrange) {
        this.stackContextBlue = stackContextBlue;
        this.stackContextOrange = stackContextOrange;
    }

    public void setLabelContext(LabelContext labelContext) {
        this.labelContext = labelContext;
    }
}
