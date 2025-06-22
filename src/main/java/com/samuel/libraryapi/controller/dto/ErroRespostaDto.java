package com.samuel.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.util.List;

@Schema(name = "Erros de resposta")
public record ErroRespostaDto(
        @Schema(name = "status")
        int status,
        @Schema(name = "mensagem")
        String mensagem,
        @Schema(name = "erro")
        List<ErroCampoDto> erro) {

    public static ErroRespostaDto respostaPadrao(String mensagem) {
        return new ErroRespostaDto(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }

    public static ErroRespostaDto conflito(String mensagem){
        return new ErroRespostaDto(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }

}
