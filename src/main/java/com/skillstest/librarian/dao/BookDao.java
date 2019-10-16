package com.skillstest.librarian.dao;

import com.skillstest.librarian.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    List<Book> getBooks();
    List<Book> getByTitle(String title);
    List<Book> getByAuthorName(String name);
    Optional<Book> getById(Long id);
    Book update(Book book);
    void delete (Book book);
    Book create(Book book);
    List<Book> createAll(List<Book> books);

}
