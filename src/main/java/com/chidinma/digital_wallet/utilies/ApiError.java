package com.chidinma.digital_wallet.utilies;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@AllArgsConstructor
public class ApiError {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private List<String> details;

    public static ApiError of(int status, String error, String message) {
        return new ApiError(LocalDateTime.now(), status, error, message, null);
    }

    public static ApiError of(int status, String error, String message, List<String> details) {
        return new ApiError(LocalDateTime.now(), status, error, message, details);
    }
}
