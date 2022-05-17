package Hub;

import javafx.application.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class Hub extends Application {

    @FXML
    Canvas GridCanvasPomodoro;
    @FXML
    Canvas GridCanvasDoToday;
    @FXML
    Canvas GridCanvasOfficeOverview;
    @FXML
    Canvas GridCanvasAdmin;
    @FXML
    GridPane hubGridPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hub/Hub.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent,400,200);
        primaryStage.setResizable(false);
        scene.getStylesheets().add(super.getClass().getResource("/Hub/Style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Hub");
        primaryStage.show();



        //TODO, make the 4 grids render icons for the programs and add onClick launching for the programs
        //TODO, (will require popups to run the fxml files of the other programs)
    }

    @FXML
    public void startPomodoro(){
        //load Pomodoro into popup

    }

    @FXML
    public void startDoToday(){
        //load DoToday into popup
    }

    @FXML
    public void startOfficeOverview(){
        //load OfficeOverview into popup
    }

    @FXML
    public void startAdmin(){
        //load Admin into popup
        //GridCanvasAdmin.getClass().getResource("/Hub/Admin/View/Admin.fxml"); - Tried to be fancy, doesnt work
        //Basically when Admin Canvas is clicked, the admin fxml will be launched
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hub/Admin/View/Admin.fxml"));
            Parent root = fxmlLoader.load();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Administrator");
            primaryStage.setScene(new Scene(root));
            //Scene scene = new Scene(root,400,400); - To be used if not using new Stage
            primaryStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }


    }

}
