package ovoce.thedrake.ui.menu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ovoce.thedrake.game.*;
import ovoce.thedrake.ui.BoardView;
import ovoce.thedrake.ui.TheDrakeApplication;

public class MenuController extends TheDrakeApplication implements Initializable {
    @FXML private Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void showMultiplayer(MouseEvent event) {
        setLabel("Hra dvou hráčů");
    }

    public void startMultiplayer(ActionEvent event) throws Exception  {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../game.fxml")));
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void showComputer(MouseEvent event) {
        setLabel("Hra proti počítači");
    }

    public void showInternet(MouseEvent event) {
        setLabel("Hra na internetu");
    }

    public void showExit(MouseEvent event) {
        setLabel("Konec");
    }

    public void exit(ActionEvent event) {
        System.exit(0);
    }

    public void hideLabel(MouseEvent event) {
        setLabel("");
    }

    private void setLabel(String text) {
        label.setText(text);
    }
}
