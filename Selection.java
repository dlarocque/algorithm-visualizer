import java.awt.*;
import javax.swing.*;

public class Selection extends JPanel {
    /**
     *
     */
    private AlgVisualiser alg;
    private static final long serialVersionUID = 1L;
    private int checkedIndex1;
    private int checkedIndex2;
    private Integer[] arr;

    public Selection(AlgVisualiser alg, Integer[] arr) {
        this.alg = alg;
        this.arr = arr;
    }

    public void sort() {
        int n = arr.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n - 1; i++) {
            checkedIndex1 = i;
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                checkedIndex2 = j;
                if (arr[j] < arr[min_idx])
                    min_idx = j;
                    this.repaint();
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Swap the found minimum element with the first
            // element
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
        checkedIndex1 = -1;
        checkedIndex2 = -1;
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2d = (Graphics2D) g;

        graphics2d.setColor(Color.DARK_GRAY);
        graphics2d.fillRect(0, 0, alg.getWidth(), alg.getHeight());
        for (int i = 0; i < arr.length; i++) {
            int width = (int) (alg.getWidth() / (double) alg.getArraySize());
            int height = arr[i] * (alg.getHeight() / alg.getArraySize());
            int x = i * width;
            int y = alg.getHeight() - height;
            if (i == checkedIndex1 || i == checkedIndex2) {
                graphics2d.setColor(Color.RED);
            } else if (checkedIndex1 == -1) {
                graphics2d.setColor(Color.GREEN);

            } else {
                graphics2d.setColor(Color.WHITE);
            }
            graphics2d.fillRect(x, y, width, height);

        }
    }
}