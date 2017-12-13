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
//        this.gameBoard = new BoardView(state);
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
}
