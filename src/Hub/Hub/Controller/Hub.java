package Hub.Hub.Controller;

import Hub.Admin.Controller.Admin;
import Hub.DoToday.Controller.DoToday;
import Hub.OfficeOverview.Controller.OfficeOverview;
import Hub.Foundation.*;
import Hub.Pomodoro.Controller.Pomodoro;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class Hub extends Program {
    @Override
    public void expand() {
        Button button = new Button();
        FXControls.setButton(button,10,10,"Admin");
        button.setOnAction(event -> startAdmin());
        getUiRoot().getChildren().add(button);
    }

    public void startPomodoro(){
        Pomodoro pomodoro = new Pomodoro();
        launchPackage("Pomodoro",pomodoro);
    }


    public void startDoToday(){
        DoToday doToday = new DoToday();
        launchPackage("DoToday",doToday);
    }


    public void startOfficeOverview(){
        OfficeOverview officeOverview = new OfficeOverview();
        launchPackage("OfficeOverview",officeOverview);
    }


    public void startAdmin(){
        Admin admin = new Admin();
        launchPackage("Admin",admin);
    }


    //Lanches a package with the given name and program
    private void launchPackage(String title, Program program){
        AnchorPane ap = program.getUiRoot();
        Scene scene = new Scene(ap,getUiRoot().getHeight(),getUiRoot().getWidth());
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

}
