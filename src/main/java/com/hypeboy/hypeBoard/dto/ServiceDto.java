package com.hypeboy.hypeBoard.dto;

import lombok.Data;

import java.util.Optional;

@Data
public class ServiceDto<T> {

    private boolean isOk = true;
    private T data;
    private ErrorDto error;

    public void setData(T data) {
        this.data = data;
    }

    public void setError(String msg) {
        this.isOk = false;
        this.error = new ErrorDto(msg);
    }
}
