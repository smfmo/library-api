package com.samuel.libraryapi.repository;
import com.samuel.libraryapi.model.Autor;
import com.samuel.libraryapi.model.GeneroLivro;
import com.samuel.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */
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

    List<Livro> findByTituloOrIsbnOrderByTitulo(String titulo, String isbn);

    //select * from livro where data_publicacao between ? and ?
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    //utilizando JPQL (Java Persistence Query Language)

    //select l.* from livro as l order by l.titulo
    @Query("select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    /**
     * select a.*
     *     from livro l
     *     join autor as a on a.id = l.id_autor
     */
    @Query("select a from Livro l join l.autor a  ")
    List<Autor> listarAutoresDosLivros();

    //select distinct l.* from livro l
    @Query("select distinct l.titulo from Livro l ")
    List<String> listarNomesLivros();

    @Query("""
    select l.genero
    from Livro l
    join l.autor a
    where a.nacionalidade = 'Espanhol'
    order by l.genero
""")
    List<String> listarGenerosAutoresBrasileiros();

    //named parameters -> parametros nomeados
    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro>findByGenero(@Param("genero") GeneroLivro generoLivro,
                            @Param("paramOrdenacao") String nomePropriedade
    );

    //positional parameters
    @Query("select l from Livro l where l.genero =?1 order by ?2")
    List<Livro> findByGeneroPositionalParameters(
             GeneroLivro generoLivro,
             String nomePropriedade
    );

    @Modifying
    @Transactional
    @Query(" delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro genero);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate novaData);
}
