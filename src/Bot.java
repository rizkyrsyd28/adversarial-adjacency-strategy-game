import javafx.scene.control.Button;

import java.util.*;

public class Bot {

    /**
     * Finds the best move for the bot player in the Adjacency Strategy Game using the Minimax algorithm with Alpha-Beta Pruning.
     *
     * @param buttons     The current state of the game board represented as a 2D array of Buttons.
     * @param roundsLeft  The number of rounds left in the game.
     * @param xScore      The score of the X player.
     * @param oScore      The score of the O player.
     * @param isBotFirst  Indicates whether the bot is the first player.
     * @return An array of length 2 representing the row and column indices of the best move for the bot.
     */
    public int[] makeBestMove(Button[][] buttons, int roundsLeft, int xScore, int oScore, boolean isBotFirst) {
        int bestScore = Integer.MIN_VALUE, nextRound;
        if (isBotFirst) {
            nextRound = roundsLeft;
        } else {
            nextRound = roundsLeft - 1;
        }
        int[] bestMove = new int[2];

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (UtilityFunction.isWorthy(buttons, i, j, "X")) {
                    int oIncrement = 0;
                    buttons[i][j].setText("O");
                    boolean up = false, right = false, down = false, left = false;
                    if (i - 1 >= 0 && buttons[i - 1][j].getText().equals("X")) {
                        up = true;
                        buttons[i - 1][j].setText("O");
                        oIncrement++;
                    }
                    if (j + 1 < 8 && buttons[i][j + 1].getText().equals("X")) {
                        oIncrement++;
                        buttons[i][j + 1].setText("O");
                        right = true;
                    }
                    if (i + 1 < 8 && buttons[i + 1][j].getText().equals("X")) {
                        oIncrement++;
                        buttons[i + 1][j].setText("O");
                        down = true;
                    }
                    if (j - 1 >= 0 && buttons[i][j - 1].getText().equals("X")) {
                        oIncrement++;
                        buttons[i][j - 1].setText("O");
                        left = true;
                    }

                    int score = minimax(
                            nextRound,
                            Integer.MIN_VALUE,
                            Integer.MAX_VALUE,
                            false,
                            xScore - oIncrement,
                            oScore + 1 + oIncrement,
                            buttons,
                            isBotFirst,
                            oIncrement
                    );
                    buttons[i][j].setText("");
                    if (up) {
                        buttons[i - 1][j].setText("X");
                    }
                    if (right) {
                        buttons[i][j + 1].setText("X");
                    }
                    if (down) {
                        buttons[i + 1][j].setText("X");
                    }
                    if (left) {
                        buttons[i][j - 1].setText("X");
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
     * @param xScore        The score of the X player.
     * @param oScore        The score of the O player.
     * @param buttons       The current state of the game board represented as a 2D array of Buttons.
     * @param isBotFirst    Indicates whether the bot is the first player.
     * @return The best score for the current game state.
     */
    public int minimax(int currentRound, int alpha, int beta, boolean isMaximizing, int xScore, int oScore, Button[][] buttons, boolean
            isBotFirst, int eat) {
        if (currentRound == 0) {
            return (oScore - xScore) + (eat);
        }

        int bestScore, nextRound;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            nextRound = currentRound;
            if (!isBotFirst) {
                nextRound -= 1;
            }
            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons[i].length; j++) {
                    if (UtilityFunction.isWorthy(buttons, i, j, "X")) {
                        int oIncrement = 0;
                        buttons[i][j].setText("O");
                        boolean up = false, right = false, down = false, left = false;
                        if (i - 1 >= 0 && buttons[i - 1][j].getText().equals("X")) {
                            up = true;
                            buttons[i - 1][j].setText("O");
                            oIncrement++;
                        }
                        if (j + 1 < 8 && buttons[i][j + 1].getText().equals("X")) {
                            oIncrement++;
                            buttons[i][j + 1].setText("O");
                            right = true;
                        }
                        if (i + 1 < 8 && buttons[i + 1][j].getText().equals("X")) {
                            oIncrement++;
                            buttons[i + 1][j].setText("O");
                            down = true;
                        }
                        if (j - 1 >= 0 && buttons[i][j - 1].getText().equals("X")) {
                            oIncrement++;
                            buttons[i][j - 1].setText("O");
                            left = true;
                        }

                        int score = minimax(
                                nextRound,
                                alpha,
                                beta,
                                false,
                                xScore - oIncrement,
                                oScore + 1 + oIncrement,
                                buttons,
                                isBotFirst,
                                oIncrement
                        );

                        buttons[i][j].setText("");
                        if (up) {
                            buttons[i - 1][j].setText("X");
                        }
                        if (right) {
                            buttons[i][j + 1].setText("X");
                        }
                        if (down) {
                            buttons[i + 1][j].setText("X");
                        }
                        if (left) {
                            buttons[i][j - 1].setText("X");
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
            if (isBotFirst) {
                nextRound -= 1;
            }
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons[i].length; j++) {
                    if (UtilityFunction.isWorthy(buttons, i, j, "O")) {
                        int xIncrement = 0;
                        buttons[i][j].setText("X");
                        boolean up = false, right = false, down = false, left = false;
                        if (i - 1 >= 0 && buttons[i - 1][j].getText().equals("O")) {
                            up = true;
                            buttons[i - 1][j].setText("X");
                            xIncrement++;
                        }
                        if (j + 1 < 8 && buttons[i][j + 1].getText().equals("O")) {
                            xIncrement++;
                            buttons[i][j + 1].setText("X");
                            right = true;
                        }
                        if (i + 1 < 8 && buttons[i + 1][j].getText().equals("O")) {
                            xIncrement++;
                            buttons[i + 1][j].setText("X");
                            down = true;
                        }
                        if (j - 1 >= 0 && buttons[i][j - 1].getText().equals("O")) {
                            xIncrement++;
                            buttons[i][j - 1].setText("X");
                            left = true;
                        }
                        int score = minimax(
                                nextRound,
                                alpha,
                                beta,
                                true,
                                xScore + 1 + xIncrement,
                                oScore - xIncrement,
                                buttons,
                                isBotFirst,
                                xIncrement
                        );
                        if (up) {
                            buttons[i - 1][j].setText("O");
                        }
                        if (right) {
                            buttons[i][j + 1].setText("O");
                        }
                        if (down) {
                            buttons[i + 1][j].setText("O");
                        }
                        if (left) {
                            buttons[i][j - 1].setText("O");
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

    public int[] makeBestHillClimbMove(Button[][] buttons) {
        PriorityQueue<int[]> p = new PriorityQueue<>(Comparator.comparing(arr -> arr[2]));
        int tempPrio;
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j].getText().equals("")) {
                    tempPrio = 1;
                    if (i - 1 >= 0 && buttons[i - 1][j].getText().equals("X")) {
                        tempPrio++;
                    }
                    if (j - 1 >= 0 && buttons[i][j - 1].getText().equals("X")) {
                        tempPrio++;
                    }
                    if (i + 1 < 8 && buttons[i + 1][j].getText().equals("X")) {
                        tempPrio++;
                    }
                    if (j + 1 < 8 && buttons[i][j + 1].getText().equals("X")) {
                        tempPrio++;
                    }
                    p.offer(new int[]{i, j, tempPrio});
                }
            }
        }
        int[] lastElement = null;
        while (!p.isEmpty()) {
            lastElement = p.poll();
        }
        assert lastElement != null;
        return new int[]{lastElement[0], lastElement[1]};
    }

    public int[] hillClimb(Button[][] buttons, int roundsLeft, boolean isBotFirst) {
        // Get initial value
        List<int[]> currentNode = UtilityFunction.getInitialValue(buttons, roundsLeft, isBotFirst);

        // Loop until value decreases
        while (true) {
            // Generate neighbor
            List<int[]> neighbor = UtilityFunction.getHighestValueNeighbor(buttons, currentNode);
            if (UtilityFunction.checkScore(buttons, neighbor) <= UtilityFunction.checkScore(buttons, currentNode)) {
                return currentNode.get(0);
            }
            currentNode = new ArrayList<>(neighbor);
        }
    }

    /**
     * Performs the Random Restart Hill Climbing algorithm to find the best move in the Adjacency Strategy Game.
     * Random Restart Hill Climbing is used to iteratively search for the best move by restarting the search multiple times
     * with a random initial state and selecting the best solution among all restarts.
     *
     * @param buttons      The current state of the game board represented as a 2D array of Buttons.
     * @param roundsLeft   The number of rounds left in the game.
     * @param isBotFirst   Indicates whether the bot is the first player.
     * @return An array of length 2 representing the row and column indices of the best move for the bot.
     */
    public int[] randomRestart(Button[][] buttons, int roundsLeft, boolean isBotFirst) {
        // Define the maximum amount of iterations
        int maxIterations = 75;

        // Define the best solution and its fitness
        List<int[]> bestNode = new ArrayList<>();
        int bestValue = Integer.MIN_VALUE;

        for (int i = 0; i < maxIterations; i++) {
            // Get initial value
            List<int[]> currentNode = UtilityFunction.getInitialValue(buttons, roundsLeft, isBotFirst);
            int currentBest = UtilityFunction.checkScore(buttons, currentNode);

            // Loop until value decreases
            while (true) {
                // Generate neighbor
                List<int[]> neighbor = UtilityFunction.getHighestValueNeighbor(buttons, currentNode);
                int neighborScore = UtilityFunction.checkScore(buttons, neighbor);
                if (neighborScore <= currentBest) {
                    break;
                }
                currentNode = new ArrayList<>(neighbor);
                currentBest = neighborScore;
            }

            // Update value of bestValue
            if (currentBest > bestValue) {
                bestNode = new ArrayList<>(currentNode);
                bestValue = currentBest;
            }
        }

        // Return the beginning node
        return bestNode.get(0);
    }

}


