package TheTomatoCo.Pomodoro.Controller;

import TheTomatoCo.Foundation.DB;
import TheTomatoCo.Foundation.FXControls;
import TheTomatoCo.Foundation.Program;
import TheTomatoCo.Foundation.SQLHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.*;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class PomodoroTimer extends Program
{
    Group timerGroup = new Group();
    Group timerSettingsGroup = new Group();

    //region Constants
    private TextField pomodoroDurationChange;
    private TextField shortBreakDurationChange;
    private TextField longBreakDurationChange;
    private int pomoTime;
    private int shortBreakTime;
    private int longBreakTime;
    private Text pomodoroTimer, shortBreakTimer, longBreakTimer;
    private HBox timerOptionsContainer;
    private Button pauseButton;
    private Button resumeButton;
    private Label timerLabel;
    private Timer timer = new Timer();
    private int seconds, minutes = 0;
    private int counter = 60 * 25; //placeholder 25min
    private boolean isRunning = false;
    private boolean isBreak = false;
    //endregion

    //TODO: better counter variable, currently resume/start defaults to magic number counter

    @Override
    public void expand(){
        try {
            setUpTimer();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setUpTimerSettings();

    }

    public void setUpTimer() throws SQLException {

        //region Change view to settings
        Button timerSettings = new Button();
        FXControls.setButton(timerSettings,500,20,"Settings");
        timerSettings.setOnAction(event -> changeGroup(timerGroup,timerSettingsGroup));

        timerGroup.getChildren().add(timerSettings);
        //endregion
        //region Change view back to timer
        Button exitTimerSettings = new Button();
        FXControls.setButton(exitTimerSettings,100,100,"Return");
        exitTimerSettings.setOnAction(event -> changeGroup(timerSettingsGroup, timerGroup));

        timerSettingsGroup.getChildren().add(exitTimerSettings);
        //endregion

        getUiRoot().getChildren().add(timerGroup);

        //region setting up the different timer options
        timerOptionsContainer = new HBox();
        pomodoroTimer = new Text("Pomodoro");
        shortBreakTimer = new Text("Short Break");
        longBreakTimer = new Text("Long Break");

        timerOptionsContainer.setPadding(new Insets(50,40,50,40));
        timerOptionsContainer.setSpacing(50);
        timerOptionsContainer.setAlignment(Pos.CENTER);
        timerOptionsContainer.getChildren().addAll(pomodoroTimer,shortBreakTimer,longBreakTimer);

        timerGroup.getChildren().add(timerOptionsContainer);


        //endregion
        //region Switch to pomodoro timer
        pomodoroTimer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    SQLHandler.setConsultantTime(DB.getCon(),pomoTime,shortBreakTime,longBreakTime);
                    counter = 60 * pomoTime; //should work in theory, it doesnt, counter becomes 00:00 TODO: fix me
                    //runTimer();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        //endregion
        //region Switch to short break timer
        shortBreakTimer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    SQLHandler.setConsultantTime(DB.getCon(),pomoTime,shortBreakTime,longBreakTime);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        //endregion
        //region Switch to long break timer
        longBreakTimer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    SQLHandler.setConsultantTime(DB.getCon(),pomoTime,shortBreakTime,longBreakTime);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        //endregion

        //region the timer itself and start/pause button
        timerLabel = new Label(); //default
        timerLabel.setFont(Font.font("Verdana", FontWeight.BOLD,70));
        pauseButton = new Button();
        FXControls.setButton(pauseButton,200,200,"Pause");
        resumeButton = new Button();
        FXControls.setButton(resumeButton,250,200,"Resume/Start");
        VBox vBox = new VBox(timerLabel, pauseButton, resumeButton,timerOptionsContainer);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        pauseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pauseTimer();
            }
        });
        resumeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                runTimer();
            }
        });

        timerGroup.getChildren().add(vBox);
        //endregion

    }

    public void pauseTimer(){timer.cancel();}
    public void runTimer(){
        //region the clock
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    counter--;
                    seconds = counter % 60; //Modulo gives the remainder
                    minutes = counter / 60;

                    //Making timer look pretty
                    if(seconds < 10 && minutes < 10){ //if its 09:09, it knows to put 0 infront of the counters
                        timerLabel.setText("0" + minutes + ":0" + seconds);
                    }else if(minutes < 10){ //if its 09:15, it knows seconds is normal counter
                        timerLabel.setText("0" + minutes + ":" + seconds);
                    }else{
                        timerLabel.setText(minutes + ":" + seconds); //If neither is below 10 in counter
                    }
                });
            }
        },0,1000);
        //endregion
    }

    public void setUpTimerSettings(){
        //region Input changed Pomodoro Duration
        TextField pomodoroDurationChange = new TextField();
        pomodoroDurationChange.setPromptText("Enter desired Pomodoro Time");
        FXControls.setPosition(pomodoroDurationChange,150,150);

        pomodoroDurationChange.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { //takes the vale of the field and if it is not a Decimal Integer ->
                pomodoroDurationChange.setText(newValue.replaceAll("[^\\d]", "")); //it replaces ever non-Decimal-integer with a blank field
            }
        });

        timerSettingsGroup.getChildren().add(pomodoroDurationChange);
        //endregion
        //region Input changed short break duration
        TextField shortBreakDurationChange = new TextField();
        shortBreakDurationChange.setPromptText("Enter desired Short Break Time");
        FXControls.setPosition(shortBreakDurationChange,150,180);

        shortBreakDurationChange.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { //takes the vale of the field and if it is not a Decimal Integer ->
                shortBreakDurationChange.setText(newValue.replaceAll("[^\\d]", "")); //it replaces ever non-Decimal-integer with a blank field
            }
        });

        timerSettingsGroup.getChildren().add(shortBreakDurationChange);
        //endregion
        //region Input changed long break duration
        TextField longBreakDurationChange = new TextField();
        longBreakDurationChange.setPromptText("Enter desired Long Break Time");
        FXControls.setPosition(longBreakDurationChange,150,210);

        longBreakDurationChange.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { //takes the vale of the field and if it is not a Decimal Integer ->
                longBreakDurationChange.setText(newValue.replaceAll("[^\\d]", "")); //it replaces ever non-Decimal-integer with a blank field
            }
        });

        timerSettingsGroup.getChildren().add(longBreakDurationChange);
        //endregion
        //region Save Button
        Button saveChange = new Button();
        FXControls.setButton(saveChange,150,250,"Save Changes");
        saveChange.setOnAction(event -> {
            try {
                SQLHandler.updateConsultant(DB.getCon(),pomodoroDurationChange,shortBreakDurationChange,longBreakDurationChange);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            changeGroup(timerSettingsGroup,timerGroup);
        });
        timerSettingsGroup.getChildren().add(saveChange);
        //endregion

        //getUiRoot().getChildren().add(timerSettingsGroup);
    }

    public boolean isRunning(){return isRunning;}
    public void setRunning(boolean running){isRunning = running;}
    //region group methods
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
    //endregion
    //region to be used for binding
    public void setPomodoroDurationChange(int input){
        this.pomodoroDurationChange.getText();
    }
    public void setShortBreakDurationChange(int input){
        this.shortBreakDurationChange.getText();
    }
    public void setLongBreakDurationChange(int input){
        this.longBreakDurationChange.getText();
    }
    //endregion

}