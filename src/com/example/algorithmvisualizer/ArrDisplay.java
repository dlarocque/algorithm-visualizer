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

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class ArrDisplay extends JComponent {

	private static final long serialVersionUID = 1L;
	private int swappedIndex1;
	private int swappedIndex2;
	private int numChunks; // amount of frames painted since the array was shuffled
	private boolean isComplete; // if the array is sorted
	private ArrayList<Integer[]> swappedIndexes;
	private Integer[] arr;
	private AlgVisualizer algVisualizer;
	private ContentWindow frame;

	public ArrDisplay(AlgVisualizer algVisualizer, ContentWindow frame) {
		this.algVisualizer = algVisualizer;
		this.frame = frame;
		swappedIndexes = new ArrayList<Integer[]>();
	}

	/*
	 * We override the paintComponent method to allow us to render our own
	 * visualization of the array in bar graph form. This method is called through
	 * the use of the repaint() method that is called in the SwingWorkers publish()
	 * method. We use loops to iterate through the array and draw every index,
	 * filling the arrPanel.
	 * 
	 * Since this method can take some time to execute, it may only be able to draw
	 * a fraction of the total frames at lower delay times.
	 * 
	 * There is a list of swappedIndexes that contains each pair of indexes that
	 * were swapped during sorting. Since we always draw the most recent chunk,
	 * numChunks will always represent the number of the array clone we're drawing,
	 * so using that to get the correct index in swappedIndexes will work.
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setColor(Color.DARK_GRAY);
		graphics2d.fillRect(0, 0, frame.getWidth(), frame.getArrDisplayHeight());
		if (algVisualizer.getSort().equals("Not Sorting") || isComplete) {
			swappedIndex1 = -1;
			swappedIndex2 = -1;
		} else if (!algVisualizer.stopSort()) {
			// exclude the first chunk as its used to draw the first array, not related to the sorting
			swappedIndex1 = swappedIndexes.get(numChunks)[0];	// index out of bounds exception?
			swappedIndex2 = swappedIndexes.get(numChunks)[1];
		}
		// Iterate through the array and drawn every index
		for (int i = 0; i < arr.length; i++) {
			int width = (int) (frame.getWidth() / (double) arr.length);
			int height = arr[i] * (frame.getArrDisplayHeight() / arr.length);
			int x = i * width;
			int y = frame.getArrDisplayHeight() - height;
			if (i == swappedIndex1 && !algVisualizer.stopSort()) {
				graphics2d.setColor(Color.RED);
			} else if (i == swappedIndex2 && !algVisualizer.stopSort()) {
				graphics2d.setColor(Color.BLUE);
			} else if (isComplete) {
				graphics2d.setColor(Color.GREEN);
			} else {
				graphics2d.setColor(Color.WHITE);
			}
			// Draw the current indexes bar
			graphics2d.fillRect(x, y, width, height);
		}
		algVisualizer.updatePerformance();
	}

	public ArrayList<Integer[]> getSwappedIndexes() {
		return swappedIndexes;
	}

	// Add a pair of swapped indexes to the end of the list
	public void addSwappedIndexes(int swappedIndex1, int swappedIndex2) {
		swappedIndexes.add(new Integer[] { swappedIndex1, swappedIndex2 });
	}

	public void clearSwappedIndexes() {
		swappedIndexes = new ArrayList<Integer[]>();
	}

	public void setNumChunk(int numChunks) {
		this.numChunks = numChunks;
	}

	public int getNumChunks() {
		return numChunks;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean complete) {
		this.isComplete = complete;
	}

	public Integer[] getArr() {
		return arr;
	}

	public void setArr(Integer[] arr) {
		this.arr = arr;
	}
}
