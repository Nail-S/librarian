package com.skillstest.librarian.domain.model;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
@Component
public class EntityDomainDtoConverter{

    public AuthorDto authorDomainToDto(Author source){
        if (source == null) return null;
        AuthorDto dto = new AuthorDto(source.getId(), source.getFirstName(), source.getLastName());
        Set<BookDto> books = source.getBooks().stream()
                .map(ab -> new BookDto(ab.getBook().getId(), ab.getBook().getTitle()))
                .collect(Collectors.toSet());
        dto.setBooks(books);
        return dto;
    }

    public Author authorDtoToDomain(AuthorDto source){
        if (source == null) return null;
        Author author = new Author();
        author.setId(source.getId());
        author.setFirstName(source.getFirstName());
        author.setLastName(source.getLastName());
        source.getBooks().stream()
                .forEach(b -> {
                    Book book = new Book();
                    book.setId(b.getId());
                    book.setTitle(b.getTitle());
                    author.addBook(book);
                });
        return author;
    }

    public BookDto bookDomainToDto(Book source){
       if (source == null) return null;
       BookDto book = new BookDto(source.getId(), source.getTitle());
       Set<AuthorDto> authors = source.getAuthors().stream()
               .map(ab -> ab.getAuthor())
               .map(a -> new AuthorDto(a.getId(), a.getFirstName(), a.getLastName()))
               .collect(Collectors.toSet());
       book.setAuthors(authors);
       return book;
    }

    public Book bookDtoToDomain(BookDto source){
        if (source == null) return null;
        Book book = new Book();
        book.setId(source.getId());
        book.setTitle(source.getTitle());
        source.getAuthors().stream()
                .forEach(a -> {
                    Author author = new Author();
                    author.setId(a.getId());
                    author.setFirstName(a.getFirstName());
                    author.setLastName(a.getLastName());
                    book.addAuthor(author);
                });
        return book;
    }
}
