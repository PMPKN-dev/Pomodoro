package Hub.Admin.Controller;

import Hub.Foundation.*;
import javafx.scene.text.Text;

public class Admin extends Program {

    @Override
    public void expand(){
        Text test = new Text();
        FXControls.setPosition(test,50,50);
        test.setText("this is a test text for this purpose");


        getUiRoot().getChildren().add(test);
    }
}
