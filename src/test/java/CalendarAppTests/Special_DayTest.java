package CalendarAppTests;
import org.junit.Test;
import CalendarApp.Special_Day;

import java.time.LocalDate;

import static org.junit.Assert.*;


public class Special_DayTest {
    @Test
    public void testCreateSpecial_Day1(){
        LocalDate localDate = LocalDate.parse("2017-03-05");
        Special_Day special_day1 = new Special_Day("Bill's Birthday", localDate, null, localDate, null, true, "Bill Harris BD", "Atlanta", true, true);
        assertNotNull(special_day1);
    }
    @Test
    public void testCreateSpecial_Day2(){
        String string = "\"Bill's Birthday\",\"03/05/2017\",\"\",\"03/05/2017\",\"\",\"TRUE\",\"Bill Harris BD\",\"Atlanta\",\"TRUE\",\"TRUE\"";
        Special_Day special_day1 = new Special_Day(string);
        assertNotNull(special_day1);
        assertTrue(special_day1.serialize().equals(string));
    }
    @Test
    public void testSerializeSpecial_Day(){
        LocalDate localDate = LocalDate.parse("2017-03-05");
        Special_Day special_day1 = new Special_Day("Bill's Birthday", localDate, null, localDate, null, true, "Bill Harris BD", "Atlanta", true, true);
        assertTrue(special_day1.serialize().equals("\"Bill's Birthday\",\"03/05/2017\",\"\",\"03/05/2017\",\"\",\"TRUE\",\"Bill Harris BD\",\"Atlanta\",\"TRUE\",\"TRUE\""));
    }
}
