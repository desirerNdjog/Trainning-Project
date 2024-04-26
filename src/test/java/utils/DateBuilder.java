package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateBuilder {
    /**
     * map a String to Date to LocalDate
     * @param date of type String
     * @return LocalDate
     */
    public static LocalDate date(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date,  formatter);
    }
}
