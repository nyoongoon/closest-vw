package com.example.closestv2.api.exception;

import com.example.closestv2.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> ResponseEntity<ApiResponse<T>> bindException(MethodArgumentNotValidException e) {

        ApiResponse<T> errorResponse = ApiResponse.error(
                (HttpStatus) e.getStatusCode(),
                // 첫 번째 에러의 메시지 꺼내기..
                e.getFieldErrors().getFirst().getDefaultMessage()
        );

        for (FieldError fieldError : e.getFieldErrors()) {
            errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
