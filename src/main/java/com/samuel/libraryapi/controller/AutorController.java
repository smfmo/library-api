package com.samuel.libraryapi.controller;

import com.samuel.libraryapi.controller.dto.AutorDTO;
import com.samuel.libraryapi.model.Autor;
import com.samuel.libraryapi.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autores")
//http://localhost:8080/autores
public class AutorController {

    private final AutorService service;

    @Autowired
    public AutorController(AutorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody AutorDTO autor) {
        var autorEntidade = autor.mapearParaAutor();
        service.salvar(autorEntidade);
        return new ResponseEntity("Autor Salvo com sucesso!" + autor, HttpStatus.CREATED); //cód 201
    }

}
