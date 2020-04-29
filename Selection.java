import java.awt.*;
import javax.swing.*;

public class Selection extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static int checkedIndex1;
    private static int checkedIndex2;
    private Integer[] arr;

    public Selection(Integer[] arr) {
        this.arr = arr;
    }

    public void selectionSort() {
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
                AlgVisualiser.frame.repaint();
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
        AlgVisualiser.frame.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2d = (Graphics2D) g;

        graphics2d.setColor(Color.DARK_GRAY);
        graphics2d.fillRect(0, 0, AlgVisualiser.CONTENT_WIDTH, AlgVisualiser.CONTENT_HEIGHT);
        for (int i = 0; i < arr.length; i++) {
            int width = (int) (AlgVisualiser.CONTENT_WIDTH / (double) AlgVisualiser.N);
            int height = arr[i] * (AlgVisualiser.CONTENT_HEIGHT / AlgVisualiser.N);
            int x = i * width;
            int y = AlgVisualiser.CONTENT_HEIGHT - height;
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