package JensenConsultantCompany.Pomodoro.Controller;

import JensenConsultantCompany.Foundation.*;
import JensenConsultantCompany.Hub.Controller.LoginData;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.SQLException;

public class Pomodoro extends Program {
    Group timerGroup = new Group();
    Group timerSettingsGroup = new Group();

    //region Constants
    private Button pauseButton, resumeButton = new Button();
    private Text pomodoroTimer, shortBreakTimer, longBreakTimer;
    private HBox timerOptionsContainer;
    private Label timerLabel = new Label();
    String TimerType = "";
    Connection con = DB.getCon();
    //endregion
    LoginData LoginID = LoginData.getInstance();
    Timer theTimer = new Timer();
    Thread theThread = new Thread(theTimer);

    //private boolean sound = true;
    //private int timeLeft = 0;
    //private int seconds = 0;
    //private int minutes = 0;
    //private String seconds_string = String.format("%02d", seconds);
    //private String minutes_string = String.format("%02d", minutes);



    @Override
    /**
     * Overrides the expand() within the Program Class
     */
    public void expand() {

        try {
            setUpTimer();
            //In order to update the label, we create a thread that runs the timer class, this starts a task in which we take the given PomodoroTimer, decrement it every second and return that to the label via the messageProperty() function.
            timerLabel.textProperty().bind(theTimer.messageProperty());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setUpTimerSettings();
        super.userNameText.setText(LoginID.getUserID()+"");
        //In the first two eventhandlers we handle the thread mentioned above, first button suspends the thread from running and thusly wont decrement, the later button either starts the thread if not active or resumes it.
        pauseButton.setOnAction(event -> {
            stopTimer();
        });
        resumeButton.setOnAction(event -> {
            startTimer();
        });
        //The next three eventhandlers, we get the duration of either the pomodoro or the two break timers from the database via a query,
        //then we assign this value to the PomodoroTimer in the timer class so the timer will count down from there.
        pomodoroTimer.setOnMouseClicked(event -> {
            try {
                TimerType = "Pomodoro";
                theTimer.setPomodoroTimer(SQLHandler.getPomodoroTimer(con,LoginID.getUserID(),TimerType)*60);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        shortBreakTimer.setOnMouseClicked(event -> {
            try {
                TimerType = "ShortBreak";
                theTimer.setPomodoroTimer(SQLHandler.getPomodoroTimer(con,LoginID.getUserID(),TimerType)*60);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        longBreakTimer.setOnMouseClicked(event -> {
            try {
                TimerType = "LongBreak";
                theTimer.setPomodoroTimer(SQLHandler.getPomodoroTimer(con,LoginID.getUserID(),TimerType)*60);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });;
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


        //region the timer itself and start/pause button
        timerLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 70));
        pauseButton = new Button();
        FXControls.setButton(pauseButton, 200, 200, "Pause");
        resumeButton = new Button();
        FXControls.setButton(resumeButton, 250, 200, "Resume/Start");
        VBox vBox = new VBox(timerLabel, pauseButton, resumeButton, timerOptionsContainer);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        timerGroup.getChildren().add(vBox);
        //endregion
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

    private void startTimer(){
        if(theThread.isAlive()){
            theThread.resume();
        }else{
            theThread.start();
        }
    }
    private void stopTimer(){
        theThread.suspend();
    }
    //endregion


}