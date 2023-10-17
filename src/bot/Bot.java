package bot;

import javafx.scene.control.Button;

import java.util.*;

import controllers.OutputFrameController;
import utils.UtilityFunction;

public abstract class Bot {

    public abstract int[] move(OutputFrameController outputFrameController);

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
        int maxIterations = 50;

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


