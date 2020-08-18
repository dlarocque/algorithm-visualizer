package com.example.algorithmvisualizer;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class ArrDisplay extends JComponent {

	private static final long serialVersionUID = 1L;
	private Integer[] arr;
	private AlgVisualizer algVisualizer;
	private int framesPainted;
	private ArrayList<Integer[]> swappedIndexes;
	private int swappedIndex1;
	private int swappedIndex2;
	private boolean isComplete;

	public ArrDisplay(AlgVisualizer algVisualizer, Integer[] arr) {
		this.algVisualizer = algVisualizer;
		this.setArr(arr);
		swappedIndexes = new ArrayList<Integer[]>();
	}

	@Override
	public void paintComponent(Graphics g) {
		// Takes ~ 40ms to draw ( depending on the system )
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setColor(Color.DARK_GRAY);
		graphics2d.fillRect(0, 0, algVisualizer.getWidth(), algVisualizer.getArrDispHeight());
				
		if (algVisualizer.getSort().equals("Not Sorting") || isComplete) {
			swappedIndex1 = -1;
			swappedIndex2 = -1;
		} else if (!algVisualizer.stopSort()) {
			swappedIndex1 = swappedIndexes.get(framesPainted)[0];
			swappedIndex2 = swappedIndexes.get(framesPainted++)[1];
		}

		for (int i = 0; i < arr.length; i++) {
			int width = (int) (algVisualizer.getWidth() / (double) arr.length);
			int height = arr[i] * (algVisualizer.getArrDispHeight() / arr.length);
			int x = i * width;
			int y = algVisualizer.getArrDispHeight() - height;
			if (i == swappedIndex1 && !algVisualizer.stopSort()) {
				graphics2d.setColor(Color.RED);
			} else if (i == swappedIndex2 && !algVisualizer.stopSort()) {
				graphics2d.setColor(Color.BLUE);
			} else if (isComplete) {
				graphics2d.setColor(Color.GREEN);
			} else {
				graphics2d.setColor(Color.WHITE);
			}
			graphics2d.fillRect(x, y, width, height);
		}
	}

	public ArrayList<Integer[]> getSwappedIndexes() {
		return swappedIndexes;
	}

	public void addSwappedIndexes(int swappedIndex1, int swappedIndex2) {
		swappedIndexes.add(new Integer[] { swappedIndex1, swappedIndex2 });
	}

	public void clearSwappedIndexes() {
		swappedIndexes = new ArrayList<Integer[]>();
	}

	public void setFramesPainted(int framesPainted) {
		this.framesPainted = framesPainted;
	}

	public int getFramesPainted() {
		return framesPainted;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean complete) {
		algVisualizer.getStatsButton().setEnabled(complete);
		this.isComplete = complete;
	}

	public Integer[] getArr() {
		return arr;
	}

	public void setArr(Integer[] arr) {
		this.arr = arr;
	}
}
