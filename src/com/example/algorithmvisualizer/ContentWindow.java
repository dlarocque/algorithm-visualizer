package com.example.algorithmvisualizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

/*
 * ContentWindow is a JFrame that holds the array display, buttons, size changers, etc...
 */
public class ContentWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private int arrDisplayHeight;
	private int arrDisplayWidth;
	private int contentWidth;
	private int contentHeight;
	private AlgVisualizer algVisualizer;
	private JPanel arrPanel;
	private ArrDisplay arrDisplay;
	private JPanel buttonPanel;
	private JButton resetButton;
	private JButton bubbleButton;
	private JButton insertionButton;
	private JButton selectionButton;
	private JButton mergeButton;
	private JButton quickButton;
	private JComboBox<String> sizeChanger;
	private JSlider FPSslider;
	private JLabel performanceLabel;

	public ContentWindow(AlgVisualizer algVisualizer) {
		super("Algorithm Visualizer"); // Set the name of the frame
		this.algVisualizer = algVisualizer;
		initComponents(); // initialize the components of the frame
		setFrame(); // Set up the frame and add all of the initialized components
	}

	/*
	 * Initializes all of the components that will be in this frame. Add the action
	 * / change listeners and set their colors.
	 */
	public void initComponents() {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double screenWidth = screenSize.getWidth();
		double screenHeight = screenSize.getHeight();
		String[] sizeOptions;
		
		if (screenHeight > 1080.0) { // 4k
			arrDisplayHeight = 1000;
			contentWidth = arrDisplayHeight;
			sizeOptions = new String[] { "10", "50", "100", "250", "500", "1000" };
		} else if (screenHeight < 1080.0) { // too small for original dimensions
			arrDisplayHeight = 500;
			contentWidth = arrDisplayHeight + 400;
			sizeOptions = new String[] { "10", "50", "100", "250", "500" };
		} else { // Original dimensions
			arrDisplayHeight = 900;
			contentWidth = arrDisplayHeight;
			sizeOptions = new String[] { "10", "50", "100", "300", "450", "900" };
		}

		contentHeight = arrDisplayHeight + 110;
		arrDisplayWidth = arrDisplayHeight;
		algVisualizer.setSizeOptions(sizeOptions);

		arrDisplay = new ArrDisplay(algVisualizer, this);
		arrDisplay.setArr(algVisualizer.getArr());
		arrDisplay.setPreferredSize(new Dimension(arrDisplayWidth, arrDisplayHeight));

		// Panels in the frame that will hold all components.
		// JPanels use the flowLayout to manage their components automatically.
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.DARK_GRAY);

		arrPanel = new JPanel();
		arrPanel.setBackground(Color.DARK_GRAY);
		arrPanel.add(arrDisplay);
		arrDisplay.setAlignmentX(0);

		// Initialize all components and add action listeners
		resetButton = new JButton("Reset");
		resetButton.addActionListener(algVisualizer);
		resetButton.setBackground(Color.WHITE);

		bubbleButton = new JButton("Bubble Sort");
		bubbleButton.addActionListener(algVisualizer);
		bubbleButton.setBackground(Color.WHITE);

		selectionButton = new JButton("Selection Sort");
		selectionButton.addActionListener(algVisualizer);
		selectionButton.setBackground(Color.WHITE);

		insertionButton = new JButton("Insertion Sort");
		insertionButton.addActionListener(algVisualizer);
		insertionButton.setBackground(Color.WHITE);

		mergeButton = new JButton("Merge Sort");
		mergeButton.addActionListener(algVisualizer);
		mergeButton.setBackground(Color.WHITE);

		quickButton = new JButton("Quick Sort");
		quickButton.addActionListener(algVisualizer);
		quickButton.setBackground(Color.WHITE);

		sizeChanger = new JComboBox<String>(algVisualizer.getSizeOptions()); // Pass the String containing all of the
																				// size options
		sizeChanger.addActionListener(algVisualizer);
		sizeChanger.setBackground(Color.WHITE);

		FPSslider = new JSlider(JSlider.HORIZONTAL, algVisualizer.getMinFPS(), algVisualizer.getMaxFPS(),
				algVisualizer.getInitFPS());
		FPSslider.addChangeListener(algVisualizer);
		FPSslider.setBackground(Color.DARK_GRAY);
		//FPSslider.setPreferredSize(new Dimension(75,25));
		
		performanceLabel = new JLabel();
		performanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
	}

	/*
	 * Sets up the frame and adds all of the components to this frame
	 */
	public void setFrame() { // Add JButtons / components to button panel
		buttonPanel.add(resetButton);
		buttonPanel.add(bubbleButton);
		buttonPanel.add(selectionButton);
		buttonPanel.add(insertionButton);
		buttonPanel.add(mergeButton);
		buttonPanel.add(quickButton);
		buttonPanel.add(sizeChanger);
		buttonPanel.add(FPSslider);
		// Initialize and make the frame visible
		setPreferredSize(new Dimension(contentWidth, contentHeight)); // 105 is the height of the performance label and
																		// the button panel
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false); // Cannot be resizable, causes visual issues
		add(buttonPanel, BorderLayout.PAGE_START); // Button panel added to the top of the frame
		add(arrPanel, BorderLayout.PAGE_END); // Array display is added to the bottom of the frame
		add(performanceLabel);
		pack();
		setLocationRelativeTo(null); // center of the screen
		setVisible(true);
	}

	// Set the availability of the sorting buttons
	public void setSortButtons(boolean toSet) {
		bubbleButton.setEnabled(toSet);
		selectionButton.setEnabled(toSet);
		insertionButton.setEnabled(toSet);
		mergeButton.setEnabled(toSet);
		quickButton.setEnabled(toSet);
	}

	public JLabel getPerformanceLabel() {
		return performanceLabel;
	}

	public ArrDisplay getArrDisplay() {
		return arrDisplay;
	}

	public int getContentWidth() {
		return contentWidth;
	}
	
	public int getContentHeight() {
		return contentWidth;
	}
	
	public int getArrDisplayWidth() {
		return arrDisplayHeight;
	}
	
	public int getArrDisplayHeight() {
		return arrDisplayHeight;
	}
	

	public JButton getBubbleButton() {
		return bubbleButton;
	}

	public JButton getSelectionButton() {
		return selectionButton;
	}

	public JButton getInsertionButton() {
		return insertionButton;
	}

	public JButton getMergeButton() {
		return mergeButton;
	}

	public JButton getQuickButton() {
		return quickButton;
	}

	public JButton getResetButton() {
		return resetButton;
	}

	public JComboBox<String> getSizeChanger() {
		return sizeChanger;
	}

}
