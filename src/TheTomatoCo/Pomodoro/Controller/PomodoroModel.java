package TheTomatoCo.Pomodoro.Controller;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class PomodoroModel {
    private boolean isRunning = false;
    private boolean isBreak = false;
    private Timer timer = new Timer();
    private final Label timerLabel, whatsNextTODO;
    private int seconds, minutes = 0;
    private int counter = 60 * 25; //placeholder 25min

    public PomodoroModel(Label timerLabel, Label whatsNextTODO){
        this.timerLabel = timerLabel;
        this.whatsNextTODO = whatsNextTODO;
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
                    //This is the inner workings on the counter
                    counter--;
                    seconds = counter % 60; //Modulo gives the remainder
                    minutes = counter / 60;
                    //NORMAL COUNTER
                    if(seconds < 10 && minutes < 10){ //if its 09:09, it knows to put 0 infront of the counters
                        timerLabel.setText("0" + minutes + ":0" + seconds);
                    }else if(minutes < 10){ //if its 09:15, it knows seconds is normal counter
                        timerLabel.setText("0" + minutes + ":" + seconds);
                    }else{
                        timerLabel.setText(minutes + ":" + seconds); //If neither is below 10 in counter
                    }
                    if(counter == 0 && !isBreak){ //Break, and it isnt already a break
                        isBreak = true;
                        counter = 15 * 60; //15min TODO: Insert binding from settings-chosen-min
                    } else if(counter == 0 ){ //break finished
                        counter = 25 * 60; //default 25min TODO: Insert binding from settings-chosen-min
                        isBreak = false;
                    }

                    //TODO: insert the binding for the labels for breaks and pausing/starting timer
                });
            }
        },0,1000); //otherwise runLater doesnt work
    }
}
