package com.samuel.libraryapi.controller.mappers;

import com.samuel.libraryapi.controller.dto.UsuarioDto;
import com.samuel.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Usuario toEntity(UsuarioDto dto);

}
