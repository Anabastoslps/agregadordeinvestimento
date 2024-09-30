package com.example.agregadordeinvestimentos.service;

import com.example.agregadordeinvestimentos.Controller.CreateUserDTO;
import com.example.agregadordeinvestimentos.Controller.UpdateUserDto;
import com.example.agregadordeinvestimentos.models.User;
import com.example.agregadordeinvestimentos.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDTO createUserDTO){

        //dto converter para entity para usar o entity ali embaixo

       var entity = new User(
                UUID.randomUUID(),
                createUserDTO.username(),
                createUserDTO.email(),
                createUserDTO.password(),
                Instant.now(),//data de criação
                null); //data de finalização

        var userSaved = userRepository.save(entity);

        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId){

        return  userRepository.findById(UUID.fromString(userId));
    }
    public List<User>listusers(){
        return userRepository.findAll();
    }

    public void updateUserById(String userId,
                               UpdateUserDto updateUserDto){
        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()){
            var user = userEntity.get();

            if (updateUserDto.username() != null){
                user.setUsername(updateUserDto.username());
            }

            if (updateUserDto.password() != null){
                user.setPassword(updateUserDto.password());
        }
            userRepository.save(user);
    }

        userRepository.deleteById(id);
    }

    public void deleteById(String userId){
        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if (userExists){
            userRepository.deleteById(id);
        }
    }
}
