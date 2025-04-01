package com.samuel.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public")
@Getter //gera getters automáticos (lombok)
@Setter //gera setters automáticos (lombok)
@ToString(exclude = "livro")
@EntityListeners(AuditingEntityListener.class) //
public class Autor {
    //atributos
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome",
            length = 100,
            nullable = false)
    private String nome;

    @Column(name = "data_nascimento",
            nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade",
            length = 100,
            nullable = false)
    private String nacionalidade;

    @OneToMany(mappedBy = "autor",
            //cascade = CascadeType.ALL,
            fetch =  FetchType.LAZY)
    private List<Livro> livro;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;


    public Autor() {
        // para uso do framework
    }
}
