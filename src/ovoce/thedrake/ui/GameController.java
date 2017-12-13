package ovoce.thedrake.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.GridPane;
import ovoce.thedrake.game.*;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends TheDrakeApplication implements Initializable {
    @FXML private SubScene gameScene;
    @FXML private GridPane gameBoard;

    public GameController() {
        GameState state = createTestGame();
        this.gameBoard = new BoardView(state);
//        this.gameScene.setRoot(new Parent(gameBoard));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

//    public void start() {
//        GameState state = createTestGame();
//
//        BoardView boardView = new BoardView(state);
//        Scene scene = new Scene(boardView);
//        mainStage.setScene(scene);
//        mainStage.show();
//    }

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
