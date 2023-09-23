package com.hypeboy.hypeBoard.dto;

import lombok.Data;

@Data
public class ErrorDto {
    private String field;
    private String msg;

    public ErrorDto(String field, String msg) {
        this.msg = msg;
        this.field = field;
    }

    public ErrorDto(String msg) {
        this.msg = msg;
    }
}
