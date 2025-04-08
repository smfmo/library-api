package com.samuel.libraryapi.controller.common;

import com.samuel.libraryapi.controller.dto.ErroCampoDto;
import com.samuel.libraryapi.controller.dto.ErroRespostaDto;
import com.samuel.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.samuel.libraryapi.exceptions.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RegistroDuplicadoException.class)
    public ErroRespostaDto handleRegistroDuplicadoException(RegistroDuplicadoException e){
        return ErroRespostaDto.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroRespostaDto handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e){
        return ErroRespostaDto.respostaPadrao(e.getMessage());
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroRespostaDto handleErrosNaoTratados(RuntimeException e){
        return new ErroRespostaDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro inesperado, entre em contato com a administração", List.of());
    }
}
