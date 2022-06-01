package TheTomatoCo.Login.Controller;

import TheTomatoCo.Foundation.DB;
import TheTomatoCo.Foundation.FXControls;
import TheTomatoCo.Foundation.Program;
import TheTomatoCo.Foundation.SQLHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends Program {


    Group initialScreen = new Group();
    TextField id;
    PasswordField pass;
    Text LoginErrorText;


    @Override
    public void expand() {

        initialScreen();

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
        id.setLayoutX(200);
        id.setLayoutY(200);
        initialScreen.getChildren().add(id);

        pass = new PasswordField();
        pass.setPromptText("Enter Password");
        pass.setLayoutX(200);
        pass.setLayoutY(250);
        initialScreen.getChildren().add(pass);

        //region obolete login check
        /*
        PreparedStatement ps;
        ResultSet rs;
        String userName = id.getText();
        String password = pass.getText();

        String query = "SELECT * FROM Users WHERE userName=? AND userPass=?"; //DB not created yet, values may change

        try{
            ps = DB.getCon().prepareStatement(query);
            ps.setString(1,userName);
            ps.setString(2,password);
            rs = ps.executeQuery();

            if(rs.next()){
                //enter pomodoro
            }else{
                //TODO: prevent entering
                id.setText("");
                pass.setText("");
            }

        }catch(SQLException ex){
        }
         */
         //endregion

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
            int result = SQLHandler.checkLogin(
                    DB.getCon(),
                    (int) Double.parseDouble(userName)
            );

            if (result == (int) Double.parseDouble(userPass)) {
                LoginErrorText.setText("");
                //make login work/ make it open the jub program here
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
        } catch (NumberFormatException n){
            id.setText("");
            LoginErrorText.setText("Username can only consist of int");
            n.printStackTrace();
        }

        DB.closeCon();

    }
}
