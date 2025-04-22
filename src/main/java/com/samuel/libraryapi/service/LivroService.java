package com.samuel.libraryapi.service;

import com.samuel.libraryapi.model.GeneroLivro;
import com.samuel.libraryapi.model.Livro;
import com.samuel.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static com.samuel.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro) {
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisa(
            String isbn,
            String titulo,
            String nomeAutor,
            GeneroLivro genero,
            Integer AnoPublicacao){

        //select * from livro where isbn = :isbn and nomeAutor =
//        Specification<Livro> specification = Specification
//                .where(LivroSpecs.isbnEqual(isbn))
//                .and(LivroSpecs.tituloLike(titulo))
//                .and(LivroSpecs.generoEqual(genero));

        //select * from livro where 0 = 0
        Specification<Livro> specs = Specification
                .where((root,
                        query,
                        criteriaBuilder) -> criteriaBuilder.conjunction());

        if (isbn != null){
            // query = query and isbn = :isbn
            specs = specs.and(isbnEqual(isbn));
        }
        if (titulo != null){
            specs = specs.and(tituloLike(titulo));
        }
        if (genero != null){
            specs = specs.and(generoEqual(genero));
        }
        if (AnoPublicacao != null){
            specs = specs.and(anoPublicacaoEqual(AnoPublicacao));
        }
        if (nomeAutor != null){
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        return livroRepository.findAll(specs);
    }

    public void atualizar(Livro livro) {
        if (livro.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o livro esteja salvo na base");
        }

        livroRepository.save(livro);
    }

}
