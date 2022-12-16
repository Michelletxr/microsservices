package com.br.userserver.service;

import com.br.userserver.dto.UserDto;
import com.br.userserver.model.UserModel;
import com.br.userserver.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    UserDto userDto = new UserDto();
    public List<UserDto> findAll(){
        List<UserDto> responseList = new ArrayList<>();
        List<UserModel> list = userRepository.findAll();
        list.forEach(user -> responseList.add(userDto.buildUserToUserDto(user)));
        return  responseList;
    }

    public UUID save(UserDto userDto){
        UUID id = null;
        UserModel user = new UserModel();
        BeanUtils.copyProperties(userDto, user);
        UserModel userCreate = userRepository.save(user);
        if(Objects.nonNull(userCreate)){
            id = userCreate.getId();
        }
        return id;
    }

    public UserDto findById(UUID id){
        UserDto responseUser = new UserDto();
        Optional<UserModel> user = userRepository.findById(id);
        if(user.isPresent()){
            responseUser = userDto.buildUserToUserDto(user.get());
        }
        return responseUser;
    }

    public boolean delete(UUID id){
        Boolean delete = false;
        Optional<UserModel> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.delete(user.get());
            delete = true;
        }
        return delete;
    }

    public UserDto findByName(String name) {

        return userDto.buildUserToUserDto(userRepository.findByName(name));
    }
}
