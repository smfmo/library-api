package com.samuel.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ClientDto(
        UUID id,
        @NotBlank(message = "Campo obrigatório")
        @Size(max = 150, min = 2, message = "campo fora do tamanho padrão")
        String clientId,
        @NotBlank(message = "Campo obrigatório")
        @Size(max = 400, min = 2, message = "campo fora do tamanho padrão")
        String clientSecret,
        @NotBlank(message = "Campo obrigatório")
        @Size(max = 200, min = 2, message = "campo fora do tamanho padrão")
        String redirectUri,
        String scope) {

}
