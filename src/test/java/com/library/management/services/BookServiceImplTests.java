package com.library.management.services;

import com.library.management.mappers.Mapper;
import com.library.management.model.dto.BookDto;
import com.library.management.model.entities.Book;
import com.library.management.repositories.BookRepository;
import com.library.management.services.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BookServiceImplTests {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private Mapper<Book, BookDto> bookMapper;

    private BookServiceImpl bookService;

    private Book book;
    private BookDto bookDto;

    @Captor
    private ArgumentCaptor<Book> bookCaptor;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository, bookMapper); // Manually instantiate BookServiceImpl

        book = new Book(1L, "The Shadow in the Attic", "Sila Sea", 2005, "9781234567890", false);
        bookDto = new BookDto(1L, "The Shadow in the Attic", "Sila Sea", 2005, "9781234567890", false);
    }

    @Test
    void testSave() {
        when(bookMapper.mapEntityFromDto(bookDto)).thenReturn(book);
        when(bookMapper.mapEntityToDto(book)).thenReturn(bookDto);
        when(bookRepository.save(book)).thenReturn(book);

        BookDto savedBookDto = bookService.save(bookDto);

        assertNotNull(savedBookDto);
        assertEquals(bookDto, savedBookDto);
        verify(bookMapper).mapEntityFromDto(bookDto);
        verify(bookMapper).mapEntityToDto(book);
        verify(bookRepository).save(bookCaptor.capture());
        assertFalse(bookCaptor.getValue().getBorrowed());
    }

    @Test
    void testFindAll() {
        List<Book> books = Arrays.asList(book);
        List<BookDto> bookDtos = Arrays.asList(bookDto);

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.mapEntityToDto(book)).thenReturn(bookDto);

        List<BookDto> result = bookService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bookDtos, result);
        verify(bookRepository).findAll();
        verify(bookMapper).mapEntityToDto(book);
    }

    @Test
    void testFindOne() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.mapEntityToDto(book)).thenReturn(bookDto);

        Optional<BookDto> foundBook = bookService.findOne(1L);

        assertTrue(foundBook.isPresent());
        assertEquals(bookDto, foundBook.get());
        verify(bookRepository).findById(1L);
        verify(bookMapper).mapEntityToDto(book);
    }

    @Test
    void testIsExistsById() {
        when(bookRepository.existsById(1L)).thenReturn(true);

        assertTrue(bookService.isExists(1L));
        verify(bookRepository).existsById(1L);
    }

    @Test
    void testIsExistsByIsbn() {
        when(bookRepository.findByIsbn("9781234567890")).thenReturn(Optional.of(book));

        assertTrue(bookService.isExists("9781234567890"));
        verify(bookRepository).findByIsbn("9781234567890");
    }

    @Test
    void testUpdateBook() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookMapper.mapEntityFromDto(bookDto)).thenReturn(book);
        when(bookMapper.mapEntityToDto(book)).thenReturn(bookDto);
        when(bookRepository.save(book)).thenReturn(book);

        BookDto updatedBookDto = bookService.updateBook(1L, bookDto);

        assertNotNull(updatedBookDto);
        assertEquals(bookDto, updatedBookDto);
        verify(bookRepository).existsById(1L);
        verify(bookMapper).mapEntityFromDto(bookDto);
        verify(bookMapper).mapEntityToDto(book);
        verify(bookRepository).save(bookCaptor.capture());
        assertEquals(1L, bookCaptor.getValue().getId());
    }

    @Test
    void testDelete() {
        when(bookRepository.existsById(1L)).thenReturn(true);

        bookService.delete(1L);

        verify(bookRepository).existsById(1L);
        verify(bookRepository).deleteById(1L);
    }

    @Test
    void testDeleteAll() {
        bookService.deleteAll();

        verify(bookRepository).deleteAll();
    }

    @Test
    void testIsBorrowed() {
        // Given
        book.setBorrowed(true); // Ensure the book is marked as borrowed
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // When
        boolean result = bookService.isBorrowed(1L);

        // Then
        assertTrue(result); // Verify the result is true
        verify(bookRepository).findById(1L);
    }
}


