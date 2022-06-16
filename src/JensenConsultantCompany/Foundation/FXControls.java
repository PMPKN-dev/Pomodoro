package JensenConsultantCompany.Foundation;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class FXControls {

    /**
     * Allows all programs to use the same positioning
     * @param node The node
     * @param x The coordinate for x
     * @param y The coordinate for y
     */
    public static void setPosition(Node node, int x, int y) {
        node.setLayoutY(y);
        node.setLayoutX(x);
    }

    /**
     * Allows all programs to easily set up a Button
     * @param button Button
     * @param x The coordinate for x
     * @param y The coordinate for y
     * @param text The text
     */
    public static void setButton(Button button, int x, int y, String text){
        setPosition(button,x,y);
        button.setText(text);
    }

    /**
     * Allows all programs to easily set up a Text
     * @param textNode The node
     * @param x The coordinate for x
     * @param y The coordinate for y
     * @param text Text
     */
    public static void setTextNode(Text textNode, int x, int y, String text){
        setPosition(textNode,x,y);
        textNode.setText(text);
    }

    /**
     * Allows all programs to easily set up a PromptText
     * @param textNode The node
     * @param x The coordinate for x
     * @param y The coordinate for y
     * @param text PromptText
     */
    public static void setTextNode(TextField textNode, int x, int y, String text){
        setPosition(textNode,x,y);
        textNode.setPromptText(text);
    }

    /**
     * Allows all programs to easily set up a Text
     * @param textNode The node
     * @param x The coordinate for x
     * @param y The coordinate for y
     * @param text Text
     */
    public static void setTextNode(Label textNode, int x, int y, String text){
        setPosition(textNode,x,y);
        textNode.setText(text);
    }

    /**
     * Allows all programs to easily set up spacing
     * @param under The node
     * @param over The node
     * @param spacing Spacing between objects
     */
    public static void under(Node under, Node over, int spacing){
        under.setLayoutY(over.getLayoutY()+spacing);
        under.setLayoutX(over.getLayoutX());
    }

}
