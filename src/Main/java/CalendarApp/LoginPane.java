package CalendarApp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.function.Consumer;


public class LoginPane extends FlowPane  {

    /** This method will clear the window of the pane */
    public void clearWindow() {
        getChildren().clear();
    }

    /** For multiple user inputs the scene needs to be restarted to be able to access it again. */
    public void restart(Consumer<User> postLoginAction) {
        setPadding(new Insets(100, 150, 150, 200));
        setHgap(5);
        setVgap(5);
        getChildren().addAll(new Label("Are you a first time user?"));

        Button yes = new Button("Yes");
        Button no = new Button("No");
        Button add = new Button("Add");
        Button confirm = new Button("Confirm");
        Button clear = new Button("Clear");
        Button back = new Button("Back");
        add.setStyle("-fx-background-color: #06f642; ");
        clear.setStyle("-fx-background-color: rgba(205,18,18,0.75); ");


        EventHandler<ActionEvent> event = e -> {
            getChildren().addAll(new Label("Please enter your first and last name:"));
            TextField name = new TextField();
            name.setPrefColumnCount(10);
            getChildren().addAll(name);
            getChildren().addAll(new Label("Please enter your date of birth:"));
            DatePicker date_holder = new DatePicker();
            getChildren().addAll(date_holder, add, clear, back);
            yes.setDisable(true);
            no.setDisable(true);

            add.setOnAction(e1 -> {
                String full_name;
                full_name = name.getText();
                LocalDate date;
                date = date_holder.getValue();


                if ((name.getText() != null && !name.getText().isEmpty())) {
                    try {
                        User user = new User(full_name, date);
                        clearWindow();
                        restart(postLoginAction);
                    } catch (IllegalArgumentException | IOException f) {
                        System.out.println(f.getMessage());
                    }
                }
            });

            //Setting an action for the Clear button
            clear.setOnAction(event1 -> {
                name.setText("");
                date_holder.getEditor().clear();
            });

            back.setOnAction(event1 -> {
                clearWindow();
                restart(postLoginAction);
            });
        };
        EventHandler<ActionEvent> event1 = e -> {
            yes.setDisable(true);
            no.setDisable(true);
            Label user_prompt = new Label("Please select a name:");
            ChoiceBox<String> choice = new ChoiceBox<>();
            File folder = new File("./resources/Users");
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                choice.getItems().add(file.getName());
            }
            confirm.setOnAction(confirmUser -> {
                try {
                    postLoginAction.accept(new User(choice.getValue()));
                } catch (IOException o) {
                    final Label label = new Label("Please select a name from the box!");
                    label.setTranslateY(20);
                    label.setTranslateX(30);
                    getChildren().add(label);
                }
            });

            back.setOnAction(event2 -> {
                clearWindow();
                restart(postLoginAction);
            });
            getChildren().addAll(confirm, user_prompt, choice, back);
            user_prompt.setTranslateX(-280);
            user_prompt.setTranslateY(30);
            choice.setTranslateX(115);
            choice.setTranslateY(0);
            confirm.setTranslateX(30);
            confirm.setTranslateY(30);
            back.setTranslateX(200);
            back.setTranslateY(0);

        };

        add.setAlignment(Pos.BASELINE_CENTER);
        clear.setAlignment(Pos.BASELINE_RIGHT);
        yes.setOnAction(event);
        no.setOnAction(event1);
        getChildren().addAll(yes, no);

    }
/** This is the main constructor.  Here is where the login screen is set. */
    public LoginPane(Consumer<User> postLoginAction) {
        setPadding(new Insets(100, 150, 150, 200));
        setHgap(5);
        setVgap(5);
        getChildren().addAll(new Label("Are you a first time user?"));


        Button yes = new Button("Yes");
        Button no = new Button("No");
        Button add = new Button("Add");
        Button clear = new Button("Clear");
        Button confirm = new Button("Confirm");
        Button back = new Button("Back");
        add.setStyle("-fx-background-color: #06f642; ");
        clear.setStyle("-fx-background-color: rgba(205,18,18,0.75); ");


        EventHandler<ActionEvent> event = e -> {
            getChildren().addAll(new Label("Please enter your first and last name:"));
            TextField name = new TextField();
            name.setPrefColumnCount(10);
            getChildren().addAll(name);
            getChildren().addAll(new Label("Please enter your date of birth:"));
            DatePicker date_holder = new DatePicker();
            getChildren().addAll(date_holder, add, clear, back);
            yes.setDisable(true);
            no.setDisable(true);



            add.setOnAction(e1 -> {
                String full_name;
                full_name = name.getText();
                LocalDate date;
                date = date_holder.getValue();


                if ((name.getText() != null && !name.getText().isEmpty())) {
                    try {
                        User user = new User(full_name, date);
                        clearWindow();
                        restart(postLoginAction);
                    } catch (IllegalArgumentException | IOException f) {
                        System.out.println(f.getMessage());
                    }
                }
            });

            //Setting an action for the Clear button
            clear.setOnAction(event1 -> {
                name.setText("");
                date_holder.getEditor().clear();
            });

            back.setOnAction(event1 -> {
                clearWindow();
                restart(postLoginAction);
            });
        };

        EventHandler<ActionEvent> event1 = e -> {
            yes.setDisable(true);
            no.setDisable(true);
            Label user_prompt = new Label("Please select a name:");
            ChoiceBox<String> choice = new ChoiceBox<>();
            File folder = new File("./resources/Users");
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                choice.getItems().add(file.getName());
            }
            confirm.setOnAction(confirmUser -> {
                try {
                    postLoginAction.accept(new User(choice.getValue()));
                } catch (IllegalArgumentException | IOException o) {
                    final Label label = new Label(o.getMessage());
                    label.setTranslateY(20);
                    label.setTranslateX(30);
                    getChildren().add(label);
                }
            });
            back.setOnAction(event2 -> {
                clearWindow();
                restart(postLoginAction);
            });
            getChildren().addAll(confirm, user_prompt, choice, back);
            user_prompt.setTranslateX(-280);
            user_prompt.setTranslateY(30);
            choice.setTranslateX(115);
            choice.setTranslateY(0);
            confirm.setTranslateX(30);
            confirm.setTranslateY(30);
            back.setTranslateX(200);
            back.setTranslateY(0);

        };

        add.setAlignment(Pos.BASELINE_CENTER);
        clear.setAlignment(Pos.BASELINE_CENTER);
        yes.setOnAction(event);
        no.setOnAction(event1);
        getChildren().addAll(yes, no);

    }

}