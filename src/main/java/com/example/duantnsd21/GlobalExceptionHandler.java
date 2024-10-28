package com.example.duantnsd21;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Global exception handler for the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ProductAlreadyInCartException.
     *
     * @param ex The exception.
     * @return ResponseEntity with error details.
     */
    @ExceptionHandler(ProductAlreadyInCartException.class)
    public ResponseEntity<Map<String, String>> handleProductAlreadyInCartException(ProductAlreadyInCartException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
    }

    /**
     * Handles UsernameNotFoundException.
     *
     * @param ex The exception.
     * @return ResponseEntity with error details.
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    /**
     * Handles IllegalArgumentException.
     *
     * @param ex The exception.
     * @return ResponseEntity with error details.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    /**
     * Handles all other exceptions.
     *
     * @param ex The exception.
     * @return ResponseEntity with error details.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        ex.printStackTrace(); // Replace with proper logging in production
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Đã xảy ra lỗi"));
    }
}
