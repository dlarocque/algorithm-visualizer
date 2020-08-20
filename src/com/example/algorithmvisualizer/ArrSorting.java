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
import javax.swing.SwingWorker;

public class ArrSorting extends SwingWorker<Void, Integer[]> {

	private int n;
	private Integer[] arr;
	private AlgVisualizer algVisualizer;
	private ArrDisplay arrDisplay;
	private int numChunks;

	public ArrSorting(AlgVisualizer algVisualizer, Integer[] arr, ArrDisplay arrDisplay) {
		this.algVisualizer = algVisualizer;
		this.arr = arr;
		this.arrDisplay = arrDisplay;
		this.n = arr.length;
	}

	/*
	 * doInBackground() can only be called once by calling execute() This method
	 * determines what to do when an action is done. We use if statements to
	 * determine what buttons was clicked, helping determine what action to do next.
	 * If a reset was done, redraw the new shuffled array, and reset this object so
	 * that it is able to sort the next time execute() is called. If a sorting
	 * algorithm was clicked, simply perform that sorting algorithm. Make sure to
	 * set the start and end times for the performance window at the end of sorting.
	 */
	@Override
	protected Void doInBackground() throws Exception {
		if (algVisualizer.stopSort()) {
			publish(arr.clone());
			sleep();
			algVisualizer.resetSwingWorker(algVisualizer, arr, arrDisplay);
		} else {
			algVisualizer.setStartTime(System.currentTimeMillis());
		}
		if (algVisualizer.getSort().equals("Bubble Sort")) {
			bubbleSort();
		} else if (algVisualizer.getSort().equals("Insertion Sort")) {
			insertionSort();
		} else if (algVisualizer.getSort().equals("Selection Sort")) {
			selectionSort();
		} else if (algVisualizer.getSort().equals("Merge Sort")) {
			mergeSort(0, arr.length - 1);
		} else if (algVisualizer.getSort().equals("Quick Sort")) {
			quickSort(0, arr.length - 1);
		}
		algVisualizer.setEndTime(System.currentTimeMillis());
		return null;
	}

	/*
	 * This method accepts a clone of the array that was sent to it as a parameter
	 * from the sorting algorithms. If this method isn't finished executing by the
	 * time that another publish(Arr.clone()) is made, it will send in the array
	 * clone at the end of the list of chunks.
	 * 
	 * Since the repaint() method can not keep up with the amount of chunks sent in
	 * from the sorting algorithms when there is very low delay time (Repaint time
	 * depends on the system), it will only draw a fraction of the array clones sent
	 * to it.
	 * 
	 * To make sure that the colored bars representing the swapped indexes are in
	 * sync with the array being painted, we track the amount of chunks sent, and
	 * send it to arrDisplay, so that whenever repaint() has the opportunity to
	 * execute, the chunk number of the array clone that is being drawn will be
	 * represented by numChunk, which will be used to get the swappedIndexes from
	 * the list. ( numChunk are swappedIndexes.size() are always equal. )
	 */
	@Override
	protected void process(List<Integer[]> chunks) {
		while (chunks.size() > 0) {
			// Paint each cloned array and update number of chunks
			numChunks++;
			arrDisplay.setNumChunk(this.numChunks);
			arrDisplay.setArr(chunks.get(0));
			arrDisplay.repaint();
			chunks.remove(0);
		}
	}

	/*
	 * The amount of time this thread waits after sending a chunk to be drawn in the
	 * Event Dispatch Thread
	 */
	private void sleep() {
		try {
			Thread.sleep(algVisualizer.getDelay());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void bubbleSort() {
		for (int i = 0; i < n - 1; i++) {
			// If sorting has been stopped, don't sort
			if (algVisualizer.stopSort()) {
				break;
			}
			for (int j = 0; j < n - i - 1; j++) {
				// If sorting has been stopped, don't sort
				if (algVisualizer.stopSort()) {
					break;
				} else {
					// Add a comparison
					algVisualizer.setIndexComparisons(algVisualizer.getIndexComparisons() + 1);

					if (arr[j] > arr[j + 1]) { // comparison

						// swap arr[j] and arr[j+1]
						int temp = arr[j];
						arr[j] = arr[j + 1];
						arr[j + 1] = temp;
						// Add the pair of indexes that were swapped to the list.
						arrDisplay.addSwappedIndexes(j, j + 1);
						// Repaint the new array.
						publish(arr.clone());
						sleep();
					}
				}
			}
		}
		// If sorting hasn't been stopped, set end time, complete and draw one more
		// time.
		if (!algVisualizer.stopSort()) {
			algVisualizer.setEndTime(System.currentTimeMillis());
			arrDisplay.setComplete(true);
			publish(arr.clone());
			sleep();
		}
	}

	private void selectionSort() {

		for (int i = 0; i < n - 1; i++) {
			// If sorting has been stopped, don't sort
			if (algVisualizer.stopSort()) {
				break;
			}

			int min_idx = i;

			for (int j = i + 1; j < n; j++) {
				// Add a comparison
				algVisualizer.setIndexComparisons(algVisualizer.getIndexComparisons() + 1);

				if (arr[j] < arr[min_idx]) // comparison
					min_idx = j;
			}
			int temp = arr[min_idx];
			arr[min_idx] = arr[i];
			arr[i] = temp;
			// Add a pair of swapped indexes
			arrDisplay.addSwappedIndexes(min_idx, i);
			// Repaint the new array.
			publish(arr.clone());
			sleep();
		}
		// If sorting hasn't been stopped, set complete and draw one more time.
		if (!algVisualizer.stopSort()) {
			arrDisplay.setComplete(true);
			publish(arr.clone());
			sleep();
		}
	}

	private void insertionSort() {

		for (int i = 1; i < n; ++i) {
			// If sorting has been stopped, don't sort
			if (algVisualizer.stopSort()) {
				break;
			}

			int key = arr[i];
			int j = i - 1;

			while (j >= 0 && arr[j] > key) { // compare arr[j] and arr[i]
				if (algVisualizer.stopSort()) {
					publish(arr.clone());
					sleep();
					break;
				}
				// Add a comparison
				algVisualizer.setIndexComparisons(algVisualizer.getIndexComparisons() + 1);

				arr[j + 1] = arr[j];
				j = j - 1;
				// Add a pair of swapped indexes
				arrDisplay.addSwappedIndexes(j, j + 1);
				// Repaint the array
				publish(arr.clone());
				sleep();
			}
			// Add a pair of swapped indexes
			algVisualizer.setIndexComparisons(algVisualizer.getIndexComparisons() + 1);

			arr[j + 1] = key;
		}
		// If sorting hasn't been stopped, set complete and draw one more time.
		if (!algVisualizer.stopSort()) {
			arrDisplay.setComplete(true);
			publish(arr.clone());
			sleep();
		}
	}

	private void mergeSort(int l, int r) {
		if (algVisualizer.getSort().equals("Merge Sort") && !algVisualizer.stopSort()) { // Needed because of recursion
			if (l < r) {
				// Find the middle point
				int m = (l + r) / 2;

				// Sort first and second halves
				mergeSort(l, m);
				mergeSort(m + 1, r);

				// Merge the sorted halves
				merge(l, m, r);
			}
			// If sorting is done and a reset has not been done, repaint one more time
			if (isSorted() && !algVisualizer.stopSort()) {
				arrDisplay.setComplete(true);
				publish(arr.clone());
				sleep();

			}
		}
	}

	// Merges two subarrays of arr[].
	// First subarray is arr[l..m]
	// Second subarray is arr[m+1..r]
	private void merge(int l, int m, int r) {
		if (algVisualizer.getSort().equals("Merge Sort") && !algVisualizer.stopSort()) { // Needed because of recursion
			// Find sizes of two subarrays to be merged
			int n1 = m - l + 1;
			int n2 = r - m;

			// Create temp arrays
			int L[] = new int[n1];
			int R[] = new int[n2];

			// Copy data to temp arrays
			for (int i = 0; i < n1; ++i)
				L[i] = arr[l + i];
			for (int j = 0; j < n2; ++j)
				R[j] = arr[m + 1 + j];

			// Merge the temp arrays

			// Initial indexes of first and second subarrays
			int i = 0, j = 0;

			// Initial index of merged subarray array
			int k = l;
			while (i < n1 && j < n2) {
				// If sorting has been stopped, don't merge
				if (algVisualizer.stopSort())
					break;
				// Add a comparison
				algVisualizer.setIndexComparisons(algVisualizer.getIndexComparisons() + 1);

				if (L[i] <= R[j]) { // comparison
					arr[k] = L[i];
					i++;
					// Add a pair of swapped indexes
					arrDisplay.addSwappedIndexes(k, k + i);
					// Repaint the array
					publish(arr.clone());
					sleep();
				} else {
					arr[k] = R[j];
					j++;
					// Add a pair of swapped indexes
					arrDisplay.addSwappedIndexes(k, k + j);
					// Repaint the arrays
					publish(arr.clone());
					sleep();
				}
				k++;
			}

			/* Copy remaining elements of L[] if any */
			while (i < n1) {
				// If sorting has been stopped, don't copy
				if (algVisualizer.stopSort())
					break;

				arr[k] = L[i];
				// Add a pair of swapped indexes
				arrDisplay.addSwappedIndexes(k, k + i);
				// Repaint the array
				publish(arr.clone());
				sleep();
				i++;
				k++;
			}

			/* Copy remaining elements of R[] if any */
			while (j < n2) {
				// If sorting has been stopped, don't copy
				if (algVisualizer.stopSort())
					break;

				arr[k] = R[j];
				// Add a pair of swapped indexes
				arrDisplay.addSwappedIndexes(k, k + j);
				publish(arr.clone());
				sleep();
				j++;
				k++;
			}
		}
	}

	private void quickSort(int low, int high) {
		if (algVisualizer.getSort().equals("Quick Sort") && !algVisualizer.stopSort()) { // Needed because of recursion
			if (low < high) {
				// pi is partitioning index, arr[pi] is now at right place
				int pi = partition(low, high);

				// Recursively sort elements before
				// partition and after partition
				quickSort(low, pi - 1);
				quickSort(pi + 1, high);
			}
			// don't think there is another way to know whether or not
			// the array is sorted besides this
			if (isSorted() && !algVisualizer.stopSort()) {
				arrDisplay.setComplete(true);
				publish(arr.clone());
				sleep();
			}
		}
	}

	private int partition(int low, int high) {
		if (algVisualizer.getSort().equals("Quick Sort") && !algVisualizer.stopSort()) { // Needed because of recursion
			int pivot = arr[high];
			int i = (low - 1); // index of smaller element

			for (int j = low; j < high; j++) {
				if (algVisualizer.getSort().equals("Quick Sort") && !algVisualizer.stopSort()) { // Needed because of
																									// recursion
					// Add a comparison
					algVisualizer.setIndexComparisons(algVisualizer.getIndexComparisons() + 1);

					// If current element is smaller than the pivot
					if (arr[j] < pivot) { // comparison

						i++;

						// swap arr[i] and arr[j]
						int temp = arr[i];
						arr[i] = arr[j];
						arr[j] = temp;
						// Add a pair of swapped indexes
						arrDisplay.addSwappedIndexes(i, j);
						// Repaint the array
						publish(arr.clone());
						sleep();
					}
				}
			}
			if (algVisualizer.getSort().equals("Quick Sort") && !algVisualizer.stopSort()) { // Needed because of
																								// recursion
				// swap arr[i+1] and arr[high] (or pivot)
				int temp = arr[i + 1];
				arr[i + 1] = arr[high];
				arr[high] = temp;
				// Add a pair of swapped indexes
				arrDisplay.addSwappedIndexes(i + 1, high);
				// Repaint the array
				publish(arr.clone());
				sleep();
			}
			return i + 1;
		}
		return 0;
	}

	/*
	 * This method tells us if the array is sorted or not. Used to check in the
	 * recursive sorts.
	 */
	private boolean isSorted() {
		boolean isSorted = true;
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i] > arr[i + 1])
				isSorted = false;
		}
		return isSorted;
	}

}
