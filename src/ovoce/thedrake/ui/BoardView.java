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
    public GameState state;
    private TileView selected;

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

        if (state.isVictory()) System.out.println("Victory!!!");

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
            if (state.sideOnTurn() == PlayingSide.BLUE) {
                state = ((PlacingGuardsGameState) state).placeGuard(move.target());
                stackContextBlue.removeBorder();
                stackContextBlue.changeTroop(state.troopStacks().peek(PlayingSide.BLUE));
            }
            else {
                state = ((PlacingGuardsGameState) state).placeGuard(move.target());
                stackContextOrange.removeBorder();
                stackContextOrange.changeTroop(state.troopStacks().peek(PlayingSide.ORANGE));
            }
        }
        else {
            if (stackSelected) {
                if (state.sideOnTurn() == PlayingSide.BLUE) {
                    state = ((MiddleGameState) state).placeFromStack(move.target());
                    stackContextBlue.removeBorder();
                    stackContextBlue.changeTroop(state.troopStacks().peek(PlayingSide.BLUE));
                }
                else {
                    state = ((MiddleGameState) state).placeFromStack(move.target());
                    stackContextOrange.removeBorder();
                    stackContextOrange.changeTroop(state.troopStacks().peek(PlayingSide.ORANGE));
                }
            }
            else {
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



//	private Board createTestBoard() {
//		StandardDrakeSetup setup = new StandardDrakeSetup();
//		Board board = new Board(
//				4,
//				new CapturedTroops(),
//				new TroopTile(new TilePosition("a1"), new Troop(setup.MONK, PlayingSide.BLUE)),
//				new TroopTile(new TilePosition("b1"), new Troop(setup.DRAKE, PlayingSide.BLUE)),
//				new TroopTile(new TilePosition("a2"), new Troop(setup.SPEARMAN, PlayingSide.BLUE)),
//				new TroopTile(new TilePosition("c2"), new Troop(setup.CLUBMAN, PlayingSide.BLUE)),
//				new TroopTile(new TilePosition("a4"), new Troop(setup.ARCHER, PlayingSide.ORANGE, TroopFace.BACK)),
//				new TroopTile(new TilePosition("b4"), new Troop(setup.DRAKE, PlayingSide.ORANGE, TroopFace.BACK)),
//				new TroopTile(new TilePosition("c3"), new Troop(setup.SWORDSMAN, PlayingSide.ORANGE)));
//		return board;
//	}
//
//	private GameState createTestGame() {
//		Board board = createTestBoard();
//		StandardDrakeSetup setup = new StandardDrakeSetup();
//		return new MiddleGameState(
//				board,
//				new BasicTroopStacks(setup.CLUBMAN),
//				new BothLeadersPlaced(new TilePosition("b1"), new TilePosition("b4")),
//				PlayingSide.BLUE);
//	}
}
