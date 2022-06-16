package JensenConsultantCompany.Hub.Controller;

import JensenConsultantCompany.Foundation.*;
import JensenConsultantCompany.Admin.Controller.Admin;
import JensenConsultantCompany.DoToday.Controller.DoToday;
import JensenConsultantCompany.OfficeOverview.Controller.OfficeOverview;
//import TheTomatoCo.Pomodoro.Controller.Pomodoro;
import JensenConsultantCompany.Pomodoro.Controller.Pomodoro;
import javafx.scene.control.*;
import javafx.stage.*;

public class Hub extends Program {
    @Override
    public void expand() {
        initialize();
    }

    /**
     * Connects the individual program Pomodoro to the Hub
     */
    public void startPomodoro(){
        Pomodoro pomodoro = new Pomodoro();
        launchPackage(pomodoro);
    }

    /**
     * Connects the individual program DoToday to the Hub
     */
    public void startDoToday(){
        DoToday doToday = new DoToday();
        launchPackage(doToday);
    }

    /**
     * Connects the individual program OfficeOverview to the Hub
     */
    public void startOfficeOverview(){
        OfficeOverview officeOverview = new OfficeOverview();
        launchPackage(officeOverview);
    }

    /**
     * Connects the individual program Admin to the Hub
     */
    public void startAdmin(){
        Admin admin = new Admin();
        launchPackage(admin);
    }

    //Launches a package with the given name and program
    private void launchPackage(Program program){
        Stage stage = new Stage();
        program.start(stage);
        stage.show();
    }

    private void initialize(){
        Button adminLaunch = new Button();
        FXControls.setButton(adminLaunch,10,10,"Admin");
        adminLaunch.setOnAction(event -> startAdmin());
        getUiRoot().getChildren().add(adminLaunch);

        Button officeOverviewLaunch = new Button();
        FXControls.setButton(officeOverviewLaunch,80,10,"Office Overview");
        officeOverviewLaunch.setOnAction(event -> startOfficeOverview());
        getUiRoot().getChildren().add(officeOverviewLaunch);

        Button doTodayLaunch = new Button();
        FXControls.setButton(doTodayLaunch,210,10,"Do Today");
        doTodayLaunch.setOnAction(event -> startDoToday());
        getUiRoot().getChildren().add(doTodayLaunch);

        Button Pomodoro = new Button();
        FXControls.setButton(Pomodoro,300,10,"Pomodoro");
        Pomodoro.setOnAction(event -> startPomodoro());
        getUiRoot().getChildren().add(Pomodoro);
    }
}
