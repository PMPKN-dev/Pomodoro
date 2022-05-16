package Hub.Pomodoro.Controller;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

public class Pomodoro extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hub/Pomodoro/View/Pomodoro.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent,600,400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
