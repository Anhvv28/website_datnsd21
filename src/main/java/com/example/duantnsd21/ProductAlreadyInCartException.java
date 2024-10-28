package com.example.duantnsd21;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Product already in cart.")
public class ProductAlreadyInCartException extends RuntimeException {

    /**
     * Constructs a new ProductAlreadyInCartException with the specified detail message.
     *
     * @param message the detail message.
     */
    public ProductAlreadyInCartException(String message) {
        super(message);
    }

    /**
     * Constructs a new ProductAlreadyInCartException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public ProductAlreadyInCartException(String message, Throwable cause) {
        super(message, cause);
    }
}
