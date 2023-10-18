package bot;

import controllers.OutputFrameController;
import javafx.scene.control.Button;
import utils.UtilityFunction;

import javax.swing.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MinimaxBot extends Bot {

    public MinimaxBot(String playerString) {
        this.playerString = playerString;
        if (this.playerString.equals("O")) {
            this.opponentString = "X";
        } else {
            this.opponentString = "O";
        }
    }

    @Override
    /*
     * Finds the best move for the bot player in the Adjacency Strategy Game using the Minimax algorithm with Alpha-Beta Pruning.
     *
     * @param buttons     The current state of the game board represented as a 2D array of Buttons.
     * @param roundsLeft  The number of rounds left in the game.
     * @param xScore      The score of the X player.
     * @param oScore      The score of the O player.
     * @param isBotFirst  Indicates whether the bot is the first player.
     * @return An array of length 2 representing the row and column indices of the best move for the bot.
     */
    public int[] move(OutputFrameController of) {
        int bestScore = Integer.MIN_VALUE, nextRound;

        int maxDepth = 4;
        if (of.getRoundsLeft() < maxDepth) {
            maxDepth = of.getRoundsLeft();
        }

        if (of.isBotFirst()) {
            if (this.playerString.equals("X")) {
                nextRound = maxDepth - 1;
            } else {
                nextRound = maxDepth;
            }
        } else {
            if (this.playerString.equals("X")) {
                nextRound = maxDepth;
            } else {
                nextRound = maxDepth - 1;
            }
        }
        int[] bestMove = new int[2];

        for (int i = 0; i < of.getButtons().length; i++) {
            for (int j = 0; j < of.getButtons()[i].length; j++) {
                if (UtilityFunction.isWorthy(of.getButtons(), i, j, this.opponentString)) {
                    int oIncrement = 0;
                    of.getButtons()[i][j].setText(this.playerString);
                    boolean up = false, right = false, down = false, left = false;
                    if (i - 1 >= 0 && of.getButtons()[i - 1][j].getText().equals(this.opponentString)) {
                        up = true;
                        of.getButtons()[i - 1][j].setText(this.playerString);
                        oIncrement++;
                    }
                    if (j + 1 < 8 && of.getButtons()[i][j + 1].getText().equals(this.opponentString)) {
                        oIncrement++;
                        of.getButtons()[i][j + 1].setText(this.playerString);
                        right = true;
                    }
                    if (i + 1 < 8 && of.getButtons()[i + 1][j].getText().equals(this.opponentString)) {
                        oIncrement++;
                        of.getButtons()[i + 1][j].setText(this.playerString);
                        down = true;
                    }
                    if (j - 1 >= 0 && of.getButtons()[i][j - 1].getText().equals(this.opponentString)) {
                        oIncrement++;
                        of.getButtons()[i][j - 1].setText(this.playerString);
                        left = true;
                    }

                    int playerScore, opponentScore;
                    if (this.playerString.equals("O")) {
                        playerScore = of.getPlayerOScore();
                        opponentScore = of.getPlayerXScore();
                    } else {
                        playerScore = of.getPlayerXScore();
                        opponentScore = of.getPlayerOScore();
                    }

                    int score = minimax(
                            nextRound,
                            Integer.MIN_VALUE,
                            Integer.MAX_VALUE,
                            false,
                            opponentScore - oIncrement,
                            playerScore + 1 + oIncrement,
                            of.getButtons(),
                            of.isBotFirst(),
                            oIncrement
                    );
                    of.getButtons()[i][j].setText("");
                    if (up) {
                        of.getButtons()[i - 1][j].setText(this.opponentString);
                    }
                    if (right) {
                        of.getButtons()[i][j + 1].setText(this.opponentString);
                    }
                    if (down) {
                        of.getButtons()[i + 1][j].setText(this.opponentString);
                    }
                    if (left) {
                        of.getButtons()[i][j - 1].setText(this.opponentString);
                    }
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{i, j};
                    }
                }
            }
        }
        return bestMove;
    }

    /**
     * Recursive function that performs the Minimax algorithm with Alpha-Beta Pruning to evaluate game states and make decisions.
     *
     * @param currentRound  The current round of the game.
     * @param alpha         The alpha value for Alpha-Beta Pruning.
     * @param beta          The beta value for Alpha-Beta Pruning.
     * @param isMaximizing  Indicates whether it is the maximizing player's turn (true for O, false for X).
     * @param opponentScore The score of the X player.
     * @param playerScore   The score of the O player.
     * @param buttons       The current state of the game board represented as a 2D array of Buttons.
     * @param isBotFirst    Indicates whether the bot is the first player.
     * @return The best score for the current game state.
     */
    public int minimax(int currentRound, int alpha, int beta, boolean isMaximizing, int opponentScore, int playerScore, Button[][] buttons, boolean
            isBotFirst, int eat) {
        if (currentRound == 0) {
            return (playerScore - opponentScore) + (eat);
        }

        int bestScore, nextRound;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            nextRound = currentRound;
            if ((!isBotFirst && this.playerString.equals("O")) || (isBotFirst && this.playerString.equals("X"))) {
                nextRound -= 1;
            }
            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons[i].length; j++) {
                    if (UtilityFunction.isWorthy(buttons, i, j, this.opponentString)) {
                        int oIncrement = 0;
                        buttons[i][j].setText(this.playerString);
                        boolean up = false, right = false, down = false, left = false;
                        if (i - 1 >= 0 && buttons[i - 1][j].getText().equals(this.opponentString)) {
                            up = true;
                            buttons[i - 1][j].setText(this.playerString);
                            oIncrement++;
                        }
                        if (j + 1 < 8 && buttons[i][j + 1].getText().equals(this.opponentString)) {
                            oIncrement++;
                            buttons[i][j + 1].setText(this.playerString);
                            right = true;
                        }
                        if (i + 1 < 8 && buttons[i + 1][j].getText().equals(this.opponentString)) {
                            oIncrement++;
                            buttons[i + 1][j].setText(this.playerString);
                            down = true;
                        }
                        if (j - 1 >= 0 && buttons[i][j - 1].getText().equals(this.opponentString)) {
                            oIncrement++;
                            buttons[i][j - 1].setText(this.playerString);
                            left = true;
                        }

                        int score = minimax(
                                nextRound,
                                alpha,
                                beta,
                                false,
                                opponentScore - oIncrement,
                                playerScore + 1 + oIncrement,
                                buttons,
                                isBotFirst,
                                oIncrement
                        );

                        buttons[i][j].setText("");
                        if (up) {
                            buttons[i - 1][j].setText(this.opponentString);
                        }
                        if (right) {
                            buttons[i][j + 1].setText(this.opponentString);
                        }
                        if (down) {
                            buttons[i + 1][j].setText(this.opponentString);
                        }
                        if (left) {
                            buttons[i][j - 1].setText(this.opponentString);
                        }
                        bestScore = Math.max(bestScore, score);
                        alpha = Math.max(alpha, score);
                        if (beta <= alpha) {
                            break; // Beta cutoff
                        }
                    }
                }
            }
        } else {
            nextRound = currentRound;
            if ((isBotFirst && this.playerString.equals("O")) || (!isBotFirst && this.playerString.equals("X"))) {
                nextRound -= 1;
            }
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons[i].length; j++) {
                    if (UtilityFunction.isWorthy(buttons, i, j, this.playerString)) {
                        int xIncrement = 0;
                        buttons[i][j].setText(this.opponentString);
                        boolean up = false, right = false, down = false, left = false;
                        if (i - 1 >= 0 && buttons[i - 1][j].getText().equals(this.playerString)) {
                            up = true;
                            buttons[i - 1][j].setText(this.opponentString);
                            xIncrement++;
                        }
                        if (j + 1 < 8 && buttons[i][j + 1].getText().equals(this.playerString)) {
                            xIncrement++;
                            buttons[i][j + 1].setText(this.opponentString);
                            right = true;
                        }
                        if (i + 1 < 8 && buttons[i + 1][j].getText().equals(this.playerString)) {
                            xIncrement++;
                            buttons[i + 1][j].setText(this.opponentString);
                            down = true;
                        }
                        if (j - 1 >= 0 && buttons[i][j - 1].getText().equals(this.playerString)) {
                            xIncrement++;
                            buttons[i][j - 1].setText(this.opponentString);
                            left = true;
                        }
                        int score = minimax(
                                nextRound,
                                alpha,
                                beta,
                                true,
                                opponentScore + 1 + xIncrement,
                                playerScore - xIncrement,
                                buttons,
                                isBotFirst,
                                xIncrement
                        );
                        if (up) {
                            buttons[i - 1][j].setText(this.playerString);
                        }
                        if (right) {
                            buttons[i][j + 1].setText(this.playerString);
                        }
                        if (down) {
                            buttons[i + 1][j].setText(this.playerString);
                        }
                        if (left) {
                            buttons[i][j - 1].setText(this.playerString);
                        }
                        buttons[i][j].setText("");
                        bestScore = Math.min(bestScore, score);
                        beta = Math.min(beta, score);
                        if (beta <= alpha) {
                            break; // Alpha cutoff
                        }
                    }
                }
            }
        }

        return bestScore;
    }

}
