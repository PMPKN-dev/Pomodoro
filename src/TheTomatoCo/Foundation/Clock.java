
package TheTomatoCo.Foundation;

import TheTomatoCo.Pomodoro.Controller.Pomodoro;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class Clock {

    private int cycle = 1;
    private int currentTimePomodoro;
    private int currentTimeShortBreak;
    private int currentTimeLongBreak;
    private Label clockLabel;
    private Timer timer;
    private int minutes;
    private int seconds;
    private Timeline timeline;
    private int remainingTime;

    public void run(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {

                    minutes = (remainingTime / 60) % 60;
                    seconds = remainingTime % 60;

                    seconds--;
                    if(seconds == 0){
                        minutes--;
                    }
                    if(minutes == 0 && seconds == 0){
                        timer.cancel();

                    }
                });
            }
        },1000,1000);
    }

}

/**
 * Synchronized counter ca be updated using several threads
 */
