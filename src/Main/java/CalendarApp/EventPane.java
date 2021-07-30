package CalendarApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.lang.reflect.Array;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class EventPane extends GridPane {
    private final Text sceneTitle = new Text();
    private final TextField eventTitle = new TextField();
    private final DatePicker startDate = new DatePicker();
    private final DatePicker endDate = new DatePicker();
    private final TextField startTime = new TextField(LocalTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("hh:mm a")));
    private final TextField endTime = new TextField(LocalTime.now().plusHours(2).format(DateTimeFormatter.ofPattern("hh:mm a")));
    private final CheckBox isAllDay = new CheckBox();
    private final TextField eventDescription = new TextField();
    private final TextField eventLocation = new TextField();
    private final CheckBox isPrivate = new CheckBox();
    private final CheckBox isRecurring = new CheckBox();
    private final Label errorMessage = new Label("");
    private final HBox times = new HBox();
    private final User user;
    private final Consumer<Boolean> postAddEventAction;

    private final Special_Day special_day;
    private final Button edit = new Button("edit");
    private final Button cancel = new Button("cancel");
    private final Button confirm = new Button("confirm");
    private final Button delete = new Button("delete");

    /** Creates an EventPane for making a new Special_Day
     *
     * @param user current user logged in
     * @param postAddEventAction action to be done after event is added
     */
    public EventPane(User user, Consumer<Boolean> postAddEventAction){

        this.postAddEventAction = postAddEventAction;
        this.user = user;
        special_day = null;

        sceneTitle.setText("Add Event");

        Button addButton = new Button("Add");
        add(addButton, 1, 9);

        addButton.setOnAction(event -> {
            try {
                user.addSpecialDay(generateSpecial_Day());
                user.getCalendar().populateCalendar();
                postAddEventAction.accept(true);
            } catch (DateTimeParseException e) {
                errorMessage.setText("ERROR: Date format is incorrect, try hh:mm AM/PM format");
            } catch (Exception e) {
                errorMessage.setText("ERROR: " + e.getMessage());
            }
        });
        populateEventPane();
    }

    /** Constructs a window to view or edit a selected event
     *
     * @param user current user logged in
     * @param postAddEventAction action to be done after event is added
     * @param special_day special_day being edited
     */
    public EventPane(User user, Consumer<Boolean> postAddEventAction, Special_Day special_day) {

        this.postAddEventAction = postAddEventAction;
        this.user = user;
        this.special_day = special_day;

        sceneTitle.setText("View Event");
        populateEventPane();
        populateEventPaneParameters(special_day);
        initializeChoiceButtons();
        disableEditMode();

    }

    /** populates the event pane */
    private void populateEventPane() {

        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        add(sceneTitle, 0, 0, 2, 1);

        setAlignment(Pos.BASELINE_RIGHT);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25));

        Label eventTitleLabel = new Label("Title:");
        add(eventTitleLabel, 0, 1);
        add(eventTitle, 1, 1);

        HBox dates = new HBox(startDate, endDate);
        add(dates, 0, 2, 2, 1);

        times.getChildren().addAll(startTime, endTime);
        add(times, 0, 3, 2, 1);

        Label isAllDayLabel = new Label("All Day");
        add(isAllDayLabel, 0, 4);
        add(isAllDay, 1, 4);

        Label eventDescriptionLabel = new Label("Description:");
        add(eventDescriptionLabel, 0, 5);
        add(eventDescription, 1, 5);

        Label eventLocationLabel = new Label("Location:");
        add(eventLocationLabel, 0, 6);
        add(eventLocation, 1, 6);

        Label isPrivateLabel = new Label("Private");
        add(isPrivateLabel, 0, 7);
        add(isPrivate, 1, 7);

        Label isRecurringLabel = new Label("Recurring");
        add(isRecurringLabel, 0, 8);
        add(isRecurring, 1, 8);

        add(errorMessage, 0, 10, 2, 1);

        isAllDay.setOnAction(event -> times.setVisible(!isAllDay.isSelected()));
        isRecurring.setOnAction(event -> endDate.setVisible(!isRecurring.isSelected()));
    }

    /** Contains all Special_Day attributes the user can edit */
    private final Node[] nodes = new Node[]{eventTitle,
            startDate,
            endDate,
            startTime,
            endTime,
            isAllDay,
            isPrivate,
            eventDescription,
            eventLocation,
            isRecurring};

    /** Creates each ChoiceButton for the user to select */
    private void initializeChoiceButtons() {

        HBox choices = new HBox();
        choices.getChildren().addAll(edit, cancel, confirm, delete);
        add(choices, 0, 9, 2, 1);

        edit.setOnAction(event -> enableEditMode());

        cancel.setOnAction(event -> disableEditMode());

        confirm.setOnAction(event -> {
            try {
                user.addSpecialDay(generateSpecial_Day());
                user.removeSpecialDay(special_day);
                user.getCalendar().populateCalendar();
                postAddEventAction.accept(true);
            } catch (IllegalArgumentException e) {
                errorMessage.setText("ERROR: " + e.getMessage());
            } catch (DateTimeParseException e) {
                errorMessage.setText("ERROR: Date format is incorrect, try hh:mm AM/PM format");
            }
        });

        delete.setOnAction(event -> {
                user.removeSpecialDay(special_day);
                user.getCalendar().populateCalendar();
                postAddEventAction.accept(true);
        });
    }

    /** allows the user to edit a special day that was already created */
    private void enableEditMode() {
        edit.setVisible(false);
        for (Node node : nodes)
            node.setDisable(false);
        cancel.setVisible(true);
        confirm.setVisible(true);
        delete.setVisible(true);
        sceneTitle.setText("Edit Event");
    }

    /** disables the edit mode */
    private void disableEditMode() {
        cancel.setVisible(false);
        confirm.setVisible(false);
        delete.setVisible(false);
        for (Node node : nodes)
            node.setDisable(true);
        edit.setVisible(true);
        populateEventPaneParameters(special_day);
        sceneTitle.setText("View Event");
    }

    /** populated the event pane parameters */
    private void populateEventPaneParameters(Special_Day special_day) {

        eventTitle.setText(special_day.getTitle());
        startDate.setValue(special_day.getStartdate());
        if (!special_day.isRecurring())
            endDate.setValue(special_day.getEnddate());
        else
            endDate.setVisible(false);
        if (!special_day.isAllDay()) {
            startTime.setText(special_day.getStarttime().format(DateTimeFormatter.ofPattern("hh:mm a")));
            endTime.setText(special_day.getEndtime().format(DateTimeFormatter.ofPattern("hh:mm a")));
        } else {
            times.setVisible(false);
        }
        isAllDay.setSelected(special_day.isAllDay());
        isPrivate.setSelected(special_day.isPrivate());
        eventDescription.setText(special_day.getDescription());
        eventLocation.setText(special_day.getLocation());
        isRecurring.setSelected(special_day.isRecurring());
    }

    /** generate d Special_Day using user created parameters */
    private Special_Day generateSpecial_Day() {
        if (isAllDay.isSelected()) {
            if (isRecurring.isSelected())
                return new Special_Day(eventTitle.getText(), startDate.getValue(), null, startDate.getValue(), null, isAllDay.isSelected(), eventDescription.getText(), eventLocation.getText(), isPrivate.isSelected(), isRecurring.isSelected());
            else
                return new Special_Day(eventTitle.getText(), startDate.getValue(), null, endDate.getValue(), null, isAllDay.isSelected(), eventDescription.getText(), eventLocation.getText(), isPrivate.isSelected(), isRecurring.isSelected());
        }
        else {
            if (isRecurring.isSelected())
                return new Special_Day(eventTitle.getText(), startDate.getValue(), LocalTime.parse(startTime.getText(), DateTimeFormatter.ofPattern("hh:mm a")), startDate.getValue(), LocalTime.parse(endTime.getText(), DateTimeFormatter.ofPattern("hh:mm a")), isAllDay.isSelected(), eventDescription.getText(), eventLocation.getText(), isPrivate.isSelected(), isRecurring.isSelected());
            else
                return new Special_Day(eventTitle.getText(), startDate.getValue(), LocalTime.parse(startTime.getText(), DateTimeFormatter.ofPattern("hh:mm a")), endDate.getValue(), LocalTime.parse(endTime.getText(), DateTimeFormatter.ofPattern("hh:mm a")), isAllDay.isSelected(), eventDescription.getText(), eventLocation.getText(), isPrivate.isSelected(), isRecurring.isSelected());
        }
    }
}