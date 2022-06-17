package TheTomatoCo.Pomodoro.Controller;

import javafx.concurrent.Task;

public class Timer extends Task<Void> {
    public void setElapsed(int elapsed) {
        this.elapsed = elapsed;
    }

    int elapsed = 0;
    boolean isRunning = false;


    @Override
    public Void call() {
    isRunning = true;

        while(isRunning & elapsed>0){
            try{
                Thread.sleep(1000);
                elapsed--;
                if(elapsed%60<10){
                    updateMessage(elapsed/60+":0"+elapsed%60);
                }else
                    updateMessage(elapsed/60+":"+elapsed%60);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
