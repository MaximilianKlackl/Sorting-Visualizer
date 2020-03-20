package Max.Sort;

import Max.SortingVisualizer;
import Max.SortingVisualizerGUI;
import javafx.application.Platform;

public class SelectionSort implements Runnable
{
    private int[] array;
    private SortingVisualizerGUI gui;

    public SelectionSort(int[] array, SortingVisualizerGUI gui)
    {
        this.array = array;
        this.gui = gui;
    }

    @Override
    public void run()
    {
        SortingVisualizer.setIsSorting(true);
        for(int i = 0; i < (array.length - 1); i++)
        {
            for (int j = i + 1; j < array.length; j++)
            {
                if (array[i] > array[j])
                {
                    gui.setHighlightedIndex1(j);
                    gui.setHighlightedIndex2(i);

                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;

                    try {
                        Thread.sleep(SortingVisualizer.getSpeed());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> gui.redraw());
                }
            }
        }
        gui.setHighlightedIndex2(-1);
        gui.setHighlightedIndex1(-1);
        gui.highlightAll();
        gui.redraw();
        SortingVisualizer.setIsSorting(false);
    }
}
