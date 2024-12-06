package com.example.closestv2.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY) //응답 내려줄 떄 비어있는 필드는 없애고 json 변환
public class ApiErrorResponse<T> { //todo error 공통
    private int code;
    private String status;
    private String message;
    private T data;
    private Map<String, String> validation;

    private ApiErrorResponse(HttpStatus status, String message, T data, Map<String, String> validation) {
        this.code = status.value();
        this.status = status.name();
        this.message = message;
        this.data = data;
        this.validation = validation;
    }

    public static <T> ApiErrorResponse<T> ok() {
        return ApiErrorResponse.ok(null);
    }

    public static <T> ApiErrorResponse<T> ok(T data) {
        return new ApiErrorResponse<T>(HttpStatus.OK, HttpStatus.OK.name(), data, null);
    }

    public static ApiErrorResponse error(HttpStatus status, String message, Map<String, String> validation) {
        return new ApiErrorResponse<>(status, message, null, validation);
    }

    public static ApiErrorResponse error(HttpStatus status, String message) {
        return ApiErrorResponse.error(status, message, new HashMap<>());
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Map<String, String> getValidation() {
        return validation;
    }
}
