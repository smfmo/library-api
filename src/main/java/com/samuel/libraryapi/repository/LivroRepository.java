package com.samuel.libraryapi.repository;

import com.samuel.libraryapi.model.Autor;
import com.samuel.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
    //QUERY Method
    //select * from livro where id_autor = id
    List <Livro> findByAutor(Autor autor);

    //pesquisar o livro pelo titulo do livro
    //select * from livro where titulo = titulo
    List<Livro> findByTitulo(String titulo);

    //pesquisar o livro pelo Isbn
    //select * from livro where isbn = isbn
    List<Livro> findByIsbn(String isbn);

    //pesquisar o titulo e o preco
    //select * from livro where titulo = ? and preco = ?
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    //select * from livro where titulo = ? or isbn = ?
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);
}
