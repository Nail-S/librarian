package com.skillstest.librarian.controller;

import com.skillstest.librarian.data.entity.Author;
import com.skillstest.librarian.data.entity.AuthorDto;
import com.skillstest.librarian.data.entity.BookDto;
import com.skillstest.librarian.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDto>> getAuthors () {
        List<AuthorDto> payload = authorService.getAll();
        ResponseEntity<List<AuthorDto>> response = new ResponseEntity<>(payload, HttpStatus.OK);
        return response;
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor (@PathVariable Long id) {
        Optional<AuthorDto> resultOptional = authorService.get(id);
        ResponseEntity<AuthorDto> response =  resultOptional.isPresent() ? new ResponseEntity<>(resultOptional.get(),
                HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }
    @PostMapping("/authors")
    public ResponseEntity<AuthorDto> createAuthor (@RequestBody AuthorDto author) {
        Optional<AuthorDto> resultOptional = authorService.createAuthor(author);
        ResponseEntity<AuthorDto> response = resultOptional.isPresent() ? new ResponseEntity<>(resultOptional.get(),
                HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }

    @PostMapping("/authors/all")
    public ResponseEntity<List<AuthorDto>> createAll(@RequestBody List<AuthorDto> author) {
        Optional<List<AuthorDto>> resultOptional = authorService.createAll(author);
        ResponseEntity<List<AuthorDto>> response = resultOptional.isPresent() ? new ResponseEntity<>(resultOptional.get(),
                HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }

    @PostMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> addNewBookToAuthor (@PathVariable Long id, @RequestBody BookDto book) {
        Optional<AuthorDto> resultOptional = authorService.addNewBookToAuthor(id, book);
        ResponseEntity<AuthorDto> response = resultOptional.isPresent() ? new ResponseEntity<>(resultOptional.get(),
                HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> updateAuthor (@PathVariable(name = "id") Long byId, @RequestBody AuthorDto source) {
        Optional<AuthorDto> resultOptional = authorService.updateAuthorById(byId, source);
        ResponseEntity<AuthorDto> response = resultOptional.isPresent() ? new ResponseEntity<>(resultOptional.get(),
                HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }

    @PutMapping("/authors/add/{id}/{bookId}")
    public ResponseEntity<AuthorDto> addBookById (@PathVariable Long id, @PathVariable Long bookId) {
        Optional<AuthorDto> resultOptional = authorService.addBookById(id, bookId);
        ResponseEntity<AuthorDto> response = resultOptional.isPresent() ? new ResponseEntity<>(resultOptional.get(),
                HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }

    @PutMapping("/authors/remove/{id}/{bookId}")
    public ResponseEntity<AuthorDto> removeBookById  (@PathVariable Long id, @PathVariable Long bookId) {
        Optional<AuthorDto> resultOptional = authorService.removeBookById(id, bookId);
        ResponseEntity<AuthorDto> response = resultOptional.isPresent() ? new ResponseEntity<>(resultOptional.get(),
                HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity deleteAuthorById (@PathVariable Long id) {
        Optional<AuthorDto> resultOptional = authorService.deleteAuthorById(id);
        ResponseEntity response = new ResponseEntity(resultOptional.isPresent() ? HttpStatus.NO_CONTENT : HttpStatus.ACCEPTED);
        return response;
    }

}
