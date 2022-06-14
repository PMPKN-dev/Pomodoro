package TheTomatoCo.Admin.Controller;

import TheTomatoCo.Foundation.*;
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
    int spacing = 20; // spacing between the TextFields for use with te FXControls.Under() method


    @Override
    public void expand(){

        setUpInitial();
        setUpCreateConsultant();
        setUpDeactivateConsultant();
        getUiRoot().getChildren().add(initial);

        //Todo; set up Create Project, Edit Project and Edit Consultant

        //for edit consultant create the function to load current info based on ID


    }

    private void setUpInitial(){

        //region buttons for changing views
        Button createConsultant = new Button();
        FXControls.setButton(createConsultant,100,100,"Create Consultant");
        createConsultant.setOnAction(event -> changeView(initial,createConsultantGroup));

        initial.getChildren().add(createConsultant);

        Button deactivateConsultant = new Button();
        FXControls.setButton(deactivateConsultant,100,135,"Deactivate Consultant");
        deactivateConsultant.setOnAction(event -> changeView(initial,deactivateConsultantGroup));

        initial.getChildren().add(deactivateConsultant);
        //endregion

        //todo; add the new buttons!!!
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
        FXControls.setTextNode(userIDinitial,50,50,"enter the ID for editing");
        editConsultantGroup.getChildren().add(userIDinitial);
        //endregion

        //region grab data button
        //todo; make this button grab all the old data from the database and into an array and then set the following fields
        //todo; with data from said array
        //endregion

        //region fName TextField
        TextField fName = new TextField();
        FXControls.under(fName,userIDinitial,spacing);
        editConsultantGroup.getChildren().add(fName);
        //endregion

        //region lName TextField
        TextField lName = new TextField();
        FXControls.under(lName,fName,spacing);
        editConsultantGroup.getChildren().add(lName);
        //endregion

        //region PomodoroTime TextField
        TextField PomodoroTime = new TextField();
        FXControls.under(PomodoroTime,lName,spacing);
        editConsultantGroup.getChildren().add(PomodoroTime);
        //endregion

        //region shortBreakTime TextField
        TextField shortBreakTime = new TextField();
        FXControls.under(shortBreakTime,PomodoroTime,spacing);
        editConsultantGroup.getChildren().add(shortBreakTime);
        //endregion

        //region longBreakTime TextField
        TextField longBreakTime = new TextField();
        FXControls.under(longBreakTime,shortBreakTime,spacing);
        editConsultantGroup.getChildren().add(longBreakTime);
        //endregion

        //region UserID TextField
        TextField UserIDedit = new TextField();
        FXControls.setPosition(UserIDedit,200,50);
        editConsultantGroup.getChildren().add(UserIDedit);
        //endregion

        //region userPassword PasswordField
        PasswordField userPassword = new PasswordField();
        FXControls.under(userPassword,UserIDedit,spacing);
        editConsultantGroup.getChildren().add(userPassword);
        //endregion

        //region userPermissionLevel TextField
        TextField userPermissionLevel = new TextField();
        FXControls.under(userPermissionLevel,userPassword,spacing);
        editConsultantGroup.getChildren().add(userPermissionLevel);
        //endregion


    }

    private void setUpCreateProject(){

        //region back button
        Button back = new Button();
        FXControls.setButton(back,20,20,"back");
        back.setOnAction(event -> changeView(createProjectGroup,initial));
        createProjectGroup.getChildren().add(back);
        //endregion

        //region ProjectID
        //endregion

        //region ProjectName
        //endregion

        //region ProjectDuration
        //endregion

    }

    private void setUpEditProject(){

        //region back button
        Button back = new Button();
        FXControls.setButton(back,20,20,"back");
        back.setOnAction(event -> changeView(editProjectGroup,initial));
        editProjectGroup.getChildren().add(back);
        //endregion

        //region oldProjectID
        //endregion

        //region grab data button
        //todo; make this button grab all the old data from the database and into an array and then set the following fields
        //todo; with data from said array
        //endregion

        //region newProjectID (by default copied from old)
        //endregion

        //region ProjectName
        //endregion

        //region ProjectDuration
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
