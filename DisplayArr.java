package algorithmVisualizer;


import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DisplayArr extends JComponent {

    protected List<Integer> checkedIndex1 = new ArrayList<Integer>();
    protected List<Integer> checkedIndex2 = new ArrayList<Integer>();
    private static final long serialVersionUID = 1L;
    protected Integer[] arr;
    protected AlgVisualizer alg;

    public DisplayArr(AlgVisualizer alg, Integer[] arr) {
        this.alg = alg;
        this.arr = arr;
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
            try {
                if (i == checkedIndex1.get(0) || i == checkedIndex2.get(0)) {
                    graphics2d.setColor(Color.RED);
                } else if (checkedIndex1.get(0) == -1) {
                    graphics2d.setColor(Color.GREEN);
                } else {
                    graphics2d.setColor(Color.WHITE);
                }
            } catch (IndexOutOfBoundsException e) {
                
            }

            graphics2d.fillRect(x, y, width, height);
        }
        checkedIndex1.remove(0);
        checkedIndex2.remove(0);
    }

    public void addCheckedIndex1(Integer checkedIndex) {
        checkedIndex1.add(0, checkedIndex);
    }

    public void addCheckedIndex2(Integer checkedIndex) {
        checkedIndex2.add(0, checkedIndex);
    }

}
