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
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final EntityDomainDtoConverter converter;

    @Autowired
    public BookServiceImpl(BookDao bookDao, AuthorDao authorDao, EntityDomainDtoConverter converter) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.converter = converter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAll() {
        return bookDao.getBooks().stream()
                .map(b -> converter.bookDomainToDto(b))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getByTitle(String title) {
        return bookDao.getByTitle(title).stream()
                .map(b -> converter.bookDomainToDto(b))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getByAuthorName(String name) {
        return bookDao.getByAuthorName(name).stream()
                .map(b -> converter.bookDomainToDto(b))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookDto> createBook(BookDto book) {
        Book persisted = bookDao.update(converter.bookDtoToDomain(book));
        return Optional.of(converter.bookDomainToDto(persisted));
    }

    @Override
    public List<BookDto> createAll(List<BookDto> books) {
        List<Book> received = books.stream()
                .map(b -> converter.bookDtoToDomain(b))
                .collect(Collectors.toList());
        List<BookDto> persisted = bookDao.createAll(received).stream()
                .map(b -> converter.bookDomainToDto(b))
                .collect(Collectors.toList());
         return persisted;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> get(Long id) {
        Optional<Book> bookOptional = bookDao.getById(id);
        if (!bookOptional.isPresent()) return  Optional.empty();
        return Optional.of(converter.bookDomainToDto(bookOptional.get()));
    }

    @Override
    public Optional<BookDto> addNewAuthorToBook(Long id, AuthorDto source) {
        Optional<Book> bookOptional = bookDao.getById(id);
        if (!bookOptional.isPresent()) return Optional.empty();
        Book book = bookOptional.get();
        if (source == null) return Optional.of(converter.bookDomainToDto(book));
        Author author = authorDao.update(converter.authorDtoToDomain(source));
        book.addAuthor(author);
        return Optional.of(converter.bookDomainToDto(bookDao.update(book)));
    }

    @Override
    public Optional<BookDto> updateBookById(Long byId, BookDto source) {
        Optional<Book> bookOptional = bookDao.getById(byId);
        if (!bookOptional.isPresent()) return Optional.empty();
        Book book = converter.bookDtoToDomain(source);
        book.setId(byId);
        return Optional.of(converter.bookDomainToDto(bookDao.update(book)));
    }

    @Override
    public Optional<BookDto> addAuthorById(Long id, Long authorId) {
        Optional<Book> bookOptional = bookDao.getById(id);
        if (bookOptional.isPresent()) {
            Optional<Author> authorOptional = authorDao.getById(authorId);
            Book book = bookOptional.get();
            if (!authorOptional.isPresent()) return Optional.of(converter.bookDomainToDto(book));
            book.addAuthor(authorOptional.get());
            return Optional.of(converter.bookDomainToDto(bookDao.update(book)));
        }
        return Optional.empty();
    }

    @Override
    public Optional<BookDto> removeAuthorById(Long id, Long authorId) {
        Optional<Book> bookOptional = bookDao.getById(id);
        if (bookOptional.isPresent()) {
            Optional<Author> authorOptional = authorDao.getById(authorId);
            Book book = bookOptional.get();
            if (!authorOptional.isPresent()) return Optional.of(converter.bookDomainToDto(book));
            book.removeAuthor(authorOptional.get());
            return Optional.of(converter.bookDomainToDto(bookDao.update(book)));
        }
        return Optional.empty();
    }

    @Override
    public Optional<BookDto> deleteBookById(Long id) {
        Optional<Book> bookOptional = bookDao.getById(id);
        if (bookOptional.isPresent()) {
            bookDao.delete(bookOptional.get());
            return Optional.of(converter.bookDomainToDto(bookOptional.get()));
        }
        return Optional.empty();
    }
}
