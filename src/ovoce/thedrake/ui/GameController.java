package ovoce.thedrake.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import ovoce.thedrake.game.*;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends TheDrakeApplication implements Initializable, LabelContext {
    private GameState state;

    @FXML private TroopStackView troopStackBlue;
    @FXML private TroopStackView troopStackOrange;
    @FXML private BoardView boardView;

    @FXML private Label mainLabel;
    @FXML private Label capturedBlueLabel;
    @FXML private Label capturedOrangeLabel;

    private int capturedBlue;
    private int capturedOrange;

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
        boardView.setLabelContext(this);
        capturedBlue = 0;
        capturedOrange = 0;
        capturedBlueLabel.setText(Integer.toString(capturedBlue));
        capturedOrangeLabel.setText(Integer.toString(capturedOrange));
    }

    public void blueStackClicked() {
        boardView.stackSelected = true;
        if (boardView.selected != null) boardView.selected.unselect();
        if (boardView.state.sideOnTurn() == PlayingSide.BLUE) {
            troopStackBlue.addBorder();
            boardView.showMoves(boardView.state.allMoves());
        }
    }

    public void orangeStackClicked() {
        boardView.stackSelected = true;
        if (boardView.selected != null) boardView.selected.unselect();
        if (boardView.state.sideOnTurn() == PlayingSide.ORANGE) {
            troopStackOrange.addBorder();
            boardView.showMoves(boardView.state.allMoves());
        }
    }

    public void backToMenu() throws Exception {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("menu/menu.fxml")));
        mainStage.setScene(scene);
        mainStage.show();
    }

    @Override
    public void setMainLabel(String label) {
        mainLabel.setText(label);
    }

    @Override
    public void addCapturedBlue() {
        capturedBlueLabel.setText(Integer.toString(++capturedBlue));
    }

    @Override
    public void addCapturedOrange() {
        capturedOrangeLabel.setText(Integer.toString(++capturedOrange));
    }
}
