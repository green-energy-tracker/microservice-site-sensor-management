package com.green.energy.tracker.site_sensor_management.controller;

import com.green.energy.tracker.site_sensor_management.client.UserManagementUnavailableException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class SiteSensorControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        return ErrorResponse.builder(ex, ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage())).instance(URI.create(request.getRequestURI())).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleBadRequest(IllegalArgumentException ex, HttpServletRequest request) {
        return ErrorResponse.builder(ex, ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage())).instance(URI.create(request.getRequestURI())).build();
    }

}
