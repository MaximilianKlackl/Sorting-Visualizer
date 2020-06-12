package Max;

import Max.Sort.BubbleSort;
import Max.Sort.MergeSort;
import Max.Sort.SelectionSort;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;

public class SortingVisualizerGUI extends Application
{
    private final Color BG_COLOR = Color.rgb(28,28,28);
    private final Color SOLVED_COLOR = Color.rgb(220,20,60);
    private final Color UNSOLVED_COLOR = Color.rgb(122,255,254);
    private final Color SORTING_COLOR = Color.rgb(60,157,63);

    private ComboBox algorithmSelection;
    private Slider lengthSlider;
    private Slider speedSlider;
    private Button sortButton;
    private Label lengthSliderLabel;
    private Label speedSliderLabel;
    private HBox controlsContainer;
    private VBox mainContainer;
    private AnchorPane pane;
    private Rectangle[] data;

    private int highlightedIndex1;
    private int highlightedIndex2;
    private int leftSpacing;

    @Override
    public void start(Stage stage)
    {
        try
        {
            highlightedIndex1 = -1;
            highlightedIndex2 = -1;

            ObservableList<String> algorithmList = FXCollections.observableArrayList(
                    "SelectionSort",
                    "BubbleSort",
                    "MergeSort"
            );

            algorithmSelection = new ComboBox(algorithmList);
            algorithmSelection.getSelectionModel().selectFirst();
            algorithmSelection.valueProperty().addListener(comboListener);

            lengthSlider = new Slider();
            lengthSlider.setMin(10);
            lengthSlider.setMax(50);
            lengthSlider.setValue(25);
            lengthSlider.valueProperty().addListener(changeListener);

            speedSlider = new Slider();
            speedSlider.setMin(50);
            speedSlider.setMax(1000);
            speedSlider.setValue(100);

            sortButton = new Button("Sort");
            sortButton.addEventHandler(ActionEvent.ACTION, buttonListener);

            lengthSliderLabel = new Label("Length");
            speedSliderLabel = new Label("Speed");
            lengthSliderLabel.setTextFill(Color.WHITE);
            speedSliderLabel.setTextFill(Color.WHITE);

            controlsContainer = new HBox(20, algorithmSelection, lengthSliderLabel, lengthSlider, speedSliderLabel ,speedSlider, sortButton);
            controlsContainer.setPadding(new Insets(20));
            controlsContainer.setAlignment(Pos.CENTER);
            controlsContainer.setBackground(new Background(new BackgroundFill(BG_COLOR, CornerRadii.EMPTY,Insets.EMPTY)));

            SortingVisualizer.setLength((int)lengthSlider.getValue());
            SortingVisualizer.generateArray();

            pane = new AnchorPane();
            drawArray();

            mainContainer = new VBox(controlsContainer, pane);

            Scene scene = new Scene(mainContainer,1000,700);
            stage.setResizable(false);
            stage = new Stage();
            stage.setTitle("Sorting Visualizer");
            stage.setScene(scene);
            stage.show();
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void drawBG()
    {
        Rectangle background = new Rectangle();
        background.setWidth(1000);
        background.setHeight(700);
        background.setFill(BG_COLOR);
        pane.getChildren().add(background);
    }

    public void drawArray()
    {
        drawBG();
        data = new Rectangle[SortingVisualizer.getLength()];

        int spacing = 10;
        int width = 10;
        int dataLength = width * data.length + spacing * data.length-1;
        int windowLength = 1000;
        leftSpacing = windowLength / 2 - dataLength / 2;

        for(int i = 0; i < SortingVisualizer.getLength(); i++)
        {
            data[i] = new Rectangle(10, SortingVisualizer.getArray()[i]);
            data[i].setX(i*20 + leftSpacing);
            data[i].setY(0);
            data[i].setFill(UNSOLVED_COLOR);
            pane.getChildren().add(data[i]);
        }
        data[0].setFill(UNSOLVED_COLOR);
    }

    public void redraw()
    {
        for(int i = 0; i < SortingVisualizer.getLength(); i++)
        {
            data[i].setHeight(SortingVisualizer.getArray()[i]);
            data[i].setX(i*20 + leftSpacing);
            data[i].setY(0);

            if(data[i].getFill() == SORTING_COLOR)
            {
                data[i].setFill(UNSOLVED_COLOR);
            }
        }

        if(highlightedIndex1 != -1)
        {
            data[highlightedIndex1].setFill(SORTING_COLOR);
        }

        if(highlightedIndex2 != -1)
        {
            data[highlightedIndex2].setFill(SOLVED_COLOR);
        }

        if(!SortingVisualizer.isIsSorting())
        {
            data[data.length-1].setFill(SOLVED_COLOR);
        }
    }

    public void highlightAll()
    {
        for(int i = 0; i < data.length; i++)
        {
            data[i].setFill(SOLVED_COLOR);
        }
    }

    EventHandler<ActionEvent> buttonListener = new EventHandler<ActionEvent>()
    {
        public void handle(ActionEvent event)
        {
            SortingVisualizer.setSpeed((long)speedSlider.getValue());
            if(!SortingVisualizer.isIsSorting())
            {
                String selected = algorithmSelection.getSelectionModel().getSelectedItem().toString();
                sort(selected);
            }
        }
    };

    private void sort(String type)
    {
        switch (type)
        {
            case "MergeSort":
            {
                new Thread(new MergeSort(SortingVisualizer.getArray(), this)).start();
            }
            case "BubbleSort":
            {
                new Thread(new BubbleSort(SortingVisualizer.getArray(), this)).start();
            }
            case "SelectionSort":
            {
                new Thread(new SelectionSort(SortingVisualizer.getArray(), this)).start();
            }
        }
    }

    ChangeListener changeListener = new ChangeListener()
    {
        @Override
        public void changed(ObservableValue observableValue, Object o, Object t1)
        {
            if(!SortingVisualizer.isIsSorting())
            {
                SortingVisualizer.setLength((int)lengthSlider.getValue());
                SortingVisualizer.generateArray();
                drawArray();
            }
        }
    };

    ChangeListener comboListener =  new ChangeListener<String>()
    {
        @Override
        public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
        {
            algorithmSelection.getSelectionModel().select(t1);
        }
    };

    public int getHighlightedIndex1() {
        return highlightedIndex1;
    }

    public void setHighlightedIndex1(int highlightedIndex1) {
        this.highlightedIndex1 = highlightedIndex1;
    }

    public int getHighlightedIndex2() {
        return highlightedIndex2;
    }

    public void setHighlightedIndex2(int highlightedIndex2) {
        this.highlightedIndex2 = highlightedIndex2;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
