package com.br.servermessage.service;

import com.br.servermessage.model.EmailModel;
import com.br.servermessage.model.StatusEmail;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;


@Service
public class ServiceMessage {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private JavaMailSender emailSender;

    @CircuitBreaker(name= "circuitBreakerService", fallbackMethod = "fallbackCircuitBreaker")
    @Bulkhead(name = "bulkheadService", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "fallbackBulkHead")
    @Retry(name = "retryService", fallbackMethod = "fallbackRetry")
    public ResponseEntity getRestponseEntity() {
        String entityUrl = "http://USER-SERVER/user";
        return restTemplate.getForEntity(entityUrl, String.class);
    }

    public ResponseEntity fallbackCircuitBreaker(Throwable error){
        return ResponseEntity.ok("test fallback circuitbreaker");
    }

    public ResponseEntity fallbackBulkHead(Throwable error){
        return ResponseEntity.ok("test fallback bulkheader");
    }

    public ResponseEntity fallbackRetry(Throwable error){
        return ResponseEntity.ok("test fallback retry");
    }


    public boolean sendEmail(EmailModel msg) {

        EmailModel email = new EmailModel();
        email.setSendDateEmail(LocalDateTime.now());
        Boolean send = null;

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getTitle());
            message.setText(email.getText());
            emailSender.send(message);
            email.setStatusEmail(StatusEmail.SEND);
            send = true;
        } catch (MailException e){
            send = false;
            email.setStatusEmail(StatusEmail.ERROR);
        }

        return send;
    }
}
