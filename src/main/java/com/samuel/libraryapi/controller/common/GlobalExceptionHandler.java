package com.samuel.libraryapi.controller.common;

import com.samuel.libraryapi.controller.dto.ErroCampoDto;
import com.samuel.libraryapi.controller.dto.ErroRespostaDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroRespostaDto handlerMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampoDto> listaErros = fieldErrors
                .stream()
                .map(fe -> new ErroCampoDto(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErroRespostaDto(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "erro de validação.", listaErros);
    }
}
