package com.skillstest.librarian.repository.security;

import com.skillstest.librarian.data.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
  @Query(value = "SELECT * FROM USERS U WHERE U.EMAIL =:email", nativeQuery = true)
  User findUsersByEmail (@Param("email") String email);
}
