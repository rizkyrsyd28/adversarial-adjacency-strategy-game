import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UtilityFunction {

    public static boolean isWorthy(Button[][] buttons, int i, int j, String opponent) {
        boolean isEmpty = buttons[i][j].getText().equals("");
        boolean upWorthy = (i - 1 >= 0 && buttons[i - 1][j].getText().equals(opponent));
        boolean leftWorthy = (j - 1 >= 0 && buttons[i][j - 1].getText().equals(opponent));
        boolean downWorthy = (i + 1 < 8 && buttons[i + 1][j].getText().equals(opponent));
        boolean rightWorthy = (j + 1 < 8 && buttons[i][j + 1].getText().equals(opponent));

        return isEmpty && (upWorthy || leftWorthy || downWorthy || rightWorthy);
    }

    public static List<int[]> getInitialValue(Button[][] buttons, int roundsLeft, boolean isBotFirst) {
        List<int[]> result = new ArrayList<>();

        int listLength = 2 * roundsLeft;
        if (!isBotFirst) {
            listLength--;
        }

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j].getText().equals("")) {
                    result.add(new int[]{i, j});
                }
            }
        }

        Collections.shuffle(result);

        return result.subList(0, listLength);
    }

    public static int checkScore(Button[][] buttons, List<int[]> order) {
        String[][] content = new String[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                content[i][j] = buttons[i][j].getText();
            }
        }
        String player, opponent;
        for (int i = 0; i < order.size(); i++) {
            if (i % 2 == 0) {
                player = "O";
                opponent = "X";
            } else {
                player = "X";
                opponent = "O";
            }
            int row = order.get(i)[0], col = order.get(i)[1];
            if (content[row][col].equals("")) {
                content[row][col] = player;
            }
            if (row - 1 >= 0 && content[row - 1][col].equals(opponent)) {
                content[row - 1][col] = player;
            }
            if (col - 1 >= 0 && content[row][col - 1].equals(opponent)) {
                content[row][col - 1] = player;
            }
            if (row + 1 < 8 && content[row + 1][col].equals(opponent)) {
                content[row + 1][col] = player;
            }
            if (col + 1 < 8 && content[row][col + 1].equals(opponent)) {
                content[row][col + 1] = player;
            }
        }
        int xScore = 0;
        int oScore = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (content[i][j].equals("X")) {
                    xScore++;
                } else {
                    oScore++;
                }
            }
        }

        return oScore - xScore;
    }

    public static List<int[]> getHighestValueNeighbor(Button[][] buttons, List<int[]> currentNode) {

        // Generate a string matrix
        String[][] content = generateCurrentState(buttons, currentNode);

        // Initialize variables
        int bestScore = Integer.MIN_VALUE, fromIndex = -1, toIndex = -1;
        int[] toCoordinates = {-1, -1};
        boolean notNew = false;

        // Swapping within the order
        for (int i = 0; i < currentNode.size() - 1; i += 2) {
            for (int j = i + 2; j < currentNode.size(); j += 2) {
                List<int[]> temp = new ArrayList<>(currentNode);
                Collections.swap(temp, i, j);
                int tempScore = checkScore(buttons, temp);
                if (tempScore > bestScore) {
                    bestScore = tempScore;
                    fromIndex = i;
                    toIndex = j;
                    notNew = true;
                }
            }
        }

        // Swapping outside the order
        if (countEmpty(content) != 0) {                             // Determine if it is already full
            for (int i = 0; i < currentNode.size(); i++) {
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < 8; k++) {
                        if (!currentNode.contains(new int[]{j, k}) && content[j][k].equals("")) {
                            int[] temp = {currentNode.get(i)[0], currentNode.get(i)[1]};
                            currentNode.set(i, new int[]{j, k});
                            int tempScore = checkScore(buttons, currentNode);
                            if (tempScore > bestScore) {
                                bestScore = tempScore;
                                fromIndex = i;
                                toIndex = -1;
                                notNew = false;
                                toCoordinates = currentNode.get(i);
                            }
                            currentNode.set(i, temp);
                        }
                    }
                }
            }
        }

        // Initialize result
        List<int[]> result = new ArrayList<>(currentNode);

        // Index == - 1 -> no possible swap
        if (fromIndex == -1) {
            return result;
        }

        // Change the tuple in the index
        if (notNew) {
            Collections.swap(result, fromIndex, toIndex);
        } else {
            result.set(fromIndex, toCoordinates);
        }

        // Return the neighboring;
        return result;
    }

    public static String[][] generateCurrentState(Button[][] buttons, List<int[]> order) {
        String player, opponent;
        String[][] result = new String[8][8];

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                result[i][j] = buttons[i][j].getText();
            }
        }

        for (int i = 0; i < order.size(); i++) {
            if (i % 2 == 0) {
                player = "O";
                opponent = "X";
            } else {
                player = "X";
                opponent = "O";
            }
            int row = order.get(i)[0], col = order.get(i)[1];
            if (buttons[row][col].getText().equals("")) {
                result[row][col] = player;
            }
            if (row - 1 >= 0 && buttons[row - 1][col].getText().equals(opponent)) {
                result[row - 1][col] = player;
            }
            if (col - 1 >= 0 && buttons[row][col - 1].getText().equals(opponent)) {
                result[row][col - 1] = player;
            }
            if (row + 1 < 8 && buttons[row + 1][col].getText().equals(opponent)) {
                result[row + 1][col] = player;
            }
            if (col + 1 < 8 && buttons[row][col + 1].getText().equals(opponent)) {
                result[row][col + 1] = player;
            }
        }

        return result;
    }

    public static int countEmpty(String[][] content) {
        int result = 0;
        for (String[] strings : content) {
            for (String string : strings) {
                if (string.equals("")) {
                    result++;
                }
            }
        }
        return result;
    }

}
