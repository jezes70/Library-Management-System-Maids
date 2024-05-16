package com.library.management.services.impl;

import com.library.management.mappers.Mapper;
import com.library.management.model.dto.BookDto;
import com.library.management.model.entities.Book;
import com.library.management.repositories.BookRepository;
import com.library.management.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {


    private final BookRepository bookRepository;

    private final Mapper<Book, BookDto> bookMapper;


    @Override
    @CacheEvict(value = "bookList", allEntries = true)
    public BookDto save(BookDto bookDto) {
        Book book = bookMapper.mapEntityFromDto(bookDto);
        book.setBorrowed(false);

        return  bookMapper.mapEntityToDto(bookRepository.save(book));
    }

    @Override
    @Cacheable(value = "bookList", key = "#root.methodName")
    public List<BookDto> findAll() {
        List<Book> books =
                StreamSupport.stream(
                        bookRepository.findAll().spliterator(), false
                        ).toList();

        return books.stream()
                .map(bookEntity -> bookMapper.mapEntityToDto(bookEntity))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "findBookById", key = "#id")
    public Optional<BookDto> findOne(Long id) {
        Optional<Book> foundBook = bookRepository.findById(id);

        return foundBook.map(bookEntity -> bookMapper.mapEntityToDto(bookEntity));
    }

    @Override
    public boolean isExists(Long id) {
        return bookRepository.existsById(id);
    }

    @Override
    public boolean isExists(String isbn) {
        return bookRepository.findByIsbn(isbn).isPresent();
    }

    @Override
    @CacheEvict(value = {"bookList", "findBookById"}, allEntries = true)
    public BookDto updateBook(Long id, BookDto bookDto) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book does not exist");
        }

        Book book = bookMapper.mapEntityFromDto(bookDto);
        book.setId(id);
        return  bookMapper.mapEntityToDto(bookRepository.save(book));
    }

    @Override
    @CacheEvict(value = {"bookList", "findBookById"}, allEntries = true)
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book does not exist");
        }

        bookRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value = {"bookList", "findBookById"}, allEntries = true)
    public void deleteAll() {
        bookRepository.deleteAll();
    }

    @Override
    public boolean isBorrowed(Long bookId){
        Optional<Book> bookEntity = bookRepository.findById(bookId);
        return bookEntity.isPresent() && bookEntity.get().getBorrowed() == true;

    }
}


