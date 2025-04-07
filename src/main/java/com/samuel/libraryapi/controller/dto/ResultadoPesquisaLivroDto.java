package com.samuel.libraryapi.controller.dto;

import com.samuel.libraryapi.model.GeneroLivro;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisaLivroDto(
        UUID id,
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        AutorDto autor) {

}
