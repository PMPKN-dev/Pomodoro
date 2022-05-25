package TheTomatoCo.Pomodoro.Controller;

import TheTomatoCo.Foundation.Program;
import javafx.animation.*;
import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Pomodoro extends Program {




    //private class constant and variables
    private static final Double startTime = 15.0;
    private Timeline timeline;
    private final Label timerLabel = new Label();
    private final DoubleProperty timeMinutes = new SimpleDoubleProperty(startTime); //sets up binding


    public static void main(String[] args) {
        Program.launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        //setup stage and scene
        primaryStage.setTitle("Timer");
        Group root = new Group();
        Scene scene = new Scene(root, 300,250);


        //Label configuration
        timerLabel.setText(timeMinutes.toString());
        timerLabel.setTextFill(Color.RED);
        timerLabel.setStyle("-fx-font-size: 4em;");
        timerLabel.textProperty().bind(timeMinutes.asString()); //timerLabel shows current value of timeSeconds
        //Binding: whenever the value of timeSeconds changes, the timerLabel text also changes

        //button
        Button button = new Button();
        button.setText("Start");
        button.setOnAction(actionEvent -> {
            if(timeline != null){
                timeline.stop();
            }
            timeMinutes.set(startTime);
            timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(startTime+1),new KeyValue(timeMinutes,0)));
            timeline.playFromStart();

        });
        //configure vbox
        VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);
        vb.setPrefWidth(scene.getWidth());
        vb.setLayoutY(30);
        vb.getChildren().addAll(button,timerLabel);
        root.getChildren().add(vb);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Calc of min and sec
     */
    private String secondsToString(int seconds) {
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }


}
