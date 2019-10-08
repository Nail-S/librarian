package com.skillstest.librarian.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "authors")
public class Author implements Serializable {
    private static final long serialVersionUID = -656268807894848826L;
    @Version
    @Column( name = "rec_version" )
    private long version;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column( name = "first_name" )
    private String firstName;
    @Column( name = "last_name" )
    private String lastName;
    @OneToMany(
            mappedBy = "pk.author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnoreProperties({"author", "authors"})
    //@Transient
    private Set<AuthorBook> books = new HashSet<>();

    protected Author() {
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

    public Set<AuthorBook> getBooks() {
        return books;
    }

    public void setBooks(Set<AuthorBook> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        AuthorBook author_book = new AuthorBook();
        author_book.setAuthor(this);
        author_book.setBook(book);
        books.add(author_book);
        book.getAuthors().add(author_book);
    }

    public void removeBook(Book book) {
        AuthorBook author_book = new AuthorBook();
        author_book.setAuthor(this);
        author_book.setBook(book);
        book.getAuthors().remove(author_book);
        books.remove(author_book);
        author_book.setAuthor(null);
        author_book.setBook(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return firstName.equals(author.firstName) &&
                lastName.equals(author.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
