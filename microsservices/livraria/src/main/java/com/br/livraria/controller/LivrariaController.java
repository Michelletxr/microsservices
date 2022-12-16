package com.br.livraria.controller;

import com.br.livraria.dto.EscritorDto;
import com.br.livraria.dto.LivroDto;
import com.br.livraria.service.EscritorServiceImpl;
import com.br.livraria.service.LivrosServiceImpl;
import com.br.livraria.service.ServiceRestTemplate;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/lib")
public class LivrariaController {
    @Autowired
     LivrosServiceImpl serviceLivros;
    @Autowired
     ServiceRestTemplate serviceRestTemplate;
    @Autowired
    EscritorServiceImpl serviceEscritor;

    @Value("${server.port}")
    private int port;

    public LivrariaController(LivrosServiceImpl service, EscritorServiceImpl serviceEscritor) {
        this.serviceLivros = service;
        this.serviceEscritor = serviceEscritor;
    }

    @GetMapping
    @Cacheable("livros")
    public ResponseEntity<?> findAllLivros(@PageableDefault(size = 5, sort={"id"}, direction = Sort.Direction.DESC) Pageable pagination){

        ResponseEntity<?> response;
        Page<LivroDto> livrosDto = serviceLivros.findAll(pagination);
        response = new ResponseEntity<>(livrosDto, HttpStatus.OK);
        return response;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findByLivroId(@PathVariable UUID id){

        LivroDto livroDto =  serviceLivros.findById(id);
        ResponseEntity<Object> response;

        if(Objects.isNull(livroDto)){
            response = new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }else{
            response = new ResponseEntity<>(livroDto, HttpStatus.OK);
        }
        return response;
    }

    @GetMapping(value = "escritor")
    public ResponseEntity<Object> findEscritor(@RequestParam(required = false) UUID id_livro, @RequestParam(required = false)String nome_livro, Pageable pagination){

        EscritorDto escritor = null;
        ResponseEntity<Object> response;

       if(Objects.nonNull(id_livro)){
            escritor = serviceEscritor.findEscritorByLivroId(id_livro);
       } else if (Objects.nonNull(nome_livro)) {
           escritor = serviceEscritor.findEscritorByLivroName(nome_livro);
       }else{
           Page<EscritorDto> escritoresDto = serviceEscritor.findAll(pagination);
       }

       return new ResponseEntity<>(escritor, HttpStatus.OK);
    }


    @PostMapping
    @CacheEvict(value = {"livros"}, allEntries = true)
    public ResponseEntity<UUID> saveLivro(@RequestBody @Valid LivroDto livroDto){
        UUID livroId = serviceLivros.save(livroDto);
        ResponseEntity<UUID> response = new ResponseEntity<UUID>(livroId, HttpStatus.OK);
        return response;
    }

    @PostMapping(value = "escritor-cadastro")
    public ResponseEntity<UUID> saveEscritor(@RequestBody @Valid EscritorDto escritorDto) throws Exception {
        UUID EscritorId =serviceEscritor.save(escritorDto);
        ResponseEntity<UUID> response = new ResponseEntity<UUID>(EscritorId, HttpStatus.OK);
        return response;
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<?> updateLivro(@PathVariable UUID id,  @RequestBody LivroDto livroRequest){
        ResponseEntity<?> response;

        try{
            boolean isUpdate = serviceLivros.update(id);

            if(isUpdate){
                response = new ResponseEntity<>(id, HttpStatus.OK);
            }else{
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @PutMapping(value = "escritor/{id}")
    public ResponseEntity<?> updateEscritor(@PathVariable UUID id,  @RequestBody EscritorDto escritorRequest){
        ResponseEntity<?> response;

        try{
            boolean isUpdate = serviceEscritor.update(id);

            if(isUpdate){
                response = new ResponseEntity<>(id, HttpStatus.OK);
            }else{
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @DeleteMapping(value = "{id}")
    @CacheEvict(value = {"livros"}, allEntries = true)
    public ResponseEntity<?> deleteLivro(@PathVariable UUID id){

        ResponseEntity<?> response;
        boolean isDelet = serviceLivros.delete(id);

        if(!isDelet){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            response = new ResponseEntity<>(HttpStatus.OK);
        }

        return response;
    }

    @DeleteMapping(value = "escritor/{id}")
    public ResponseEntity<?> deleteEscritor(@PathVariable UUID id){

        ResponseEntity<?> response;
        boolean isDelet = serviceEscritor.delete(id);

        if(!isDelet){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            response = new ResponseEntity<>(HttpStatus.OK);
        }

        return response;
    }

    record BuyBook(String name, String user_name, String author){}


    @PostMapping(value = "/buy")
    public ResponseEntity buyBook(@RequestBody BuyBook request){
       return serviceRestTemplate.BuyBook(request.name, request.author, request.user_name);

    }

    @GetMapping("/port")
    public ResponseEntity getPort(){
        return serviceRestTemplate.getRestTemplateFunction();
    }

    public ResponseEntity fallbackCircuitBreaker(Throwable error){
        return new ResponseEntity("test fallback circuitbreaker", HttpStatus.BAD_REQUEST);
    }

}
