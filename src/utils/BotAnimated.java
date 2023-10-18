package utils;

import controllers.OutputFrameController;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class BotAnimated extends Task<Void> {

    private OutputFrameController outputFC;

    public BotAnimated(OutputFrameController outputFC) {
        this.outputFC = outputFC;
    }

    @Override
    protected Void call() throws Exception {
        try {
            while (this.outputFC.getRoundsLeft() != 0) {
                Platform.runLater(() -> {
                    this.outputFC.moveBotX();
                });
                
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
