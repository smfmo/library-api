package com.samuel.libraryapi.controller.dto;

import com.samuel.libraryapi.model.GeneroLivro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Livro")
public record CadastroLivroDto(
        @NotBlank(message = "campo obrigátorio")
        @ISBN
        @Schema(name = "isbn")
        String isbn,
        @NotBlank(message = "campo obrigátorio")
        @Schema(name = "tituloLivro")
        String titulo,
        @NotNull(message = "campo obrigátorio")
        @Past(message = "não pode ser uma data futura")
        @Schema(name = "dataPublicacao")
        LocalDate dataPublicacao,
        @Schema(name = "genero")
        GeneroLivro genero,
        @Schema(name = "preco")
        BigDecimal preco,
        @NotNull(message = "campo obrigatorio")
        @Schema(name = "idAutor")
        UUID idAutor) {
}
