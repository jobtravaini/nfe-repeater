package com.job.nfe_repeater.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {

    @ResponseBody
    @ExceptionHandler(NfeNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNfeNotFound(NfeNotFoundException ex)  {
        return ex.getMessage();
    }
}
