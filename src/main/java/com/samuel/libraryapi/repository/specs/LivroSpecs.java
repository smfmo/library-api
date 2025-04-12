package com.samuel.libraryapi.repository.specs;

import com.samuel.libraryapi.model.GeneroLivro;
import com.samuel.libraryapi.model.Livro;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn) {
        return ((root,
                 query,
                 criteriaBuilder) -> criteriaBuilder.equal(root.get("isbn"), isbn));
    }

    public static Specification<Livro> tituloLike(String titulo) {
        return ((root,
                 query,
                 criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("titulo")),
                "%" + titulo.toUpperCase() + "%"));
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero) {
        return (root,
                query,
                criteriaBuilder) -> criteriaBuilder.equal(root.get("genero"), genero);
    }



}
