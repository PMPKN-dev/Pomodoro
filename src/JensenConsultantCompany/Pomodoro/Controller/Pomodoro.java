package JensenConsultantCompany.Pomodoro.Controller;

import JensenConsultantCompany.Foundation.*;
import JensenConsultantCompany.Hub.Controller.LoginData;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class Pomodoro extends Program {
    Group timerGroup = new Group();
    Group timerSettingsGroup = new Group();

    //region Constants
    private Button pauseButton, resumeButton = new Button();
    private Text pomodoroTimer, shortBreakTimer, longBreakTimer;
    private HBox timerOptionsContainer;
    private Label timerLabel = new Label();
    private Timer timer = new Timer();
    private boolean sound = true;
    private int timeLeft = 0;
    private int seconds = 0;
    private int minutes = 0;
    private String seconds_string = String.format("%02d", seconds);
    private String minutes_string = String.format("%02d", minutes);
    Connection con = DB.getCon();
    //endregion
    LoginData LoginID = LoginData.getInstance();


    @Override
    /**
     * Overrides the expand() within the Program Class
     */
    public void expand() {

        try {
            setUpTimer();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setUpTimerSettings();
        super.userNameText.setText(LoginID.getUserID()+"");
    }

    private void setUpTimer() throws SQLException {

        //region Change view to settings
        Button timerSettings = new Button();
        FXControls.setButton(timerSettings, 500, 100, "Settings");
        timerSettings.setOnAction(event -> changeGroup(timerGroup, timerSettingsGroup));

        timerGroup.getChildren().add(timerSettings);
        //endregion
        //region Change view back to timer
        Button exitTimerSettings = new Button();
        FXControls.setButton(exitTimerSettings, 100, 100, "Return");
        exitTimerSettings.setOnAction(event -> changeGroup(timerSettingsGroup, timerGroup));

        timerSettingsGroup.getChildren().add(exitTimerSettings);
        //endregion

        getUiRoot().getChildren().add(timerGroup);

        //region setting up the different timer options
        timerOptionsContainer = new HBox();
        pomodoroTimer = new Text("Pomodoro");
        shortBreakTimer = new Text("Short Break");
        longBreakTimer = new Text("Long Break");

        timerOptionsContainer.setPadding(new Insets(50, 40, 50, 40));
        timerOptionsContainer.setSpacing(50);
        timerOptionsContainer.setAlignment(Pos.CENTER);
        timerOptionsContainer.getChildren().addAll(pomodoroTimer, shortBreakTimer, longBreakTimer);

        timerGroup.getChildren().add(timerOptionsContainer);


        //endregion


        //region Switch to pomodoro timer updated through database
        pomodoroTimer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    int updatedPomodoro = SQLHandler.setPomodoroTime(con, LoginID.getUserID());
                    timerLabel.setText(updatedPomodoro + ":" + seconds_string);
                    timeLeft = (int) Double.parseDouble(timerLabel.getText());

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        //endregion
        //region Switch to short break timer updated through database
        shortBreakTimer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int updatedShortBreak = SQLHandler.setShortBreakTime(con, LoginID.getUserID());
                    timerLabel.setText(updatedShortBreak + ":" + seconds_string);
                    timeLeft = (int) Double.parseDouble(timerLabel.getText());
                    startTimer();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        //endregion
        //region Switch to long break timer updated through database
        longBreakTimer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int updatedLongBreak = SQLHandler.setLongBreakTime(con, LoginID.getUserID());
                    timerLabel.setText(updatedLongBreak + ":" + seconds_string);
                    timeLeft = (int) Double.parseDouble(timerLabel.getText());

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        //endregion

        //then it needs to count down the seconds from 59-0 and when sec reaches 0, -1 for minute and reset to 59sec
        //It also needs to recognize if the current timer is a break or work

        //region the timer itself and start/pause button
        timerLabel.setText(minutes_string + ":" + seconds_string);
        timerLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 70));
        pauseButton = new Button();
        FXControls.setButton(pauseButton, 200, 200, "Pause");
        resumeButton = new Button();
        FXControls.setButton(resumeButton, 250, 200, "Resume/Start");
        VBox vBox = new VBox(timerLabel, pauseButton, resumeButton, timerOptionsContainer);
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
                //startTimer();
                //runTimer();
                //formerRunTimer();
            }
        });

        timerGroup.getChildren().add(vBox);
        //endregion
    }

    private void pauseTimer() {
        timer.cancel();
    }

    private void startTimer(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),e -> advanceDuration()),new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timerLabel.setText(timeline.toString());
        timeline.play();
    }

    private void advanceDuration(){
        if(seconds < 59){
            seconds++;
        } else{
            seconds = 0;
            if(minutes < 59){
                minutes++;
            }else{
                minutes = 0;
            }
        }

    }
    private void runTimer() {
        //this method grabs Clock class and uses it as an object
        //the clock is used on timeLeft to make counter actually go down
        //the timeLeft is then added to the String timerLabel

        Clock clock = new Clock();

        //clock.run();



        //endregion
    }

    private void alarm(){
        if(timeLeft == 0){
            sound = true;
        }
    }

    private void setUpTimerSettings() {
        //region Input changed Pomodoro Duration
        TextField pomodoroDurationChange = new TextField();
        pomodoroDurationChange.setPromptText("Enter desired Pomodoro Time in minutes");
        FXControls.setPosition(pomodoroDurationChange, 150, 150);


        //it adds a ChangeListener to the TextField
        pomodoroDurationChange.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { //takes the vale of the field and if it is not a Decimal Integer ->
                pomodoroDurationChange.setText(newValue.replaceAll("[^\\d]", "")); //it replaces ever non-Decimal-integer with a blank field
            }

        });
        //this makes it so that only numeric input is allowed
        pomodoroDurationChange.getText();

        timerSettingsGroup.getChildren().add(pomodoroDurationChange);
        //endregion
        //region Input changed short break duration
        TextField shortBreakDurationChange = new TextField();
        shortBreakDurationChange.setPromptText("Enter desired Short Break Time in minutes");
        FXControls.setPosition(shortBreakDurationChange, 150, 180);

        shortBreakDurationChange.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { //takes the vale of the field and if it is not a Decimal Integer ->
                shortBreakDurationChange.setText(newValue.replaceAll("[^\\d]", "")); //it replaces ever non-Decimal-integer with a blank field
            }
        });

        timerSettingsGroup.getChildren().add(shortBreakDurationChange);
        //endregion
        //region Input changed long break duration
        TextField longBreakDurationChange = new TextField();
        longBreakDurationChange.setPromptText("Enter desired Long Break Time in minutes");
        FXControls.setPosition(longBreakDurationChange, 150, 210);

        longBreakDurationChange.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { //takes the vale of the field and if it is not a Decimal Integer ->
                longBreakDurationChange.setText(newValue.replaceAll("[^\\d]", "")); //it replaces ever non-Decimal-integer with a blank field
                longBreakDurationChange.getText();
            }
        });

        timerSettingsGroup.getChildren().add(longBreakDurationChange);
        //endregion
        //region Save Button
        Button saveChange = new Button();
        FXControls.setButton(saveChange, 150, 250, "Save Changes");
        saveChange.setOnAction(event -> {
            try {
                updateConsultant(
                        (int) Double.parseDouble(pomodoroDurationChange.getText()),
                        (int) Double.parseDouble(shortBreakDurationChange.getText()),
                        (int) Double.parseDouble(longBreakDurationChange.getText())
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
            changeGroup(timerSettingsGroup, timerGroup);
        });
        timerSettingsGroup.getChildren().add(saveChange);
        //endregion

    }

    //region methods to make it work
    private void changeGroup(Group currentGroup, Group targetGroup) {
        removeGroup(currentGroup);
        loadGroup(targetGroup);
    }

    private void removeGroup(Group group) {
        getUiRoot().getChildren().removeAll(group);
    }

    private void loadGroup(Group group) {
        getUiRoot().getChildren().addAll(group);
    }

    private void updateConsultant(int pomoChange, int sBreakChange, int lBreakChange) throws SQLException {
        Connection con = DB.getCon();
        SQLHandler.updateConsultant(con, pomoChange, sBreakChange, lBreakChange, LoginID.getUserID());
        con.close();
    }
    //endregion


}