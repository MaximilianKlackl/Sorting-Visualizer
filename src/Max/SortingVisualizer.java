package Max;

public class SortingVisualizer
{
    //private static SortingVisualizerGUI sortingVisualizer;
    private static int[] array;
    private static int length;
    private static long speed;
    private static boolean isSorting;

    SortingVisualizer()
    {
        //sortingVisualizer = new SortingVisualizerGUI();
        speed = 50;
        length = 30;
    }

    public static void generateArray()
    {
        array = new int[length];

        for(int i = 0; i < length; i++)
        {
            array[i] = randomWithRange(50, 450);
        }
    }

    private static int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    public static long getSpeed()
    {
        return speed;
    }

    public static void setSpeed(long speed)
    {
        SortingVisualizer.speed = speed;
    }

    public static int[] getArray() {
        return array;
    }

    public static void setArray(int[] array)
    {
        SortingVisualizer.array = array;
    }

    public static int getLength()
    {
        return length;
    }

    public static void setLength(int length)
    {
        SortingVisualizer.length = length;
    }

    public static boolean isIsSorting()
    {
        return isSorting;
    }

    public static void setIsSorting(boolean isSorting)
    {
        SortingVisualizer.isSorting = isSorting;
    }
}
