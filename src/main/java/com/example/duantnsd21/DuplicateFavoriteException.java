package com.example.duantnsd21;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Ngoại lệ được ném khi sản phẩm đã được yêu thích trước đó.
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateFavoriteException extends RuntimeException {

    public DuplicateFavoriteException(String message) {
        super(message);
    }
}
