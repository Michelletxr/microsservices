package com.br.livraria.service;

import com.br.livraria.dto.EscritorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EscritorService {

    UUID save(EscritorDto EscritorDto) throws Exception;
    Page<EscritorDto> findAll(Pageable pagination);

    EscritorDto findEscritorByLivroName(String livroNome);

    EscritorDto findEscritorByLivroId(UUID id);

    boolean delete(UUID id);

    boolean update(UUID id);
}
