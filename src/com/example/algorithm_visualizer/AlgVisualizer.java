package com.example.algorithm_visualizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class AlgVisualizer implements ActionListener {

	private int n;
	private final int CONTENT_WIDTH = 800;
	private final int CONTENT_HEIGHT = 860;
	private final int ARR_DISPLAY_HEIGHT = 800;
	private Integer[] arr;
	private JFrame frame = new JFrame("Algorithm Visualizer");
	private JPanel arrPanel;
	private ArrDisplay arrDisplay;
	private JButton statsButton;
	private JOptionPane statsPane;
	private JPanel buttonPanel;
	private JButton bubbleButton;
	private JButton insertionButton;
	private JButton selectionButton;
	private JButton mergeButton;
	private JButton quickButton;
	private JButton resetButton;
	private JComboBox<String> sizeChanger;
	final String[] SIZE_OPTIONS = { "10", "50", "100", "200", "400", "800" };
	private SwingWorker<Void, Integer[]> arrSort;
	private boolean doBubbleSort;
	private boolean doInsertionSort;
	private boolean doSelectionSort;
	private boolean doMergeSort;
	private boolean doQuickSort;
	private boolean stopSort;
	private Integer indexComparisons;
	private Double timeElapsed;

	public static void main(String[] args) {
		AlgVisualizer algVisualizer = new AlgVisualizer();
		algVisualizer.initializeVars();
		algVisualizer.setFrame();
	}

	public void initializeVars() {

		setN(10);

		arr = new Integer[n];
		arr = fillArr(arr);
		arr = shuffleArr(arr);

		arrDisplay = new ArrDisplay(this, arr);
		arrDisplay.setPreferredSize(new Dimension(CONTENT_WIDTH, ARR_DISPLAY_HEIGHT));
		
		arrSort = new ArrSorting(this, this.arr, this.arrDisplay);
		
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

		mergeButton = new JButton("Merge Sort");
		mergeButton.addActionListener(this);
		mergeButton.setBackground(Color.WHITE);

		quickButton = new JButton("Quick Sort");
		quickButton.addActionListener(this);
		quickButton.setBackground(Color.WHITE);

		sizeChanger = new JComboBox<String>(SIZE_OPTIONS);
		sizeChanger.addActionListener(this);
		sizeChanger.setBackground(Color.WHITE);
		
		statsButton = new JButton("Performance");
		statsButton.addActionListener(this);
		statsButton.setBackground(Color.WHITE);
		statsButton.setEnabled(false);
	}

	public void setFrame() {

		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.DARK_GRAY);
		buttonPanel.add(resetButton);
		buttonPanel.add(bubbleButton);
		buttonPanel.add(selectionButton);
		buttonPanel.add(insertionButton);
		buttonPanel.add(mergeButton);
		buttonPanel.add(quickButton);
		buttonPanel.add(sizeChanger);
		buttonPanel.add(statsButton);
		buttonPanel.setVisible(true);

		arrPanel = new JPanel();
		arrPanel.add(arrDisplay);
		arrPanel.setVisible(true);
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(buttonPanel, BorderLayout.PAGE_START);
		frame.add(arrPanel, BorderLayout.PAGE_END);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent event) {
		setStopSort(false);
		doBubbleSort = false;
		doSelectionSort = false;
		doInsertionSort = false;
		doMergeSort = false;
		doQuickSort = false;
		
		if (event.getSource() == bubbleButton) {
			doBubbleSort = true;
			arrSort.execute();
		} else if (event.getSource() == selectionButton) {
			doSelectionSort = true;
			arrSort.execute();
		} else if (event.getSource() == insertionButton) {
			doInsertionSort = true;
			arrSort.execute();
		} else if (event.getSource() == mergeButton) {
			doMergeSort = true;
			arrSort.execute();
		} else if (event.getSource() == quickButton) {
			doQuickSort = true;
			arrSort.execute();
		} else if (event.getSource() == resetButton) {
			reset();
			arrSort.execute();
		} else if (event.getSource() == sizeChanger) {
			// Create a new array of the size selected
			String selectedSize = (String) sizeChanger.getSelectedItem();
			setN(Integer.valueOf(selectedSize));
			arr = new Integer[n];
			arr = fillArr(arr);
			// Clear and paint the new array
			reset();
			arrSort.execute();
		} else if (event.getSource() == statsButton) {
			//open JOptionPane
			String statsMessage = String.format("Index Comparisons : %d  Time Elapsed : %f", indexComparisons, timeElapsed);
			JOptionPane.showMessageDialog(frame, statsMessage, "Performance", JOptionPane.PLAIN_MESSAGE);
		}
	}

	public void reset() {
		setStopSort(true);
		arr = shuffleArr(arr);
		arrDisplay.clearSwappedIndexes();
		arrDisplay.setFramesPainted(0);
		arrDisplay.setComplete(false);
		arrDisplay.setArr(arr);
		resetSwingWorker(this, arr, arrDisplay);
	}

	public void resetSwingWorker(AlgVisualizer alg, Integer[] arr, ArrDisplay displayArr) {
		arrSort = new ArrSorting(this, arr, displayArr); // need to reset arrAccesses and timeElapsed
	}

	public Integer[] shuffleArr(Integer[] arr) {
		arr = fillArr(arr);
		List<Integer> list = Arrays.asList(arr);
		Collections.shuffle(list);
		arr = list.toArray(arr);
		return arr;
	}

	public Integer[] fillArr(Integer[] arr) {
		for (int i = 0; i < n; i++) {
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

	public ArrDisplay getDisplayArr() {
		return arrDisplay;
	}

	public SwingWorker<Void, Integer[]> getArrSorting() {
		return arrSort;
	}

	public void setSort(String sort) {
		if (sort.equals("Bubble Sort")) {
			doBubbleSort = true;
		} else if (sort.equals("Insertion Sort")) {
			doInsertionSort = true;
		} else if (sort.equals("Selection Sort")) {
			doSelectionSort = true;
		} else if (sort.equals("Merge Sort")) {
			doMergeSort = true;
		} else if (sort.equals("Quick Sort")) {
			doQuickSort = true;
		}
	}

	public String getSort() {
		String sort = "Not Sorting";
		if (doBubbleSort)
			sort = "Bubble Sort";
		if (doInsertionSort)
			sort = "Insertion Sort";
		if (doSelectionSort)
			sort = "Selection Sort";
		if (doMergeSort)
			sort = "Merge Sort";
		if (doQuickSort)
			sort = "Quick Sort";
		return sort;
	}

	public boolean stopSort() {
		return stopSort;
	}

	public void setStopSort(boolean toSet) {
		// The availability of the sort buttons depends on the status of stopSort
		bubbleButton.setEnabled(toSet);
		selectionButton.setEnabled(toSet);
		insertionButton.setEnabled(toSet);
		mergeButton.setEnabled(toSet);
		quickButton.setEnabled(toSet);
		stopSort = toSet;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}
	
	public JButton getStatsButton() {
		return statsButton;
	}
	
	public Integer getIndexComparisons() {
		return indexComparisons;
	}
	
	public void setIndexComparisons(int indexComparisons) {
		this.indexComparisons = indexComparisons;
	}
	
	public Double getArrAccesses() {
		return timeElapsed;
	}
	
	public void setArrAccesses (double timeElapsed) {
		this.timeElapsed = timeElapsed;
	}	
}
