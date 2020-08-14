package com.example.algorithm_visualizer;

import java.util.*;
import javax.swing.SwingWorker;

public class Sorting extends SwingWorker<Void, Integer[]> {

	private Integer[] arr;
	private AlgVisualizer alg;
	private DisplayArr displayArr;
	private final int SLEEP_TIME = 60;

	public Sorting(AlgVisualizer alg, Integer[] arr, DisplayArr displayArr) {
		this.alg = alg;
		this.arr = arr;
		this.displayArr = displayArr;
	}

	@Override
	protected Void doInBackground() throws Exception {
		if (alg.stopSort()) {
			publish(arr.clone());
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			alg.resetSwingWorker(alg, arr, displayArr);
		} else if (alg.getSort().equals("Bubble Sort")) {
			bubbleSort();
		} else if (alg.getSort().equals("Insertion Sort")) {
			insertionSort();
		} else if (alg.getSort().equals("Selection Sort")) {
			selectionSort();
		}
		return null;
	}

	@Override
	protected void process(List<Integer[]> chunks) {
		if (chunks.size() > 1) {
			System.out.println(chunks.size());
		}
		displayArr.repaint();
	}

	public void bubbleSort() {

		int n = arr.length;

		for (int i = 0; i < n - 1; i++) {

			for (int j = 0; j < n - i - 1; j++) {
				if (alg.stopSort()) {
					// Re-shuffle the array and stop sorting
					arr = alg.shuffleArr(arr);
					publish(arr.clone());
					break;
				}
				if (arr[j] > arr[j + 1]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
					// Paint the new array after swapping
					displayArr.addSwappedIndexes(j, j + 1);
					publish(arr.clone());
					try {
						Thread.sleep(SLEEP_TIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}
		if (!alg.stopSort()) {
			displayArr.setComplete(true);
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

			if (alg.stopSort()) {
				arr = alg.shuffleArr(arr);
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

			displayArr.addSwappedIndexes(min_idx, i);
			publish(arr.clone());
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (!alg.stopSort()) {
			displayArr.setComplete(true);
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
				
				if (alg.stopSort()) {
					arr = alg.shuffleArr(arr);
					publish(arr.clone());
					break;
				}
				
				arr[j + 1] = arr[j];
				j = j - 1;

				displayArr.addSwappedIndexes(j, j + 1);
				publish(arr.clone());
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			arr[j + 1] = key;
		}
		if (!alg.stopSort()) {
			displayArr.setComplete(true);
			publish(arr.clone());
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
