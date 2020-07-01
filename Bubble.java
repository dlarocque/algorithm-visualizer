
/**
 Bubble Sort algorithm that displays the 
 sorting by overriding the paintComponent 
 method from Java Swing and Thread.wait to slow it down.
 */

import java.awt.*;
import javax.swing.*;

public class Bubble extends JComponent {

    private int checkedIndex1;
    private int checkedIndex2;
    private static final long serialVersionUID = 1L;
    private Integer[] arr;
    private AlgVisualiser alg;

    public Bubble(AlgVisualiser alg, Integer[] arr) {
        this.alg = alg;
        this.arr = arr;
    }

    public void sort() {

        for (int i = 0; i < alg.getArraySize() - 1; i++) {
            for (int j = 0; j < alg.getArraySize() - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    checkedIndex1 = i;
                    checkedIndex2 = j;
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    this.repaint();
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
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