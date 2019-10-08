package com.skillstest.librarian.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book implements Serializable {
    private static final long serialVersionUID = 4951118115422210414L;
    @Version
    @Column( name = "rec_version" )
    private long version;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NaturalId
    private String title;
  //  @JsonBackReference
    @OneToMany(
            mappedBy = "pk.book",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    //@Transient
    @JsonIgnoreProperties({"book", "books"})
    private Set<AuthorBook> authors = new HashSet<>();

    protected Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<AuthorBook> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorBook> authors) {
        this.authors = authors;
    }

    public void addAuthor (Author author) {
        AuthorBook author_book = new AuthorBook();
        author_book.setAuthor(author);
        author_book.setBook(this);
        authors.add(author_book);
        author.getBooks().add(author_book);
    }

    public void removeAuthor (Author author) {
        AuthorBook author_book = new AuthorBook();
        author_book.setAuthor(author);
        author_book.setBook(this);
        author.getBooks().remove(author_book);
        authors.remove(author_book);
        author_book.setAuthor(null);
        author_book.setBook(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return title.equals(book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
