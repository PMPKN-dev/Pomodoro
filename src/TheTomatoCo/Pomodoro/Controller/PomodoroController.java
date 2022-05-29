package TheTomatoCo.Pomodoro.Controller;

import javafx.scene.Node;

public class PomodoroController {
    public PomodoroController(PomodoroModel pomodoroModel, PomodoroView pomodoroView){
        pomodoroView.getStartPauseButton().setOnAction((event) -> {
            if(pomodoroModel.isRunning()){
                pomodoroModel.pauseTimer();
                pomodoroModel.setRunning(false);
            }else{
                pomodoroModel.startTimer();
                pomodoroModel.setRunning(true);
            }
        });
    }
}
