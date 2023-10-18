package bot;

import java.util.Comparator;
import java.util.PriorityQueue;

import controllers.OutputFrameController;

public class LocalSearchBot extends Bot {

    public LocalSearchBot(String playerString) {
        this.playerString = playerString;
        if (this.playerString.equals("O")) {
            this.opponentString = "X";
        } else {
            this.opponentString = "O";
        }
    }

    @Override
    public int[] move(OutputFrameController outputFrameController) {
        int[] bestMove = new int[2];
        int bestGreedy = Integer.MIN_VALUE;
        for (int i = 0; i < outputFrameController.getButtons().length; i++) {
            for (int j = 0; j < outputFrameController.getButtons()[i].length; j++) {
                if (outputFrameController.getButtons()[i][j].getText().equals("")) {
                    int pQueue = 1;
                    if (i - 1 >= 0 && outputFrameController.getButtons()[i - 1][j].getText().equals(this.opponentString)) {
                        pQueue++;
                    }
                    if (j - 1 >= 0 && outputFrameController.getButtons()[i][j - 1].getText().equals(this.opponentString)) {
                        pQueue++;
                    }
                    if (i + 1 < 8 && outputFrameController.getButtons()[i + 1][j].getText().equals(this.opponentString)) {
                        pQueue++;
                    }
                    if (j + 1 < 8 && outputFrameController.getButtons()[i][j + 1].getText().equals(this.opponentString)) {
                        pQueue++;
                    }
                    if (pQueue >= bestGreedy) {
                        bestGreedy = pQueue;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }
}
