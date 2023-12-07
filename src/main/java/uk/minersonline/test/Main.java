package uk.minersonline.test;

import com.flexganttfx.extras.GanttChartStatusBar;
import com.flexganttfx.extras.GanttChartToolBar;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.flexganttfx.model.Activity;
import com.flexganttfx.model.Layer;
import com.flexganttfx.model.Row;
import com.flexganttfx.model.activity.MutableActivityBase;
import com.flexganttfx.view.GanttChart;


public class Main extends Application {
    private int count = 0;

    public static void main(String[] args) {
        launch();
    }

    class ModelObject<
            P extends Row<?,?,?>, // Type of parent row
            C extends Row<?,?,?>, // Type of child rows
            A extends Activity> extends Row<P, C, A> { }

    class Fleet extends ModelObject<Row<?,?,?>, Aircraft, Activity> { }

    class Aircraft extends ModelObject<Fleet, Fleet, Flight> { }

    class Flight extends MutableActivityBase<Object> { }

    @Override
    public void start(Stage stage) throws IOException {
        Fleet fleet = new Fleet();
        fleet.setExpanded(true);
        GanttChart<ModelObject<?,?,?>> gantt = new GanttChart<>(fleet);

        // Layers based on service type.
        Layer cargoLayer = new Layer("Cargo");
        Layer trainingLayer = new Layer("Training");
        Layer charterLayer = new Layer("Charter");
        gantt.getLayers().addAll(cargoLayer, trainingLayer, charterLayer);

        // Create the aircrafts.
        Aircraft aircraft1 = new Aircraft();
        Aircraft aircraft2 = new Aircraft();

        // Add the aircrafts to the fleet.
        fleet.getChildren().addAll(aircraft1, aircraft2);

        // Create the flights
        Flight flight1 = new Flight(); // a cargo flight
        Flight flight2 = new Flight(); // a training flight
        Flight flight3 = new Flight(); // a charter flight

        aircraft1.addActivity(cargoLayer, flight1);
        aircraft1.addActivity(trainingLayer, flight2);
        aircraft2.addActivity(charterLayer, flight3);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(new GanttChartToolBar<>(gantt));
        borderPane.setCenter(gantt);
        borderPane.setBottom(new GanttChartStatusBar<>(gantt));

        stage.setTitle("Test");
        Scene scene = new Scene(borderPane, 640, 480);
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