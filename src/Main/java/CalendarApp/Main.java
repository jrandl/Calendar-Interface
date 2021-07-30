package CalendarApp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        LoginPane loginPane = new LoginPane(user -> {
            LocalDate now = LocalDate.now();
            CalendarPane calendarPane = new CalendarPane(now.getMonthValue(), now.getYear(), user, closeApp -> primaryStage.close());
            Scene calendar = new Scene(calendarPane);
            primaryStage.setScene(calendar);
        });

        Scene scene = new Scene(loginPane);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}

