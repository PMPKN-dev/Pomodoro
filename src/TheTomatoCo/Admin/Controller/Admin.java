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

    @Override
    public void expand(){

        setUpInitial();
        setUpCreateConsultant();
        setUpDeactivateConsultant();
        getUiRoot().getChildren().add(initial);



    }

    private void setUpInitial(){
        //region test text
        Text text = new Text();
        FXControls.setPosition(text,60,60);
        text.setText("this text is the initial display in this window");

        initial.getChildren().add(text);
        //endregion

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
    }

    private void changeView(Group currentGroup,Group targetGroup){
        getUiRoot().getChildren().remove(currentGroup);
        getUiRoot().getChildren().add(targetGroup);
    }

    private void setUpCreateConsultant(){

        //region back button
        Button back = new Button();
        FXControls.setButton(back,20,20,"back");
        back.setOnAction(event -> changeView(createConsultantGroup,initial));
        createConsultantGroup.getChildren().add(back);
        //endregion

        //region fName field
        TextField fNameInput  = new TextField();
        FXControls.setPosition(fNameInput,40,80);
        fNameInput.setMaxWidth(120);
        fNameInput.setPromptText("First name");

        createConsultantGroup.getChildren().add(fNameInput);
        //endregion

        //region lName field
        TextField lNameInput  = new TextField();
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

        //concatenation kekw
        String fullName = fNameInput.getText()+" "+lNameInput.getText();

        createButton.setOnAction(event -> {
            try {
                createConsultantHandler(
                        fullName,
                        (int) Double.parseDouble(PomodoroDurationInput.getText()),
                        (int) Double.parseDouble(shortBreakDurationInput.getText()),
                        (int) Double.parseDouble(longBreakDurationInput.getText())
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        createConsultantGroup.getChildren().add(createButton);
        //endregion
    }

    //todo; create consultant deactivation (not deletion but change of status to inactive)
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

    private void createConsultantHandler(String name, int pomodoroDur, int shortBreakDur, int longBreakDur) throws SQLException {
        Connection con = DB.getCon();
        SQLHandler.createConsultant(con,name,pomodoroDur,shortBreakDur,longBreakDur);
        DB.closeCon();
    }

    private void deactivateConsultantHandler(String ID){
        try{
        SQLHandler.deactivateConsultant(DB.getCon(),ID);
        DB.closeCon();
        }
        catch(SQLException s){
            s.printStackTrace();
        }
    }

}
