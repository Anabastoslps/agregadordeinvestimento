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
        var entity = new User(
                UUID.randomUUID(),
                createUserDTO.username(),
                createUserDTO.email(),
                createUserDTO.password(),
                Instant.now(),
                null
        );
        var userSaved = userRepository.save(entity);
        return userSaved.getUserId();
    }

    public Optional<User> getUserById(UUID userId){
        return userRepository.findById(userId);
    }

    public List<User> listusers(){
        return userRepository.findAll();
    }

    public void updateUserById(UUID userId, UpdateUserDto updateUserDto) {
        var userEntity = userRepository.findById(userId); // Correção aqui

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }

            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }
            userRepository.save(user);
        }
    }

    public void deleteById(UUID userId) {
        var userExists = userRepository.existsById(userId); // Correção aqui

        if (userExists) {
            userRepository.deleteById(userId);
        }
    }
}

