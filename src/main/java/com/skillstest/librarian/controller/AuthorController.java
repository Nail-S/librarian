package com.skillstest.librarian.controller;

import com.skillstest.librarian.domain.model.AuthorDto;
import com.skillstest.librarian.domain.model.BookDto;
import com.skillstest.librarian.service.AuthorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Log4j2
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDto>> getAuthors () {
        log.trace("All authors list was requested");
        List<AuthorDto> payload = authorService.getAll();
        ResponseEntity<List<AuthorDto>> response = new ResponseEntity<>(payload, HttpStatus.OK);
        return response;
    }

    @GetMapping("/authors/name")
    public ResponseEntity<List<AuthorDto>> findAuthorsByName (@RequestPart String name) {
        log.trace(String.format("An authors list by the name {0} was requested", name));
        List<AuthorDto> payload = authorService.getByName(name);
        ResponseEntity<List<AuthorDto>> response = new ResponseEntity<>(payload, HttpStatus.OK);
        return response;
    }

    @GetMapping("/authors/book")
    public ResponseEntity<List<AuthorDto>> findAuthorsByBookTitle (@RequestPart String title) {
        log.trace(String.format("An authors list by the book title {0} was requested", title));
        List<AuthorDto> payload = authorService.getByBookTitle(title);
        ResponseEntity<List<AuthorDto>> response = new ResponseEntity<>(payload, HttpStatus.OK);
        return response;
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor (@PathVariable Long id) {
        log.trace(String.format("An author by id {0} was requested", id));
        Optional<AuthorDto> resultOptional = authorService.get(id);
        ResponseEntity<AuthorDto> response =  resultOptional.isPresent() ? new ResponseEntity<>(resultOptional.get(),
                HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }

    @PostMapping("/authors")
    public ResponseEntity<AuthorDto> createAuthor (@RequestBody AuthorDto author) {
        log.trace(String.format("An author creation request was issued: {0} ", author));
        AuthorDto dtoAuthor = authorService.createAuthor(author);
        ResponseEntity<AuthorDto> response = dtoAuthor != null ? new ResponseEntity<>(dtoAuthor,
                HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }

    @PostMapping("/authors/all")
    public ResponseEntity<List<AuthorDto>> createAll(@RequestBody List<AuthorDto> authors) {
        log.trace(String.format("An authors list creation request was issued: {0} ", authors));
        List<AuthorDto> dtoAuthors = authorService.createAll(authors);
        ResponseEntity<List<AuthorDto>> response = dtoAuthors == null || dtoAuthors.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(dtoAuthors, HttpStatus.CREATED);
        return response;
    }

    @PostMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> addNewBookToAuthor (@PathVariable Long id, @RequestBody BookDto book) {
        log.trace(String.format("A new book creation request for the author with id {0} was issued: {1} ", id, book));
        Optional<AuthorDto> resultOptional = authorService.addNewBookToAuthor(id, book);
        ResponseEntity<AuthorDto> response = resultOptional.isPresent() ? new ResponseEntity<>(resultOptional.get(),
                HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> updateAuthor (@PathVariable(name = "id") Long byId, @RequestBody AuthorDto source) {
        log.trace(String.format("An author update request for the author with id {0} was issued: {1} ", byId, source));
        Optional<AuthorDto> resultOptional = authorService.updateAuthorById(byId, source);
        ResponseEntity<AuthorDto> response = resultOptional.isPresent() ? new ResponseEntity<>(resultOptional.get(),
                HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }

    @PutMapping("/authors/add/{id}/{bookId}")
    public ResponseEntity<AuthorDto> addBookById (@PathVariable Long id, @PathVariable Long bookId) {
        log.trace(String.format("An authors book list modification request for an author with the id {0} and a book id {1} was issued", id, bookId));
        Optional<AuthorDto> resultOptional = authorService.addBookById(id, bookId);
        ResponseEntity<AuthorDto> response = resultOptional.isPresent() ? new ResponseEntity<>(resultOptional.get(),
                HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }

    @PutMapping("/authors/remove/{id}/{bookId}")
    public ResponseEntity<AuthorDto> removeBookById  (@PathVariable Long id, @PathVariable Long bookId) {
        log.trace(String.format("A book remove request for an author with the id {0} and a book id {1} was issued", id, bookId));
        Optional<AuthorDto> resultOptional = authorService.removeBookById(id, bookId);
        ResponseEntity<AuthorDto> response = resultOptional.isPresent() ? new ResponseEntity<>(resultOptional.get(),
                HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity deleteAuthorById (@PathVariable Long id) {
        log.trace(String.format("An author with the id {0} was requested for deletion", id));
        Optional<AuthorDto> resultOptional = authorService.deleteAuthorById(id);
        ResponseEntity response = new ResponseEntity(resultOptional.isPresent() ? HttpStatus.NO_CONTENT : HttpStatus.ACCEPTED);
        return response;
    }

}
