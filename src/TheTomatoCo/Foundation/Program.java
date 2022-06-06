package TheTomatoCo.Foundation;

import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.*;

//import java.awt.*;
import java.util.Objects;

/**
 * A custom template for an Application that allows us to have the same layout and design on basic things like exit buttons
 * or other displays across all the controllers with ease.
 * <p>This class, by all means, functions as an abstract for our controllers with the difference that it is not marked as such due to
 * technical limitations which prevent it from working the way we intend when abstract
 */
public class Program extends Application {

    Group defaults = new Group();
    public Group userInfo = new Group();
    AnchorPane uiRoot = new AnchorPane();

    /**
     * A method for getting the AnchorPane/Parent of the current Program Application
     * @return The AnchorPane serving as the uiRoot of the current Program
     */
    public AnchorPane getUiRoot (){
        return uiRoot;
    }


    private void aProgramInitialize(Scene scene){
        scene.getStylesheets().add(Objects.requireNonNull(super.getClass().getResource("/TheTomatoCo/Resources/Style.css")).toExternalForm());

        //region exit button
        Button exit = new Button();
        FXControls.setButton(exit,300,350,"exit");
        EventHandler<ActionEvent> handler = event -> {
            Stage stage = (Stage) getUiRoot().getScene().getWindow();
            stage.close();
        };
        exit.setOnAction(handler);

        defaults.getChildren().add(exit);
        //endregion

        //region userInfoBackground Rectangle
        Rectangle userInfoBackground = new Rectangle();
        FXControls.setPosition(userInfoBackground,400,0);
        userInfoBackground.setHeight(90);
        userInfoBackground.setWidth(198);
        userInfoBackground.setFill(Color.WHITE);
        userInfoBackground.setStroke(Color.BLACK);

        userInfo.getChildren().add(userInfoBackground);
        //endregion

        Label userNameLabel = new Label();
        FXControls.setTextNode(userNameLabel,405,5,"Logged in as: ");
        userInfo.getChildren().add(userNameLabel);

        Label userStatusLabel = new Label();
        FXControls.setTextNode(userStatusLabel,405,45,"With Permission level: ");
        userInfo.getChildren().add(userStatusLabel);

        Text userNameText = new Text();
        FXControls.setTextNode(userNameText,405,40,"Unknown");
        userInfo.getChildren().add(userNameText);

        Text userStatusText = new Text();
        FXControls.setTextNode(userStatusText,405,80,"Undefined");
        userInfo.getChildren().add(userStatusText);
    }


    public void start(Stage primaryStage) {
        uiRoot = new AnchorPane();
        uiRoot.getChildren().addAll(defaults,userInfo);
        Scene scene = new Scene(uiRoot, 600, 400);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        aProgramInitialize(scene);
        primaryStage.setTitle(this.getClass().getSimpleName());
        expand();
        primaryStage.show();
    }

    /**
     * Override this to add code to the Controller
     */
    public void expand(){
        Text text = new Text();
        FXControls.setPosition(text,100,90);
        text.setText("Looks like you did not @Override the expand() method");
        defaults.getChildren().add(text);
    }
}
