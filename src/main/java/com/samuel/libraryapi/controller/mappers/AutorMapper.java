package com.samuel.libraryapi.controller.mappers;

import com.samuel.libraryapi.controller.dto.AutorDto;
import com.samuel.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "dataNascimento", target = "dataNascimento")
    @Mapping(source = "nacionalidade", target = "nacionalidade")
    Autor toEntity(AutorDto dto);

    AutorDto toDto(Autor autor);
}
