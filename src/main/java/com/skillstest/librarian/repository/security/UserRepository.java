package com.skillstest.librarian.repository.security;

import com.skillstest.librarian.domain.security.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
  @Query(value = "SELECT * FROM USERS U WHERE U.EMAIL =:email", nativeQuery = true)
  User findUsersByEmail (@Param("email") String email);
}
