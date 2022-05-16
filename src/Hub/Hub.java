package Hub;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.stage.*;

public class Hub extends Application {

    @FXML
    Canvas GridCanvasPomodoro;
    @FXML
    Canvas GridCanvasDoToday;
    @FXML
    Canvas GridCanvasOfficeOverview;
    @FXML
    Canvas GridCanvasAdmin;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hub/Hub.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent,400,200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Crazy name you won't believe");
        primaryStage.show();

        //TODO, make the 4 grids render icons for the programs and add onClick launching for the programs
        //TODO, (will require popups to run the fxml files of the other programs)


    }
}
