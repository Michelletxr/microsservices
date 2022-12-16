package com.br.livraria.service;

import com.br.livraria.dto.LivroDto;
import com.br.livraria.model.Escritor;
import com.br.livraria.model.Livro;
import com.br.livraria.repository.EscritorRepository;
import com.br.livraria.repository.LivrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.http.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

@Service
public class LivrosServiceImpl implements LivrosService {
    //injetando a classe
    @Autowired
    LivrosRepository livrosRepository;
    @Autowired
    EscritorRepository escritorRepository;


    @Override
    public UUID save(LivroDto livroDto){
        Livro livro = livroDto.buildLivroDtoToLivro();
         return livrosRepository.save(livro).getId();
    }


    @Override
    public boolean update(UUID id){

        Optional<Livro> livro = livrosRepository.findById(id);
        boolean isUpdate = false;
        if(livro.isPresent()){
            livrosRepository.save(livro.get());
            isUpdate = true;
        }
        return isUpdate;
    }

    @Override
    public Page<LivroDto> findAll(Pageable pagination) {

        Page<Livro> livros = livrosRepository.findAll(pagination);
        return  livros.map(LivroDto:: new);
    }

    @Override
    public LivroDto findById(UUID id) {

        LivroDto livroDto = null;
        Optional<Livro> livro = livrosRepository.findById(id);

        if(livro.isPresent()){
           livroDto = new LivroDto(livro.get());
        }

        return livroDto;
    }

    @Override
    public boolean delete(UUID id) {

        boolean isDelet;
        Optional<Livro> livro = livrosRepository.findById(id);

        if(!livro.isPresent()){
            isDelet = false;
        }else{
            livrosRepository.delete(livro.get());
            isDelet = true;
        }
         return isDelet;
    }

}
