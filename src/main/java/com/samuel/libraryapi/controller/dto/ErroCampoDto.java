package com.samuel.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Erros de campo")
public record ErroCampoDto(
        @Schema(name = "campo")
        String campo,
        @Schema(name = "erro")
        String erro) {

}
