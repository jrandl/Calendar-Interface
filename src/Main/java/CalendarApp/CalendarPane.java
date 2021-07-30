package CalendarApp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;


public class CalendarPane extends BorderPane {
    private final Calendar CalendarBody;
    private final Label datetext;


    /** Pane that contains a calendar as well as some buttons
     *
     * @param month Current month being displayed
     * @param year Current year being displayed
     * @param user Current user logged in
     */
    public CalendarPane(int month, int year, User user, Consumer<Boolean> exit){
        CalendarBody = new Calendar(month, year, user);
        datetext = new Label(CalendarBody.dateToString());
        Button nextmonth = new Button(">");
        Button previousmonth = new Button("<");
        Button nextyear = new Button(">>");
        Button previousyear = new Button("<<");
        Button addevent = new Button("Add Event");
        Button countdown = new Button("Countdown");
        Button saveandexit = new Button("Save and exit");
        user.setCalendar(CalendarBody);

        HBox leftButtons = new HBox(previousyear, previousmonth);
        HBox rightButtons = new HBox(nextmonth, nextyear);
        HBox topSector = new HBox(leftButtons, datetext, rightButtons);
        HBox bottomSector = new HBox(addevent, countdown, saveandexit);

        countdown.setOnAction(event -> {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(new Countdown(user));
            stage.setAlwaysOnTop(true);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        });

        nextmonth.setOnAction(event -> {
            CalendarBody.moveMonthForwards();
            datetext.setText(CalendarBody.dateToString());
        });

        previousmonth.setOnAction(event -> {
            CalendarBody.moveMonthBackwards();
            datetext.setText(CalendarBody.dateToString());
        });
        nextyear.setOnAction(event -> {
            CalendarBody.moveYearForwards();
            datetext.setText(CalendarBody.dateToString());
        });

        previousyear.setOnAction(event -> {
            CalendarBody.moveYearBackwards();
            datetext.setText(CalendarBody.dateToString());
        });

        addevent.setOnAction(event -> {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(new EventPane(user, closestage -> stage.close()));
            stage.setAlwaysOnTop(true);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        });

        saveandexit.setOnAction(event -> {
            try {
                user.saveSpecial_Days();
                exit.accept(true);
            } catch (IOException e) {
                final Label label = new Label(e.getMessage());
                label.setTranslateY(20);
                label.setTranslateX(30);
                getChildren().add(label);
            }
        });

        previousmonth.setTooltip(new Tooltip("Goes back a month"));
        nextmonth.setTooltip(new Tooltip("Goes forward a month"));

        previousyear.setTooltip(new Tooltip("Goes back a year"));
        nextyear.setTooltip(new Tooltip("Goes forward a year"));

        previousyear.setAlignment(Pos.CENTER_LEFT);
        previousmonth.alignmentProperty().setValue(Pos.CENTER_LEFT);
        datetext.alignmentProperty().setValue(Pos.CENTER);
        nextmonth.alignmentProperty().setValue(Pos.CENTER_RIGHT);
        nextyear.alignmentProperty().setValue(Pos.CENTER_RIGHT);

        topSector.setAlignment(Pos.CENTER);
        topSector.setSpacing(100);
        CalendarBody.setAlignment(Pos.TOP_CENTER);

        setCenter(CalendarBody);
        setTop(topSector);
        setBottom(bottomSector);
    }
}