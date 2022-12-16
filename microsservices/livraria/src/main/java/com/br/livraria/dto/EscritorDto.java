package com.br.livraria.dto;

import com.br.livraria.model.Escritor;
import com.br.livraria.model.Livro;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class EscritorDto {

    private UUID id;

    @NotEmpty @NotNull
    private String nome;

    private int idade;

    @NotEmpty @NotNull
    private UUID livro_id;

    public EscritorDto(Escritor escritor) {
        this.nome = escritor.getNome();
        this.idade = escritor.getIdade();
        this.livro_id = escritor.getLivro().getId();
        this.id = escritor.getId();
    }

    public Escritor buildEscritorDtoToEscritor(Livro livro){
        Escritor escritor = new Escritor();
        escritor.setNome(this.nome);
        escritor.setIdade(this.idade);
        escritor.setLivro(livro);
        return escritor;
    }

}
