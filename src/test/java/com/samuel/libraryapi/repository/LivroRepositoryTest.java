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
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

     private final LivroRepository livroRepository;
     private final AutorRepository autorRepository;

    @Autowired
    public LivroRepositoryTest(LivroRepository livroRepository,
                               AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    @Test
    public void salvarLivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("90876-9777");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Programação OO 2.0");
        livro.setDataPublicacao(LocalDate.of(2005, 7,18));

        Autor autor = autorRepository.findById(UUID.fromString("348dbb44-73ee-4dc4-90a3-7ddd41b02762"))
                        .orElse(null);

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    public void salvarAutorELivroTest(){
        //salva o autor
        Autor autor = new Autor();
        autor.setNome("Francisca");
        autor.setNacionalidade("Americano");
        autor.setDataNascimento(LocalDate.of(1990, 10, 12));

        autorRepository.save(autor);

        //salva o livro
        Livro livro = new Livro();
        livro.setIsbn("54345-9777");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("livro da Francisca");
        livro.setDataPublicacao(LocalDate.of(2015, 12,12));


        livro.setAutor(autor);

        livroRepository.save(livro);

        if (autor.getNome().equals("Jose")) {
            throw new RuntimeException("rollback");
        }
    }

    @Test
    public void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("90876-9777");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Programação OO 3.0");
        livro.setDataPublicacao(LocalDate.of(2005, 7,18));

        Autor autor = new Autor();
        autor.setNome("ricardo");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2000, 6, 12));

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    public void atualizarAutorDoLivro(){
        UUID id = UUID.fromString("607cb1b6-3382-43ff-8bd5-a217d0d667ec");
        var livroParaAtualizar = livroRepository.findById(id)
                .orElse(null);

        UUID autorId = UUID.fromString("5fb6dd41-ce12-48d6-b6e4-637d7cae65ce");
        Autor autor = autorRepository.findById(autorId)
                .orElse(null);

        livroParaAtualizar.setAutor(autor);

        livroRepository.save(livroParaAtualizar);
    }

    @Test
    public void deletarLivroTest(){
        UUID id = UUID.fromString("30ab84ea-7c83-4f30-82b8-128926262e9b");
        livroRepository.deleteById(id);
    }

    @Test
    public void deletarCascade(){
        UUID id = UUID.fromString("607cb1b6-3382-43ff-8bd5-a217d0d667ec");
        livroRepository.deleteById(id);
    }

    @Test
    @Transactional
    public void buscarLivroTest(){
        UUID id = UUID.fromString("03441f9c-8d3c-4104-859a-c0e4a4c35ca8");
        Livro livro = livroRepository.findById(id).orElse(null);
        System.out.println("Livro:");
        System.out.println(livro.getTitulo());

        //System.out.println("Autor:");
        //System.out.println(livro.getAutor().getNome());
    }

    @Test
    public void pesquisarLivroPeloTituloTest(){
        List<Livro> pesquisarLivro = livroRepository.findByTitulo("Estoicismo");
        pesquisarLivro.forEach(System.out::println);

    }

    @Test
    public void pesquisarPorIsbnTest(){
        List<Livro> pesquisarLivro = livroRepository.findByIsbn("12345-9777");
        pesquisarLivro.forEach(System.out::println);

    }

    @Test
    public void pesquisarPorTituloAndPrecoTest(){
        var preco = BigDecimal.valueOf(150.00);
        List<Livro> pesquisarLivro = livroRepository.findByTituloAndPreco("Estoicismo", preco);
        pesquisarLivro.forEach(System.out::println);

    }
    @Test
    public void pesquisarPorTituloOrPrecoTest(){
        List<Livro> pesquisarLivro = livroRepository.findByTituloOrIsbnOrderByTitulo("Estoicismo", "9976-0099");
        pesquisarLivro.forEach(System.out::println);
    }

    @Test
    public void listarLivrosComQueryJPQL(){
        var resultado = livroRepository.listarTodosOrdenadoPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }
    @Test
    public void listarAutoresDosLivrosComQueryJPQL(){
        var resultado = livroRepository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    public void listarTitulosNaoRepetidos(){
        var resultado = livroRepository.listarNomesLivros();
        resultado.forEach(System.out::println);
    }
    @Test
    public void listarGenerosDeLivrosAutoresBrasileiros(){
        var resultado = livroRepository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    public void listarPorGeneroQueryParam(){
        var resultado = livroRepository.findByGenero(
                GeneroLivro.CIENCIA,
                "dataPublicacao");
        resultado.forEach(System.out::println);
    }
    @Test
    public void listarPorGeneroPositionalParam(){
        var resultado = livroRepository.findByGeneroPositionalParameters(
                GeneroLivro.CIENCIA,
                "preco");
        resultado.forEach(System.out::println);
    }

    @Test
    public void deletePorGenero(){
        livroRepository.deleteByGenero(GeneroLivro.CIENCIA);
    }
    @Test
    public void atualizarDataPublicacao(){
        livroRepository.updateDataPublicacao(LocalDate.of(2000 ,1, 1));
    }
}