package com.samuel.libraryapi.controller.dto;
import com.samuel.libraryapi.model.Autor;

import java.time.LocalDate;

public record AutorDTO(String nome,
                       LocalDate dataNascimento,
                       String nacionalidade) {


    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);

        return autor;
    }
}
