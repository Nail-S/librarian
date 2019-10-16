package com.skillstest.librarian.domain.model;

import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@ToString
public class AuthorDto implements Serializable {
    private static final long serialVersionUID = -162117000065679856L;

    private Long id;
    private String firstName;
    private String lastName;
    private Set<BookDto> books = new HashSet<>();

    public AuthorDto() {
    }

    public AuthorDto(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<BookDto> getBooks() {
        return books;
    }

    public void setBooks(Set<BookDto> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorDto)) return false;
        AuthorDto authorDto = (AuthorDto) o;
        return id.equals(authorDto.id) &&
                firstName.equals(authorDto.firstName) &&
                lastName.equals(authorDto.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
