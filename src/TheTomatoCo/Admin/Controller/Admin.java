package TheTomatoCo.Admin.Controller;

import TheTomatoCo.Foundation.*;
import TheTomatoCo.Foundation.Utility;
import TheTomatoCo.Hub.Controller.LoginData;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Window;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public class Admin extends Program {

    Group initial = new Group();
    Group createConsultantGroup = new Group();
    Group deactivateConsultantGroup = new Group();
    Group editConsultantGroup = new Group();
    Group createProjectGroup = new Group();
    Group editProjectGroup = new Group();
    TextField fNameInput;
    TextField lNameInput;
    Text confirmation;
    int spacing = 35; // spacing between the TextFields for use with te FXControls.Under() method
    LoginData LoginID = LoginData.getInstance();


    @Override
    /**
     * Overrides the expand within the Program Class
     */
    public void expand(){

        setUpInitial();
        setUpCreateConsultant();
        setUpDeactivateConsultant();
        setUpEditConsultant();
        setUpCreateProject();
        setUpEditProject();
        getUiRoot().getChildren().add(initial);
        //for edit consultant create the function to load current info based on ID
        super.userNameText.setText(LoginID.getUserID()+"");


    }

    private void setUpInitial(){

        //region createConsultant Button
        Button createConsultant = new Button();
        FXControls.setButton(createConsultant,100,100,"Create Consultant");
        createConsultant.setOnAction(event -> changeView(initial,createConsultantGroup));

        initial.getChildren().add(createConsultant);
        //endregion

        //region deactivateConsultant Button
        Button deactivateConsultant = new Button();
        FXControls.under(deactivateConsultant,createConsultant,spacing);
        deactivateConsultant.setText("Deactivate Consultant");
        deactivateConsultant.setOnAction(event -> changeView(initial,deactivateConsultantGroup));

        initial.getChildren().add(deactivateConsultant);
        //endregion

        //region editConsultant Button
        Button editConsultant = new Button();
        FXControls.under(editConsultant,deactivateConsultant,spacing);
        editConsultant.setText("Edit Consultant");
        editConsultant.setOnAction(event -> changeView(initial,editConsultantGroup));

        initial.getChildren().add(editConsultant);
        //endregion

        //region createProject Button
        Button createProject = new Button();
        FXControls.under(createProject,editConsultant,spacing);
        createProject.setText("Create Project");
        createProject.setOnAction(event -> changeView(initial,createProjectGroup));

        initial.getChildren().add(createProject);
        //endregion

        //region deactivateConsultant Button
        Button editProject = new Button();
        FXControls.under(editProject,createProject,spacing);
        editProject.setText("Edit Project");
        editProject.setOnAction(event -> changeView(initial,editProjectGroup));

        initial.getChildren().add(editProject);
        //endregion
    }

    private void setUpCreateConsultant(){

        //region back button
        Button back = new Button();
        FXControls.setButton(back,20,20,"back");
        back.setOnAction(event -> changeView(createConsultantGroup,initial));
        createConsultantGroup.getChildren().add(back);
        //endregion

        //region fName field
        fNameInput  = new TextField();
        FXControls.setPosition(fNameInput,40,80);
        fNameInput.setMaxWidth(120);
        fNameInput.setPromptText("First name");

        createConsultantGroup.getChildren().add(fNameInput);
        //endregion

        //region lName field
        lNameInput  = new TextField();
        FXControls.setPosition(lNameInput,170,80);
        lNameInput.setMaxWidth(120);
        lNameInput.setPromptText("Last name");

        createConsultantGroup.getChildren().add(lNameInput);
        //endregion

        //region DurationNotifierText
        Text notifierText = new Text();
        FXControls.setTextNode(notifierText,40,140,"Durations in minutes:");

        createConsultantGroup.getChildren().add(notifierText);
        //endregion

        //region Pomodoro duration field
        TextField PomodoroDurationInput = new TextField();
        PomodoroDurationInput.setPromptText("Pomodoro");
        PomodoroDurationInput.setMaxWidth(100);
        FXControls.setPosition(PomodoroDurationInput,40,160);

        //this part is not coded by me but taken from the internet, it is however not required for the program to work
        // it serves as a Quality of Life improvement

        //it adds a ChangeListener to the TextField
        PomodoroDurationInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { //takes the vale of the field and if it is not a Decimal Integer ->
                PomodoroDurationInput.setText(newValue.replaceAll("[^\\d]", "")); //it replaces ever non-Decimal-integer with a blank field
            }
        });
        //this makes it so that only numeric input is allowed

        createConsultantGroup.getChildren().add(PomodoroDurationInput);
        //endregion

        //region longBreak duration field
        TextField longBreakDurationInput = new TextField();
        longBreakDurationInput.setPromptText("Long break");
        longBreakDurationInput.setMaxWidth(100);
        FXControls.setPosition(longBreakDurationInput,150,160);

        longBreakDurationInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { //takes the vale of the field and if it is not a Decimal Integer ->
                longBreakDurationInput.setText(newValue.replaceAll("[^\\d]", "")); //it replaces ever non-Decimal-integer with a blank field
            }
        });

        createConsultantGroup.getChildren().add(longBreakDurationInput);
        //endregion

        //region shortBreak duration field
        TextField shortBreakDurationInput = new TextField();
        shortBreakDurationInput.setPromptText("Short break");
        shortBreakDurationInput.setMaxWidth(100);
        FXControls.setPosition(shortBreakDurationInput,260,160);

        shortBreakDurationInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { //takes the vale of the field and if it is not a Decimal Integer ->
                shortBreakDurationInput.setText(newValue.replaceAll("[^\\d]", "")); //it replaces ever non-Decimal-integer with a blank field
            }
        });

        createConsultantGroup.getChildren().add(shortBreakDurationInput);
        //endregion

        //region create button
        Button createButton = new Button();
        FXControls.setButton(createButton,150,230,"Create");

        createButton.setOnAction(event -> {
            try {
                generateID(
                        getNameFormatted(),
                        (int) Double.parseDouble(PomodoroDurationInput.getText()),
                        (int) Double.parseDouble(shortBreakDurationInput.getText()),
                        (int) Double.parseDouble(longBreakDurationInput.getText())
                );

            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace();
            }
        });
        createConsultantGroup.getChildren().add(createButton);
        //endregion
    }

    private void setUpDeactivateConsultant(){

        //region back button
        Button back = new Button();
        FXControls.setButton(back,20,20,"back");
        back.setOnAction(event -> changeView(deactivateConsultantGroup,initial));
        deactivateConsultantGroup.getChildren().add(back);
        //endregion

        //region IDInput field
        TextField IDInput  = new TextField();
        FXControls.setPosition(IDInput,120,150);
        IDInput.setPromptText("Input Username/ConsultantID");

        deactivateConsultantGroup.getChildren().add(IDInput);
        //endregion

        //region deactivate button
        Button deactivateButton = new Button();
        FXControls.setButton(deactivateButton,300,300,"Deactivate");
        deactivateButton.setOnAction(event -> deactivateConsultantHandler(IDInput.getText()));
        deactivateConsultantGroup.getChildren().add(deactivateButton);
        //endregion



        //engine for searching for Consultants, search criteria being name, last name or user ID
        //clicking on Consultant puts them in focus displaying extra details and grabbing a hold of them
        //deactivation prompt admin password, upon correct enter it changes the Consultant's status to inactive and refreshes the list
    }

    private void setUpEditConsultant(){


        //region back Button
        Button back = new Button();
        FXControls.setButton(back,20,20,"back");
        back.setOnAction(event -> changeView(editConsultantGroup,initial));
        editConsultantGroup.getChildren().add(back);
        //endregion

        //region UserID TextField
        TextField userID = new TextField();
        FXControls.setTextNode(userID,40,80,"enter the ID for editing");
        editConsultantGroup.getChildren().add(userID);
        //endregion


        //region Name TextField
        TextField Name = new TextField();
        FXControls.under(Name,userID,50);
        Name.setPromptText("First Name");
        editConsultantGroup.getChildren().add(Name);
        //endregion

        //region PomodoroTime TextField
        TextField PomodoroTime = new TextField();
        FXControls.under(PomodoroTime,Name,spacing);

        PomodoroTime.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { //takes the vale of the field and if it is not a Decimal Integer ->
                PomodoroTime.setText(newValue.replaceAll("[^\\d]", "")); //it replaces ever non-Decimal-integer with a blank field
            }
        });
        PomodoroTime.setTooltip(new Tooltip("Pomodoro duration"));

        editConsultantGroup.getChildren().add(PomodoroTime);
        //endregion

        //region shortBreakTime TextField
        TextField shortBreakTime = new TextField();
        FXControls.under(shortBreakTime,PomodoroTime,spacing);

        shortBreakTime.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { //takes the vale of the field and if it is not a Decimal Integer ->
                shortBreakTime.setText(newValue.replaceAll("[^\\d]", "")); //it replaces ever non-Decimal-integer with a blank field
            }
        });
        shortBreakTime.setTooltip(new Tooltip("Short break duration"));

        editConsultantGroup.getChildren().add(shortBreakTime);
        //endregion

        //region longBreakTime TextField
        TextField longBreakTime = new TextField();
        FXControls.under(longBreakTime,shortBreakTime,spacing);

        longBreakTime.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { //takes the vale of the field and if it is not a Decimal Integer ->
                longBreakTime.setText(newValue.replaceAll("[^\\d]", "")); //it replaces ever non-Decimal-integer with a blank field
            }
        });
        longBreakTime.setTooltip(new Tooltip("Long break duration"));

        editConsultantGroup.getChildren().add(longBreakTime);
        //endregion


        //region userPassword PasswordField
        PasswordField userPassword = new PasswordField();
        FXControls.setTextNode(userPassword,250,130,"User Password");
        editConsultantGroup.getChildren().add(userPassword);
        //endregion

        //region userPermissionLevel TextField
        TextField userPermissionLevel = new TextField();
        FXControls.under(userPermissionLevel,userPassword,spacing);
        userPermissionLevel.setPromptText("User Permission Level");
        userPermissionLevel.setTooltip(new Tooltip("Permission Level"));
        editConsultantGroup.getChildren().add(userPermissionLevel);
        //endregion

        //region Warning Text
        Text warningText = new Text();
        FXControls.under(warningText,userPermissionLevel,50);
        warningText.setText("Warning: Once you click Update data the process \nis irreversible so ensure all input data is final");
        editConsultantGroup.getChildren().add(warningText);
        //endregion


        //region grab data button
        Button grabData = new Button();
        FXControls.setButton(grabData,230,80,"Grab data");

        //String[] data;

        grabData.setOnAction(event -> {
            try {
                String[] data = editConsultantGrabHandler(userID.getText());

                //region setData
                Name.setText(data[1]);
                PomodoroTime.setText(data[2]);
                shortBreakTime.setText(data[3]);
                longBreakTime.setText(data[4]);
                userPassword.setText(data[6]);
                userPermissionLevel.setText(data[7]);
                //endregion
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        editConsultantGroup.getChildren().add(grabData);
        //endregion

        //region updateData Button
        Button updateData = new Button();
        FXControls.under(updateData,longBreakTime,spacing);
        updateData.setText("Update Data");
        updateData.setOnAction(event -> {
                    try {
                        editConsultantUpdateHandler(
                                userID.getText(),
                                Name.getText(),
                                (int) Double.parseDouble(PomodoroTime.getText()),
                                (int) Double.parseDouble(shortBreakTime.getText()),
                                (int) Double.parseDouble(longBreakTime.getText()),
                                userPassword.getText(),
                                (int) Double.parseDouble(userPermissionLevel.getText()));

                        Text confirmation = new Text();
                        FXControls.setTextNode(confirmation,(int) updateData.getLayoutX()+120,(int) updateData.getLayoutY()+20,"Done!");
                        editConsultantGroup.getChildren().add(confirmation);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

        );
        editConsultantGroup.getChildren().add(updateData);
        //endregion

    }

    private void setUpCreateProject(){

        //region back button
        Button back = new Button();
        FXControls.setButton(back,20,20,"back");
        back.setOnAction(event -> changeView(createProjectGroup,initial));
        createProjectGroup.getChildren().add(back);
        //endregion

        //region projectID TextField
        TextField projectID = new TextField();
        FXControls.setTextNode(projectID,40,80,"enter new ProjectID");
        createProjectGroup.getChildren().add(projectID);
        //endregion

        //region projectName TextField
        TextField projectName = new TextField();
        FXControls.under(projectName,projectID,spacing);
        projectName.setPromptText("Enter new project Name");
        createProjectGroup.getChildren().add(projectName);
        //endregion

        //region ProjectDuration TextField
        TextField projectDuration = new TextField();
        FXControls.under(projectDuration,projectName,spacing);

        projectDuration.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { //takes the vale of the field and if it is not a Decimal Integer ->
                projectDuration.setText(newValue.replaceAll("[^\\d]", "")); //it replaces ever non-Decimal-integer with a blank field
            }
        });
        projectDuration.setTooltip(new Tooltip("Project Duration"));

        createProjectGroup.getChildren().add(projectDuration);
        //endregion

        //region CreateProject button
        Button createProject = new Button();
        FXControls.under(createProject,projectDuration,spacing);
        createProject.setText("Create Project");

        createProject.setOnAction(event -> createProjectHandler(
                projectID.getText(),
                projectName.getText(),
                (int) Double.parseDouble(projectDuration.getText())
        ));

        createProjectGroup.getChildren().add(createProject);
        //endregion

        //region Confirmation Text
        confirmation = new Text();
        FXControls.under(confirmation,createProject,55);

        createProjectGroup.getChildren().add(confirmation);
        //endregion
    }

    private void setUpEditProject(){

        /*
        yes, yes, yes, duplicate code and whatnot. this stays duplicate though until someone finds a convenient way
        to have one node in two Groups without the whole thing going mad
         */

        //region back button
        Button back = new Button();
        FXControls.setButton(back,20,20,"back");
        back.setOnAction(event -> changeView(editProjectGroup,initial));
        editProjectGroup.getChildren().add(back);
        //endregion

        //region projectID
        TextField projectID = new TextField();
        FXControls.setTextNode(projectID,40,80,"enter old ProjectID");
        editProjectGroup.getChildren().add(projectID);
        //endregion



        //region ProjectName
        TextField projectName = new TextField();
        FXControls.under(projectName,projectID,spacing);
        projectName.setPromptText("Project Name");
        editProjectGroup.getChildren().add(projectName);
        //endregion

        //region ProjectDuration
        TextField projectDuration = new TextField();
        FXControls.under(projectDuration,projectName,spacing);

        projectDuration.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { //takes the vale of the field and if it is not a Decimal Integer ->
                projectDuration.setText(newValue.replaceAll("[^\\d]", "")); //it replaces ever non-Decimal-integer with a blank field
            }
        });

        projectDuration.setPromptText("Project duration");

        editProjectGroup.getChildren().add(projectDuration);
        //endregion

        //region grab data button
        Button grabData = new Button();
        FXControls.setButton(grabData,230,80,"Grab data");
        grabData.setOnAction(event -> {
            try {
                String[] data = editProjectGrabHandler(projectID.getText());
                projectName.setText(data[1]);
                projectDuration.setText(data[2]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        editProjectGroup.getChildren().add(grabData);
        //endregion


        //region updateData Button
        Button updateData = new Button();
        FXControls.under(updateData,projectDuration,spacing);
        updateData.setText("Update Data");
        updateData.setOnAction(event -> {
            try {
                editProjectUpdateHandler(
                        projectID.getText(),
                        projectName.getText(),
                        (int) Double.parseDouble(projectDuration.getText())
                );

                Text confirmation = new Text();
                FXControls.setTextNode(confirmation,(int) updateData.getLayoutX()+120,(int) updateData.getLayoutY()+20,"Done!");
                editProjectGroup.getChildren().add(confirmation);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        editProjectGroup.getChildren().add(updateData);
        //endregion

    }


    private void changeView(Group currentGroup,Group targetGroup){
        getUiRoot().getChildren().remove(currentGroup);
        getUiRoot().getChildren().add(targetGroup);
    }


    private void generateID(String name, int PomodoroDuration, int shortBreakDuration, int LongBreakDuration) throws SQLException {
       boolean done = false;
       int i = 0;
        do{
            String bufferedID= getName()+i;
            System.out.println(bufferedID);

            if(!SQLHandler.checkUsername(bufferedID)){
                System.out.println("ID accepted: "+bufferedID);
                createConsultantHandler(
                        bufferedID,
                        name,
                        PomodoroDuration,
                        shortBreakDuration,
                        LongBreakDuration
                );
                done=true;
            }
            i++;
            if(i>=999){
                Text text = new Text();
                FXControls.setTextNode(text,180,180,"Error, the given name combination of "+getName()+" exceeds the " +
                        "numerical value limit of 999 for ID creation. Contact support.");
            }
        } while (!done);
    }

    private String getName(){
        return fNameInput.getText()+lNameInput.getText();
    }

    private String getNameFormatted(){
        return fNameInput.getText()+" "+lNameInput.getText();
    }


    private String[] editConsultantGrabHandler(String ID) throws SQLException {

        Connection con = DB.getCon();

        String[] Consultant = SQLHandler.grabConsultantData(con,ID);
        String[] Login = SQLHandler.grabLoginData(con,ID);
        return Utility.arrayMerge(Consultant,Login);
    }

    private void editConsultantUpdateHandler(String ID, String Name, int Pomodoro, int shortBreak, int longBreak, String password, int permissionLevel) throws SQLException {
        Connection con = DB.getCon();
        SQLHandler.updateConsultant(con, ID, Name, Pomodoro,shortBreak,longBreak,password,permissionLevel);
        DB.closeCon();
    }

    private void createProjectHandler(String ID, String Name, int Duration){
        Connection con = DB.getCon();
        try {
            SQLHandler.createProject(con, ID, Name, Duration);
            confirmation.setText("Project Created Successfully");
        } catch (SQLException e){
            e.printStackTrace();
            confirmation.setText("An error occurred. Check input and try again");
        }
    }

    private String[] editProjectGrabHandler(String ID) throws SQLException {
        Connection con = DB.getCon();
        return SQLHandler.grabProjectData(con,ID);
    }

    private void editProjectUpdateHandler(String ID, String Name, int Duration) throws SQLException {
        Connection con = DB.getCon();
        SQLHandler.updateProject(con, ID, Name, Duration);
        DB.closeCon();
    }

    private void createConsultantHandler(String ID,String name, int pomodoroDur, int shortBreakDur, int longBreakDur) throws SQLException {
        Connection con = DB.getCon();
        SQLHandler.createConsultant(con,ID,name,pomodoroDur,shortBreakDur,longBreakDur);
        SQLHandler.createConsultantLogin(con,ID);
        DB.closeCon();
    }

    private void deactivateConsultantHandler(String ID){
        Text statusResult = new Text();
        FXControls.setTextNode(statusResult,120, 110,"");
        deactivateConsultantGroup.getChildren().add(statusResult);

        //fixme this does not distinguish real ID's from "wrong" ID's that are not in the database, somehow
        try{
        SQLHandler.deactivateConsultant(DB.getCon(),ID);
        DB.closeCon();
        statusResult.setText("User successfully deactivated");
        }
        catch(SQLException s){
            s.printStackTrace();
            statusResult.setText("An error occurred. Ensure the ID is correct or that the user isn't already inactive");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
