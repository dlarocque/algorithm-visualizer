package algorithmVisualizer;

import java.util.*;
import javax.swing.SwingWorker;

public class Sorting extends SwingWorker<Void, Integer[]> {

	private Integer[] arr;
	private AlgVisualizer alg;
	private DisplayArr display;

	public Sorting(AlgVisualizer alg, Integer[] arr, DisplayArr display) {
		this.alg = alg;
		this.arr = arr;
		this.display = display;
	}

	@Override
	protected Void doInBackground() throws Exception {
		if (alg.getSort().equals("Bubble Sort")) {
			bubbleSort();
		} else if (alg.getSort().equals("Insertion Sort")) {
			insertionSort();
		} else if (alg.getSort().equals("Selection Sort")) {
			selectionSort();
		} else {
			publish(arr.clone());
		}
		return null;
	}

	@Override
	protected void process(List<Integer[]> chunks) {
		if (chunks.size() > 1) {
			System.out.println(chunks.size());
		}
		display.repaint();
	}

	public void bubbleSort() {

		int n = arr.length;

		for (int i = 0; i < n - 1; i++) {

			if (alg.stopSort()) {
				// Re-shuffle the array and stop sorting
				arr = alg.shuffleArr(arr);
				publish(arr.clone());
				break;
			}

			for (int j = 0; j < n - i - 1; j++) {

				if (arr[j] > arr[j + 1]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
					// Paint the new array after swapping
					display.addSwappedIndexes(j, j + 1);
					publish(arr.clone());
					try {
						Thread.sleep(40);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}
		display.setComplete(true);
		publish(arr.clone());
		try {
			Thread.sleep(40);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void insertionSort() {

		int n = arr.length;

		for (int i = 1; i < n; ++i) {

			if (alg.stopSort()) {
				arr = alg.shuffleArr(arr);
				publish(arr.clone());
				break;
			}

			int key = arr[i];
			int j = i - 1;

			while (j >= 0 && arr[j] > key) {
				arr[j + 1] = arr[j];
				j = j - 1;

				display.addSwappedIndexes(j, j + 1);
				publish(arr.clone());
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			arr[j + 1] = key;
		}
		display.setComplete(true);
		publish(arr.clone());
		try {
			Thread.sleep(40);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void selectionSort() {

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

			display.addSwappedIndexes(min_idx, i);
			publish(arr.clone());
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		display.setComplete(true);
		publish(arr.clone());
		try {
			Thread.sleep(40);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
