package com.samuel.libraryapi.repository;

import com.samuel.libraryapi.model.Autor;
import com.samuel.libraryapi.model.GeneroLivro;
import com.samuel.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;

    @Autowired
    public AutorRepositoryTest(AutorRepository autorRepository,
                               LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    @Test
    public void salvarTeste(){
        Autor autor = new Autor();
        autor.setNome("Ricardo");
        autor.setNacionalidade("americano");
        autor.setDataNascimento(LocalDate.of(2000, 6, 12));

        var autorSalvo = autorRepository.save(autor);
        System.out.println("Autor salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTeste(){
        var id = UUID.fromString("73ce0f63-552a-406a-bd0e-db024c7120d2");

        Optional<Autor> possivelAutor = autorRepository.findById(id);

        if(possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor:");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(2006, 6, 25));
            autorEncontrado.setNacionalidade("candango");

            autorRepository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTeste(){
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("contagem de autores " + autorRepository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("73ce0f63-552a-406a-bd0e-db024c7120d2");
        autorRepository.deleteById(id);
    }

    @Test
    public void deletarPorObjetoTest(){
        var id = UUID.fromString("83760a71-1d19-4ceb-8e7b-740c20f58baa");
        var samuel = autorRepository.findById(id).get();
        autorRepository.delete(samuel);
    }

    @Test
    public void salvarAutorComLivrosTest(){

        //cadastrar autor
        Autor autor = new Autor();
        autor.setNome("Romulo");
        autor.setNacionalidade("Espanhol");
        autor.setDataNascimento(LocalDate.of(2001, 8, 12));

        //cadastrar Livro
        Livro livro = new Livro();
        livro.setIsbn("9976-0099");
        livro.setTitulo("Estoicismo");
        livro.setDataPublicacao(LocalDate.of(2006, 12, 13));
        livro.setPreco(BigDecimal.valueOf(150));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setAutor(autor);

        //cadastrar mais um livro
        Livro livro2 = new Livro();
        livro2.setIsbn("12345-6789");
        livro2.setTitulo("A arte da perssuasão");
        livro2.setGenero(GeneroLivro.CIENCIA);
        livro2.setPreco(BigDecimal.valueOf(200));
        livro2.setDataPublicacao(LocalDate.of(2010, 10, 3));
        livro2.setAutor(autor);


        //lista de livros do autor
        autor.setLivro(new ArrayList<>());
        autor.getLivro().add(livro);
        autor.getLivro().add(livro2);

        autorRepository.save(autor);

        //livroRepository.saveAll(autor.getLivro());
    }

    @Test
    public void listarLivroAutor(){
        var id = UUID.fromString("43a3f3e4-9446-4c6d-b3e3-82d41d7f78f4");
        Autor autor = autorRepository.findById(id).get();

        //buscar os livros do autor
        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivro(livrosLista);

        autor.getLivro().forEach(System.out::println);
    }
}
