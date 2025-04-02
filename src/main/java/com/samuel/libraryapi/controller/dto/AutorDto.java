package com.samuel.libraryapi.controller.dto;
import com.samuel.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDto(UUID id,
                       @NotBlank(message = "campo Obrigátorio")
                       String nome,
                       @NotNull(message = "campo obrigátorio")
                       LocalDate dataNascimento,
                       @NotBlank(message = "campo obrigatorio")
                       String nacionalidade) {


    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);

        return autor;
    }
}
