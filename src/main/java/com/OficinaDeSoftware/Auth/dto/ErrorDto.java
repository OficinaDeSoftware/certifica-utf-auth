package com.OficinaDeSoftware.Auth.dto;

import lombok.Data;

@Data
public class ErrorDto {

    private String message;

    public ErrorDto() {
        super();
    }

    public ErrorDto(String message) {
        this.message = message;
    }

}
