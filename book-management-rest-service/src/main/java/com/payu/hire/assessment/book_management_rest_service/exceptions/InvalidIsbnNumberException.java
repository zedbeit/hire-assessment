package com.payu.hire.assessment.book_management_rest_service.exceptions;

public class InvalidIsbnNumberException extends RuntimeException {
    public InvalidIsbnNumberException(String message) {
        super(message);
    }
}
