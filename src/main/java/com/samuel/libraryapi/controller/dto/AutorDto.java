package com.samuel.libraryapi.controller.dto;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDto(UUID id,
                       @NotBlank(message = "campo Obrigátorio")
                               @Size(max = 100, min = 2, message = "campo fora do tamanho padrão")
                       String nome, //parei aqui ()
                       @NotNull(message = "campo obrigátorio")
                               @Past(message = "não po")
                       LocalDate dataNascimento,
                       @NotBlank(message = "campo obrigatorio")
                               @Size(max = 50, min = 2, message = "campo fora do tamanho padrão")
                       String nacionalidade) {

}
