
/**
 Bubble Sort algorithm that displays the 
 sorting by overriding the paintComponent 
 method from Java Swing and Thread.wait to slow it down.
 */

import java.awt.*;
import javax.swing.*;

public class Bubble extends JPanel {

    private static int checkedIndex1;
    private static int checkedIndex2;
    private static final long serialVersionUID = 1L;
    private Integer[] arr;

    public Bubble(Integer[] arr) {
        this.arr = arr;
    }

    public void displayArr(){
        AlgVisualiser.frame.repaint();
    }

    public void sort() {
        
        for (int i = 0; i < AlgVisualiser.N - 1; i++) {
            for (int j = 0; j < AlgVisualiser.N - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    checkedIndex1 = i;
                    checkedIndex2 = j;
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    AlgVisualiser.frame.repaint();
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
        AlgVisualiser.frame.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        System.out.println("z");

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
            } else if (checkedIndex1 == -1){
                graphics2d.setColor(Color.GREEN);

            } else {
                graphics2d.setColor(Color.WHITE);
            }
            graphics2d.fillRect(x, y, width, height);
        }
    }

}