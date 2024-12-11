package com.payu.hire.assessment.book_management_rest_service.exceptions;

public class InvalidBookTypeException extends RuntimeException {
    public InvalidBookTypeException(String message){
        super(message);
    }
}
