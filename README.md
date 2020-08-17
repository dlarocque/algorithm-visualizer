# AlgorithmVisualizer

A program for visualizing sorting algorithms.

This is my freshman year summer project! :)

## Set up

You can either download the source code and run the application through your IDE or download the runnable JAR "AlgorithmVisualizer.jar" and run that.

## How to use 

On startup, the application will open a Frame containing an unsorted array of integers and buttons to reset, change the size of the array and select the sorting algorithm to visualize.

![Startup](https://github.com/dlarocque/AlgorithmVisualizer/blob/master/images/Startup.PNG)

To change the size of the array being sorted, simply press the drop down menu that initially shows '10' as the array size.
The only available options are 10,50,100,200,400,800.

![Startup](https://github.com/dlarocque/AlgorithmVisualizer/blob/master/images/SizeChanger.png)

![Startup](https://github.com/dlarocque/AlgorithmVisualizer/blob/master/images/LargeArray.png)

To begin sorting the array, simply click the algorithm you want to see visualized.
You can press the 'Reset' button to re-shuffle the array and stop sorting so that you are able to see another algorithm.

![Startup](https://github.com/dlarocque/AlgorithmVisualizer/blob/master/images/QuickSort.png)

To see the sorting process slower, you can choose a larger array.

## Future updates

- Add stats such as index swaps, accesses, and time elapsed.
- Make the window resizable for different resolutions.
- Make the sorting time depend on the systems capabilities to remove lag and delay between swapped indexes and the sorting.

## Known Bugs

When a system has poor perfomance issues, there is some delay between the red and cyan bars displaying the swapped indexes and the sorting. 
This is caused by the program not giving enough time to repaint the array, and sending in another chunk, putting the sorting out of sync with the list of swapped indexes.

