package com.payu.hire.assessment.book_management_rest_service.exceptions;

public class ServiceException extends RuntimeException {
    public ServiceException(String message){
        super(message);
    }
}