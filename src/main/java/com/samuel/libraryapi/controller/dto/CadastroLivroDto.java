package com.samuel.libraryapi.controller.dto;

import com.samuel.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDto(
        @NotBlank(message = "campo obrigátorio")
        @ISBN
        String isbn,
        @NotBlank(message = "campo obrigátorio")
        String titulo,
        @NotNull(message = "campo obrigátorio")
        @Past(message = "não pode ser uma data futura")
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        @NotNull(message = "campo obrigatorio")
        UUID idAutor) {
}
