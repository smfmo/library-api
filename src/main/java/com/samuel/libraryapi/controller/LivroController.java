package com.samuel.libraryapi.controller;

import com.samuel.libraryapi.controller.dto.CadastroLivroDto;
import com.samuel.libraryapi.controller.mappers.LivroMapper;
import com.samuel.libraryapi.model.Livro;
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
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDto dto) {
        Livro livro = mapper.toEntity(dto);
        service.salvar(livro);

        var url = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(url).build();

    }
}
