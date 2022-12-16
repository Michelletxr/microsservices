package com.br.livraria.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name="escritor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Escritor {
    @Id  @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = false, updatable = false)
    private String nome;

    @Column(unique = false, updatable = true)
    private int idade;

    @ManyToOne//muitos livros para um escritor
    @JoinColumn(name = "livro_id")
    private Livro livro; //lista de livros?


}
