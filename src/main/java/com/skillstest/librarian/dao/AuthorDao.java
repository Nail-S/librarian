package com.skillstest.librarian.dao;

import com.skillstest.librarian.domain.model.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> getAll();
    List<Author> findByName(String name);
    List<Author> findByBookTitle(String title);
    Author getById(Long id);
    void update(Author author);
    void delete (Author author);
    void create(Author author);

}
