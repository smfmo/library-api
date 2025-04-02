package com.samuel.libraryapi.controller.dto;
import com.samuel.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDto(UUID id,
                       @NotBlank(message = "campo Obrigátorio")
                               @Size( min = 2 ,max = 100, message = "campo fora do tamanho padrão")
                       String nome, //parei aqui ()
                       @NotNull(message = "campo obrigátorio")
                       LocalDate dataNascimento,
                       @NotBlank(message = "campo obrigatorio")
                               @Size(max = 50, min = 2, message = "campo fora do tamanho padrão")
                       String nacionalidade) {


    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);

        return autor;
    }
}
