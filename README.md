# algorithm-visualizer

A program for visualizing sorting algorithms.

This is my freshman year summer project!  Trying to get a better understanding of sorting algorithms before my Data Structures and Algorithms class this fall while applying new things i've learned such as Swing, Git, Maven, and multi-threading.

Despite this type of project being unoriginal, the code itself is original.

For a small demo of the project, you can check out <a src="https://dlarocque.github.io/2021/04/29/Sorting-Algorithm-Visualizer.html">this post</a> on my website.

<!-- ![Startup](https://github.com/dlarocque/AlgorithmVisualizer/blob/master/images/Startup.PNG) -->

[![AlgorithmVisualizer](https://github.com/dlarocque/AlgorithmVisualizer/blob/master/images/Startup.PNG)](https://www.youtube.com/watch?v=WDOpFcnzuaQ "AlgorithmVisualizer")

## Installation

### Prerequisite

*You must have the JDK installed on your system [Download JDK](https://www.oracle.com/java/technologies/javase-downloads.html)*

*Have Apache Maven version 3.6.3 or later installed on your computer* (If you want to modify source code)

-----------------------------------------------

#### Clone

`git clone https://github.com/dlarocque/AlgorithmVisualizer`

#### Download ZIP

Download the .zip file from https://github.com/dlarocque/AlgorithmVisualizer and extract its contents

## Running

*For Linux/macOS users*

From the root of the project, build and execute simultaneously:

`mvn install exec:java -Dexec.mainClass=com.example.algorithmvisualizer.Main`

## How to use 

On startup, the application will open up a frame containing buttons and an unsorted array in bar form.  There is a Reset button, a button for each sorting algorithm, a drop down size changer, an FPS slider, and a label showing statistics.

_The Reset Button_ will re-shuffle the array and stop any sorting that is being done, while keeping the size set to the array.

_The Sort Buttons_ will be available when sorting isn't being done.  Clicking any of them will start sorting the displayed array with the selected sorting algorithm.

_The Size Changer_ is initially set at N = 10, clicking it will open a drop down menu listing the available array sizes, clicking any of them will stop sorting and display a new array of the chosen size.

_The FPS Slider_ is initialially set at 10 fps, it ranges from 1 fps all the way to 100 fps.  This slider can be used to speed up the sorting / visualization process on bigger arrays or slow it down to get a better understanding of the sorting process on smaller arrays.  It can be used before, during, and after sorting.

_The Performance Label_ between the array and the buttons displays all of the stats that can help the user understand how different sorting algorithms perform compared to each other.  (It's interesting to see how bubble sort is better performing than merge and quick sort on small arrays)

#### Notes

The time to visualize the sorting of one of the algorithms is not a good representation of its real sorting time.  This is because the arr is updated every time there is an index swap, this makes it so that sorting algorithms with fewer swaps are visualized much faster.  This is the reasoning behind including a 'Sorting Time' stat along with 'Visualzation Time' in the Performance label.

## Authors

- [dlarocque](https://github.com/dlarocque)

## License

This project is licensed under the MIT License.  See [LICENSE.txt](https://github.com/dlarocque/AlgorithmVisualizer/blob/master/LICENSE.txt) for more details

## Issues

If there is an issue with the application, please take a look at [issues](https://github.com/dlarocque/AlgorithmVisualizer/issues).  If your issue is not already listed, please create a new issue that describes your problem!

## Acknowledgments

Inspiration for the project https://www.youtube.com/watch?v=kPRA0W1kECg


