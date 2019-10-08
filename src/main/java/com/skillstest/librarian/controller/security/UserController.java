package com.skillstest.librarian.controller.security;

import com.skillstest.librarian.data.security.User;
import com.skillstest.librarian.data.security.UserDto;
import com.skillstest.librarian.repository.security.UserRepository;
import com.skillstest.librarian.data.security.SecurityDomainDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private final UserRepository userRepository;
    private final SecurityDomainDtoConverter converter;

    @Autowired
    public UserController(UserRepository userRepository, SecurityDomainDtoConverter converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }

    @GetMapping ("/users")
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> persisted = (List<User>) userRepository.findAll();
        List<UserDto> payload = persisted.stream()
                .map(p -> converter.domainToDto(p))
                .collect(Collectors.toList());
        return new ResponseEntity<List<UserDto>>(payload, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<UserDto> get(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.isEmpty() ? new ResponseEntity<UserDto>( HttpStatus.NO_CONTENT) :
                new ResponseEntity<UserDto>(converter.domainToDto(userOptional.get()), HttpStatus.OK);
    }

    @PostMapping ("/users")
    @Transactional
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {
        User user = converter.dtoToDomain(dto);
        User persisted = userRepository.save(user);
        ResponseEntity<UserDto> response = new ResponseEntity<>(converter.domainToDto(persisted), HttpStatus.CREATED);
        return response;
    }

    @PutMapping("/users/{id}")
    @Transactional
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto dto){
        if (!userRepository.existsById(id)) return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        User user = converter.dtoToDomain(dto);
        user.setId(id);
        User persisted = userRepository.save(user);
        ResponseEntity<UserDto> response = new ResponseEntity<>(converter.domainToDto(persisted), HttpStatus.OK);
        return response;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUserById (@PathVariable Long id) {
        if (!userRepository.existsById(id)) return new ResponseEntity(HttpStatus.ACCEPTED);
        userRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
