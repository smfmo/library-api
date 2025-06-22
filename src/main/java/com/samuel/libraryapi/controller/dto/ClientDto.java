package com.samuel.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(name = "Clientes")
public record ClientDto(
        @Schema(name = "id")
        UUID id,
        @NotBlank(message = "Campo obrigatório")
        @Size(max = 150, min = 2, message = "campo fora do tamanho padrão")
        @Schema(name = "clienteId")
        String clientId,
        @NotBlank(message = "Campo obrigatório")
        @Size(max = 400, min = 2, message = "campo fora do tamanho padrão")
        @Schema(name = "clientSecret")
        String clientSecret,
        @NotBlank(message = "Campo obrigatório")
        @Size(max = 200, min = 2, message = "campo fora do tamanho padrão")
        @Schema(name = "redirectUri")
        String redirectUri,
        @Schema(name = "scope")
        String scope) {

}
