package org.kolesnik.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void runtimeExceptionHandler(HttpServletRequest req, RuntimeException e) {
        System.out.println(e.getStackTrace());
    }
}
