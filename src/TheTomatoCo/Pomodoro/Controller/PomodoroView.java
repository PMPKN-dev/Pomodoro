package TheTomatoCo.Pomodoro.Controller;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PomodoroView {
    private final Label timerLabel, whatsNextTODO;
    private final Button startPauseButton;
    private final Scene scene;

    public PomodoroView(){

        whatsNextTODO = new Label();
        HBox hBox = new HBox(whatsNextTODO);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        timerLabel = new Label("25:00");
        timerLabel.setFont(Font.font("Verdana", FontWeight.BOLD,70));
        startPauseButton = new Button("Start/Pause");
        VBox vBox = new VBox(timerLabel,startPauseButton, hBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        scene = new Scene(vBox,400,400);

    }
    public Scene getScene(){return scene;}
    public Label getWhatsNextTODO(){return whatsNextTODO;}
    public Label getTimerLabel(){return timerLabel;}
    public Button getStartPauseButton(){return startPauseButton;}


}
