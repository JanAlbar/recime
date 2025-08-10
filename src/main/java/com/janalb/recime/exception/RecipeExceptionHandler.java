package com.janalb.recime.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RecipeExceptionHandler {

    private Map<String, Object> buildErrorBody(HttpStatus status, String message, String path) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", path);
        return body;
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleRecipeNotFoundException(RecipeNotFoundException ex, HttpServletRequest request) {
        Map<String, Object> body = buildErrorBody(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, Object> errors = new HashMap<>();
        for (FieldError error: ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        Map<String, Object> body = buildErrorBody(HttpStatus.BAD_REQUEST, "Request Validation Failed", request.getRequestURI());
        body.put("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {

        String message = "Duplicate value violates a unique constraint.";
        if (ex.getCause() instanceof ConstraintViolationException cause) {
            if ("recipe_title_key".equalsIgnoreCase(cause.getConstraintName())){
                message = "Recipe title already exists.";
            }
        }
        Map<String, Object> body = buildErrorBody(HttpStatus.CONFLICT, message, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleUnexpectedException(Exception ex, HttpServletRequest request) {
        Map<String, Object> body = buildErrorBody(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
