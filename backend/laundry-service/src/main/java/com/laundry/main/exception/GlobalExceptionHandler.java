package com.laundry.main.exception;

import com.laundry.main.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI(), null);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleDuplicateResource(
            DuplicateResourceException ex,
            HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        Map<String, String> validationErrors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                validationErrors.put(error.getField(), error.getDefaultMessage()));

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                request.getRequestURI(),
                validationErrors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleGenericException(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getRequestURI(),
                null);
    }

    private ResponseEntity<ApiResponse<ErrorResponse>> buildErrorResponse(
            HttpStatus status,
            String message,
            String path,
            Map<String, String> validationErrors) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .validationErrors(validationErrors)
                .build();

        return ResponseEntity.status(status).body(ApiResponse.failure(message, errorResponse));
    }
}
