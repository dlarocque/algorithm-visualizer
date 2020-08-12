package algorithmVisualizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class AlgVisualizer implements ActionListener {

	final int N = 50;
	final int CONTENT_WIDTH = 800;
	final int CONTENT_HEIGHT = 860;
	final int ARR_DISPLAY_HEIGHT = 800;
	private Integer[] arr;
	private JFrame frame = new JFrame("Algorithm Visualizer");
	private JPanel arrPanel;
	private DisplayArr displayArr;
	private JPanel buttonPanel;
	private JButton bubbleButton;
	private JButton insertionButton;
	private JButton selectionButton;
	private JButton resetButton;
	private SwingWorker<Void, Integer[]> sort;
	private boolean doBubbleSort;
	private boolean doInsertionSort;
	private boolean doSelectionSort;
	private boolean stopSort;

	public static void main(String[] args) {
		AlgVisualizer alg = new AlgVisualizer();
		alg.initializeVars();
		alg.setFrame();
	}

	public void setFrame() {
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.DARK_GRAY);
		buttonPanel.add(resetButton);
		buttonPanel.add(bubbleButton);
		buttonPanel.add(selectionButton);
		buttonPanel.add(insertionButton);
		buttonPanel.setVisible(true);

		arrPanel = new JPanel();
		arrPanel.add(displayArr);
		arrPanel.setVisible(true);

		frame.add(buttonPanel, BorderLayout.PAGE_START);
		frame.add(arrPanel, BorderLayout.PAGE_END);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	public void initializeVars() {

		arr = new Integer[N];
		arr = fillArr(arr);
		arr = shuffleArr(arr);

		displayArr = new DisplayArr(this, arr);
		displayArr.setPreferredSize(new Dimension(CONTENT_WIDTH, ARR_DISPLAY_HEIGHT));

		sort = new Sorting(this, arr, displayArr);

		resetButton = new JButton("Reset");
		resetButton.addActionListener(this);
		resetButton.setBackground(Color.WHITE);

		bubbleButton = new JButton("Bubble Sort");
		bubbleButton.addActionListener(this);
		bubbleButton.setBackground(Color.WHITE);

		selectionButton = new JButton("Selection Sort");
		selectionButton.addActionListener(this);
		selectionButton.setBackground(Color.WHITE);

		insertionButton = new JButton("Insertion Sort");
		insertionButton.addActionListener(this);
		insertionButton.setBackground(Color.WHITE);
	}

	public void actionPerformed(ActionEvent event) {
		stopSort = false;
		doBubbleSort = false;
		doSelectionSort = false;
		doInsertionSort = false;
		if (event.getSource() == bubbleButton) {
			doBubbleSort = true;
			sort.execute();
		} else if (event.getSource() == selectionButton) {
			doSelectionSort = true;
			sort.execute();
		} else if (event.getSource() == insertionButton) {
			doInsertionSort = true;
			sort.execute();
		} else if (event.getSource() == resetButton) {
			reset();
			sort.execute();
		}
	}

	public void reset() {
		displayArr.clearSwappedIndexes();
		displayArr.setFramesPainted(0);
		displayArr.setComplete(false);
		stopSort = true;
		doBubbleSort = false;
		doSelectionSort = false;
		doInsertionSort = false;
		sort = new Sorting(this, arr, displayArr);
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

	public Integer[] getArr() {
		return arr;
	}

	public int getArrDispHeight() {
		return ARR_DISPLAY_HEIGHT;
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

	public DisplayArr getDisplayArr() {
		return displayArr;
	}

	public SwingWorker<Void, Integer[]> getSorting() {
		return sort;
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
		String algorithm = "Not Sorting";
		if (doBubbleSort)
			algorithm = "Bubble Sort";
		if (doInsertionSort)
			algorithm = "Insertion Sort";
		if (doSelectionSort)
			algorithm = "Selection Sort";
		return algorithm;
	}

	public boolean stopSort() {
		return stopSort;
	}

	public void setStopSort(boolean toSet) {
		stopSort = toSet;
	}

}
