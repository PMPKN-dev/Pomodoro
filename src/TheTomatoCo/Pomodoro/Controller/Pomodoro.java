package TheTomatoCo.Pomodoro.Controller;

import TheTomatoCo.Foundation.DB;
import TheTomatoCo.Foundation.FXControls;
import TheTomatoCo.Foundation.Program;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Pomodoro extends Program {

    /**
     * DONE - Step 1: Have a default label with 25:00 for pomodoro, 5:00 for short break and 10:00 for long break
     * TODO: Step 2: Create individual logins for each consultant that have their data saved in database for next usage
     * TODO: Step 3: Setup binding so that if in settings you change min:sec, it updates automatically for that particular user
     * TODO: Step 4: Option for sounds when timer runs out
     * TODO: Step 5: Figure out how the heck Group-changing works, because it would make the code more smooth
     */



    //private class constant and variables
    //private static final Integer startTime = 15; //Placeholder until I figure out min:sec
    //TODO: create a binding to a Label that updates the Label text as the chosen Min:sec
    //TODO: make the chosen Min:Sec part of what user can edit
    //private Timeline timeline;
    //private final Label timerLabel = new Label();
    //private final IntegerProperty timeSeconds = new SimpleIntegerProperty(startTime); //sets up binding
    Group pomodoroScreen = new Group();


    @Override
    public void expand(){

        pomodoroSetup();

    }


    /*
    //@Override
    public void start(Stage stage){
        PomodoroView pomodoroView = new PomodoroView();
        PomodoroModel pomodoroModel = new PomodoroModel(pomodoroView.getTimerLabel(),pomodoroView.getWhatsNextTODO());
        new PomodoroController(pomodoroModel,pomodoroView);
        stage.setTitle("Wonder if it works");
        stage.setScene(pomodoroView.getScene());
        stage.show();
    }
     */




    private void pomodoroSetup(){
        //Cancel button
        //Button cancel = new Button();
        //FXControls.setButton(cancel,450,20,"Cancel");
        //cancel.setAlignment(Pos.BOTTOM_CENTER);
        //cancel.setOnAction(event -> returnToInitial(pomodoroScreen));
        //pomodoroScreen.getChildren().add(cancel);

        //TODO: make this work, it currently wont show up, addChildren wont work
        /*Fixme it doesn't work because PomodoroView is not a Node. I can't say about the PomodoroModel but
            it will be easier to implement the Nodes from PomodoroView inside of this class instead.
         */
        PomodoroView pomodoroView = new PomodoroView();
        PomodoroModel pomodoroModel = new PomodoroModel(pomodoroView.getTimerLabel(),pomodoroView.getWhatsNextTODO());
        PomodoroController con = new PomodoroController(pomodoroModel,pomodoroView);
        //pomodoroScreen.getChildren().add(pomodoroView);








        /*
        Label configuration
        timerLabel.setLayoutX(250);
        timerLabel.setLayoutY(100);
        timerLabel.setText(timeSeconds.toString());
        timerLabel.setTextFill(Color.RED);
        timerLabel.setStyle("-fx-font-size: 4em");
        timerLabel.textProperty().bind(timeSeconds.asString()); //timerLabel shows current value of timeSeconds
        Binding: whenever the value of timeSeconds changes, the timerLabel text also changes
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



        });
        pomodoroScreen.getChildren().add(startButton);


         */
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
    /*private void returnToInitial(Group currentGroup){
        changeGroup(currentGroup,initialScreen);
    }

     */








    /*
    private String secondsToString(int seconds) {
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

     */


}
