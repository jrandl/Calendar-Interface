package CalendarApp;

import javafx.scene.layout.GridPane;

import java.time.LocalDate;

import static java.lang.Math.floor;

public class Calendar extends GridPane {

    private int month;
    private int year;
    private final User user;


    /** Creates a Calendar in a GridPane
     *
     * @param month current month being displayed
     * @param year current year being displayed
     */
    public Calendar(int month, int year, User user){
        this.month = month;
        this.year = year;
        this.user = user;
        populateCalendar(month, year);
    }

    /** Populates Calendar with Day boxes
     *
     * @param month current month being displayed
     * @param year current year being displayed
     */
    public void populateCalendar(int month, int year){
        // Clear out current calendar
        getChildren().clear();

        int firstday = startingDay(month, year);
        int startingmonthnumber;
        int startingyearnumber = year;
        int startingdaynumber;
        boolean currentmonth;

        // Determines last month
        if (month == 1) {
            startingmonthnumber = 12;
            startingyearnumber = year - 1;
        }
        else
            startingmonthnumber = month - 1;

        // Determines first and month day to print
        if (firstday == 0) {
            startingdaynumber = 1;
            startingmonthnumber = month;
        }
        else
            startingdaynumber = monthdays[month-1] - firstday + 1;

        LocalDate currentday = LocalDate.of(startingyearnumber, startingmonthnumber, startingdaynumber);

        for (int i = 0; i < 7; i++) {
            add(new weekdayBox(weekDays[i]), i, 0);
        }

        for (int i = 1; i < 7; i++)
            for (int j = 0; j < 7; j++){
                currentmonth = currentday.getMonthValue() == month;
                add(new CalendarBox(currentday, user, currentmonth), j, i);
                currentday = currentday.plusDays(1);
            }
    }

    /** This method creates the calender */
    public void populateCalendar(){
        populateCalendar(month, year);
    }

    /** Starts at December for looping purposes */
    private static final int[] monthdays = new int[]{31, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**  Contains codes based on Zeller's Congruence algorithm */
    private static final int[] monthcode = new int[]{11, 12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    private static final String[] months = new String[]{null ,"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    /** Creating week day labels */
    private static final String[] weekDays = new String[]{"Sunday", "Monday", "Tuesday" , "Wednesday", "Thursday", "Friday", "Saturday"};


    /** Determines first day of month using Zeller's Congruence algorithm
     *
     * @param month current month being displayed
     * @param year current year being displayed
     * @return day of week in number form (0-6)
     */
    private static int startingDay(int month, int year) {
        int k = 1;
        int m = monthcode[month-1];
        if (month == 1 || month == 2)
            year -= 1;
        int D = year % 100;
        int C = year / 100;
        int F = (k+((13*m-1)/5)+D+(D/4)+(C/4)-2*C);
        if (F >= 0)
            return F % 7;
        else // function to mod a negative number
            return (int)(F - (floor(F / 7.0) * 7));
    }

    /** returns the date in a string */
    public String dateToString(){
        return months[month] + ", " + year;
    }

    /** moves the calender a month forward when selected */
    public void moveMonthForwards(){
        if (month == 12) {
            month = 1;
            year++;
        } else month++;
        populateCalendar(month, year);
    }

    /** moves the calender a month backward when selected */
    public void moveMonthBackwards(){
        if (month == 1) {
            month = 12;
            year--;
        } else month--;
        populateCalendar(month, year);
    }
    /** moves the calender a year forward when selected */
    public void moveYearForwards(){
        year++;
        populateCalendar(month, year);
    }

    /** moves the calender a year backward when selected */
    public void moveYearBackwards(){
        year--;
        populateCalendar(month, year);
    }

}