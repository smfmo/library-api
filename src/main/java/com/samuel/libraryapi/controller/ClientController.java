package com.samuel.libraryapi.controller;

import com.samuel.libraryapi.controller.dto.ClientDto;
import com.samuel.libraryapi.controller.mappers.ClientMapper;
import com.samuel.libraryapi.model.Client;
import com.samuel.libraryapi.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "clientes")
@Slf4j
public class ClientController {

    private final ClientService service;
    private final ClientMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar", description = "cadastrar novo client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso!"),
    })
    public void salvarClient(@RequestBody @Valid ClientDto dto){
        log.info("Registrando novo client: {} com scope {}", dto.clientId(), dto.scope());

        Client client = mapper.toEntity(dto);
        service.salvar(client);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Pesquisar", description = "realiza pesquisa de clientes por parâmetros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso!")
    })
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
