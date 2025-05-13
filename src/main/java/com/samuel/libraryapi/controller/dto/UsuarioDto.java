package com.samuel.libraryapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDto(
        @NotBlank(message = "Campo obrigatório" )
        String login,
        @NotBlank(message = "Campo obrigatório")
        String senha,
        @Email(message = "Email inválido")
        @NotBlank(message = "Campo obrigatório")
        String email,
        List<String>roles) {
}
