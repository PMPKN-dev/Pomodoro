package Hub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Hub extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hub/Pomodoro/View/Pomodoro.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent,600,400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
