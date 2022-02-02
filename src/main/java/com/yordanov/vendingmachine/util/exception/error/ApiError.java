package com.yordanov.vendingmachine.util.exception.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ApiError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;

    public ApiError(HttpStatus status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
    }
}