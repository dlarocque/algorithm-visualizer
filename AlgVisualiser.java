/*
    add an action Listener to space bar to reset program
    add text on paintComponent to tell this to the user
*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.CardLayout;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class AlgVisualiser implements ActionListener {

    final int N = 100;
    Integer[] arr;
    final int CONTENT_WIDTH = 800;
    final int CONTENT_HEIGHT = 800;
    JFrame frame = new JFrame("Sorting Algorithms");
    DisplayArr display;
    JPanel buttonPanel = new JPanel();
    JButton bubbleButton;
    JButton insertionButton;
    JButton selectionButton;
    SwingWorker<Void, Integer[]> bubbleWorker;
    private boolean doBubbleSort;
    private boolean doInsertionSort;
    private boolean doSelectionSort;

    public static void main(String[] args) {
        AlgVisualiser alg = new AlgVisualiser();
        alg.initializeVars();
        alg.setFrame();

    }

    public void setFrame() {
        frame.setLayout(new CardLayout());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.getContentPane().setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
        buttonPanel.setVisible(true);
        frame.add(buttonPanel);
        frame.add(display);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public void initializeVars() {

        doBubbleSort = false;
        doInsertionSort = false;
        doSelectionSort = false;

        arr = new Integer[N];
        arr = fillArr(arr);
        arr = shuffleArr(arr);

        display = new DisplayArr(this, arr);
        display.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));

        bubbleButton = new JButton("Bubble Sort");
        bubbleButton.addActionListener(this);

        selectionButton = new JButton("Selection Sort");
        selectionButton.addActionListener(this);

        insertionButton = new JButton("Insertion Sort");
        insertionButton.addActionListener(this);

        bubbleButton.setBackground(Color.WHITE);
        selectionButton.setBackground(Color.WHITE);
        insertionButton.setBackground(Color.WHITE);

        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.add(bubbleButton);
        buttonPanel.add(selectionButton);
        buttonPanel.add(insertionButton);

        bubbleWorker = new bubbleWorker(this, arr, display);

    }

    public void actionPerformed(ActionEvent event) {

        doBubbleSort = false;
        doSelectionSort = false;
        doInsertionSort = false;

        if (event.getSource() == bubbleButton) {
            buttonPanel.setVisible(false);
            display.setVisible(true);
            doBubbleSort = true;
            bubbleWorker.execute();
        } else if (event.getSource() == selectionButton) {
            buttonPanel.setVisible(false);
            display.setVisible(true);
            doSelectionSort = true;
            bubbleWorker.execute();
        } else if (event.getSource() == insertionButton) {
            buttonPanel.setVisible(false);
            display.setVisible(true);
            doInsertionSort = true;
            bubbleWorker.execute();
        }

    }

    public Integer[] shuffleArr(Integer[] arr) {
        arr = fillArr(arr);
        List<Integer> list = Arrays.asList(arr);
        Collections.shuffle(list);
        arr = list.toArray(arr);
        return arr;
    }

    public Integer[] fillArr(Integer[] arr) {
        for (int i = 0; i < N; i++) {
            arr[i] = i + 1;
        }
        return arr;
    }

    public int getArraySize() {
        return N;
    }

    public int getHeight() {
        return CONTENT_HEIGHT;
    }

    public int getWidth() {
        return CONTENT_WIDTH;
    }

    public JFrame getJFrame() {
        return frame;
    }

    public void setSort(String algorithm) {
        if (algorithm.equals("Bubble Sort")) {
            doBubbleSort = true;
        } else if (algorithm.equals("Insertion Sort")) {
            doInsertionSort = true;
        } else if (algorithm.equals("Selection Sort")) {
            doSelectionSort = true;
        }
    }

    public String getSort() {
        String algorithm = null;
        if (doBubbleSort)
            algorithm = "Bubble Sort";
        if (doInsertionSort)
            algorithm = "Insertion Sort";
        if (doSelectionSort)
            algorithm = "Selection Sort";
        return algorithm;
    }
}
