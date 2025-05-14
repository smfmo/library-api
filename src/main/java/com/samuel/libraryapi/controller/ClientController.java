package com.samuel.libraryapi.controller;

import com.samuel.libraryapi.controller.dto.ClientDto;
import com.samuel.libraryapi.controller.mappers.ClientMapper;
import com.samuel.libraryapi.model.Client;
import com.samuel.libraryapi.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;
    private final ClientMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    public void salvarClient(@RequestBody @Valid ClientDto dto){
        Client client = mapper.toEntity(dto);
        service.salvar(client);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<List<ClientDto>> pesquisarClient(@RequestParam(value = "clientId",
                                                                    required = false) String clientId,
                                                           @RequestParam(value = "scope",
                                                                    required = false) String scope){
        List<Client> resultado = service.pesquisaByExample(clientId, scope);
        List<ClientDto> lista = resultado
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }
}
