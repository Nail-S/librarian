package com.skillstest.librarian.data.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "authors_books")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@AssociationOverrides({
        @AssociationOverride(name = "pk.author",
                joinColumns = @JoinColumn(name = "AUTHOR_ID")),
        @AssociationOverride(name = "pk.book",
                joinColumns = @JoinColumn(name = "BOOK_ID")) })
public class AuthorBook implements Serializable {
    private static final long serialVersionUID = 7311932875370477934L;
    /**
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="author_id")
    private Author author;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
    private Book book;
    **/
    @EmbeddedId
    private AuthorBookId pk = new AuthorBookId();

    public AuthorBook() {
    }

    public AuthorBookId getPk() {
        return pk;
    }

    public void setPk(AuthorBookId pk) {
        this.pk = pk;
    }
    //@Transient
    public Author getAuthor() {
        return pk.getAuthor();
    }

    public void setAuthor(Author author) {
        pk.setAuthor(author);
    }
    //@Transient
    public Book getBook() {
        return pk.getBook();
    }

    public void setBook(Book book) {
       pk.setBook(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorBook)) return false;
        AuthorBook that = (AuthorBook) o;
        return pk.equals(that.pk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk);
    }
}
