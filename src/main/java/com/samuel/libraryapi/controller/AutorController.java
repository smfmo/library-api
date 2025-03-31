package com.samuel.libraryapi.controller;

import com.samuel.libraryapi.controller.dto.AutorDTO;
import com.samuel.libraryapi.model.Autor;
import com.samuel.libraryapi.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
//http://localhost:8080/autores
public class AutorController {

    private final AutorService service;
    private final AutorService autorService;

    @Autowired
    public AutorController(AutorService service, AutorService autorService) {
        this.service = service;
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor) {
        var autorEntidade = autor.mapearParaAutor();
        service.salvar(autorEntidade);

        //http://localhost:8080/autores/2121212-dsds-23ds2-ds (valor do id)
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(autorEntidade.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}") //algoritmo pra obter os dados pelo id do autor
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        if(autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            AutorDTO dto = new AutorDTO(autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade());

            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}") // algoritmo para excluir autor
    public ResponseEntity<Void> excluir(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if(autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deletar(autorOptional.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping //algoritmo para pesquisar pelo nome ou nacionalidade, ou pelos dois
    public ResponseEntity<List<AutorDTO>> pesquisarAutores(@RequestParam(value  = "nome",
                                                                       required = false) String nome,
                                                           @RequestParam(value = "nacionalidade",
                                                                        required = false) String nacionalidade) {
        List<Autor> resultado = service.pesquisarPeloNomeENacionalidade(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream()
                .map(autor -> new AutorDTO(autor.getId(),
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade())).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }
}
