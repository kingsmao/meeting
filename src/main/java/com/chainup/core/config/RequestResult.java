package com.chainup.core.config;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestResult<T> {

    private T data;
    private int code;
    private String message;

    public RequestResult(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
