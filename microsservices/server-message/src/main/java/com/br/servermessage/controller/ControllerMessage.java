package com.br.servermessage.controller;


import com.br.servermessage.model.EmailModel;
import com.br.servermessage.service.InvokeUserServiceInterface;
import com.br.servermessage.service.ServiceMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/message")
public class ControllerMessage {
    @Autowired
    ServiceMessage serviceMessage;
    @Autowired
    InvokeUserServiceInterface invokeUserServiceInterface;

    @GetMapping(value="/user")
    public ResponseEntity<String> getRestponseEntity() {
        ResponseEntity<String> response = serviceMessage.getRestponseEntity();
       return ResponseEntity.ok(response.getBody());
    }

    @GetMapping()
    public ResponseEntity getInfoService() {
        return  ResponseEntity.ok("server to send messager");
    }

    @PostMapping()
    public ResponseEntity postTes() {
        return  ResponseEntity.ok("server received message");
    }


    @PostMapping(value = "send-email")
    public ResponseEntity<?> createEmail(@RequestBody @Valid EmailModel msg){
        ResponseEntity<?> response;
        Boolean send = serviceMessage.sendEmail(msg);
        if(send){
            response = new ResponseEntity<>("email enviado com sucesso!", HttpStatus.CREATED);
        }else{
            response = new ResponseEntity<>("erro ao tentar enviar email!", HttpStatus.BAD_REQUEST);
        }
        return response;
    }



}

