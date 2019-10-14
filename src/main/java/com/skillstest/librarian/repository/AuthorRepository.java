package com.skillstest.librarian.repository;

import com.skillstest.librarian.domain.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
