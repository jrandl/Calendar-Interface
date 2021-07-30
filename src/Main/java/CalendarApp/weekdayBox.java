package CalendarApp;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class weekdayBox extends StackPane {
    /** this is the main constructor that creates a weekdayBox */
    public weekdayBox(String day) {
        Rectangle outline = new Rectangle();
        Label dayLabel = new Label(day);
        outline.setFill(null);
        outline.setStroke(Color.BLACK);
        outline.setWidth(150);
        outline.setHeight(25);

        getChildren().addAll(outline, dayLabel);
        setAlignment(dayLabel, Pos.CENTER);
    }
}
