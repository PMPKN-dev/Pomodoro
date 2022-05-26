package TheTomatoCo.Pomodoro.Controller;

import TheTomatoCo.Foundation.FXControls;
import TheTomatoCo.Foundation.Program;
import javafx.animation.*;
import javafx.beans.property.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Pomodoro extends Program {




    //private class constant and variables
    private static final Integer startTime = 15; //Placeholder until I figure out min:sec
    //TODO: create a binding to a Label that updates the Label text as the chosen Min:sec
    //TODO: make the chosen Min:Sec part of what user can edit
    private Timeline timeline;
    private final Label timerLabel = new Label();
    private final IntegerProperty timeSeconds = new SimpleIntegerProperty(startTime); //sets up binding
    Group initialScreen = new Group();
    Group pomodoroScreen = new Group();

    @Override
    public void expand(){
        initialScreen();
        pomodoroSetup();
    }

    /*
    public static void main(String[] args) {
        Program.launch(args);
    }

     */

    private void initialScreen(){
        Text text = new Text();
        FXControls.setPosition(text,40,60);
        text.setText("Welcome to Pomodoro");
        //TODO: Insert login here

        initialScreen.getChildren().add(text);

        //change view
        Button enterPomodoro = new Button();
        FXControls.setButton(enterPomodoro,100,100,"Enter Pomodoro");
        enterPomodoro.setOnAction(event -> changeGroup(initialScreen, pomodoroScreen));
        initialScreen.getChildren().add(enterPomodoro);
        getUiRoot().getChildren().add(initialScreen);
    }

    private void pomodoroSetup(){

        //Cancel button
        Button cancel = new Button();
        FXControls.setButton(cancel,20,20,"Cancel");
        cancel.setOnAction(event -> returnToInitial(pomodoroScreen));
        pomodoroScreen.getChildren().add(cancel);

        //Label configuration
        timerLabel.setLayoutX(250);
        timerLabel.setLayoutY(100);
        timerLabel.setText(timeSeconds.toString());
        timerLabel.setTextFill(Color.RED);
        timerLabel.setStyle("-fx-font-size: 4em");
        timerLabel.textProperty().bind(timeSeconds.asString()); //timerLabel shows current value of timeSeconds
        //Binding: whenever the value of timeSeconds changes, the timerLabel text also changes
        pomodoroScreen.getChildren().add(timerLabel);

        //button
        Button startButton = new Button();
        startButton.setLayoutX(250);
        startButton.setLayoutY(250);
        startButton.setText("Start");
        startButton.setOnAction(actionEvent -> {
            if(timeline != null){
                timeline.stop();
            }
            //sets the chosen time as the starting time
            //TODO: make it work with minutes too
            timeSeconds.set(startTime);
            timeline = new Timeline();
            //sets duration of timeline/counter to startTime and makes 0 the final possible value
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(startTime+1),new KeyValue(timeSeconds,0)));
            timeline.playFromStart();
            //When you click start button it starts again. This is placeholder until breaks are added
            //TODO: Add breaks

        });
        pomodoroScreen.getChildren().add(startButton);

    }




    private void changeGroup(Group currentGroup, Group targetGroup){
        removeGroup(currentGroup);
        loadGroup(targetGroup);
    }
    private void removeGroup(Group group){
        getUiRoot().getChildren().removeAll(group);
    }
    private void loadGroup(Group group){
        getUiRoot().getChildren().addAll(group);
    }
    private void returnToInitial(Group currentGroup){
        changeGroup(currentGroup,initialScreen);
    }








    /*
    private String secondsToString(int seconds) {
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    
     */


}
