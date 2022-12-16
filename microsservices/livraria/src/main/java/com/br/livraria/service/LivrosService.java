package com.br.livraria.service;

import com.br.livraria.dto.LivroDto;
import com.br.livraria.model.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface LivrosService {
     UUID save(LivroDto livroDto);
     Page<LivroDto> findAll(Pageable pagination);

    LivroDto findById(UUID id);

     boolean delete(UUID id);

     boolean update(UUID id);
}
