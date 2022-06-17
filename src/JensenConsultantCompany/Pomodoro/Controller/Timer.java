package JensenConsultantCompany.Pomodoro.Controller;

import javafx.concurrent.Task;

/**
 * In order to eventually run multiple timers, a separate class was created utilizing the task functionality in javafx.
 * This class allows us to run a task in the background of the application, in this case the tasks functionality can be seen below in the call() method.
 */


public class Timer extends Task<Void> {

    public void setPomodoroTimer(int pomodoroTimer) {
        this.PomodoroTimer = pomodoroTimer;
    }

    int PomodoroTimer = 0;
    boolean isRunning = false;


    @Override
    public Void call() {
        isRunning = true;

        while(isRunning & PomodoroTimer >0){
            try{
                Thread.sleep(1000);
                PomodoroTimer--;
                if(PomodoroTimer %60<10){
                    //In order to send the desired information back to the Pomodoro class, the updateMessage() functionality is used,
                    // in which case we take the current timer and format it in a string.
                    updateMessage(PomodoroTimer /60+":0"+ PomodoroTimer %60);
                }else
                    updateMessage(PomodoroTimer /60+":"+ PomodoroTimer %60);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
