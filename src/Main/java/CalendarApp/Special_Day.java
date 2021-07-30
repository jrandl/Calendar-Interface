package CalendarApp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class Special_Day {
    private final String title;
    private final LocalDate startdate;
    private final LocalDate enddate;
    private final LocalTime starttime;
    private final LocalTime endtime;
    private final boolean isallday;
    private final String description;
    private final String location;
    private final boolean isprivate;
    private final boolean isrecurring;

    /** Creates a Special_Day for a user
     *
     * @param title holds the title of the special day that the user provides
     * @param startdate holds the start date that the user provides for their special day
     * @param starttime holds the start time that the user provides for their special day
     * @param enddate holds the end date that the user provides for their special day
     * @param endtime holds the end time that the user provides for their special day
     * @param isallday s a boolean variable. If the user selects this the special day is an all day event.
     * @param description holds the description of the special day that the user provides.
     * @param location holds the location of the special day that the user provides
     * @param isprivate is a boolean that the user can select their special day as private or public
     * @param isrecurring is a boolean that if selected make the special day recurring throughout the years
     * @throws IllegalArgumentException  if startdate is after enddate
     */

    public Special_Day(String title, LocalDate startdate, LocalTime starttime, LocalDate enddate, LocalTime endtime, boolean isallday, String description, String location, boolean isprivate, boolean isrecurring) throws IllegalArgumentException {
        this.title = title;
        this.startdate = startdate;
        this.enddate = enddate;
        if (!isallday) {
            this.starttime = starttime;
            this.endtime = endtime;
        } else {
            this.starttime = null;
            this.endtime = null;
        }
        this.isallday = isallday;
        this.description = description;
        this.location = location;
        this.isprivate = isprivate;
        this.isrecurring = isrecurring;

        if (startdate.isAfter(enddate))
            throw new IllegalArgumentException("Start date is after end date");
    }

    /** Creates a Special_Day for a user
     * @param csvDesc a string to be converted to a Special_Day
     * @throws IllegalArgumentException if csvDesc has improperly formatted lines
     */
    public Special_Day(String csvDesc) throws IllegalArgumentException {
        try {
            var fields = Arrays.asList(csvDesc.split(","));
            var newfields = new ArrayList<String>();
            if (fields.size() != 10) throw new IllegalArgumentException("Improper string length");

            for (String field : fields) {
                if ((field.charAt(0) != '\"' || field.charAt(field.length() - 1) != '\"'))
                    throw new IllegalArgumentException();
                else newfields.add(field.strip().substring(1, field.length() - 1));
            }

            if (!newfields.get(5).equals("TRUE") && !newfields.get(5).equals("FALSE"))
                throw new IllegalArgumentException("All Day Event boolean incorrectly formatted");
            if (!newfields.get(8).equals("TRUE") && !newfields.get(8).equals("FALSE"))
                throw new IllegalArgumentException("Private boolean incorrectly formatted");
            if (!newfields.get(9).equals("TRUE") && !newfields.get(9).equals("FALSE"))
                throw new IllegalArgumentException("Recurring boolean incorrectly formatted");

            title = newfields.get(0);
            startdate = LocalDate.parse(newfields.get(1), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            if (!Boolean.parseBoolean(newfields.get(5))) {
                starttime = LocalTime.parse(newfields.get(2), DateTimeFormatter.ofPattern("hh:mm a"));
                endtime = LocalTime.parse(newfields.get(4), DateTimeFormatter.ofPattern("hh:mm a"));
                isallday = false;
            } else {
                starttime = null;
                endtime = null;
                isallday = true;
            }
            enddate = LocalDate.parse(newfields.get(3), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            description = newfields.get(6);
            location = newfields.get(7);
            isprivate = Boolean.parseBoolean(newfields.get(8));
            isrecurring = Boolean.parseBoolean(newfields.get(9));

        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /** Take the special day constructor and forms it into a string */
    public String serialize() {
        ArrayList<String> returnlist = new ArrayList<>();
        returnlist.add(title);
        returnlist.add(startdate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        if (!isallday)
            returnlist.add(starttime.format(DateTimeFormatter.ofPattern("hh:mm a")));
        else returnlist.add("");
        returnlist.add(enddate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        if (!isallday)
            returnlist.add(endtime.format(DateTimeFormatter.ofPattern("hh:mm a")));
        else returnlist.add("");
        if (isallday) returnlist.add("TRUE");
        else returnlist.add("FALSE");
        returnlist.add(description);
        returnlist.add(location);
        if (isprivate) returnlist.add("TRUE");
        else returnlist.add("FALSE");
        if (isrecurring) returnlist.add("TRUE");
        else returnlist.add("FALSE");

        return "\"" + returnlist.get(0) + "\",\"" +
                returnlist.get(1) + "\",\"" +
                returnlist.get(2) + "\",\"" +
                returnlist.get(3) + "\",\"" +
                returnlist.get(4) + "\",\"" +
                returnlist.get(5) + "\",\"" +
                returnlist.get(6) + "\",\"" +
                returnlist.get(7) + "\",\"" +
                returnlist.get(8) + "\",\"" +
                returnlist.get(9) + "\"";
    }

    /** holds the start date for the special day */
    public LocalDate getStartdate() {
        return startdate;
    }

    /** returns the end date for the special day */
    public LocalDate getEnddate() {
        return enddate;
    }

    /** returns the start time for the special day */
    public LocalTime getStarttime() {
        return starttime;
    }

    /** returns the end time for the special day */
    public LocalTime getEndtime() {
        return endtime;
    }

    /** returns the title for the special day */
    public String getTitle() {
        return title;
    }

    /** returns the description of the special day */
    public String getDescription() {
        return description;
    }

    /** returns the location of the special day */
    public String getLocation() {
        return location;
    }

    /** returns either true or false based on if the special day is an all day event */
    public boolean isAllDay() {
        return isallday;
    }

    /** returns either true or false based in if the special day is an either private or public */
    public boolean isPrivate() {
        return isprivate;
    }

    /** returns either true or false based on if the special day is a recurring event or not */
    public boolean isRecurring() {
        return isrecurring;
    }

/** this overrides the toString method to return the title of the special day */
    @Override
    public String toString() {
        return getTitle();
    }
}

