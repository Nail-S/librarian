package com.skillstest.librarian.repository;

import com.skillstest.librarian.data.entity.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
