package com.skillstest.librarian.service;

import com.skillstest.librarian.domain.model.AuthorDto;
import com.skillstest.librarian.domain.model.BookDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
   List<AuthorDto> getAll();
   Optional<AuthorDto> createAuthor (AuthorDto author);
   Optional<List<AuthorDto>> createAll(List<AuthorDto> authors);
   Optional<AuthorDto> get(Long id);
   Optional<AuthorDto> addNewBookToAuthor (Long id, BookDto source);
   Optional<AuthorDto> updateAuthorById(Long byId, AuthorDto source);
   Optional<AuthorDto> addBookById (Long id, Long bookId);
   Optional<AuthorDto> removeBookById (Long id, Long bookId);
   Optional<AuthorDto> deleteAuthorById (Long id);
}
