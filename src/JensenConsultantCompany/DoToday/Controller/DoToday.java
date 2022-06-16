package JensenConsultantCompany.DoToday.Controller;

import JensenConsultantCompany.Foundation.DB;
import JensenConsultantCompany.Foundation.Program;

import JensenConsultantCompany.Foundation.SQLHandler;
import JensenConsultantCompany.Hub.Controller.LoginData;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoToday extends Program {
    String selectedProject;
    int ID = 0;
    Connection con = DB.getCon();
    Button createTask = new Button();
    ListView doTodayList = new ListView<>();
    LoginData LoginID = LoginData.getInstance();
    ComboBox sortbyProject = new ComboBox();

    @Override
    /**
     * Overrides the expand within the Program Class
     */
    public void expand() {
        doTodayList.setPrefHeight(370);
        Grouping();
        initialize();
        createTask.setOnAction(event -> {

            Stage primaryStage = new Stage();
            AnchorPane taskPane;
            taskPane = new AnchorPane();
            Scene scene = new Scene(taskPane, 200, 150);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.setTitle(this.getClass().getSimpleName());
            primaryStage.show();
            VBox Vbox = new VBox();
            Vbox.setSpacing(10);
            HBox HBox = new HBox();

            TextField TaskName = new TextField();
            TextField Pomodoros = new TextField();
            Pomodoros.setMaxWidth(45);

            ComboBox SelectProject = new ComboBox<>();
            SelectProject.setPrefWidth(200);

            try {
                ComboBoxFiller(SelectProject,"Select ProjectName from tbl_Project");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            SelectProject.getSelectionModel().selectedItemProperty().addListener(observable -> {
                selectedProject = SelectProject.getSelectionModel().getSelectedItem().toString();
                try {
                    ID = SQLHandler.getProjectID(con,selectedProject);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            Button Create = new Button();
            Create.setText("Create Task");
            Create.setPrefWidth(155);
            Create.setOnAction(event1 -> {
                try {
                    SQLHandler.createTask(con,LoginID.getUserID(),ID,TaskName.getText(),(int) Double.parseDouble(String.valueOf(Pomodoros.getText())));
                    doTodayList.getItems().add(TaskName.getText());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            HBox.getChildren().addAll(Pomodoros,Create);
            Vbox.getChildren().addAll(TaskName,SelectProject,HBox);
            taskPane.getChildren().add(Vbox);
        });
        sortbyProject.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String selectedProject = sortbyProject.getSelectionModel().getSelectedItem()+"";
            doTodayList.getItems().clear();
            try {
                ListViewFiller(doTodayList,"select TaskName from tbl_Tasks where ProjectID = (select ProjectID from tbl_Project where ProjectName = '"+selectedProject+"');");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(selectedProject.equals("All")){
                doTodayList.getItems().clear();
                try {
                    ListViewFiller(doTodayList,"Select TaskName from tbl_Tasks where ConsultantID ="+LoginID.getUserID());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void initialize(){
        try {
            ListViewFiller(doTodayList,"Select TaskName from tbl_Tasks where ConsultantID ="+LoginID.getUserID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            ComboBoxFiller(sortbyProject,"Select ProjectName from tbl_Project");
            sortbyProject.getItems().add("All");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void Grouping() {
        HBox listSort = new HBox();
        HBox createFinish = new HBox();
        VBox doTodayView = new VBox();


        sortbyProject.setPromptText("Sort by Project");
        Button finishTask = new Button();
        finishTask.setText("Finish task");
        createTask.setText("Create new task");


        listSort.getChildren().addAll(doTodayList,sortbyProject);
        createFinish.getChildren().addAll(createTask,finishTask);
        doTodayView.getChildren().addAll(listSort,createFinish);
        getUiRoot().getChildren().add(doTodayView);

    }
    private void ComboBoxFiller(ComboBox ComboBox, String query) throws SQLException {
        PreparedStatement p = con.prepareStatement(query);
        ResultSet rs = p.executeQuery();
        do{
            if(!rs.next()){
                break;
            }else{
                ComboBox.getItems().add(rs.getString(1));
            }
        }while(true);
        p.close();
    }

    private void ListViewFiller(ListView<String> ListView, String query) throws SQLException {
        PreparedStatement p = con.prepareStatement(query);
        ResultSet rs = p.executeQuery();
        do{
            if(!rs.next()){
                break;
            }else{
                ListView.getItems().add(rs.getString(1));
            }
        }while(true);
        p.close();

    }
}
