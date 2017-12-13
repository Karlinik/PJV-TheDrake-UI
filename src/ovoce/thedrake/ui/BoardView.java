package ovoce.thedrake.ui;

import ovoce.thedrake.game.GameState;
import ovoce.thedrake.game.Move;
import ovoce.thedrake.game.TilePosition;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class BoardView extends GridPane implements TileContext {
  private GameState state;
  private TileView selected;
  
  public BoardView(GameState state) {
	this.state = state;
	
	this.setHgap(5);
	this.setVgap(5);
	this.setPadding(new Insets(5));
	
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
  
  
}
