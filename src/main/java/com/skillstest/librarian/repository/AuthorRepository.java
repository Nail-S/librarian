package com.skillstest.librarian.repository;

import com.skillstest.librarian.data.entity.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
