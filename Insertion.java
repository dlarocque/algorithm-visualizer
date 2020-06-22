import java.awt.*;
import javax.swing.*;

public class Insertion extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static int checkedIndex1;
    private static int checkedIndex2;
    private Integer[] arr;

    public Insertion(Integer[] arr) {
        this.arr = arr;
    }

    public void sort() {
        for (int i = 1; i < AlgVisualiser.N; ++i) {
            int key = arr[i];
            int j = i - 1;
            checkedIndex1 = i;
            /*
             * Move elements of arr[0..i-1], that are greater than key, to one position
             * ahead of their current position
             */
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
                checkedIndex2 = j;
                AlgVisualiser.frame.repaint();
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            arr[j + 1] = key;
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