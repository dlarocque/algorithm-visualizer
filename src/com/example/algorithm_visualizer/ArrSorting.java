package com.example.algorithm_visualizer;

import java.util.*;
import javax.swing.SwingWorker;

public class ArrSorting extends SwingWorker<Void, Integer[]> {

	private Integer[] arr;
	private int n;
	private AlgVisualizer algVisualizer;
	private ArrDisplay arrDisplay;
	private final int SLEEP_TIME = 60;
	private Integer arrComparisons;
	private Double timeElapsed;

	public ArrSorting(AlgVisualizer algVisualizer, Integer[] arr, ArrDisplay arrDisplay) {
		this.algVisualizer = algVisualizer;
		this.arr = arr;
		this.arrDisplay = arrDisplay;
		this.n = this.arr.length;
		this.setArrComparisons(0);
		this.setTimeElapsed(0.0);
	}

	@Override
	protected Void doInBackground() throws Exception {
		if (algVisualizer.stopSort()) {
			publish(arr.clone());
			sleep();
			algVisualizer.resetSwingWorker(algVisualizer, arr, arrDisplay);
		} else if (algVisualizer.getSort().equals("Bubble Sort")) {
			bubbleSort();
		} else if (algVisualizer.getSort().equals("Insertion Sort")) {
			insertionSort();
		} else if (algVisualizer.getSort().equals("Selection Sort")) {
			selectionSort();
		} else if (algVisualizer.getSort().equals("Merge Sort")) {
			mergeSort(0, arr.length - 1);
		} else if (algVisualizer.getSort().equals("Quick Sort")) {
			quickSort(0, arr.length - 1);
		} else if (algVisualizer.getSort().equals("Bogo Sort")) {
			bogoSort();
		}
		return null;
	}

	@Override
	protected void process(List<Integer[]> chunks) {
		if (chunks.size() > 1) {
			// If chunks > 1, Red bars will be out of sync
			System.out.println("OVERLOAD" + chunks.size());
		}
		arrDisplay.repaint();
	}

	private void sleep() {
		try {
			Thread.sleep(SLEEP_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	} 

	private void bubbleSort() {

		for (int i = 0; i < n - 1; i++) {
			if (algVisualizer.stopSort()) {
				break;
			}
			for (int j = 0; j < n - i - 1; j++) {
				if (algVisualizer.stopSort()) {
					break;
				} else {
					this.arrComparisons++;
					if (arr[j] > arr[j + 1]) {
						
						// swap arr[j] and arr[j+1]
						int temp = arr[j];
						arr[j] = arr[j + 1];
						arr[j + 1] = temp;

						arrDisplay.addSwappedIndexes(j, j + 1);
						publish(arr.clone());
						sleep();
					}
				}
			}
		}
		if (!algVisualizer.stopSort()) {
			System.out.println(arrComparisons);
			arrDisplay.setComplete(true);
			publish(arr.clone());
			sleep();
		}
	}

	private void selectionSort() {

		for (int i = 0; i < n - 1; i++) {

			if (algVisualizer.stopSort()) {
				publish(arr.clone());
				break;
			}

			int min_idx = i;

			for (int j = i + 1; j < n; j++) {
				if (arr[j] < arr[min_idx])
					min_idx = j;
			}
			int temp = arr[min_idx];
			arr[min_idx] = arr[i];
			arr[i] = temp;

			arrDisplay.addSwappedIndexes(min_idx, i);
			publish(arr.clone());
			sleep();
		}
		if (!algVisualizer.stopSort()) {
			arrDisplay.setComplete(true);
			publish(arr.clone());
			sleep();
		}
	}

	private void insertionSort() {

		for (int i = 1; i < n; ++i) {

			if (algVisualizer.stopSort()) {
				publish(arr.clone());
				sleep();
				break;
			}

			int key = arr[i];
			int j = i - 1;

			while (j >= 0 && arr[j] > key) {

				if (algVisualizer.stopSort()) {
					publish(arr.clone());
					sleep();
					break;
				}
				arr[j + 1] = arr[j];
				j = j - 1;

				arrDisplay.addSwappedIndexes(j, j + 1);
				publish(arr.clone());
				sleep();
			}
			arr[j + 1] = key;
		}
		if (!algVisualizer.stopSort()) {
			arrDisplay.setComplete(true);
			publish(arr.clone());
			sleep();
		}
	}

	private void mergeSort(int l, int r) {
		if (algVisualizer.getSort().equals("Merge Sort") && !algVisualizer.stopSort()) {
			if (l < r) {
				// Find the middle point
				int m = (l + r) / 2;

				// Sort first and second halves
				mergeSort(l, m);
				mergeSort(m + 1, r);

				// Merge the sorted halves
				merge(l, m, r);
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

	// Merges two subarrays of arr[].
	// First subarray is arr[l..m]
	// Second subarray is arr[m+1..r]
	private void merge(int l, int m, int r) {
		if (algVisualizer.getSort().equals("Merge Sort") && !algVisualizer.stopSort()) {
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

				if (algVisualizer.stopSort())
					break;
				if (L[i] <= R[j]) {
					arr[k] = L[i];
					i++;

					arrDisplay.addSwappedIndexes(k, k + i);
					publish(arr.clone());
					sleep();
				} else {
					arr[k] = R[j];
					j++;

					arrDisplay.addSwappedIndexes(k, k + j);
					publish(arr.clone());
					sleep();
				}
				k++;
			}

			/* Copy remaining elements of L[] if any */
			while (i < n1) {

				if (algVisualizer.stopSort())
					break;

				arr[k] = L[i];

				arrDisplay.addSwappedIndexes(k, k + i);
				publish(arr.clone());
				sleep();
				i++;
				k++;
			}

			/* Copy remaining elements of R[] if any */
			while (j < n2) {

				if (algVisualizer.stopSort())
					break;

				arr[k] = R[j];

				arrDisplay.addSwappedIndexes(k, k + j);
				publish(arr.clone());
				sleep();
				j++;
				k++;
			}
		}
	}

	private void quickSort(int low, int high) {
		if (algVisualizer.getSort().equals("Quick Sort") && !algVisualizer.stopSort()) {
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
		if (algVisualizer.getSort().equals("Quick Sort") && !algVisualizer.stopSort()) {
			int pivot = arr[high];
			int i = (low - 1); // index of smaller element

			for (int j = low; j < high; j++) {
				if (algVisualizer.getSort().equals("Quick Sort") && !algVisualizer.stopSort()) {

					// If current element is smaller than the pivot
					if (arr[j] < pivot) {
						i++;

						// swap arr[i] and arr[j]
						int temp = arr[i];
						arr[i] = arr[j];
						arr[j] = temp;

						arrDisplay.addSwappedIndexes(i, j);
						publish(arr.clone());
						sleep();
					}
				}
			}
			if (algVisualizer.getSort().equals("Quick Sort") && !algVisualizer.stopSort()) {
				// swap arr[i+1] and arr[high] (or pivot)
				int temp = arr[i + 1];
				arr[i + 1] = arr[high];
				arr[high] = temp;

				arrDisplay.addSwappedIndexes(i + 1, high);
				publish(arr.clone());
				sleep();
			}
			return i + 1;
		}
		return 0;
	}

	private void bogoSort() {
		while (!arrDisplay.isComplete()) { // until the array is sorted

			if (!algVisualizer.stopSort()) { // if a reset has not been done

				// shuffle the array and paint it
				arr = algVisualizer.shuffleArr(arr);

				arrDisplay.addSwappedIndexes(-1, -1);
				publish(arr.clone());
				sleep();
			} else { // a reset has been done, draw one more time and stop shuffling

				publish(arr.clone());
				sleep();
				break;
			}
		}
	}

	private boolean isSorted() {
		boolean isSorted = true;
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i] > arr[i + 1])
				isSorted = false;
		}
		return isSorted;
	}

	public void setTimeElapsed(Double timeElapsed) {
		this.timeElapsed = timeElapsed;
	}

	public void setArrComparisons(Integer arrAccesses) {
		this.arrComparisons = arrAccesses;
	}

}
