package com.samuel.libraryapi.controller.mappers;

import com.samuel.libraryapi.controller.dto.ClientDto;
import com.samuel.libraryapi.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientDto dto);

    ClientDto toDto(Client client);
}
