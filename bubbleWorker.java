import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class bubbleWorker extends SwingWorker<Void, Integer[]> {

    protected int checkedIndex1;
    protected int checkedIndex2;
    private Integer[] arr;
    private AlgVisualiser alg;
    private DisplayArr display;

    public bubbleWorker(AlgVisualiser alg, Integer[] arr, DisplayArr display) {
        this.alg = alg;
        this.arr = arr;
        this.display = display;
    }

    @Override
    protected Void doInBackground() throws Exception {
        if (alg.getSort().equals("Bubble Sort")) {
            bubbleSort();
        } else if (alg.getSort().equals("Insertion Sort")) {
            insertionSort();
        } else if (alg.getSort().equals("Selection Sort")) {
            selectionSort();
        }
        return null;
    }

    @Override
    protected void process(List<Integer[]> chunks) {
        display.repaint();
    }

    public void bubbleSort() {
        for (int i = 0; i < alg.getArraySize() - 1; i++) {
            for (int j = 0; j < alg.getArraySize() - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    checkedIndex1 = i;
                    checkedIndex2 = j;
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    display.addCheckedIndex1(checkedIndex1);
                    display.addCheckedIndex2(checkedIndex2);
                    publish(arr.clone());
                    try {
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        checkedIndex1 = -1;
        checkedIndex2 = -1;
        display.addCheckedIndex1(checkedIndex1);
        display.addCheckedIndex2(checkedIndex2);
        publish(arr.clone());

        int input = JOptionPane.showOptionDialog(null, "Sorting Complete! Press OK to Continue.", "Done",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

        if (input == JOptionPane.OK_OPTION) {
            alg.reset();
        }

        try {
            Thread.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insertionSort() {
        for (int i = 1; i < alg.getArraySize(); ++i) {
            int key = arr[i];
            int j = i - 1;
            checkedIndex1 = i;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
                checkedIndex2 = j;
                display.addCheckedIndex1(checkedIndex1);
                display.addCheckedIndex2(checkedIndex2);
                publish(arr.clone());
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
        display.addCheckedIndex1(checkedIndex1);
        display.addCheckedIndex2(checkedIndex2);
        publish(arr.clone());
        
        int input = JOptionPane.showOptionDialog(null, "Sorting Complete! Press OK to Continue.", "Done",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

        if (input == JOptionPane.OK_OPTION) {
            alg.reset();
        }
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void selectionSort() {

        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            checkedIndex1 = i;
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                checkedIndex2 = j;
                if (arr[j] < arr[min_idx])
                    min_idx = j;
                display.addCheckedIndex1(checkedIndex1);
                display.addCheckedIndex2(checkedIndex2);
                publish(arr.clone());
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
        checkedIndex1 = -1;
        checkedIndex2 = -1;
        display.addCheckedIndex1(checkedIndex1);
        display.addCheckedIndex2(checkedIndex2);
        publish(arr.clone());

        int input = JOptionPane.showOptionDialog(null, "Sorting Complete! Press OK to Continue.", "Done",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

        if (input == JOptionPane.OK_OPTION) {
            alg.reset();
        }
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}
