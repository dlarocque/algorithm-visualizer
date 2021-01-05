package com.example.algorithmvisualizer;

import java.awt.*;
import javax.swing.*;

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
		initComponents(); 
        setFrame();
	}

	/*
	 * Initializes all of the components that will be in this frame. Add the action
	 * change listeners and set their colors.
	 */
	public void initComponents() {

		double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		String[] sizeOptions;
		
		if (screenHeight > 1080.0) { // 4k
			arrDisplayHeight = 2000;
			contentWidth = arrDisplayHeight;
			sizeOptions = new String[] { "10", "50", "100", "250", "500", "1000" };
		} else if (screenHeight < 1080.0) { // too small for original dimensions
			arrDisplayHeight = 500;
			contentWidth = arrDisplayHeight + 500;
			sizeOptions = new String[] { "10", "50", "100", "250", "500" };
		} else { // Original dimensions (1080p)
			arrDisplayHeight = 900;
			contentWidth = arrDisplayHeight + 20;
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
		arrPanel.setLayout(new GridBagLayout());
		arrPanel.setBackground(Color.DARK_GRAY);
		arrPanel.add(arrDisplay);

		// Initialize all components and add action listeners
        
        initJButtons();

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

    // initialize the buttons for the frame
    private void initJButtons() {
        resetButton = initJButton("Reset");
        bubbleButton = initJButton("Bubble Sort");
        selectionButton = initJButton("Selection Sort");
        insertionButton = initJButton("Insertion Sort");
        mergeButton = initJButton("Merge Sort");
        quickButton = initJButton("Quick Sort");
    }

    // only used to initialize the buttons in the frame
    private JButton initJButton(String buttonName) {
        JButton newButton = new JButton(buttonName);
        newButton.addActionListener(algVisualizer);
        setBackground(Color.WHITE);
        return newButton;
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
		return contentHeight;
	}
	
	public int getArrDisplayWidth() {
		return arrDisplayWidth;
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
