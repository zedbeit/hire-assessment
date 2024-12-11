package com.payu.hire.assessment.book_management_rest_service.utils;

import com.payu.hire.assessment.book_management_rest_service.exceptions.InvalidIsbnNumberException;

public class UtilClass {
    public static void validateIsbn(String isbnNumber) {

        if (!isbnNumber.matches("\\d+")) {
            throw new InvalidIsbnNumberException("ISBN number must contain only digits");
        }

        if (isbnNumber.length() < 10 || isbnNumber.length() > 13) {
            throw new InvalidIsbnNumberException("ISBN number must be between 10 and 13 characters, but got: " + isbnNumber.length());
        }
    }

}
