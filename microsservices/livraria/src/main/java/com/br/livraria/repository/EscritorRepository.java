package com.br.livraria.repository;
import com.br.livraria.model.Escritor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EscritorRepository extends JpaRepository<Escritor, UUID> {
    Optional<Escritor> findEscritorByLivroName(String name);
    Optional<Escritor> findEscritorByLivroId(UUID id);
}
