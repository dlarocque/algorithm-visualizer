import java.awt.Color;
import java.awt.Dimension;
import java.awt.CardLayout;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class AlgVisualiser implements ActionListener {

    static final int N = 100;
    static Integer[] arr;
    static final int CONTENT_WIDTH = 800;
    static final int CONTENT_HEIGHT = 800;
    static JFrame frame = new JFrame("Sorting Algorithms");
    static JPanel mainPanel = new JPanel();
    static JPanel buttonPanel = new JPanel();
    static JButton bubbleButton;
    static JButton insertionButton;
    static JButton selectionButton;
    static Bubble bubbleSort;
    static Insertion insertSort;
    static Selection selectionSort;
    static CardLayout cardLayout = new CardLayout();

    public static void main(String[] args) {
        setFrame();
        initializeVars();
        buttonPanel.setVisible(true);
    }

    public static void setFrame() {
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.add(mainPanel);
    }

    public static void initializeVars() {
        // initialize the array
        arr = new Integer[N];
        arr = fillArr(arr);
        arr = shuffleArr(arr);

        bubbleSort = new Bubble(arr);
        insertSort = new Insertion(arr);
        selectionSort = new Selection(arr);
        bubbleSort.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
        insertSort.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
        selectionSort.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));

        AlgVisualiser alg = new AlgVisualiser();

        // initialize the JButtons
        bubbleButton = new JButton("Bubble Sort");
        bubbleButton.setPreferredSize(new Dimension(200, 200));
        bubbleButton.addActionListener(alg);

        selectionButton = new JButton("Selection Sort");
        selectionButton.setPreferredSize(new Dimension(200, 200));
        selectionButton.addActionListener(alg);

        insertionButton = new JButton("Insertion Sort");
        insertionButton.setPreferredSize(new Dimension(200, 200));
        insertionButton.addActionListener(alg);

        bubbleButton.setBackground(Color.WHITE);
        selectionButton.setBackground(Color.WHITE);
        insertionButton.setBackground(Color.WHITE);

        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
        buttonPanel.add(bubbleButton);
        buttonPanel.add(selectionButton);
        buttonPanel.add(insertionButton);

        mainPanel.setLayout(cardLayout);
        mainPanel.add(buttonPanel);
        mainPanel.add(bubbleSort);
        mainPanel.add(insertSort);
        mainPanel.add(selectionSort);

    }

    public void actionPerformed(ActionEvent event) {
        // find which button was clicked
        if (event.getSource() == bubbleButton) {
            cardLayout.next(mainPanel);
            //frame.validate();
            bubbleSort.sort();

        } else if (event.getSource() == selectionButton) {

            buttonPanel.setVisible(false);
            // selectionSort.sort();

        } else if (event.getSource() == insertionButton) {

            buttonPanel.setVisible(false);
            // insertSort.sort();

        }
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
