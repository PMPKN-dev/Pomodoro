package TheTomatoCo.Admin.Controller;

import TheTomatoCo.Foundation.*;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class Admin extends Program {

    Group initial = new Group();
    Group createConsultantGroup = new Group();

    @Override
    public void expand(){


        setUpInitial();
        setUpCreateConsultant();



    }

    private void setUpInitial(){
        //region test text
        Text text = new Text();
        FXControls.setPosition(text,60,60);
        text.setText("this text is the initial display in this window");

        initial.getChildren().add(text);
        //endregion

        //region button for changing views
        Button createConsultant = new Button();
        FXControls.setButton(createConsultant,100,100,"Create Consultant");
        createConsultant.setOnAction(event -> changeGroup(initial,createConsultantGroup));

        initial.getChildren().add(createConsultant);
        //endregion

        getUiRoot().getChildren().add(initial);
    }

    private void setUpCreateConsultant(){
        //region back button
        Button back = new Button();
        FXControls.setButton(back,20,20,"back");
        back.setOnAction(event -> returnToInitial(createConsultantGroup));
        createConsultantGroup.getChildren().add(back);
        //endregion

        //region Name field

        //endregion

        //region Pomodoro duration field
        TextField PomodoroDurationInput = new TextField();
        FXControls.setPosition(PomodoroDurationInput,120,120);

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

        //region Break duration field
        //endregion
    }

    private void changeGroup(Group currentGroup, Group targetGroup){
        removeGroup(currentGroup);
        loadGroup(targetGroup);
    }

    private void returnToInitial(Group currentGroup){
        changeGroup(currentGroup,initial);
    }

    private void removeGroup(Group group){
        getUiRoot().getChildren().removeAll(group);
    }

    private void loadGroup(Group group){
        getUiRoot().getChildren().addAll(group);
    }
}
