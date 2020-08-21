# AlgorithmVisualizer

A program for visualizing sorting algorithms.

This is my freshman year summer project!  Trying to get a better understanding of sorting algorithms before my Data Structures and Algorithms class this fall while applying new things i've learned such as Swing, git, and multi-threading.

## Installation

### Prerequisite

*You must have a JDK installed on your system [Download JDK](https://www.oracle.com/java/technologies/javase-downloads.html)*

- Clone he project onto your system 'https://github.com/dlarocque/AlgorithmVisualizer'.
- Download the .zip file from https://github.com/dlarocque/AlgorithmVisualizer and extract its contents.

*OR*
- Download the Executable JAR file 'AlgVisualizer.jar'.

## Running

*First, make sure your PATH and JAVA_HOME Environment variables are set correctly.*

### _Running the Executable JAR_

- You can simply go through your file system and find the AlgVisualizer.jar you downloaded.  Simply open it, and the program will start

*OR*

- Through the terminal, you can go to `C:/.../AlgorithmVisualizer` and enter

`AlgorithmVisualizer.jar`

### _Running through Terminal_

- Go to `C:/.../AlgorithmVisualizer/src` and enter

`javac AlgVisualizer.java`

- This will compile the program into a binary file, where you can now find by going to `C:/.../AlgorithmVisualizer/bin`

- If there are no issues, you can now go to  `C:/.../AlgorithmVisualizer/bin` and run the program with

`java com.example.algorithmvisualizer.AlgVisualizer`

## How to use 

On startup, the application will open up a frame containing buttons and an unsorted array in bar form.  There is a Reset button, a button for each sorting algorithm, a drop down size changer, an FPS slider, and a label showing the sorting performance.

![Startup](https://github.com/dlarocque/AlgorithmVisualizer/blob/master/images/Startup.PNG)

_The Reset Button_ will re-shuffle the array and stop any sorting that is being done, while keeping the size set to the array.

_The Sort Buttons_ will be available when sorting isn't being done.  Clicking any of them will start sorting the displayed array with the selected sorting algorithm.

_The Size Changer_ is initially set at N = 10, clicking it will open a drop down menu listing the available array sizes, clicking any of them will stop sorting and display a new array of the chosen size.

_The FPS Slider_ is initialially set at 10 fps, it ranges from 1 fps all the way to 100 fps.  This slider can be used to speed up the sorting / visualization process on bigger arrays or slow it down to get a better understanding of the sorting process on smaller arrays.  It can be used before, during, and after sorting.

_The Performance Label_ between the array and the buttons displays all of the stats that can help the user understand how different sorting algorithms perform compared to each other.  (It's interesting to see how bubble sort is better performing than merge and quick sort on small arrays)

#### Notes

The time to visualize the sorting of one of the algorithms is not a good representation of its real sorting time.  This is because the arr is updated every time there is an index swap, this makes it so that sorting algorithms with fewer swaps are visualized much faster.  This is the reasoning behind including a 'Sorting Time' stat along with 'Visualzation Time' in the Performance label.

The main window is not resizable due to the way that the bar graph is drawn.  For example, if the width of the content pane is changed from 800 to 822, with an array N = 10, the drawing will not fill the entire window.  Initially, the value of an index in the array was represented by an 80 inch wide bar, now it would be 82 and leaving a 2 pixel wide empty space in the frame.
This is [Issue #15](https://github.com/dlarocque/AlgorithmVisualizer/issues/15)

## Authors

- Daniel La Rocque    [dlarocque](https://github.com/dlarocque)

## License

This project is licensed under the MIT License.  See [LICENSE.txt](https://github.com/dlarocque/AlgorithmVisualizer/blob/master/LICENSE.txt) for more details

## Issues

If there is an issue with the application, please take a look at [issues](https://github.com/dlarocque/AlgorithmVisualizer/issues).  If your issue is not already listed, please create a new issue that describes your problem!

## Acknowledgments

Inspiration for the project https://www.youtube.com/watch?v=kPRA0W1kECg


