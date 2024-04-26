package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateBuilder {

    public static LocalDate date(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date,  formatter);
    }
}
