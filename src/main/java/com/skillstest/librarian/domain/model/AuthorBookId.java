package com.skillstest.librarian.domain.model;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AuthorBookId implements Serializable {
    private static final long serialVersionUID = -1810826784977476748L;
    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    protected AuthorBookId(){
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorBookId)) return false;
        AuthorBookId that = (AuthorBookId) o;
        return author.equals(that.author) &&
                book.equals(that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, book);
    }
}
