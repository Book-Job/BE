package com.bookjob.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommonResponse<T>(String message, T data, LocalDateTime timestamp) {

    public static <T> CommonResponse<T> success() {
        return new CommonResponse<>("success", null, setCurrentTimestamp());
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>("success", data, setCurrentTimestamp());
    }

    public static <T> CommonResponse<T> failure(String message) {
        return new CommonResponse<>(message, null, setCurrentTimestamp());
    }

    private static LocalDateTime setCurrentTimestamp() {
        return LocalDateTime.now();
    }
}

