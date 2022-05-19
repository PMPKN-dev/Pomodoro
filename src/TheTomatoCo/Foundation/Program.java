package TheTomatoCo.Foundation;

import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.util.Objects;

/**
 * A custom template for an Application that allows us to have the same layout and design on basic things like exit buttons
 * or other displays across all the controllers with ease
 */
public class Program extends Application {

    Group defaults = new Group();
    AnchorPane uiRoot = new AnchorPane();

    public AnchorPane getUiRoot (){
        return uiRoot;
    }


    private void aProgramInitialize(Scene scene){
        scene.getStylesheets().add(Objects.requireNonNull(super.getClass().getResource("/TheTomatoCo/Resources/Style.css")).toExternalForm());

        Button exit = new Button();
        FXControls.setButton(exit,100,100,"exit");
        EventHandler<ActionEvent> handler = event -> Platform.exit();
        exit.setOnAction(handler);

        defaults.getChildren().addAll(exit);
    }


    public void start(Stage primaryStage) {
        uiRoot = new AnchorPane();
        uiRoot.getChildren().addAll(defaults);
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
        FXControls.setPosition(text,100,100);
        text.setText("Looks like you did not @Override the expand() method");
    }


}