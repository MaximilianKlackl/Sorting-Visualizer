package Max.Sort;

import Max.SortingVisualizer;
import Max.SortingVisualizerGUI;
import javafx.application.Platform;

public class MergeSort implements Runnable
{
    private int[] array;
    private SortingVisualizerGUI gui;

    public MergeSort(int[] array, SortingVisualizerGUI gui)
    {
        this.array = array;
        this.gui = gui;
    }

    public void run() {
        SortingVisualizer.setIsSorting(true);
        inPlaceSort(array);
        gui.setHighlightedIndex2(-1);
        gui.setHighlightedIndex1(-1);
        gui.highlightAll();
        gui.redraw();
        SortingVisualizer.setIsSorting(false);
    }
    public void inPlaceSort ( int[] array )
    {
        inPlaceSort (array, 0, array.length-1);
    }

    private void inPlaceSort ( int[] array, int first, int last )
    {
        int mid, lt, rt;
        int tmp;

        if ( first >= last ) return;

        mid = (first + last) / 2;

        inPlaceSort (array, first, mid);
        inPlaceSort (array, mid+1, last);

        lt = first;  rt = mid+1;

        if ( array[mid] <= array[rt])
        {
            return;
        }

        while (lt <= mid && rt <= last)
        {
            if ( array[lt] <= array[rt])
            {
                lt++;
            }
            else
            {
                tmp = array[rt];     // Will move to [lt]
                for (int i = rt-lt;i>0; i--)
                {
                    array[lt+i] = array[lt+i-1];
                }
                array[lt] = tmp;
                lt++;  mid++;  rt++;
            }

            try {
                Thread.sleep(SortingVisualizer.getSpeed());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gui.setHighlightedIndex1(lt);
            gui.setHighlightedIndex2(rt);
            Platform.runLater(() -> gui.redraw());
        }
    }
}