package com.samuel.libraryapi.service;

import com.samuel.libraryapi.model.Autor;
import com.samuel.libraryapi.model.GeneroLivro;
import com.samuel.libraryapi.model.Livro;
import com.samuel.libraryapi.repository.AutorRepository;
import com.samuel.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;

    @Autowired
    public TransacaoService(AutorRepository autorRepository,
                            LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    @Transactional
    public void salvarLivroComFoto(){
        //salva o livro
        //repository.save(livro);

        //pega o id do livro = livro.getId();
        //var id = livro.getId();

        //salvar foto do livro -> bucket na nuvem
        //BucketService.salvar(livro.getFoto(), id + ".png")

        //atualizar o nome do arquivo que foi salvo
        //livro.setNomeArquivoFoto(id + "png");
        //repository.save(livro);

    }

    @Transactional
    public void ataulizacaoSemAtualizar(){
        var livro = livroRepository
                .findById(UUID
                        .fromString("03441f9c-8d3c-4104-859a-c0e4a4c35ca8"))
                .orElse(null);

        livro.setDataPublicacao(LocalDate.of(2024, 7, 1));
    }

    @Transactional
    public void executar(){
        //salva o autor
        Autor autor = new Autor();
        autor.setNome("teste Francisco");
        autor.setNacionalidade("Americano");
        autor.setDataNascimento(LocalDate.of(1990, 10, 12));

        autorRepository.saveAndFlush(autor);

        //salva o livro
        Livro livro = new Livro();
        livro.setIsbn("54345-9777");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("teste livro do Francisco");
        livro.setDataPublicacao(LocalDate.of(2015, 12,12));


        livro.setAutor(autor);

        livroRepository.saveAndFlush(livro);

        if (autor.getNome().equals("teste Francisco")) {
            throw new RuntimeException("rollback");
        }
    }
}
