package com.br.userserver.controller;

import com.br.userserver.dto.UserDto;
import com.br.userserver.model.UserModel;
import com.br.userserver.repository.UserRepository;
import com.br.userserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public ResponseEntity testServiceMsg(){
        return ResponseEntity.ok("message received");
    }


    @GetMapping(value = {"/{name}"})
    public ResponseEntity findByName(@PathVariable String name){
        ResponseEntity response = null;
        try {
            UserDto user = userService.findByName(name);
            if(!Objects.isNull(user)){
                response = new ResponseEntity(user, HttpStatus.OK);
            }else{
                response = new ResponseEntity(user, HttpStatus.NOT_FOUND);
            }
        }catch (RuntimeException e){
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody UserDto user){
        UUID userId = userService.save(user);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

}
