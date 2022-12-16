package com.br.livraria.repository;

import com.br.livraria.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;

public interface LivrosRepository extends JpaRepository<Livro, UUID> {
    Optional<Livro> findByName(String name);
}
