package com.skillstest.librarian.service;

import com.skillstest.librarian.domain.model.*;
import com.skillstest.librarian.repository.AuthorRepository;
import com.skillstest.librarian.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final EntityDomainDtoConverter converter;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, EntityDomainDtoConverter converter) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.converter = converter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAll() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .map(b -> converter.bookDomainToDto(b))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookDto> createBook(BookDto book) {
        Book persisted = bookRepository.save(converter.bookDtoToDomain(book));
        return Optional.of(converter.bookDomainToDto(persisted));
    }

    @Override
    public Optional<List<BookDto>> createAll(List<BookDto> books) {
        List<Book> received = books.stream()
                .map(b -> converter.bookDtoToDomain(b))
                .collect(Collectors.toList());
        List<BookDto> returned = StreamSupport.stream(bookRepository.saveAll(received).spliterator(), false)
                .map(b -> converter.bookDomainToDto(b))
                .collect(Collectors.toList());
         return Optional.of(returned);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> get(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (!bookOptional.isPresent()) return  Optional.empty();
        return Optional.of(converter.bookDomainToDto(bookOptional.get()));
    }

    @Override
    public Optional<BookDto> addNewAuthorToBook(Long id, AuthorDto source) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (!bookOptional.isPresent()) return Optional.empty();
        Book book = bookOptional.get();
        if (source == null) return Optional.of(converter.bookDomainToDto(book));
        Author author = authorRepository.save(converter.authorDtoToDomain(source));
        book.addAuthor(author);
        return Optional.of(converter.bookDomainToDto(bookRepository.save(book)));
    }

    @Override
    public Optional<BookDto> updateBookById(Long byId, BookDto source) {
        if (!bookRepository.existsById(byId)) return Optional.empty();
        Book book = converter.bookDtoToDomain(source);
        book.setId(byId);
        return Optional.of(converter.bookDomainToDto(bookRepository.save(book)));
    }

    @Override
    public Optional<BookDto> addAuthorById(Long id, Long authorId) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Optional<Author> authorOptional = authorRepository.findById(authorId);
            Book book = bookOptional.get();
            if (!authorOptional.isPresent()) return Optional.of(converter.bookDomainToDto(book));
            book.addAuthor(authorOptional.get());
            return Optional.of(converter.bookDomainToDto(bookRepository.save(book)));
        }
        return Optional.empty();
    }

    @Override
    public Optional<BookDto> removeAuthorById(Long id, Long authorId) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Optional<Author> authorOptional = authorRepository.findById(authorId);
            Book book = bookOptional.get();
            if (!authorOptional.isPresent()) return Optional.of(converter.bookDomainToDto(book));
            book.removeAuthor(authorOptional.get());
            return Optional.of(converter.bookDomainToDto(bookRepository.save(book)));
        }
        return Optional.empty();
    }

    @Override
    public Optional<BookDto> deleteBookById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            bookRepository.delete(bookOptional.get());
            return Optional.of(converter.bookDomainToDto(bookOptional.get()));
        }
        return Optional.empty();
    }
}
