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
	private int framesPainted; // amount of frames painted since the array was shuffled
	private boolean isComplete; // if the array is sorted
	private ArrayList<Integer[]> swappedIndexes;
	private Integer[] arr;
	private AlgVisualizer algVisualizer;

	public ArrDisplay(AlgVisualizer algVisualizer, Integer[] arr) {
		this.algVisualizer = algVisualizer;
		this.arr = arr;
		swappedIndexes = new ArrayList<Integer[]>();
	}

	/*
	 * We override the paintComponent method to allow us to render our own
	 * visualization of the array in bar graph form. This method is called through
	 * the use of the repaint() method that is called in the SwingWorkers publish()
	 * method. We use loops to iterate through the array and draw every index,
	 * filling the arrPanel. A list of arrays contains pairs of swapped indexes that
	 * are drawn in red & blue. We iterate through this list every frame by keeping
	 * track of the amount of frames drawn. ISSUE : If the number of framesPainted
	 * is out of sync with the List of pairs of swappedIndexes, the coloured bars
	 * will be out of sync with the actual indexes that are being swapped, giving
	 * the effect of a very noticeable delay.
	 */
	@Override
	public void paintComponent(Graphics g) {
		// Takes ~ 40ms to draw ( depending on the system )
		Graphics2D graphics2d = (Graphics2D) g;
		// Draw the DARK_GREY background
		graphics2d.setColor(Color.DARK_GRAY);
		graphics2d.fillRect(0, 0, algVisualizer.getWidth(), algVisualizer.getArrDispHeight());

		// Find the values of the swappedIndexes and update framesPainted
		if (algVisualizer.getSort().equals("Not Sorting") || isComplete) {
			swappedIndex1 = -1;
			swappedIndex2 = -1;
		} else if (!algVisualizer.stopSort()) {
			swappedIndex1 = swappedIndexes.get(framesPainted)[0];
			swappedIndex2 = swappedIndexes.get(framesPainted++)[1];
		}

		// Iterate through the array and drawn every index
		for (int i = 0; i < arr.length; i++) {

			// Dimensions of the bars are calculated to make sure they take up the entire
			// Panel
			int width = (int) (algVisualizer.getWidth() / (double) arr.length);
			int height = arr[i] * (algVisualizer.getArrDispHeight() / arr.length);
			int x = i * width;
			int y = algVisualizer.getArrDispHeight() - height;

			// Determine the colour of the bar we are currently drawing
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
		// Performance Button's availability is based on whether or not the array is
		// sorted.
		algVisualizer.getPerformanceButton().setEnabled(complete);

		this.isComplete = complete;
	}

	public Integer[] getArr() {
		return arr;
	}

	public void setArr(Integer[] arr) {
		this.arr = arr;
	}
}
