package com.skillstest.librarian.dao;

import com.skillstest.librarian.domain.model.Book;

import java.util.List;

public interface BookDao {
    List<Book> getBooks();
    List<Book> getByTitle(String title);
    List<Book> getByAuthorName(String name);
    Book getById(Long id);
    void update(Book book);
    void delete (Book book);
    void create(Book book);


}
