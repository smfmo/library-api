package com.samuel.libraryapi.controller;

import com.samuel.libraryapi.controller.dto.CadastroLivroDto;
import com.samuel.libraryapi.controller.dto.ResultadoPesquisaLivroDto;
import com.samuel.libraryapi.controller.mappers.LivroMapper;
import com.samuel.libraryapi.model.GeneroLivro;
import com.samuel.libraryapi.model.Livro;
import com.samuel.libraryapi.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
@Tag(name = "Livros")
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Salvar", description = "cadastrar novo livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastrado com sucesso!"),
            @ApiResponse(responseCode = "422", description = "Erro de validação."),
            @ApiResponse(responseCode = "409", description = "Livro já cadastrado.")
    })
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDto dto) {
        Livro livro = mapper.toEntity(dto);
        service.salvar(livro);

        var url = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(url).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Obter detalhes", description = "retorna os dados do Livro pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro encontrado!"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado!"),
    })
    public ResponseEntity<ResultadoPesquisaLivroDto> obterDetalhes(
            @PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDto(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Deletar", description = "Deleta um livro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado!"),
    })
    public ResponseEntity<Object> deletarLivro(@PathVariable("id") String id) {
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    service.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Pesquisar", description = "realiza pesquisa de livros por parâmetros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso!")
    })
    public ResponseEntity<Page<ResultadoPesquisaLivroDto>> pesquisa(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "nome-autor", required = false) String nomeAutor,
            @RequestParam(value = "genero", required = false) GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false) Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10") Integer tamanhoPagina) {

        Page<Livro> paginaResultado = service.pesquisa(
                isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);

        Page<ResultadoPesquisaLivroDto> resultado = paginaResultado.map(mapper::toDto);

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Atualizar", description = "atualiza um livro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "atualizado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "livro não encontrado!"),
            @ApiResponse(responseCode = "409", description = "livro já cadastrado!"),

    })
    public ResponseEntity<Object> atualizarLivro(@PathVariable("id") String id,
                                               @RequestBody @Valid CadastroLivroDto dto) {
       return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    Livro entidadeAux = mapper.toEntity(dto);
                    livro.setIsbn(entidadeAux.getIsbn());
                    livro.setTitulo(entidadeAux.getTitulo());
                    livro.setDataPublicacao(entidadeAux.getDataPublicacao());
                    livro.setGenero(entidadeAux.getGenero());
                    livro.setPreco(entidadeAux.getPreco());
                    livro.setAutor(entidadeAux.getAutor());

                    service.atualizar(livro);

                    return ResponseEntity.noContent().build();

                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
