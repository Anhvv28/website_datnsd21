package com.example.duantnsd21;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Ngoại lệ được ném khi không tìm thấy tài nguyên.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    /**
     * Constructor với thông báo đơn giản.
     *
     * @param message Thông báo lỗi (ví dụ: "Product not found")
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor đầy đủ để tạo ngoại lệ với thông tin chi tiết về tài nguyên.
     *
     * @param resourceName Tên của tài nguyên (ví dụ: "SanPham")
     * @param fieldName    Tên trường (ví dụ: "id")
     * @param fieldValue   Giá trị của trường (ví dụ: 123)
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s không tìm thấy với %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
