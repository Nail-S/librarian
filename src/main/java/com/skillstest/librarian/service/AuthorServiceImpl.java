package com.skillstest.librarian.service;

import com.skillstest.librarian.dao.AuthorDao;
import com.skillstest.librarian.dao.BookDao;
import com.skillstest.librarian.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;
    private final BookDao bookDao;
    private final EntityDomainDtoConverter converter;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao, BookDao bookDao, EntityDomainDtoConverter converter) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.converter = converter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> getAll() {
        return authorDao.getAll().stream()
                .map(a -> converter.authorDomainToDto(a))
                .collect(Collectors.toList());
    }

    @Override
    public List<AuthorDto> getByName(String name) {
        return authorDao.findByName(name).stream()
                .map(a -> converter.authorDomainToDto(a))
                .collect(Collectors.toList());
    }

    @Override
    public List<AuthorDto> getByBookTitle(String title) {
        return authorDao.findByBookTitle(title).stream()
                .map(a -> converter.authorDomainToDto(a))
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDto createAuthor(AuthorDto author) {
        Author dAuthor = converter.authorDtoToDomain(author);
        AuthorDto dto = converter.authorDomainToDto(authorDao.create(dAuthor));
        return dto;
    }

    @Override
    public List<AuthorDto> createAll(List<AuthorDto> authors) {
        List<Author> converted = authors.stream()
                .map(a -> converter.authorDtoToDomain(a))
                .collect(Collectors.toList());
        List<AuthorDto> persisted = authorDao.createAll(converted).stream()
                .map(a -> converter.authorDomainToDto(a))
                .collect(Collectors.toList());
        return persisted;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorDto> get(Long id) {
        Optional<Author> authorOptional = authorDao.getById(id);
        return authorOptional.isPresent() ?
                Optional.of(converter.authorDomainToDto(authorOptional.get())) :
                Optional.empty();
    }

    @Override
    public Optional<AuthorDto> addNewBookToAuthor(Long id, BookDto source) {
        Optional<Author> authorOptional = authorDao.getById(id);
        if (!authorOptional.isPresent()) return Optional.empty();
        Author pAuthor = authorOptional.get();
        Book book = bookDao.create(converter.bookDtoToDomain(source));
        pAuthor.addBook(book);
        Author persisted = authorDao.update(pAuthor);
        return Optional.of(converter.authorDomainToDto(persisted));
    }

    @Override
    public Optional<AuthorDto> updateAuthorById(Long byId, AuthorDto source) {
        Optional<Author> authorOptional = authorDao.getById(byId);
        if (!authorOptional.isPresent()) return Optional.empty();
        Author author = converter.authorDtoToDomain(source);
        author.setId(byId);
        Author persisted = authorDao.update(author);
        return Optional.of(converter.authorDomainToDto(persisted));
    }

    @Override
    public Optional<AuthorDto> addBookById(Long id, Long bookId) {
        Optional<Author> authorOptional = authorDao.getById(id);
        if (authorOptional.isPresent()) {
            Optional<Book> bookOptional = bookDao.getById(bookId);
            if (bookOptional.isPresent()) {
                Author author = authorOptional.get();
                author.addBook(bookOptional.get());
                author = converter.authorDtoToDomain(converter.authorDomainToDto(author));
                return Optional.of(converter.authorDomainToDto(authorDao.update(author)));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<AuthorDto> removeBookById(Long id, Long bookId) {
        Optional<Author> authorOptional = authorDao.getById(id);
        if (!authorOptional.isPresent()) {
            Optional<Book> optionalBook = authorOptional.get().getBooks().stream()
                    .map(ab -> ab.getBook())
                    .filter(b -> b.getId().equals(bookId))
                    .findFirst();
            if (optionalBook.isPresent()) {
                Author author = authorOptional.get();
                author.removeBook(optionalBook.get());
                return Optional.of(converter.authorDomainToDto(authorDao.update(author)));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<AuthorDto> deleteAuthorById(Long id) {
        Optional<Author> authorOptional = authorDao.getById(id);
        if (authorOptional.isPresent()) {
            authorDao.delete(authorOptional.get());
            return Optional.of(converter.authorDomainToDto(authorOptional.get()));
        }
        return Optional.empty();
    }
}
