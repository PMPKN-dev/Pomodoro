package TheTomatoCo.OfficeOverview.Controller;

import TheTomatoCo.Pomodoro.Controller.Pomodoro;

public class MyRunnable extends Pomodoro implements Runnable {
    @Override
    public void run(){
        Pomodoro johnAdams = new Pomodoro();
        //johnAdams.setUpTimerSettings();
        //johnAdams.setUpTimer();
        //johnAdams.runTimer();
        johnAdams.expand();

    }
}
