package com.payu.hire.assessment.book_catalog_web_service.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.payu.hire.assessment.book_catalog_web_service.constants.ApplicationConstants.INVALID_DATE_FORMAT_MSG;

public class UtilClass {
    public static String validateAndFormatPublishDate(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate parsedDate = LocalDate.parse(date, inputFormatter);
            return outputFormatter.format(parsedDate);
        } catch (DateTimeParseException e) {
            throw new RuntimeException(INVALID_DATE_FORMAT_MSG);
        }
    }
}
