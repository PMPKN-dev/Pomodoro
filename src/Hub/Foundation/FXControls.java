package Hub.Foundation;

import javafx.scene.*;
import javafx.scene.control.*;

public class FXControls {

    public static void setPosition(Node node, int x, int y) {
        node.setLayoutY(y);
        node.setLayoutX(x);
    }

    public static void setPosition(Button button, int x, int y){
        button.setLayoutY(y);
        button.setLayoutX(x);
    }

    public static void setButton(Button button, int x, int y, String text){
        setPosition(button,x,y);
        button.setText(text);

    }

}
