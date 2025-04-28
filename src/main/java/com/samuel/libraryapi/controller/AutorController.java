package com.samuel.libraryapi.controller;

import com.samuel.libraryapi.controller.dto.AutorDto;
import com.samuel.libraryapi.controller.mappers.AutorMapper;
import com.samuel.libraryapi.model.Autor;
import com.samuel.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
//http://localhost:8080/autores
@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorService service;
    private final AutorMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDto dto) {

        Autor autor = mapper.toEntity(dto);
        service.salvar(autor);

        //http://localhost:8080/autores/2121212-dsds-23ds2-ds (valor do id)
        URI location = gerarHeaderLocation(autor.getId()); //metodo da interface GenericController

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}") //algoritmo pra obter os dados pelo id do autor
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<AutorDto> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);

        return service
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDto dto = mapper.toDto(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")// algoritmo para excluir autor
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> excluir(@PathVariable("id") String id) {

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deletar(autorOptional.get());

        return ResponseEntity.noContent().build();

    }

    @GetMapping //algoritmo para pesquisar pelo nome ou nacionalidade, ou pelos dois
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<List<AutorDto>> pesquisarAutores(@RequestParam(value = "nome",
                                                                   required = false) String nome,
                                                           @RequestParam(value = "nacionalidade",
                                                                   required = false) String nacionalidade) {
        List<Autor> resultado = service.pesquisaByExample(nome, nacionalidade);
        List<AutorDto> lista = resultado
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> atualizar(@PathVariable(name = "id") String id,
                                            @RequestBody @Valid AutorDto dto) {

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        service.atualizar(autor);

        return ResponseEntity.noContent().build();
    }
}
