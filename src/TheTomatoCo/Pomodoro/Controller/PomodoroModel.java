package TheTomatoCo.Pomodoro.Controller;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class PomodoroModel {
    private boolean isRunning = false;
    private boolean isBreak = false;
    private Timer timer = new Timer();
    private final Label timerLabel;
    private int seconds, minutes = 0;
    private int counter = 60 * 25; //placeholder

    public PomodoroModel(Label timerLabel){
        this.timerLabel = timerLabel;
    }

    public boolean isRunning(){
        return isRunning;
    }
    public void setRunning(boolean running){
        isRunning = running;
    }
    public void pauseTimer(){
        timer.cancel();
    }
    public void startTimer(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    counter--;
                    seconds = counter % 60;
                    minutes = counter / 60;

                    //TODO: insert the binding for the labels for breaks and pausing/starting timer
                });
            }
        },0,1000); //otherwise runLater doesnt work
    }
}
