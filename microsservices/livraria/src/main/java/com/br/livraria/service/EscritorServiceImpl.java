package com.br.livraria.service;

import com.br.livraria.dto.EscritorDto;
import com.br.livraria.model.Escritor;
import com.br.livraria.model.Livro;
import com.br.livraria.repository.EscritorRepository;
import com.br.livraria.repository.LivrosRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class EscritorServiceImpl implements EscritorService {

    EscritorRepository repositoryEscritor;
    LivrosRepository repositoryLivro;
    @Override
    public UUID save(EscritorDto escritorDto) throws Exception {
        Optional<Livro> livro = repositoryLivro.findById(escritorDto.getLivro_id());
        UUID escritor_id = null;
        if(livro.isPresent()){
           Escritor escritor = repositoryEscritor.save(escritorDto.buildEscritorDtoToEscritor(livro.get()));
           escritor_id = escritor.getId();
        }else{
            throw new Exception("O livro informado não existe");
        }
        return escritor_id;
    }

    @Override
    public Page<EscritorDto> findAll(Pageable pagination) {
        Page<Escritor> escritor = repositoryEscritor.findAll(pagination);
        return  escritor.map(EscritorDto:: new);
    }

    @Override
    public EscritorDto findEscritorByLivroId(UUID id) {
        Optional<Escritor> escritor = repositoryEscritor.findEscritorByLivroId(id);
        if(escritor.isPresent()){
            return new EscritorDto(escritor.get());
        }
        return null;
    }

    @Override
    public EscritorDto findEscritorByLivroName(String livroNome) {
        Optional<Escritor> escritor = repositoryEscritor.findEscritorByLivroName(livroNome);
        if(escritor.isPresent()){
            return new EscritorDto(escritor.get());
        }
        return null;
    }

    @Override
    public boolean delete(UUID id) {
        boolean isDelete = false;
        Optional<Escritor> escritor = repositoryEscritor.findById(id);
        if(escritor.isPresent()){
            repositoryEscritor.delete(escritor.get());
            isDelete = true;
        }
        return isDelete;
    }

    @Override
    public boolean update(UUID id) {

        Optional<Escritor> escritor = repositoryEscritor.findById(id);
        boolean isUpdate = false;
        if(escritor.isPresent()){
            repositoryEscritor.save(escritor.get());
            isUpdate = true;
        }
        return isUpdate;
    }
}
