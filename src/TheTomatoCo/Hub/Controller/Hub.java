package TheTomatoCo.Hub.Controller;

import TheTomatoCo.Foundation.*;
import TheTomatoCo.Admin.Controller.Admin;
import TheTomatoCo.DoToday.Controller.DoToday;
import TheTomatoCo.OfficeOverview.Controller.OfficeOverview;
import TheTomatoCo.Pomodoro.Controller.PomodoroTimer;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.*;

import java.sql.Connection;
import java.sql.SQLException;

public class Hub extends Program {
    LoginData LoginID = LoginData.getInstance();
    TextField Username = new TextField();
    PasswordField Password = new PasswordField();
    Button LoginButton = new Button();
    Button BacktoLogin = new Button();
    HBox Admingroup = new HBox();
    HBox Consultantgroup = new HBox();
    VBox Logingroup = new VBox();


    @Override
    public void expand() {
        Grouping();
        getUiRoot().getChildren().removeAll(Admingroup,Logingroup);
        initialize();
        LoginButton.setOnAction(event -> {
            Login();
            getUiRoot().getChildren().add(BacktoLogin);
            System.out.println(LoginID.getUserID());
        });
        BacktoLogin.setOnAction(event -> {
            getUiRoot().getChildren().removeAll(Admingroup, Consultantgroup);
            getUiRoot().getChildren().add(Logingroup);
            getUiRoot().getChildren().remove(BacktoLogin);

        });
    }

    public void startPomodoro(){
        PomodoroTimer pomodoro = new PomodoroTimer();
        launchPackage(pomodoro);
    }

    public void startDoToday(){
        DoToday doToday = new DoToday();
        launchPackage(doToday);
    }

    public void startOfficeOverview(){
        OfficeOverview officeOverview = new OfficeOverview();
        launchPackage(officeOverview);
    }

    public void startAdmin(){
        Admin admin = new Admin();
        launchPackage(admin);
    }

    //Lanches a package with the given name and program
    private void launchPackage(Program program){
        Stage stage = new Stage();
        program.start(stage);
        stage.show();
    }

    private void initialize(){
        Logingroup.getChildren().addAll(Username,Password,LoginButton);
        getUiRoot().getChildren().add(Logingroup);
        Logingroup.setSpacing(10);
        Username.setPromptText("Username");
        Password.setPromptText("Password");
        FXControls.setPosition(Username,10,10);
        FXControls.setPosition(Password,10,55);

        FXControls.setButton(LoginButton,10,100,"Login");
        FXControls.setButton(BacktoLogin,200,350,"Back");



    }
    private void Login(){
        Connection con = DB.getCon();

        try{
            String UsernameString = Username.getText();
            String PasswordString = Password.getText();
            int ID = SQLHandler.GetConsultantID(con,UsernameString);
            LoginID.setUserID(ID);
            //returns the ID of the consultant
            int PermissionLvl = SQLHandler.VerifyLogin(con,PasswordString,UsernameString);

            if(PermissionLvl==2){
                adminLogin();
            } else if(PermissionLvl==1){
                consultantLogin();
            }else{
                System.out.println("you seem to not have an assigned permission level");
            }
            System.out.println("Login successful");

        }catch (SQLException e) {
            System.out.println("Login failed");
        }

    }
    private void Grouping(){
        Button adminLaunch = new Button();
        adminLaunch.setText("Admin");
        adminLaunch.setOnAction(event -> startAdmin());

        Button officeOverviewLaunch = new Button();
        officeOverviewLaunch.setText("Office Overview");
        officeOverviewLaunch.setOnAction(event -> startOfficeOverview());

        Button doTodayLaunch = new Button();
        doTodayLaunch.setText("Do Today");
        doTodayLaunch.setOnAction(event -> startDoToday());

        Button Pomodoro = new Button();
        Pomodoro.setText("Pomodoro");
        Pomodoro.setOnAction(event -> startPomodoro());

        Button Pomodoro2 = new Button();
        Pomodoro2.setText("Pomodoro");
        Pomodoro2.setOnAction(event -> startPomodoro());


        Admingroup.getChildren().addAll(adminLaunch,officeOverviewLaunch,Pomodoro2);
        Consultantgroup.getChildren().addAll(doTodayLaunch,Pomodoro);


    }
    private void consultantLogin(){
        getUiRoot().getChildren().removeAll(Admingroup,Logingroup);
        getUiRoot().getChildren().add(Consultantgroup);

    }
    private void adminLogin(){
        getUiRoot().getChildren().removeAll(Consultantgroup,Logingroup);
        getUiRoot().getChildren().add(Admingroup);

    }

}

