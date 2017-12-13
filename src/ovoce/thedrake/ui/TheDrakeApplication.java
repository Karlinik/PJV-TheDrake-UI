package ovoce.thedrake.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TheDrakeApplication extends Application {
    protected static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("menu/menu.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("The Drake");
        stage.show();
    }
}
