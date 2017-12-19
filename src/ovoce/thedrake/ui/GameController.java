package ovoce.thedrake.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import ovoce.thedrake.game.*;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends TheDrakeApplication implements Initializable {
    @FXML private TroopStackView troopStackBlue;
    @FXML private TroopStackView troopStackOrange;
    @FXML private BoardView boardView;

    public GameController() {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createNewGame();
    }

    private void createNewGame() {
        StandardDrakeSetup setup = new StandardDrakeSetup();
        troopStackBlue.changeTroop(new Troop(setup.DRAKE, PlayingSide.BLUE));
        troopStackOrange.changeTroop(new Troop(setup.DRAKE, PlayingSide.ORANGE));
        boardView.setStackContexts(troopStackBlue, troopStackOrange);

    }

    public void blueStackClicked() {
        boardView.stackSelected = true;
        if (boardView.state.sideOnTurn() == PlayingSide.BLUE) {
            troopStackBlue.addBorder();
            boardView.showMoves(boardView.state.allMoves());
        }
    }

    public void orangeStackClicked() {
        boardView.stackSelected = true;
        if (boardView.state.sideOnTurn() == PlayingSide.ORANGE) {
            troopStackOrange.addBorder();
            boardView.showMoves(boardView.state.allMoves());
        }
    }
}
