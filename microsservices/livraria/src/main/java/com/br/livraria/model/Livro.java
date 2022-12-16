package com.br.livraria.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="livro")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Livro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//id gerado de forma automatica
    @Column(unique = true, updatable = false)
    private UUID id;

    @Column(unique = false, updatable = false)
    private String name;

    @Column(unique =    false, updatable = true)
    private String genero;

    //update na tabela livro para adicionar o campo

   /**@ManyToOne //muitos livros para um escritor
   @JoinColumn(name = "estritor_id")
    private Escritor autor;

   public Escritor getAutor() {
       return autor;
   }

   public void setAutor(Escritor autor) {
      this.autor = autor;
   } **/
}
