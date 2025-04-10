package com.samuel.libraryapi.controller.mappers;

import com.samuel.libraryapi.controller.dto.CadastroLivroDto;
import com.samuel.libraryapi.controller.dto.ResultadoPesquisaLivroDto;
import com.samuel.libraryapi.model.Livro;
import com.samuel.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {AutorMapper.class})
public abstract class LivroMapper {

    @Autowired
    AutorRepository repository;

    @Mapping(target = "autor", expression = "java( repository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDto dto);

    public abstract ResultadoPesquisaLivroDto toDto(Livro livro);
}
