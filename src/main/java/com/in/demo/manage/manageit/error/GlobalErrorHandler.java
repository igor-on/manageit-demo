package com.in.demo.manage.manageit.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleValidationException(ConstraintViolationException e) {
        Optional<ConstraintViolation<?>> constraintViolation = e.getConstraintViolations().stream().findFirst();
        return new Error(constraintViolation.orElseThrow(NoSuchElementException::new).getMessage(),
                LocalDateTime.now(Clock.systemUTC()), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleDataException(DataNotFoundException e) {
        return new Error(e.getMessage(), LocalDateTime.now(Clock.systemUTC()), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(NotEnoughPointsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handlePointsException(NotEnoughPointsException e) {
        return new Error(e.getMessage(), LocalDateTime.now(Clock.systemUTC()), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleInvalidDataException(InvalidDataException e) {
        return new Error(e.getMessage(), LocalDateTime.now(Clock.systemUTC()), HttpStatus.BAD_REQUEST.value());
    }
}
