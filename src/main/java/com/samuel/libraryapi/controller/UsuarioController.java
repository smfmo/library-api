package com.samuel.libraryapi.controller;

import com.samuel.libraryapi.controller.dto.UsuarioDto;
import com.samuel.libraryapi.controller.mappers.UserMapper;
import com.samuel.libraryapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "cadastrar usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Salvar", description = "cadastrar novo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso!")
    })
    public void salvar(@RequestBody @Valid UsuarioDto dto){
        var user = userMapper.toEntity(dto);
        usuarioService.salvar(user);
    }
}
