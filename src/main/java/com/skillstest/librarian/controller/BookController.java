package com.skillstest.librarian.controller;

import com.skillstest.librarian.domain.model.AuthorDto;
import com.skillstest.librarian.domain.model.BookDto;
import com.skillstest.librarian.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getBooks () {
        List<BookDto> payload = bookService.getAll();
        ResponseEntity<List<BookDto>> response = new ResponseEntity<>(payload, HttpStatus.OK);
        return response;
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookDto> getBook (@PathVariable Long id) {
        Optional<BookDto> resultOptional = bookService.get(id);
        ResponseEntity<BookDto> response =  resultOptional.isPresent() ? new ResponseEntity<>(resultOptional.get(),
                HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }

    @PostMapping("/books")
    public ResponseEntity<BookDto> create(@RequestBody BookDto book) {
        Optional<BookDto> resultOptional = bookService.createBook(book);
        ResponseEntity<BookDto> response = resultOptional.isPresent() ? new ResponseEntity<>(resultOptional.get(),
                HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }

    @PostMapping("/books/all")
    public ResponseEntity<List<BookDto>> createAll(@RequestBody List<BookDto> books) {
        Optional<List<BookDto>> resultOptional = bookService.createAll(books);
        ResponseEntity<List<BookDto>> response = resultOptional.isPresent() ? new ResponseEntity<>(resultOptional.get(),
                HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }

    @PostMapping ("/books/{id}")
    public ResponseEntity<BookDto> addNewAuthorToBook (@PathVariable Long id, @RequestBody AuthorDto author){
        Optional<BookDto> resultOptional = bookService.addNewAuthorToBook(id, author);
        ResponseEntity<BookDto> response = resultOptional.isPresent() ? new ResponseEntity<>(resultOptional.get(),
                 HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<BookDto> updateBook (@PathVariable (name = "id") Long byId, @RequestBody BookDto source) {
        Optional<BookDto> resultOptional = bookService.updateBookById(byId, source);
        ResponseEntity<BookDto> response = resultOptional.isPresent() ? new ResponseEntity<>(resultOptional.get(),
                 HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }

    @PutMapping("/books/add/{id}/{authorId}")
    public ResponseEntity<BookDto> addAuthorById (@PathVariable Long id, @PathVariable Long authorId) {
        Optional<BookDto> resultOptional = bookService.addAuthorById(id, authorId);
        ResponseEntity<BookDto> response = resultOptional.isPresent() ? new ResponseEntity<>(resultOptional.get(),
                 HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }

    @PutMapping("/books/remove/{id}/{authorId}")
    public ResponseEntity<BookDto> removeAuthorById  (@PathVariable Long id, @PathVariable Long authorId) {
        Optional<BookDto> resultOptional = bookService.removeAuthorById(id, authorId);
        ResponseEntity<BookDto> response = resultOptional.isPresent() ? new ResponseEntity<>(resultOptional.get(),
                 HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return response;
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity deleteBookById (@PathVariable Long id) {
        Optional<BookDto> resultOptional = bookService.deleteBookById(id);
        ResponseEntity response = new ResponseEntity(resultOptional.isPresent() ? HttpStatus.NO_CONTENT : HttpStatus.ACCEPTED);
        return response;
    }
}
