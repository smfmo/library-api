package com.samuel.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public")
@Getter //gera getters automáticos (lombok)
@Setter //gera setters automáticos (lombok)
@ToString(exclude = "livro")
public class Autor {
    //atributos
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimeto", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch =  FetchType.LAZY)
    private List<Livro> livro;

    public Autor() {
        // para uso do framework
    }
}
