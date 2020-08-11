package algorithmVisualizer;

import java.util.List;
import javax.swing.SwingWorker;

public class Sorting extends SwingWorker<Void, Integer[]> {

	protected int checkedIndex1;
	protected int checkedIndex2;
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
		display.repaint();
	}

	public void bubbleSort() {
		System.out.println("bsort");
		for (int i = 0; i < alg.getArraySize() - 1; i++) {
			if (alg.getStopSort()) {
				// Shuffle the array and draw it one last time before breaking loop
				arr = alg.shuffleArr(arr);
				publish(arr.clone());
				break;
			}
			for (int j = 0; j < alg.getArraySize() - i - 1; j++) {
				if (arr[j] > arr[j + 1]) {
					checkedIndex1 = i;
					checkedIndex2 = j;
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
					display.addCheckedIndex1(checkedIndex1);
					display.addCheckedIndex2(checkedIndex2);
					publish(arr.clone());
					try {
						Thread.sleep(4);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (!alg.getStopSort()) {
			checkedIndex1 = -1;
			checkedIndex2 = -1;
			display.addCheckedIndex1(checkedIndex1);
			display.addCheckedIndex2(checkedIndex2);
			publish(arr.clone());
		}
		try {
			Thread.sleep(4);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void insertionSort() {
		for (int i = 1; i < alg.getArraySize(); ++i) {
			if (alg.getStopSort()) {
				// Shuffle the array and draw it one last time before breaking loop
				arr = alg.shuffleArr(arr);
				publish(arr.clone());
				break;
			}
			int key = arr[i];
			int j = i - 1;
			checkedIndex1 = i;
			while (j >= 0 && arr[j] > key) {
				arr[j + 1] = arr[j];
				j = j - 1;
				checkedIndex2 = j;
				display.addCheckedIndex1(checkedIndex1);
				display.addCheckedIndex2(checkedIndex2);
				publish(arr.clone());
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			arr[j + 1] = key;
		}
		if (!alg.getStopSort()) {
			checkedIndex1 = -1;
			checkedIndex2 = -1;
			display.addCheckedIndex1(checkedIndex1);
			display.addCheckedIndex2(checkedIndex2);
			publish(arr.clone());
		}

		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void selectionSort() {

		int n = arr.length;

		for (int i = 0; i < n - 1; i++) {
			if (alg.getStopSort()) {
				// Shuffle the array and draw it one last time before breaking loop
				arr = alg.shuffleArr(arr);
				publish(arr.clone());
				break;
			}
			checkedIndex1 = i;
			int min_idx = i;
			for (int j = i + 1; j < n; j++) {
				checkedIndex2 = j;
				if (arr[j] < arr[min_idx])
					min_idx = j;
				display.addCheckedIndex1(checkedIndex1);
				display.addCheckedIndex2(checkedIndex2);
				publish(arr.clone());
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			int temp = arr[min_idx];
			arr[min_idx] = arr[i];
			arr[i] = temp;
		}
		if (!alg.getStopSort()) {
			checkedIndex1 = -1;
			checkedIndex2 = -1;
			display.addCheckedIndex1(checkedIndex1);
			display.addCheckedIndex2(checkedIndex2);
			publish(arr.clone());
		}

		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
