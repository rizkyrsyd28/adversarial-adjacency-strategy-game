package bot;

import javafx.scene.control.Button;

import java.util.*;

import controllers.OutputFrameController;
import utils.UtilityFunction;

public abstract class Bot {
    protected String playerString;
    protected String opponentString;

    public abstract int[] move(OutputFrameController outputFrameController);

}


