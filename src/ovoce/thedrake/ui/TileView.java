package ovoce.thedrake.ui;

import ovoce.thedrake.game.Move;
import ovoce.thedrake.game.Tile;
import ovoce.thedrake.game.TilePosition;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class TileView extends Pane {
  private final TileContext context;
  private final TilePosition position;
  private Tile tile;
  private Move move;
  
  private final ImageView moveImage;
  private final TileBackgrounds backgrounds;
  private final Border selectBorder = new Border(
		  new BorderStroke(
				  Color.BLACK, 
				  BorderStrokeStyle.SOLID, 
				  CornerRadii.EMPTY, 
				  new BorderWidths(3)));
  private final Border defaultBorder = new Border(
          new BorderStroke(
                  Color.BLACK,
                  BorderStrokeStyle.SOLID,
                  CornerRadii.EMPTY,
                  new BorderWidths(1)));
  
  public TileView(TileContext context, Tile tile) {
	this.context = context;
	this.tile = tile;
	this.position = tile.position();
	this.backgrounds = new TileBackgrounds();
	
	this.moveImage = new ImageView(getClass().getResource("/assets/move.png").toString());
	moveImage.setVisible(false);
	
	this.setPrefSize(100, 100);
	this.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
	this.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
	this.setBorder(defaultBorder);
	this.getChildren().add(moveImage);
	this.setOnMouseClicked(new EventHandler<MouseEvent>() {
	  @Override
	  public void handle(MouseEvent event) {
		onClicked();
	  }
	});
	update();
  }

  public TilePosition position() {
	return position;
  }

  public void setTile(Tile tile) {
	this.tile = tile;
  }
  
  public void update() {
	this.setBackground(backgrounds.get(tile));
  }
  
  public void select() {
	this.setBorder(selectBorder);
	context.tileSelected(this);
  }
  
  public void unselect() {
	this.setBorder(defaultBorder);
  }

  public void setMove(Move move) {
	this.move = move;
	moveImage.setVisible(true);
  }
  
  public void clearMove() {
	this.move = null;
	moveImage.setVisible(false);
  }
  
  private void onClicked() {
      if(move != null)
	  context.execute(move);
	else if(tile.hasTroop())
	  select();
  }
}
