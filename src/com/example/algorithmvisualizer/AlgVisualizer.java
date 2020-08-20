/*
 * An application that visualizes sorting algorithms through bar graphs.
 * 
 * Author : Daniel La Rocque
 * 
 * Date started : April 28th, 2020
 * 
 * This project is licensed under the MIT License.
 * See LICENSE.txt for more details
 * 
 * Instructions on how to use the application are included in README.md
 */

package com.example.algorithmvisualizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.*;

public class AlgVisualizer implements ActionListener, ChangeListener {

	private final int CONTENT_WIDTH = 900;
	private final int CONTENT_HEIGHT = 960;
	private final int ARR_DISPLAY_HEIGHT = 900;
	private final int FPS_MIN = 1;
	private final int FPS_INIT = 10;
	private final int FPS_MAX = 100;
	private final String[] SIZE_OPTIONS = { "10", "50", "100", "300", "450", "900" }; // array size options
	private int n;
	private int numSwaps;
	private int delay;
	private Integer indexComparisons;
	private long startTime; // start time of a sort
	private long endTime; // end time of a sort
	private long visualizationTime;
	private long sortingTime;
	private boolean doBubbleSort;
	private boolean doInsertionSort;
	private boolean doSelectionSort;
	private boolean doMergeSort;
	private boolean doQuickSort;
	private boolean stopSort; // True if sorting is stopped
	private Integer[] arr; // array that is going to be sorted
	private JFrame frame;
	private JPanel arrPanel;
	private ArrDisplay arrDisplay;
	private JPanel buttonPanel;
	private JButton resetButton;
	private JButton bubbleButton;
	private JButton insertionButton;
	private JButton selectionButton;
	private JButton mergeButton;
	private JButton quickButton;
	private JComboBox<String> sizeChanger;
	private JSlider FPSslider;
	private JLabel performanceLabel;
	private SwingWorker<Void, Integer[]> arrSort;

	/*
	 * In main(), we initialize an AlgVisualizer object, all instance variables, set
	 * up the frame / window that the application will run inside, and make it
	 * visible. By the end, a window containing what is meant to be shown on
	 * application start up will open on the users screen, waiting for input.
	 */
	public static void main(String[] args) {
		AlgVisualizer algVisualizer = new AlgVisualizer();
		algVisualizer.initializeVars();
		algVisualizer.setFrame();
	}

	public AlgVisualizer() {
		// empty constructor, variables are initialized in main method.
	}

	/*
	 * This method initializes all of this classes instance variables. The array is
	 * initialized, filled, and shuffled. The arrDisplay object that paints the
	 * array in bar graph form is initialized and passed the array. All buttons are
	 * initialized and include an action listener.
	 * 
	 */
	public void initializeVars() {

		setN(10);

		arr = new Integer[n];
		arr = fillArr(arr);
		arr = shuffleArr(arr);

		indexComparisons = 0;
		startTime = 0;
		endTime = 0;
		visualizationTime = 0;
		sortingTime = 0;
		setDelay(1000 / FPS_INIT);

		// Initialize objects that will display and sort the array

		arrDisplay = new ArrDisplay(this);
		arrDisplay.setArr(arr);
		arrDisplay.setPreferredSize(new Dimension(CONTENT_WIDTH, ARR_DISPLAY_HEIGHT));

		arrSort = new ArrSorting(this, this.arr, this.arrDisplay);

		// Panels in the frame that will hold all components.
		// JPanels use the flowLayout to manage their components automatically.

		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.DARK_GRAY);

		arrPanel = new JPanel();
		arrPanel.add(arrDisplay);

		// Initialize all components and add action listeners

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

		sizeChanger = new JComboBox<String>(SIZE_OPTIONS); // Pass the String containing all of the size options
		sizeChanger.addActionListener(this);
		sizeChanger.setBackground(Color.WHITE);

		FPSslider = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);
		FPSslider.addChangeListener(this);
		FPSslider.setBackground(Color.DARK_GRAY);
		// Initialize the performance label and center it

		performanceLabel = new JLabel();
		performanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
	}

	/*
	 * setFrame() will add all the components that were initialized in the
	 * initializeVars() method to JPanels, which will then also be added to the main
	 * JFrame. The frame is initialized and made visible.
	 */
	public void setFrame() {

		// Add JButtons / components to button panel
		buttonPanel.add(resetButton);
		buttonPanel.add(bubbleButton);
		buttonPanel.add(selectionButton);
		buttonPanel.add(insertionButton);
		buttonPanel.add(mergeButton);
		buttonPanel.add(quickButton);
		buttonPanel.add(sizeChanger);
		buttonPanel.add(FPSslider);

		// Initialize and make the frame visible
		frame = new JFrame("Algorithm Visualizer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false); // Cannot be resizable, causes visual issues
		frame.add(buttonPanel, BorderLayout.PAGE_START); // Button panel added to the top of the frame
		frame.add(arrPanel, BorderLayout.PAGE_END); // Array display is added to the bottom of the frame
		frame.add(performanceLabel);
		frame.pack();
		frame.setLocationRelativeTo(null); // center of the screen
		frame.setVisible(true);

	}

	/*
	 * When an action is performed on a component on the JFrame that has had this
	 * classes actionListener added to it, this method will decide what to do based
	 * on what component was clicked on. When a sorting button is clicked, its
	 * respective boolean do(..)Sort will be set to true, and arrSort().execute.
	 * This will call the doInBackground() method in the arrSort object, where it
	 * will use the do(..)Sort variable to discover which sorting algorithm to use,
	 * or if there is simply a reset.
	 */
	public void actionPerformed(ActionEvent event) {
		// Any time an action is performed, sorting is stopped
		setStopSort(false);
		doBubbleSort = false;
		doSelectionSort = false;
		doInsertionSort = false;
		doMergeSort = false;
		doQuickSort = false;

		// Find the source of the action
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
			// Find what size was selected, and set n to that value
			String selectedSize = (String) sizeChanger.getSelectedItem();
			setN(Integer.valueOf(selectedSize));

			// Create the new array of length n, and it will be shuffled in reset()
			arr = new Integer[n];
			arr = fillArr(arr);

			// reset and paint the new array
			reset();
			arrSort.execute();
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {
			int fps = (int) source.getValue();
			delay = 1000 / fps; // ms
			setDelay(delay);
		}
	}

	/*
	 * Reset method is called whenever the user presses the reset button, or when a
	 * new size of array is chosen from the size changer. This method stops sorting,
	 * re-shuffles the array, clears all swapped indexes, frames painted, tracked
	 * time, and comparisons. It must also reset the swingWorker so that the user is
	 * able to see another sort. Since sort.execute() can only be called once for
	 * SwingWorker, we simply re-instantiate it so that we are able to call it
	 * again.
	 */
	public void reset() {
		setStopSort(true);
		arr = shuffleArr(arr);
		arrDisplay.clearSwappedIndexes();
		arrDisplay.setComplete(false);
		arrDisplay.setArr(arr);
		indexComparisons = 0;
		resetTime();
		resetSwingWorker(this, arr, arrDisplay);
	}

	// Re-instantiates the SwingWorker so that execute() can be called again.
	public void resetSwingWorker(AlgVisualizer alg, Integer[] arr, ArrDisplay displayArr) {
		arrSort = new ArrSorting(this, arr, displayArr);
	}

	// Reset the timer on the previous sort that was done, used in the reset()
	// method
	public void resetTime() {
		startTime = 0;
		endTime = 0;
		visualizationTime = 0;
		sortingTime = 0;
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

	/*
	 * updatePerformance will be called every time the array is repainted. This
	 * makes it slower / not real time when there is a high delay.
	 * 
	 * We get the values for each performance statistic we want to track (number of
	 * swaps, number of comparisons, visualization time, sorting time), format them
	 * into a string that will then be assigned to the JLabel's text.
	 * 
	 * frame.pack() makes sure that our new text will fit in the frame, and will
	 * adjust if it does not.
	 */
	public void updatePerformance() {
		numSwaps = arrDisplay.getSwappedIndexes().size();

		if (!getSort().equals("Not Sorting") && arrDisplay.getNumChunks() == 1) {
			visualizationTime = System.currentTimeMillis() - startTime;
			sortingTime = visualizationTime - (delay * (arrDisplay.getNumChunks() - 1));
		} else if (arrDisplay.getNumChunks() > 1) {
			visualizationTime = System.currentTimeMillis() - startTime;
			sortingTime = sortingTime + (System.currentTimeMillis() - sortingTime);
		}

		String performance = String.format(
				"Index Comparisons : %d  Index Swaps : %d  Visualization Time : %dms  Sorting Time : %dms",
				indexComparisons, numSwaps, visualizationTime, sortingTime);

		performanceLabel.setText(performance);

		frame.pack();
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

	/*
	 * getSort() returns a string containing the sorting algorithm that is currently
	 * being used to sort the array.
	 */
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

	/*
	 * Returns the boolean value representing the status of the application. If
	 * there is currently a sorting algorithm being used, it will return false.
	 */
	public boolean stopSort() {
		return stopSort;
	}

	/*
	 * Since the availability of the buttons is based on the stopSort variable, we
	 * control them from this setter method. If we want to stop sorting, we enable
	 * all of the sorting buttons again, if we are starting a sorting algorithm,
	 * disable them.
	 */
	public void setStopSort(boolean toSet) {
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

	public Integer getIndexComparisons() {
		return indexComparisons;
	}

	public void setIndexComparisons(int indexComparisons) {
		this.indexComparisons = indexComparisons;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public JLabel getPerformanceLabel() {
		return performanceLabel;
	}

	public void setPerformanceLabel(JLabel performanceLabel) {
		this.performanceLabel = performanceLabel;
	}

	public int getNumSwaps() {
		return numSwaps;
	}

	public void setNumSwaps(int numSwaps) {
		this.numSwaps = numSwaps;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public JSlider getFPSslider() {
		return FPSslider;
	}

	public void setFPSslider(JSlider fPSslider) {
		FPSslider = fPSslider;
	}
}
