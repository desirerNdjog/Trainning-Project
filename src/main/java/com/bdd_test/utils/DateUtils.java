package com.bdd_test.utils;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author: desirejuniorndjog.
 * @created: 22/05/2024 : 19:09
 * @project: trainning
 */

public final class DateUtils {



    private DateUtils(){}

    /**
     * function map a String Date parttern "dd/MM/yyyy" to LocalDate Object
     * @param date
     * @return LocalDate Object
     */

    public static LocalDate stringDateToLocaDate(@NotNull String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }
}
