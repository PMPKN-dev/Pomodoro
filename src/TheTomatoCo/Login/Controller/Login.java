package TheTomatoCo.Login.Controller;

import TheTomatoCo.Foundation.DB;
import TheTomatoCo.Foundation.FXControls;
import TheTomatoCo.Foundation.Program;
import TheTomatoCo.Foundation.SQLHandler;
import TheTomatoCo.Hub.Controller.Hub;
import TheTomatoCo.Hub.Controller.LoginData;
import TheTomatoCo.Pomodoro.Controller.Timer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

import java.sql.SQLException;
import java.util.Objects;

public class Login extends Program {


    Group initialScreen = new Group();
    TextField id;
    PasswordField pass;
    Text LoginErrorText;
    LoginData LoginID = LoginData.getInstance();


    @Override
    public void expand() {

        initialScreen();
        getUiRoot().getChildren().remove(userInfo);

    }
    private void initialScreen(){

        Text text = new Text();
        FXControls.setPosition(text,40,60);
        text.setText("Welcome to Pomodoro");
        //TODO: Insert login here and make it appear before the timer through group-changing
        //Login with ID and Pass
        //TODO: Make it check if ID and Pass matches in database - DONE
        //TODO: Have it refuse to enter Pomodoro if login does not match

        id = new TextField();
        id.setPromptText("Enter ID");
        id.setText("JohnAdams01");
        id.setLayoutX(200);
        id.setLayoutY(200);
        initialScreen.getChildren().add(id);

        pass = new PasswordField();
        pass.setPromptText("Enter Password");
        pass.setText("BDhp2G");
        pass.setLayoutX(200);
        pass.setLayoutY(250);
        initialScreen.getChildren().add(pass);

        Button login = new Button();
        FXControls.setButton(login,200,150,"Log in");
        initialScreen.getChildren().add(login);

        login.setOnAction(event -> {
            loginCheckHandler(id.getText(), pass.getText());
        });

        LoginErrorText = new Text();
        FXControls.setPosition(LoginErrorText,200,100);
        initialScreen.getChildren().add(LoginErrorText);

        getUiRoot().getChildren().add(initialScreen);


    }

    public void loginCheckHandler(String userName, String userPass) {

        try {
            int PermissionLvl = SQLHandler.VerifyLogin(DB.getCon(),userPass,userName);
            String result = SQLHandler.checkLogin(
                    DB.getCon(),
                    userName
            );

            if (Objects.equals(result, userPass)) {
                LoginErrorText.setText("");
                LoginID.setUserID(userName);
                LoginID.setPermissionLvl(PermissionLvl);
                launchHub();
                setUserData();
                System.out.println("Login success");


            } else {
                LoginErrorText.setText("Password is incorrect");
                pass.setText("");
                System.out.println("Login failed");
            }


        }catch (SQLException e){
            id.setText("");
            LoginErrorText.setText("Invalid/Unknown username");
            e.printStackTrace();
        }

        DB.closeCon();

    }

    private void launchHub(){
        Stage currentStage = (Stage) getUiRoot().getScene().getWindow();
        System.out.println(currentStage.toString());
        currentStage.close();

        Hub Hub = new Hub();
        Stage stage = new Stage();
        Hub.start(stage);
        stage.show();
    }
    private void setUserData(){

        /*todo; make this call a method in the super class that loads info based on the current login
        something like:
        super.setData(id.getText());
        which then in the super does:
        sqlhandler.getPermission
        and
        sqlhandler.getName (of sorts)
        and places those into the text box
         */
    }
}
