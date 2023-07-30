package com.elias.desafioSOS.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(MarcaExistException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseError dataIntegrityViolation(MarcaExistException ex) {
        ResponseError responseError = new ResponseError();
        responseError.setCode(HttpStatus.UNAUTHORIZED.value());
        responseError.setDescription("Marca já cadastrada");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseError).getBody();
    }
}
