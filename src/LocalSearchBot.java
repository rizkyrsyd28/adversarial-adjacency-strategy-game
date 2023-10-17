import java.util.Comparator;
import java.util.PriorityQueue;

public class LocalSearchBot extends Bot {
    @Override
    public int[] move(OutputFrameController outputFrameController) {
        PriorityQueue<int[]> p = new PriorityQueue<>(Comparator.comparing(arr -> arr[2]));
        int pQueue;
        for (int i = 0; i < outputFrameController.getButtons().length; i++) {
            for (int j = 0; j < outputFrameController.getButtons()[i].length; j++) {
                if (outputFrameController.getButtons()[i][j].getText().equals("")) {
                    pQueue = 1;
                    if (i - 1 >= 0 && outputFrameController.getButtons()[i - 1][j].getText().equals("X")) {
                        pQueue++;
                    }
                    if (j - 1 >= 0 && outputFrameController.getButtons()[i][j - 1].getText().equals("X")) {
                        pQueue++;
                    }
                    if (i + 1 < 8 && outputFrameController.getButtons()[i + 1][j].getText().equals("X")) {
                        pQueue++;
                    }
                    if (j + 1 < 8 && outputFrameController.getButtons()[i][j + 1].getText().equals("X")) {
                        pQueue++;
                    }
                    p.offer(new int[]{i, j, pQueue});
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
}
