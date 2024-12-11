package com.payu.hire.assessment.book_management_rest_service.exceptions;

public class DuplicateIsbnException extends RuntimeException {
    public DuplicateIsbnException(String message) {
        super(message);
    }
}