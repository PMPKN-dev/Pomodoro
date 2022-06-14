package TheTomatoCo.Foundation;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class FXControls {

    public static void setPosition(Node node, int x, int y) {
        node.setLayoutY(y);
        node.setLayoutX(x);
    }

    public static void setButton(Button button, int x, int y, String text){
        setPosition(button,x,y);
        button.setText(text);
    }

    public static void setTextNode(Text textNode, int x, int y, String text){
        setPosition(textNode,x,y);
        textNode.setText(text);
    }

    public static void setTextNode(TextField textNode, int x, int y, String text){
        setPosition(textNode,x,y);
        textNode.setPromptText(text);
    }

    public static void setTextNode(Label textNode, int x, int y, String text){
        setPosition(textNode,x,y);
        textNode.setText(text);
    }

    public static void under(Node under, Node over, int spacing){
        under.setLayoutY(over.getLayoutY()+spacing);
    }

}
