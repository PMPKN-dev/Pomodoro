package Hub.Foundation;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.*;

import java.io.IOException;

/**
 * A custom template for an Application that allows us to have the same layout and design on basic things like exit buttons
 * or other displays across all the controllers with ease
 */
public class program extends Application {

    Group defaults = new Group();


    /**
     * Instantiates the default Nodes and StyleSheet
     * @param scene The scene you are loading, which you want to apply the StyleSheet to
     */
    private void aProgramInitialize(Scene scene){
        scene.getStylesheets().add(super.getClass().getResource("/Hub/Resources/Style.css").toExternalForm());

        Button exit = new Button();
        FXControls.setButton(exit,100,100,"exit");

        defaults.getChildren().addAll(exit);
    }


    public void start(Stage primaryStage) throws Exception {
        AnchorPane uiRoot = new AnchorPane();
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
     * Override this to add code
     */
    public void expand(){

    }


}
