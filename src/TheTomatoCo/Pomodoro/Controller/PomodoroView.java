package TheTomatoCo.Pomodoro.Controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class PomodoroView {
    private final Label timerLabel, whatsNextTODO;
    private final Button startPauseButton;
    private final Scene scene;
    private final HBox timerOptionsContainer;
    private final Text pomodoroTimer, shortBreakTimer, longBreakTimer;

    public PomodoroView(){
        timerOptionsContainer = new HBox();
        pomodoroTimer = new Text("Pomodoro");
        shortBreakTimer = new Text("Short Break");
        longBreakTimer = new Text("Long Break");

        timerOptionsContainer.setPadding(new Insets(20,10,20,10));
        timerOptionsContainer.setSpacing(50);
        timerOptionsContainer.setAlignment(Pos.CENTER);
        timerOptionsContainer.getChildren().addAll(pomodoroTimer,shortBreakTimer,longBreakTimer);



        //currently not in use TODO: use it
        whatsNextTODO = new Label();
        HBox hBox = new HBox(whatsNextTODO);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);



        timerLabel = new Label("25:00"); //default
        timerLabel.setFont(Font.font("Verdana", FontWeight.BOLD,70));
        startPauseButton = new Button("Start/Pause");
        VBox vBox = new VBox(timerLabel,startPauseButton, timerOptionsContainer);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        scene = new Scene(vBox,400,400);

    }
    public Scene getScene(){return scene;}
    public Label getWhatsNextTODO(){return whatsNextTODO;}
    public Label getTimerLabel(){return timerLabel;}
    public Button getStartPauseButton(){return startPauseButton;}


}
