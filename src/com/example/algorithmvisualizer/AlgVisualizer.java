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

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class AlgVisualizer implements ActionListener, ChangeListener {

	private final int FPS_MIN = 2;
	private final int FPS_INIT = 10;
	private final int FPS_MAX = 100;
	private String[] sizeOptions = { "10", "50", "100", "300", "450", "900" }; // array size options
	private int n;
	private int numSwaps;
	private int delay;
	private Integer indexComparisons;
	private long startTime; // start time of a sort
	private long visualizationTime;
	private boolean doBubbleSort;
	private boolean doInsertionSort;
	private boolean doSelectionSort;
	private boolean doMergeSort;
	private boolean doQuickSort;
	private boolean stopSort; // True if sorting is stopped
	private Integer[] arr; // array that is going to be sorted
	private ContentWindow frame;
	private SwingWorker<Void, Integer[]> arrSort;

	/*
	 * actionPerformed(ActionEvent event)
	 *
	 * When an action is performed on a component on the JFrame that has had this
	 * classes actionListener added to it, this method will decide what to do based
	 * on the event.
	 * 
	 * When a sorting button is clicked, its respective boolean do(..)Sort will be
	 * set to true, and arrSort().execute. This will call the doInBackground()
	 * method in the arrSort object, where it will use the do(..)Sort variable to
	 * discover which sorting algorithm to use, or if there is simply a reset.
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
		// Is there a better way to do this?
		if (event.getSource() == frame.getBubbleButton()) {
			doBubbleSort = true;
			arrSort.execute();
		} else if (event.getSource() == frame.getSelectionButton()) {
			doSelectionSort = true;
			arrSort.execute();
		} else if (event.getSource() == frame.getInsertionButton()) {
			doInsertionSort = true;
			arrSort.execute();
		} else if (event.getSource() == frame.getMergeButton()) {
			doMergeSort = true;
			arrSort.execute();
		} else if (event.getSource() == frame.getQuickButton()) {
			doQuickSort = true;
			arrSort.execute();
		} else if (event.getSource() == frame.getResetButton()) {
			reset();
			arrSort.execute();
		} else if (event.getSource() == frame.getSizeChanger()) {
			// Find what size was selected, and set n to that value
			String selectedSize = (String) frame.getSizeChanger().getSelectedItem();
			n = Integer.valueOf(selectedSize);
			// reset and paint the new array
			reset();
			arrSort.execute();
		}
	}

	/*
	 * stateChanged(ChangeEvent e)
	 *
	 * This method is called when the FPS slider is shifted to change the FPS of
	 * sorting.
	 *
	 * We change the amount of delay occuring in the sorting to what the value of
	 * the slider was moved to.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		int fps = (int) source.getValue();
		delay = 1000 / fps; // ms
		setDelay(delay);
	}

	/*
	 * reset()
	 *
	 * Reset method is called whenever the user presses the reset button, or when a
	 * new size of array is chosen from the size changer.
	 *
	 * This method stops sorting, re-shuffles the array, clears all swapped indexes,
	 * frames painted, tracked time, and comparisons. It must also reset the
	 * swingWorker so that the user is able to see another sort. Since
	 * sort.execute() can only be called once for SwingWorker, we simply
	 * re-instantiate it so that we are able to call it again.
	 */
	public void reset() {
		setStopSort(true);
		arr = initArr();
		frame.getArrDisplay().clearSwappedIndexes();
		frame.getArrDisplay().setComplete(false);
		indexComparisons = 0;
		resetTime();
		setSwingWorker(new ArrSorting(this, arr, frame.getArrDisplay()));
	}

	// Reset the timer on the previous sort that was done, used in the reset()
	public void resetTime() {
		startTime = 0;
		visualizationTime = 0;
	}

	public Integer[] initArr() {
		Integer[] arr = new Integer[n];
		arr = fillArr(arr);
		arr = shuffleArr(arr);
		return arr;
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
	 * updatePerformance()
	 *
	 * This method will be called every time that the frame is updated in order to
	 * update our performance statistics.
	 * 
	 * Finds the values for each performance statistic being tracked (number of
	 * swaps, number of comparisons, visualization time, sorting time), formats them
	 * into a string that will then be assigned to the JLabel's text.
	 * 
	 * frame.pack() makes sure that our new text will fit in the frame, and will
	 * adjust if it does not.
	 */
	public void updatePerformance() {
		numSwaps = frame.getArrDisplay().getSwappedIndexes().size();

		if (stopSort) {
			resetTime();
		} else {
			if (!frame.getArrDisplay().isComplete() && !getSort().equals("Not Sorting")
					&& (frame.getArrDisplay().getNumChunks() == 0 || frame.getArrDisplay().getNumChunks() > 1)) {
				visualizationTime = System.currentTimeMillis() - startTime;
			}
		}

		String performance = String.format("Index Comparisons : %d  Index Swaps : %d  Visualization Time : %dms",
				indexComparisons, numSwaps, visualizationTime);
		frame.getPerformanceLabel().setText(performance);
		frame.pack();
	}

	public Integer[] getArr() {
		return arr;
	}

	public void setArr(Integer[] arr) {
		this.arr = arr;
	}

	public void setN(int n) {
		this.n = n;
	}

	public ContentWindow getFrame() {
		return frame;
	}

	public void setFrame(ContentWindow frame) {
		this.frame = frame;
	}

	public SwingWorker<Void, Integer[]> getArrSorting() {
		return arrSort;
	}

	// Re-instantiates the SwingWorker so that execute() can be called again.
	public void setSwingWorker(SwingWorker<Void, Integer[]> arrSort) {
		this.arrSort = arrSort;
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
		frame.setSortButtons(toSet);
		stopSort = toSet;
	}

	public Integer getIndexComparisons() {
		return indexComparisons;
	}

	public void setIndexComparisons(int indexComparisons) {
		this.indexComparisons = indexComparisons;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
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

	public String[] getSizeOptions() {
		return sizeOptions;
	}

	public void setSizeOptions(String[] sizeOptions) {
		this.sizeOptions = sizeOptions;
	}

	public int getMaxFPS() {
		return FPS_MAX;
	}

	public int getInitFPS() {
		return FPS_INIT;
	}

	public int getMinFPS() {
		return FPS_MIN;
	}
}
