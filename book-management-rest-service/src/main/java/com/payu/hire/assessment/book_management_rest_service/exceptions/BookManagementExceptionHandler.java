package com.payu.hire.assessment.book_management_rest_service.exceptions;


import javax.validation.ConstraintViolationException;

import com.payu.hire.assessment.book_management_rest_service.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.format.DateTimeParseException;

@Slf4j
@RestControllerAdvice
public class BookManagementExceptionHandler {

    @ExceptionHandler(value = {DuplicateIsbnException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleDuplicateIsbnException(DuplicateIsbnException e) {
        logEx(e);
        return ApiResponse.error(e.getMessage());
    }


    @ExceptionHandler(value = {InvalidBookTypeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleInvalidBookTypeException(InvalidBookTypeException e) {
        logEx(e);
        return ApiResponse.error(e.getMessage());
    }

    @ExceptionHandler(value = {BookManagementException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleBookManagementException(BookManagementException e) {
        logEx(e);
        return ApiResponse.error(e.getMessage());
    }

    @ExceptionHandler(value = {InvalidIsbnNumberException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleInvalidIsbnNumberException(InvalidIsbnNumberException e) {
        logEx(e);
        return ApiResponse.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {ServiceException.class})
    public ApiResponse handleServiceException(ServiceException e) {
        logEx(e);
        return ApiResponse.error("An error occurred while processing your request.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class})
    public ApiResponse handleException(Exception e) {
        logEx(e);
        return ApiResponse.error("An error occurred while processing your request.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        logEx(e);
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(e.getMessage());

        return ApiResponse.error(errorMessage);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        logEx(e);
        return ApiResponse.error(e.getMessage());
    }

    private void logEx(Exception e){
        log.error(String.format("Exception occurred. message : %s",e.getMessage()),e);
    }
}
