package TheTomatoCo.Pomodoro.Controller;

public class PomodoroController {
    public PomodoroController(PomodoroModel pomodoroModel, PomodoroView pomodoroView){
        pomodoroView.getStartPauseButton().setOnAction((e) -> {
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
