package TheTomatoCo.DoToday.Controller;

import TheTomatoCo.Foundation.DB;
import TheTomatoCo.Foundation.Program;

import TheTomatoCo.Foundation.SQLHandler;
import TheTomatoCo.Hub.Controller.LoginData;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DoToday extends Program {
    String selectedProject;
    int ID = 0;
    Connection con = DB.getCon();
    Button createTask = new Button();
    ListView doTodayList = new ListView<>();
    LoginData LoginID = LoginData.getInstance();
    ComboBox sortbyProject = new ComboBox();
    Text AssignedPomodorosNumber = new Text();
    Text CompletedPomodorosNumber = new Text();
    Button finishTask = new Button();
    Boolean isSelected = doTodayList.getSelectionModel().isEmpty();

    @Override
    public void expand() {
        System.out.println(isSelected);
        doTodayList.setPrefHeight(370);
        sortbyProject.setPrefWidth(150);
        Grouping();
        initialize();
        doTodayListListener(doTodayList);
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
                ListViewFiller(doTodayList,"select TaskName from tbl_Tasks where ProjectID = (select ProjectID from tbl_Project where ProjectName = '"+selectedProject+"') and ConsultantID = '"+LoginID.getUserID()+"'");
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

        finishTask.setOnAction(event -> {

            if(isSelected != null){
                System.out.println(isSelected);
                System.out.println("ao");
            }


        });

    }
    private void initialize(){
        try {
            ListViewFiller(doTodayList,"Select TaskName from tbl_Tasks where ConsultantID ='"+LoginID.getUserID()+"'");
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
        HBox PomodoroGroup = new HBox();
        VBox doTodayView = new VBox();
        VBox textfields = new VBox();
        VBox fieldNumbers = new VBox();


        Text AssignedPomodoros = new Text("Assigned Pomodoros :");
        //Text CompletedPomodoros = new Text("Completed Pomodoros:");
        sortbyProject.setPromptText("Sort by Project");
        finishTask.setText("Finish task");
        createTask.setText("Create new task");

        fieldNumbers.getChildren().addAll(AssignedPomodorosNumber, CompletedPomodorosNumber);
        textfields.getChildren().addAll(AssignedPomodoros);
        PomodoroGroup.getChildren().addAll(textfields,fieldNumbers);
        PomodoroGroup.setLayoutX(405);
        PomodoroGroup.setLayoutY(90);
        listSort.getChildren().addAll(doTodayList,sortbyProject);
        createFinish.getChildren().addAll(createTask,finishTask);
        doTodayView.getChildren().addAll(listSort,createFinish);
        getUiRoot().getChildren().addAll(doTodayView,PomodoroGroup);

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
    public void doTodayListListener(ListView<String> ListView){
        ListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String selectedTask = doTodayList.getSelectionModel().getSelectedItem()+"";
            System.out.println(selectedTask);
            try {
                AssignedPomodorosNumber.setText(SQLHandler.getAssignedPomodoros(con,selectedTask,LoginID.getUserID()));
                System.out.println(isSelected);
            } catch (SQLException e) {
                e.printStackTrace();
            }


        });
    }
}

