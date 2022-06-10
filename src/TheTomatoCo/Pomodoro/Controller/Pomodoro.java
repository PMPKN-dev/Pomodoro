package TheTomatoCo.Pomodoro.Controller;

import TheTomatoCo.Foundation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class Pomodoro extends Program
{
    Group timerGroup = new Group();
    Group timerSettingsGroup = new Group();

    //region Constants
    private TextField UserName,Password;
    private Button Login, pauseButton,resumeButton = new Button();
    private Text pomodoroTimer, shortBreakTimer, longBreakTimer;
    private HBox timerOptionsContainer;
    private Label timerLabel = new Label();
    private String counterString = "";
    private Timer timer = new Timer();
    private boolean sound = true;
    private boolean isRunning = false;
    private int elapsedTime = 0;
    private int seconds = 0;
    private int minutes = 0;
    private String seconds_string = String.format("%02d",seconds);
    private String minutes_string = String.format("%02d",minutes);
    Connection con = DB.getCon();
    //endregion

    @Override
    public void expand() {

        try {
            setUpTimer();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setUpTimerSettings();

    }

    private void setUpTimer() throws SQLException {

        //region Change view to settings
        Button timerSettings = new Button();
        FXControls.setButton(timerSettings, 500, 100, "Settings");
        timerSettings.setOnAction(event -> changeGroup(timerGroup,timerSettingsGroup));

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

        timerOptionsContainer.setPadding(new Insets(50,40,50,40));
        timerOptionsContainer.setSpacing(50);
        timerOptionsContainer.setAlignment(Pos.CENTER);
        timerOptionsContainer.getChildren().addAll(pomodoroTimer,shortBreakTimer,longBreakTimer);

        timerGroup.getChildren().add(timerOptionsContainer);


        //endregion
        //region setting up login
        Login = new Button();
        FXControls.setButton(Login,400,200,"Login to your Pomodoro");
        timerGroup.getChildren().add(Login);
        Login.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //region setting up the popupwindow
                final Stage popUp = new Stage();
                popUp.initModality(Modality.APPLICATION_MODAL);
                VBox popUpVbox = new VBox(20);
                popUpVbox.getChildren().add(new Text("Login to your Pomodoro"));
                Scene popUpScene = new Scene(popUpVbox,300,200);
                popUp.setScene(popUpScene);
                popUp.show();
                //endregion

                //region setting up the login confirmation
                UserName = new TextField();
                UserName.setPromptText("Username");
                UserName.setPrefWidth(10);
                Password = new TextField();
                Password.setPromptText("Password");
                Password.setPrefWidth(10);
                Button login = new Button("Login");
                popUpVbox.getChildren().addAll(UserName,Password,login);
                login.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try {
                            int permLevel = SQLHandler.VerifyLogin(con,Password.getText(),UserName.getText());
                            //region setting up a possible override of the Info Background
                            Rectangle userInfoBackground = new Rectangle();
                            FXControls.setPosition(userInfoBackground,400,0);
                            userInfoBackground.setHeight(90);
                            userInfoBackground.setWidth(198);
                            userInfoBackground.setFill(Color.WHITE);
                            userInfoBackground.setStroke(Color.BLACK);



                            userInfo.getChildren().add(userInfoBackground);
                            Label userNameLabel = new Label();
                            FXControls.setTextNode(userNameLabel,405,5,"Logged in as: ");
                            userInfo.getChildren().add(userNameLabel);
                            Text userNameText = new Text();
                            FXControls.setTextNode(userNameText,405,40, UserName.getText());
                            userInfo.getChildren().add(userNameText);
                            Label userStatusLabel = new Label();
                            FXControls.setTextNode(userStatusLabel,405,45,"With Permission level: ");
                            userInfo.getChildren().add(userStatusLabel);
                            Text userStatusText = new Text();
                            //endregion
                            if(permLevel == 1){ //1 = consultant

                                FXControls.setTextNode(userStatusText,405,80,"Consultant");
                            }else{

                                FXControls.setTextNode(userStatusText,405,80,"Admin");
                            }
                            userInfo.getChildren().add(userStatusText);


                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        //TODO: Exit after login
                        //
                    }
                });
                //endregion
            }
        });

        //endregion

        //region Switch to pomodoro timer updated through database
        pomodoroTimer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    int updatedPomodoro = SQLHandler.setPomodoroTime(con,UserName.getText());
                    timerLabel.setText(updatedPomodoro+":"+seconds_string);

                    /*
                    if(seconds < 10){ //if its X:09, it knows to put 0 infront of the counter
                        timerLabel.setText(updatedPomodoro + ":0" + seconds);
                    }else{
                        timerLabel.setText(updatedPomodoro + ":" + seconds); //If neither is below 10 in counter
                    }


                     */
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
                    int updatedShortBreak = SQLHandler.setShortBreakTime(con,UserName.getText());
                    timerLabel.setText(updatedShortBreak+":"+seconds_string);
                    /*
                    if(seconds < 10){ //if its X:09, it knows to put 0 infront of the counter
                        timerLabel.setText(updatedShortBreak + ":0" + seconds);
                    }else{
                        timerLabel.setText(updatedShortBreak + ":" + seconds); //If neither is below 10 in counter
                    }

                     */
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
                    int updatedLongBreak = SQLHandler.setLongBreakTime(con,UserName.getText());
                    timerLabel.setText(updatedLongBreak+":"+seconds_string);
                    /*
                    if(seconds < 10){ //if its X:09, it knows to put 0 infront of the counter
                        timerLabel.setText(updatedLongBreak + ":0" + seconds);
                    }else{
                        timerLabel.setText(updatedLongBreak + ":" + seconds); //If neither is below 10 in counter
                    }

                     */
                    System.out.println(timerLabel);

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

    private void pauseTimer(){
        timer.cancel();
    }
    private void runTimer(){
        //region the clock
        //TODO: Fix the clock
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    elapsedTime = (int) Double.parseDouble(timerLabel.getText());
                    for(int i = elapsedTime; i > 0;i--){
                        minutes = (elapsedTime / 60) % 60;
                        seconds = elapsedTime % 60;
                    }
                    /**
                     * It needs to grab the current timerLabel which has been updated with int data converted to string from database
                     * then it needs to grab the current time and save it as a value that can later be added to database ontop of old data
                     */

                });
            }
        },0,1000);
        //endregion
    }


    private void setUpTimerSettings(){
        //region Input changed Pomodoro Duration
        TextField pomodoroDurationChange = new TextField();
        pomodoroDurationChange.setPromptText("Enter desired Pomodoro Time in minutes");
        FXControls.setPosition(pomodoroDurationChange,150,150);


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
        longBreakDurationChange.setPromptText("Enter desired Long Break Time in minutes");
        FXControls.setPosition(longBreakDurationChange,150,210);

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
        FXControls.setButton(saveChange,150,250,"Save Changes");
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
            changeGroup(timerSettingsGroup,timerGroup);
        });
        timerSettingsGroup.getChildren().add(saveChange);
        //endregion

    }

    //region methods to make it work
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
    private void updateConsultant(int pomoChange, int sBreakChange, int lBreakChange) throws SQLException{
        Connection con = DB.getCon();
        SQLHandler.updateConsultant(con,pomoChange,sBreakChange,lBreakChange,UserName.getText());
        con.close();
    }
    //endregion


}