package com.skillstest.librarian.service;

import com.skillstest.librarian.domain.model.AuthorDto;
import com.skillstest.librarian.domain.model.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDto> getAll();
    List<BookDto> getByTitle(String title);
    List<BookDto> getByAuthorName(String name);
    Optional<BookDto> createBook(BookDto book);
    List<BookDto> createAll(List<BookDto> books);
    Optional<BookDto> get(Long id);
    Optional<BookDto> addNewAuthorToBook(Long id, AuthorDto source);
    Optional<BookDto> updateBookById(Long byId, BookDto source);
    Optional<BookDto> addAuthorById(Long id, Long authorId);
    Optional<BookDto> removeAuthorById(Long id, Long authorId);
    Optional<BookDto> deleteBookById(Long id);
}
