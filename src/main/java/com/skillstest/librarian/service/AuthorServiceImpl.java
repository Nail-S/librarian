package com.skillstest.librarian.service;

import com.skillstest.librarian.data.entity.*;
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
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final EntityDomainDtoConverter converter;
    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository, EntityDomainDtoConverter converter) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.converter = converter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> getAll() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false)
                .map(a -> converter.authorDomainToDto(a))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorDto> createAuthor(AuthorDto author) {
        Author dAuthor = converter.authorDtoToDomain(author);
        AuthorDto dto = converter.authorDomainToDto(dAuthor);
        return Optional.of(dto);
    }

    @Override
    public Optional<List<AuthorDto>> createAll(List<AuthorDto> authors) {
        List<Author> converted = authors.stream()
                .map(a -> converter.authorDtoToDomain(a))
                .collect(Collectors.toList());
        List<AuthorDto> reverted = ((List<Author> ) authorRepository.saveAll(converted)).stream()
                .map(a -> converter.authorDomainToDto(a))
                .collect(Collectors.toList());
        return Optional.of(reverted);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorDto> get(Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        return authorOptional.isPresent() ?
                Optional.of(converter.authorDomainToDto(authorOptional.get())) :
                Optional.empty();
    }

    @Override
    public Optional<AuthorDto> addNewBookToAuthor(Long id, BookDto source) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (!authorRepository.existsById(source.getId())) return Optional.empty();
        Author pAuthor = authorOptional.get();
        Book book = bookRepository.save(converter.bookDtoToDomain(source));
        pAuthor.addBook(book);
        Author persisted = authorRepository.save(pAuthor);
        return Optional.of(converter.authorDomainToDto(persisted));
    }

    @Override
    public Optional<AuthorDto> updateAuthorById(Long byId, AuthorDto source) {
        if (!authorRepository.existsById(byId)) return Optional.empty();
        Author author = converter.authorDtoToDomain(source);
        author.setId(byId);
        Author persisted = authorRepository.save(author);
        return Optional.of(converter.authorDomainToDto(persisted));
    }

    @Override
    public Optional<AuthorDto> addBookById(Long id, Long bookId) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            Optional<Book> bookOptional = bookRepository.findById(bookId);
            if (bookOptional.isPresent()) {
                Author author = authorOptional.get();
                author.addBook(bookOptional.get());
                author = converter.authorDtoToDomain(converter.authorDomainToDto(author));
                return Optional.of(converter.authorDomainToDto(authorRepository.save(author)));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<AuthorDto> removeBookById(Long id, Long bookId) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (!authorOptional.isPresent()) {
            Optional<Book> optionalBook = authorOptional.get().getBooks().stream()
                    .map(ab -> ab.getBook())
                    .filter(b -> b.getId().equals(bookId))
                    .findFirst();
            if (optionalBook.isPresent()) {
                Author author = authorOptional.get();
                author.removeBook(optionalBook.get());
                return Optional.of(converter.authorDomainToDto(authorRepository.save(author)));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<AuthorDto> deleteAuthorById(Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            authorRepository.delete(authorOptional.get());
            return Optional.of(converter.authorDomainToDto(authorOptional.get()));
        }
        return Optional.empty();
    }
}
