package com.samuel.libraryapi.controller.dto;

import com.samuel.libraryapi.model.GeneroLivro;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Resultados da pesquisa de livros")
public record ResultadoPesquisaLivroDto(
        UUID id,
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        AutorDto autor) {

}
