package com.samuel.libraryapi.validator;

import com.samuel.libraryapi.exceptions.CampoInvalidoException;
import com.samuel.libraryapi.exceptions.RegistroDuplicadoException;
import com.samuel.libraryapi.model.Livro;
import com.samuel.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;
    private final LivroRepository livroRepository;

    public void validar(Livro livro) {
        if (existeLivroComIsbn(livro)) {
            throw new RegistroDuplicadoException("Isbn já cadastrado");
        }
        if (isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("preco", "Para livros com ano de publicção a partir de 2020 o preço é obrigatorio");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null &&
                livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;


    }

    private boolean existeLivroComIsbn(Livro livro) {
        Optional<Livro> livroEncontrado = livroRepository.findByIsbn(livro.getIsbn());

        if (livro.getId() ==null){
            return livroEncontrado.isPresent();
        }
        return livroEncontrado.map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));
    }
}
