package uk.minersonline.test;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    private int count = 0;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        URL file = Main.class.getClassLoader().getResource("main.fxml");
        Parent root = FXMLLoader.load(file);
        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void printCount(ActionEvent event) {
        count = count + 1;
        event.consume();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Button clicked %s times!".formatted(count), ButtonType.OK);
        alert.show();
    }
}