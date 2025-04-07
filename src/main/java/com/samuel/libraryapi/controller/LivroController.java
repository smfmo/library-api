package com.samuel.libraryapi.controller;

import com.samuel.libraryapi.controller.dto.CadastroLivroDto;
import com.samuel.libraryapi.controller.dto.ErroRespostaDto;
import com.samuel.libraryapi.exceptions.RegistroDuplicadoException;
import com.samuel.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDto dto) {
        try{
            //mapear dto para a entidade
            //enviar a entidade para o service validar e salvar no banco
            //criar url para acesso dos dados do livro
            //retornar codigo created com header location

            return ResponseEntity.ok(dto);
        }catch (RegistroDuplicadoException e){
            var erroDto = ErroRespostaDto.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }
}
