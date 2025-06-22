package com.samuel.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Schema(name = "usuarios")
public record UsuarioDto(
        @NotBlank(message = "Campo obrigatório" )
        @Schema(name = "login")
        String login,
        @NotBlank(message = "Campo obrigatório")
        @Schema(name = "senha")
        String senha,
        @Email(message = "Email inválido")
        @NotBlank(message = "Campo obrigatório")
        @Schema(name = "email")
        String email,
        @Schema(name = "roles")
        List<String>roles) {
}
