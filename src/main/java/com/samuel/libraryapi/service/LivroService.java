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
            String nomeAutor,
            GeneroLivro genero,
            Integer AnoPublicacao){

        Specification<Livro> specification = null;
        return livroRepository.findAll(specification);
    }
}
