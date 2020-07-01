/*
**-dont use preferred sizes for everything
**-don't use static for everything
**-add getters and setters
**-no need for revalidate
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
    JPanel buttonPanel = new JPanel();
    JPanel arrPanel = new JPanel();
    JButton bubbleButton;
    JButton insertionButton;
    JButton selectionButton;
    Bubble bubbleSort;
    Insertion insertSort;
    Selection selectionSort;

    public static void main(String[] args) {
        AlgVisualiser alg = new AlgVisualiser();
        alg.initializeVars();
        alg.setFrame();
    }

    public void setFrame() {
        frame.setLayout(new CardLayout());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
        frame.setLocationRelativeTo(null);
        buttonPanel.setVisible(true);
        frame.add(buttonPanel);
        frame.add(arrPanel);
        frame.pack();
    }

    public void initializeVars() {

        arr = new Integer[N];
        arr = fillArr(arr);
        arr = shuffleArr(arr);

        bubbleSort = new Bubble(this, arr);
        bubbleSort.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));

        insertSort = new Insertion(this, arr);
        insertSort.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));

        selectionSort = new Selection(this, arr);
        selectionSort.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));

        bubbleButton = new JButton("Bubble Sort");
        bubbleButton.setPreferredSize(new Dimension(200, 200));
        bubbleButton.addActionListener(this);

        selectionButton = new JButton("Selection Sort");
        selectionButton.setPreferredSize(new Dimension(200, 200));
        selectionButton.addActionListener(this);

        insertionButton = new JButton("Insertion Sort");
        insertionButton.setPreferredSize(new Dimension(200, 200));
        insertionButton.addActionListener(this);

        bubbleButton.setBackground(Color.WHITE);
        selectionButton.setBackground(Color.WHITE);
        insertionButton.setBackground(Color.WHITE);

        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
        buttonPanel.add(bubbleButton);
        buttonPanel.add(selectionButton);
        buttonPanel.add(insertionButton);

        arrPanel.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
        arrPanel.add(bubbleSort);
    }

    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == bubbleButton) {
            buttonPanel.setVisible(false);
            arrPanel.setVisible(true);
            bubbleSort.sort();
        } else if (event.getSource() == selectionButton) {
            buttonPanel.setVisible(false);
            arrPanel.setVisible(true);
            insertSort.sort();
        } else if (event.getSource() == insertionButton) {
            buttonPanel.setVisible(false);
            arrPanel.setVisible(true);
            selectionSort.sort();
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
}
