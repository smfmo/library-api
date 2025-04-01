package com.samuel.libraryapi.controller;

import com.samuel.libraryapi.controller.dto.AutorDto;
import com.samuel.libraryapi.controller.dto.ErroRespostaDto;
import com.samuel.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.samuel.libraryapi.exceptions.RegistroDuplicadoException;
import com.samuel.libraryapi.model.Autor;
import com.samuel.libraryapi.service.AutorService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AutorController {

    private final AutorService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody AutorDto autor) {
        try {
            var autorEntidade = autor.mapearParaAutor();
            service.salvar(autorEntidade);

            //http://localhost:8080/autores/2121212-dsds-23ds2-ds (valor do id)
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(autorEntidade.getId())
                    .toUri();

            return ResponseEntity.created(location).build();

        } catch (RegistroDuplicadoException e){
            var erroDto = ErroRespostaDto.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }

    @GetMapping("/{id}") //algoritmo pra obter os dados pelo id do autor
    public ResponseEntity<AutorDto> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        if(autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            AutorDto dto = new AutorDto(autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade());

            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}") // algoritmo para excluir autor
    public ResponseEntity<Object> excluir(@PathVariable("id") String id) {
        try{
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if(autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            service.deletar(autorOptional.get());

            return ResponseEntity.noContent().build();

        } catch (OperacaoNaoPermitidaException e) {
            var erroDto = ErroRespostaDto.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }

    }

    @GetMapping //algoritmo para pesquisar pelo nome ou nacionalidade, ou pelos dois
    public ResponseEntity<List<AutorDto>> pesquisarAutores(@RequestParam(value  = "nome",
                                                                       required = false) String nome,
                                                           @RequestParam(value = "nacionalidade",
                                                                        required = false) String nacionalidade) {
        List<Autor> resultado = service.pesquisarPeloNomeENacionalidade(nome, nacionalidade);
        List<AutorDto> lista = resultado
                .stream()
                .map(autor -> new AutorDto(autor.getId(),
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade())).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualziar(@PathVariable(name = "id") String id,
                                          @RequestBody AutorDto dto) {
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if(autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var autor = autorOptional.get();
            autor.setNome(dto.nome());
            autor.setNacionalidade(dto.nacionalidade());
            autor.setDataNascimento(dto.dataNascimento());

            service.atualizar(autor);

            return ResponseEntity.noContent().build();

        } catch (RegistroDuplicadoException e){
            var erroDto = ErroRespostaDto.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }

    }
}
