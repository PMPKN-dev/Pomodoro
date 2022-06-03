package TheTomatoCo.OfficeOverview.Controller;

import TheTomatoCo.Foundation.Program;
import TheTomatoCo.Pomodoro.Controller.PomodoroTimer;

public class MyRunnable extends PomodoroTimer implements Runnable {
    @Override
    public void run(){
        PomodoroTimer johnAdams = new PomodoroTimer();
        //johnAdams.setUpTimerSettings();
        //johnAdams.setUpTimer();
        //johnAdams.runTimer();
        johnAdams.expand();

    }
}
