package com.example.algorithmvisualizer;

public class Main {

	public static void main(String[] args) {
		AlgVisualizer algVisualizer = new AlgVisualizer();
		algVisualizer.setN(Integer.parseInt(algVisualizer.getSizeOptions()[0]));
		algVisualizer.setArr(algVisualizer.initArr());
		algVisualizer.setFrame(new ContentWindow(algVisualizer));
		// Seems very messy
		algVisualizer.setIndexComparisons(0);
		algVisualizer.setDelay(1000 / algVisualizer.getInitFPS());
		algVisualizer.setSwingWorker(
				new ArrSorting(algVisualizer, algVisualizer.getArr(), algVisualizer.getFrame().getArrDisplay()));
	}

}
