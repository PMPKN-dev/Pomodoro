package Hub.Admin.Controller;

import Hub.Foundation.FXControls;
import Hub.Foundation.program;
import javafx.scene.text.Text;

public class Admin extends program {

    @Override
    public void expand(){
        Text test = new Text();
        FXControls.setPosition(test,50,50);
        test.setText("this is a test text for this purpose");


        getUiRoot().getChildren().add(test);
    }
}
