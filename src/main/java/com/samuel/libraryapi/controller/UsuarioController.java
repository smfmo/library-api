package com.samuel.libraryapi.controller;

import com.samuel.libraryapi.controller.dto.UsuarioDto;
import com.samuel.libraryapi.controller.mappers.UserMapper;
import com.samuel.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody @Valid UsuarioDto dto){
        var user = userMapper.toEntity(dto);
        usuarioService.salvar(user);
    }
}
