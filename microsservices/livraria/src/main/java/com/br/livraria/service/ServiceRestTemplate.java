package com.br.livraria.service;

import com.br.livraria.model.Escritor;
import com.br.livraria.model.Livro;
import com.br.livraria.repository.EscritorRepository;
import com.br.livraria.repository.LivrosRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@Service
public class ServiceRestTemplate {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LivrosRepository livrosRepository;
    @Autowired
    EscritorRepository escritorRepository;
    @Autowired
    InvokeServiceFeign serviceFeign;


   @Bulkhead(name = "bulkheadService", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "fallbackBulkHead")
   @Retry(name = "retryService", fallbackMethod = "fallbackRetry")
    public ResponseEntity getRestTemplateUser(String name) {
        String entityUrl = "http://USER-SERVER/user/".concat(name);
        ResponseEntity response = restTemplate.getForEntity(entityUrl, String.class);
        return response;
    }

    public ResponseEntity getRestTemplateFunction() {
        String entityUrl = "http://CLOUD-SERVER/port/8080";
        return restTemplate.getForEntity(entityUrl, String.class);
    }



    public ResponseEntity fallbackCircuitBreaker(Throwable error){

        System.out.println("falback test");
        return new ResponseEntity("fallback circuitbreaker", HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity fallbackBulkHead(Throwable error){
        System.out.println("falback bulkehead");
       return ResponseEntity.ok("test fallback bulkheader");
    }

    public ResponseEntity fallbackRetry(Throwable error){
        System.out.println("falback retry");
        return ResponseEntity.ok("test fallback retry");
    }


    public boolean verifyAuthor(Livro livro, String author){
        boolean isValid = true;
        if(!Objects.isNull(author)){
            Optional<Escritor> escritor = escritorRepository.findEscritorByLivroName(livro.getName());
            if(escritor.isPresent()){
                if(Objects.equals(escritor.get().getNome(), author))  isValid = false;
            }
        }
        return isValid;
    }

    public boolean validateBuy(String user){
        boolean isValid = false;
        ResponseEntity response = this.getRestTemplateUser(user);
          if(response.getStatusCode() == HttpStatus.OK){
                serviceFeign.postDataMessege();
                isValid = true;
          }
          return isValid;
    }

    @CircuitBreaker(name= "circuitBreakerService", fallbackMethod = "fallbackCircuitBreaker")
    public ResponseEntity BuyBook(String name, String author, String user){
        Optional<Livro> livro = livrosRepository.findByName(name);
        ResponseEntity response = this.getRestTemplateUser(user);


        if(livro.isPresent()){
            if(verifyAuthor(livro.get(), author)){
                if(response.getStatusCode() == HttpStatus.OK){
                   serviceFeign.postDataMessege();
                }

            }
        }
        return response;
    }


}
