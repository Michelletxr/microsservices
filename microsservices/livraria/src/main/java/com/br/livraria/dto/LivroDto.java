package com.br.livraria.dto;


import com.br.livraria.model.Livro;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class LivroDto {

    private UUID id;
    @NotEmpty @NotNull
    private String name;

    @NotEmpty @NotNull
    private String genero;

    public LivroDto(Livro livro) {
        this.name = livro.getName();
        this.genero = livro.getGenero();
        this.id = livro.getId();
    }

    public Livro buildLivroDtoToLivro(){
        Livro livro = new Livro();
        livro.setGenero(this.genero);
        livro.setName(this.name);
        return livro;
    }
}
