package ovoce.thedrake.ui;

import javafx.util.Builder;
import ovoce.thedrake.game.*;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class BoardView extends GridPane implements TileContext, Builder {
  private GameState state;
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
	selected.unselect();
	selected = null;
	clearMoves();
	
	this.state = move.resultState();
	for(Node n : getChildren()) {
	  TileView view = (TileView)n;
	  view.setTile(this.state.board().tileAt(view.position()));
	  view.update();
	}
  }

	@Override
	public Object build() {
		GameState state = createTestGame();
		return new BoardView(state);
	}

	private Board createTestBoard() {
		StandardDrakeSetup setup = new StandardDrakeSetup();
		Board board = new Board(
				4,
				new CapturedTroops(),
				new TroopTile(new TilePosition("a1"), new Troop(setup.MONK, PlayingSide.BLUE)),
				new TroopTile(new TilePosition("b1"), new Troop(setup.DRAKE, PlayingSide.BLUE)),
				new TroopTile(new TilePosition("a2"), new Troop(setup.SPEARMAN, PlayingSide.BLUE)),
				new TroopTile(new TilePosition("c2"), new Troop(setup.CLUBMAN, PlayingSide.BLUE)),
				new TroopTile(new TilePosition("a4"), new Troop(setup.ARCHER, PlayingSide.ORANGE, TroopFace.BACK)),
				new TroopTile(new TilePosition("b4"), new Troop(setup.DRAKE, PlayingSide.ORANGE, TroopFace.BACK)),
				new TroopTile(new TilePosition("c3"), new Troop(setup.SWORDSMAN, PlayingSide.ORANGE)));
		return board;
	}

	private GameState createTestGame() {
		Board board = createTestBoard();
		StandardDrakeSetup setup = new StandardDrakeSetup();
		return new MiddleGameState(
				board,
				new BasicTroopStacks(setup.CLUBMAN),
				new BothLeadersPlaced(new TilePosition("b1"), new TilePosition("b4")),
				PlayingSide.BLUE);
	}
}
