package com.bdd_test.infrastructure.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateManager {
    /**
     * given label date for build LocalDate
     * with pattern dd/MM/yyyy
     * @param dateLabel String
     * @return LocalDate
     */
    public static LocalDate buildDate(String dateLabel) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dateLabel, formatter);
        } catch (
                DateTimeParseException e) {
            return null;
        }

    }
}
