package Max.Sort;

import Max.SortingVisualizer;
import Max.SortingVisualizerGUI;
import javafx.application.Platform;

public class BubbleSort implements Runnable
{
    private int[] array;
    private SortingVisualizerGUI gui;

    public BubbleSort(int[] array, SortingVisualizerGUI gui)
    {
        this.array = array;
        this.gui = gui;
    }

    @Override
    public void run()
    {
        int rightsPosition = array.length-1;
        SortingVisualizer.setIsSorting(true);
        for(int i=0; i < array.length; i++)
        {
            for(int j=1; j < (array.length-i); j++)
            {
                if(array[j-1] > array[j])
                {
                    int temp = array[j-1];
                    array[j-1] = array[j];
                    array[j] = temp;

                    gui.setHighlightedIndex1(j);

                    try {
                        Thread.sleep(SortingVisualizer.getSpeed());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> gui.redraw());
                }
            }
            gui.setHighlightedIndex2(rightsPosition);
            rightsPosition--;
        }
        gui.setHighlightedIndex2(-1);
        gui.setHighlightedIndex1(-1);
        gui.highlightAll();
        gui.redraw();
        SortingVisualizer.setIsSorting(false);
    }
}
