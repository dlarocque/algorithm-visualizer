/*
Program that visualizes the sorting of an unsorted
array of Integers from 1 to n.
*/

import java.awt.Dimension;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

public class AlgVisualiser {

    static final int N = 100;

    static final int CONTENT_WIDTH = 800;
    static final int CONTENT_HEIGHT = 800;
    static JFrame frame = new JFrame("Sorting Algorithms");
    static Bubble bubbleSort;
    static Insertion insertSort;

    public static void main(String[] args) {

        Integer[] arr = new Integer[N];
        arr = shuffleArr(arr);

        setFrame();

        // bubbleSort = new Bubble(arr);
        // frame.add(bubbleSort);

        // sort the unsorted arr
        // bubbleSort.bubbleSort();

        Selection selectionSort = new Selection(arr);
        frame.add(selectionSort);

        selectionSort.selectionSort();
    }

    public static void setFrame() {
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public static Integer[] shuffleArr(Integer[] arr) {
        arr = fillArr(arr);
        // Use list methods to shuffle the array, then return to array
        List<Integer> list = Arrays.asList(arr);
        Collections.shuffle(list);
        arr = list.toArray(arr);
        return arr;
    }

    public static String printArr(Integer[] arr) {
        String line = "[ ";
        for (int i = 0; i < N; i++) {
            line += arr[i] + ", ";
        }
        return line + "]";
    }

    public static Integer[] fillArr(Integer[] arr) {
        for (int i = 0; i < N; i++) {
            arr[i] = i + 1;
        }
        return arr;
    }
}