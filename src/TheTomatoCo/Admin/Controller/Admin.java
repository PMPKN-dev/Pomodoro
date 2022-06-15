package TheTomatoCo.Admin.Controller;

import TheTomatoCo.Foundation.*;
import TheTomatoCo.Hub.Controller.LoginData;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.SQLException;

public class Admin extends Program {

    Group initial = new Group();
    Group createConsultantGroup = new Group();
    Group deactivateConsultantGroup = new Group();
    Group editConsultantGroup = new Group();
    Group createProjectGroup = new Group();
    Group editProjectGroup = new Group();
    TextField fNameInput;
    TextField lNameInput;
    int spacing = 35; // spacing between the TextFields for use with te FXControls.Under() method
    LoginData LoginID = LoginData.getInstance();


    @Override
    public void expand(){

        setUpInitial();
        setUpCreateConsultant();
        setUpDeactivateConsultant();
        setUpEditConsultant();
        setUpCreateProject();
        setUpEditProject();
        getUiRoot().getChildren().add(initial);
        //Todo; set up Create Project, Edit Project and Edit Consultant
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
        TextField userIDinitial = new TextField();
        FXControls.setTextNode(userIDinitial,40,80,"enter the ID for editing");
        editConsultantGroup.getChildren().add(userIDinitial);
        //endregion

        //region grab data button
        Button grabData = new Button();
        FXControls.setButton(grabData,230,80,"Grab data");
        editConsultantGroup.getChildren().add(grabData);
        //todo; make this button grab all the old data from the database and into an array and then set the following fields
        //todo; with data from said array
        //endregion

        //region fName TextField
        TextField fName = new TextField();
        FXControls.under(fName,userIDinitial,50);
        fName.setPromptText("First Name");
        editConsultantGroup.getChildren().add(fName);
        //endregion

        //region lName TextField
        TextField lName = new TextField();
        FXControls.under(lName,fName,spacing);
        lName.setPromptText("Last Name");
        editConsultantGroup.getChildren().add(lName);
        //endregion

        //region PomodoroTime TextField
        TextField PomodoroTime = new TextField();
        FXControls.under(PomodoroTime,lName,spacing);

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

        //region UserID TextField
        TextField UserIDEdit = new TextField();
        FXControls.setPosition(UserIDEdit,250,130);
        UserIDEdit.setPromptText("User ID");
        editConsultantGroup.getChildren().add(UserIDEdit);
        //endregion

        //region userPassword PasswordField
        PasswordField userPassword = new PasswordField();
        FXControls.under(userPassword,UserIDEdit,spacing);
        userPassword.setPromptText("User Password");
        editConsultantGroup.getChildren().add(userPassword);
        //endregion

        //region userPermissionLevel TextField
        TextField userPermissionLevel = new TextField();
        FXControls.under(userPermissionLevel,userPassword,spacing);
        userPermissionLevel.setPromptText("User Permission Level");
        editConsultantGroup.getChildren().add(userPermissionLevel);
        //endregion

        //region updateData Button
        Button updateData = new Button();
        FXControls.under(updateData,longBreakTime,spacing);
        updateData.setText("Update Data");
        editConsultantGroup.getChildren().add(updateData);
        //todo; make a Button that updates the data at target ID (being the one i the old ID field used to find the data)
        //todo; to all the data input in the further ones
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
        //FIXME; the Database has a sub-optimal specification for the project ID

        createProjectGroup.getChildren().add(createProject);
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

        //region oldProjectID
        TextField oldProjectID = new TextField();
        FXControls.setTextNode(oldProjectID,40,80,"enter old ProjectID");
        editProjectGroup.getChildren().add(oldProjectID);
        //endregion

        //region grab data button
        Button grabData = new Button();
        FXControls.setButton(grabData,230,80,"Grab data");
        editProjectGroup.getChildren().add(grabData);
        //todo; make this button grab all the old data from the database and into an array and then set the following fields
        //todo; with data from said array
        //endregion

        //region newProjectID (by default copied from old)
        TextField newProjectID = new TextField();
        FXControls.under(newProjectID,oldProjectID,50);
        newProjectID.setPromptText("Project ID");
        editProjectGroup.getChildren().add(newProjectID);
        //endregion

        //region ProjectName
        TextField projectName = new TextField();
        FXControls.under(projectName,newProjectID,spacing);
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

        //region updateData Button
        Button updateData = new Button();
        FXControls.under(updateData,projectDuration,spacing);
        updateData.setText("Update Data");
        editProjectGroup.getChildren().add(updateData);
        //todo; make a Button that updates the data at target ID (being the one i the old ID field used to find the data)
        //todo; to all the data input in the further ones
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


    private void editConsultantGrabHandler(){
        //order of op:
        /*
        run a SQL statement to get all info for selected ID and put said info into a String[]
        use the String[] to put info into corresponding textFields
         */
    }

    private void editConsultantUpdateHandler(){
        //order of op:
        /*
        grab all the info from the textFields and run an SQL statement based on the old ID in which you update the entries to the new info
         */
    }

    private void createProjectHandler(String ID, String Name, int Duration){
        Connection con = DB.getCon();
        try {
            SQLHandler.createProject(con, ID, Name, Duration);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //has to wait for clarification of ProjectID
    private void editProjectGrabHandler(){
        //order of op:
        /*
        run a SQL statement to get all info for selected ID and put said info into a String[]
        use the String[] to put info into corresponding textFields
         */
    }

    //has to wait for clarification of ProjectID
    private void editProjectUpdateHandler(){
        //order of op:
        /*
        grab all the info from the textFields and run an SQL statement based on the old ID in which you update the entries to the new info
         */
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
