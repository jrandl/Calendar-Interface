package CalendarApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.time.LocalDate;

public class CalendarBox extends StackPane {

    /** Creates a specific day box for a Calendar
     *
     * @param currentday  holds the current day
     * @param user holds the user that is selected in the login screen
     * @param currentmonth holds the current month
     */
    public CalendarBox(LocalDate currentday, User user, boolean currentmonth){

        int day = currentday.getDayOfMonth();

        // Outline and number of box
        Rectangle outline = new Rectangle();
        Label daynumber = new Label(day + ".");
        outline.setFill(currentmonth ? null : Color.LIGHTGRAY);
        outline.setStroke(Color.BLACK);
        outline.setWidth(150);
        outline.setHeight(150);

        // List of events in box
        VBox events = new VBox();
        events.alignmentProperty().setValue(Pos.TOP_RIGHT);
        events.setAlignment(Pos.BASELINE_RIGHT);
        events.setMaxWidth(110);

        // Adds and aligns children
        getChildren().addAll(outline, daynumber, events);
        setAlignment(daynumber, Pos.TOP_LEFT);
        setMargin(daynumber, new Insets(4));
        setMargin(events, new Insets(4));

        // Dropdown for event overflow
        MenuButton eventsdropdown = new MenuButton();


        int extraevents = 0;
        // Adds each Special_Day to events if they meet criteria
        for (Special_Day special_day : user.getSpecial_Days()) {
            if (events.getChildren().size() < 4){
                if (currentday.isAfter(special_day.getStartdate()) && currentday.isBefore(special_day.getEnddate()))
                    events.getChildren().add(new EventButton(user, special_day));
                else if (currentday.isEqual(special_day.getStartdate()) || currentday.isEqual(special_day.getEnddate()))
                    events.getChildren().add(new EventButton(user, special_day));
                else if (currentday.getDayOfMonth() == special_day.getStartdate().getDayOfMonth() && currentday.getMonthValue() == special_day.getStartdate().getMonthValue() && special_day.isRecurring())
                    events.getChildren().add(new EventButton(user, special_day));
                else if (currentday.getDayOfYear() == special_day.getStartdate().getDayOfYear())
                    events.getChildren().add(new EventButton(user, special_day));
            }
            // if event overflow, add to dropdown instead
            else {
                if (events.getChildren().size() == 4) events.getChildren().add(eventsdropdown);
                if (currentday.isAfter(special_day.getStartdate()) && currentday.isBefore(special_day.getEnddate())) {
                    eventsdropdown.getItems().add(new CustomMenuItem(new EventButton(user, special_day)));
                    extraevents++;
                    eventsdropdown.setText(extraevents + " more");
                }
                else if (currentday.isEqual(special_day.getStartdate()) || currentday.isEqual(special_day.getEnddate())) {
                    eventsdropdown.getItems().add(new CustomMenuItem(new EventButton(user, special_day)));
                    extraevents++;
                    eventsdropdown.setText(extraevents + " more");
                }
            }


        }
    }
}