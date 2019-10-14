package com.skillstest.librarian.repository;

import com.skillstest.librarian.domain.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
