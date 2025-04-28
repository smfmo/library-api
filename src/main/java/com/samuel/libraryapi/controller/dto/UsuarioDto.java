package com.samuel.libraryapi.controller.dto;

import java.util.List;

public record UsuarioDto(
        String login,
        String senha,
        List<String>roles) {
}
