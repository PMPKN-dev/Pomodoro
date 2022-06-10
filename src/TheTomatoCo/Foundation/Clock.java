
package TheTomatoCo.Foundation;

import javafx.scene.control.Label;

public class Clock {

    private int currentTimePomodoro;
    private int currentTimeShortBreak;
    private int currentTimeLongBreak;
    private Label clockLabel;
    Clock(){

    }
    public void run(){
        int i = (int) Double.parseDouble(String.valueOf(currentTimePomodoro));

        this.clockLabel.setText(String.valueOf(i));
        System.out.println(i);
    }


}