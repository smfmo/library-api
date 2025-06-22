package com.samuel.libraryapi.controller.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Autor")
public record AutorDto(UUID id,
                       @NotBlank(message = "campo Obrigátorio")
                               @Size(max = 100, min = 2, message = "campo fora do tamanho padrão")
                       @Schema(name = "nome")
                       String nome,
                       @NotNull(message = "campo obrigátorio")
                               @Past(message = "não pode ser data futura")
                       @Schema(name = "dataNascimento")
                       LocalDate dataNascimento,
                       @NotBlank(message = "campo obrigatorio")
                               @Size(max = 50, min = 2, message = "campo fora do tamanho padrão")
                       @Schema(name = "nacionalidade")
                       String nacionalidade) {

}
