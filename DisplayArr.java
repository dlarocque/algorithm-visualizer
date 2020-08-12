package algorithmVisualizer;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class DisplayArr extends JComponent {

	private static final long serialVersionUID = 1L;
	protected Integer[] arr;
	protected AlgVisualizer alg;
	private int framesPainted;
	private ArrayList<Integer[]> swappedIndexes;
	private int swappedIndex1;
	private int swappedIndex2;
	private boolean complete;

	public DisplayArr(AlgVisualizer alg, Integer[] arr) {
		this.alg = alg;
		this.arr = arr;
		swappedIndexes = new ArrayList<Integer[]>();
	}

	@Override
	public void paintComponent(Graphics g) {
		// Takes ~ 40ms to draw
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setColor(Color.DARK_GRAY);
		graphics2d.fillRect(0, 0, alg.getWidth(), alg.getArrDispHeight());

		if (alg.getSort().equals("Not Sorting") || complete) {
			swappedIndex1 = -1;
			swappedIndex2 = -1;
		} else {
			swappedIndex1 = swappedIndexes.get(framesPainted)[0];
			swappedIndex2 = swappedIndexes.get(framesPainted++)[1];
		}

		for (int i = 0; i < arr.length; i++) {
			int width = (int) (alg.getWidth() / (double) arr.length);
			int height = arr[i] * (alg.getArrDispHeight() / arr.length);
			int x = i * width;
			int y = alg.getArrDispHeight() - height;
				if ((i == swappedIndex1 || i == swappedIndex2) && !alg.stopSort()) {
					graphics2d.setColor(Color.RED);
				} else if (complete) {
					graphics2d.setColor(Color.GREEN);
				} else {
					graphics2d.setColor(Color.WHITE);
				}
			graphics2d.fillRect(x, y, width, height);
		}
	}
	
	public ArrayList<Integer[]> getSwappedIndexes(){
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
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}
}
