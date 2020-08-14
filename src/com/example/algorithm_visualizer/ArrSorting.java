package com.example.algorithm_visualizer;

import java.util.*;
import javax.swing.SwingWorker;

public class ArrSorting extends SwingWorker<Void, Integer[]> {

	private Integer[] arr;
	private AlgVisualizer algVisualizer;
	private ArrDisplay arrDisplay;
	private final int SLEEP_TIME = 60;

	public ArrSorting(AlgVisualizer algVisualizer, Integer[] arr, ArrDisplay arrDisplay) {
		this.algVisualizer = algVisualizer;
		this.arr = arr;
		this.arrDisplay = arrDisplay;
	}

	@Override
	protected Void doInBackground() throws Exception {
		if (algVisualizer.stopSort()) {
			publish(arr.clone());
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			algVisualizer.resetSwingWorker(algVisualizer, arr, arrDisplay);
		} else if (algVisualizer.getSort().equals("Bubble Sort")) {
			bubbleSort();
		} else if (algVisualizer.getSort().equals("Insertion Sort")) {
			insertionSort();
		} else if (algVisualizer.getSort().equals("Selection Sort")) {
			selectionSort();
		}
		return null;
	}

	@Override
	protected void process(List<Integer[]> chunks) {
		if (chunks.size() > 1) {
			// If chunks > 1, Red bars will be out of sync
			System.out.println(chunks.size());
		}
		arrDisplay.repaint();
	}

	public void bubbleSort() {

		int n = arr.length;

		for (int i = 0; i < n - 1; i++) {

			for (int j = 0; j < n - i - 1; j++) {
				if (algVisualizer.stopSort()) {
					// Re-shuffle the array and stop sorting
					arr = algVisualizer.shuffleArr(arr);
					publish(arr.clone());
					break;
				}
				if (arr[j] > arr[j + 1]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
					// Paint the new array after swapping
					arrDisplay.addSwappedIndexes(j, j + 1);
					publish(arr.clone());
					try {
						Thread.sleep(SLEEP_TIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}
		if (!algVisualizer.stopSort()) {
			arrDisplay.setComplete(true);
			publish(arr.clone());
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void selectionSort() {
		// shows that it sorts faster than it really does because
		// it swaps less indexes than bubble and insertion sort.
		int n = arr.length;

		for (int i = 0; i < n - 1; i++) {

			if (algVisualizer.stopSort()) {
				arr = algVisualizer.shuffleArr(arr);
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
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (!algVisualizer.stopSort()) {
			arrDisplay.setComplete(true);
			publish(arr.clone());
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void insertionSort() {

		int n = arr.length;

		for (int i = 1; i < n; ++i) {

			int key = arr[i];
			int j = i - 1;

			while (j >= 0 && arr[j] > key) {

				if (algVisualizer.stopSort()) {
					arr = algVisualizer.shuffleArr(arr);
					publish(arr.clone());
					break;
				}

				arr[j + 1] = arr[j];
				j = j - 1;

				arrDisplay.addSwappedIndexes(j, j + 1);
				publish(arr.clone());
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			arr[j + 1] = key;
		}
		if (!algVisualizer.stopSort()) {
			arrDisplay.setComplete(true);
			publish(arr.clone());
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
