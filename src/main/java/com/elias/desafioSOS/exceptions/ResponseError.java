package com.elias.desafioSOS.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Schema(name = "Error")
public class ResponseError {

    private int code;
    private String description;
    private String message;
    private Map<String, String> fields = new HashMap<>();

    public String getMessage() {
        return this.getDescription();
    }
}