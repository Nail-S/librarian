package com.skillstest.librarian.dao;

import com.skillstest.librarian.domain.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    List<Author> getAll();
    List<Author> findByName(String name);
    List<Author> findByBookTitle(String title);
    Optional<Author> getById(Long id);
    Author update(Author author);
    void delete (Author author);
    Author create(Author author);
    List<Author> createAll(List<Author> authors);

}
