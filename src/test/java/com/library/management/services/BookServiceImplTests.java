//package com.library.management.services;
//
//import com.library.management.TestDataUtil;
//import com.library.management.mappers.Mapper;
//import com.library.management.model.dto.BookDto;
//import com.library.management.model.entities.Book;
//import com.library.management.repositories.BookRepository;
//import com.library.management.services.impl.BookServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//public class BookServiceImplTests {
//
//    @Mock
//    private BookRepository bookRepository;
//
//    @Mock
//    private Mapper<Book, BookDto> bookMapper;
//
//    @InjectMocks
//    private  BookServiceImpl bookService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testSaveBook() {
//        BookDto inputBookDto = TestDataUtil.createTestBookDtoA();
//        Book savedBook = TestDataUtil.createTestBookEntityA();
//        Mockito.when(bookMapper.mapEntityFromDto(inputBookDto)).thenReturn(savedBook);
//        Mockito.when(bookRepository.save(savedBook)).thenReturn(savedBook);
//        Mockito.when(bookMapper.mapEntityToDto(savedBook)).thenReturn(inputBookDto);
//
////        BookDto result = bookService.save(inputBookDto);
//
////        Assertions.assertEquals(inputBookDto, result);
//        Mockito.verify(bookRepository, Mockito.times(1)).save(savedBook);
//    }
//
//    @Test
//    public void testFindAllBooks() {
//        List<Book> bookEntities = Arrays.asList(
//                TestDataUtil.createTestBookEntityA(),
//                TestDataUtil.createTestBookEntityB()
//        );
//        List<BookDto> expectedBookDtos = Arrays.asList(
//                TestDataUtil.createTestBookDtoA(),
//                TestDataUtil.createTestBookDtoB()
//        );
//        Mockito.when(bookRepository.findAll()).thenReturn(bookEntities);
//        Mockito.when(bookMapper.mapEntityToDto(Mockito.any(Book.class)))
//                .thenReturn(expectedBookDtos.get(0), expectedBookDtos.get(1));
//
//        List<BookDto> result = bookService.findAll();
//
//        Assertions.assertEquals(expectedBookDtos, result);
//        Mockito.verify(bookRepository, Mockito.times(1)).findAll();
//    }
//
//    @Test
//    public void testFindOneBookById() {
//        Long bookId = 1L;
//        Book book = TestDataUtil.createTestBookEntityA();
//        BookDto expectedBookDto = TestDataUtil.createTestBookDtoA();
//        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
//        Mockito.when(bookMapper.mapEntityToDto(book)).thenReturn(expectedBookDto);
//
//        Optional<BookDto> result = bookService.findOne(bookId);
//
//        Assertions.assertTrue(result.isPresent());
//        Assertions.assertEquals(expectedBookDto, result.get());
//        Mockito.verify(bookRepository, Mockito.times(1)).findById(bookId);
//    }
//
//    @Test
//    public void testFindOneBookByIdNotFound() {
//        Long bookId = 1L;
//        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
//
//        Optional<BookDto> result = bookService.findOne(bookId);
//
//        Assertions.assertTrue(result.isEmpty());
//        Mockito.verify(bookRepository, Mockito.times(1)).findById(bookId);
//    }
//
//    @Test
//    public void testIsBookExists() {
//        Long bookId = 1L;
//        Mockito.when(bookRepository.existsById(bookId)).thenReturn(true);
//
//        boolean result = bookService.isExists(bookId);
//
//        Assertions.assertTrue(result);
//        Mockito.verify(bookRepository, Mockito.times(1)).existsById(bookId);
//    }
//
//    @Test
//    public void testIsBookNotExists() {
//        Long bookId = 1L;
//        Mockito.when(bookRepository.existsById(bookId)).thenReturn(false);
//
//        boolean result = bookService.isExists(bookId);
//
//        Assertions.assertFalse(result);
//        Mockito.verify(bookRepository, Mockito.times(1)).existsById(bookId);
//    }
//
//    @Test
//    public void testIsIsbnExists() {
//        String isbn = "123456789";
//        Mockito.when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.of(TestDataUtil.createTestBookEntityA()));
//
//        boolean result = bookService.isExists(isbn);
//
//        Assertions.assertTrue(result);
//        Mockito.verify(bookRepository, Mockito.times(1)).findByIsbn(isbn);
//    }
//
//    @Test
//    public void testIsIsbnNotExists() {
//        String isbn = "123456789";
//        Mockito.when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.empty());
//
//        boolean result = bookService.isExists(isbn);
//
//        Assertions.assertFalse(result);
//        Mockito.verify(bookRepository, Mockito.times(1)).findByIsbn(isbn);
//    }
//
//    @Test
//    public void testUpdateBook() {
//        Long bookId = 1L;
//        BookDto updatedBookDto = TestDataUtil.createTestBookDtoB();
//        Book existingBook = TestDataUtil.createTestBookEntityA();
//        Book updatedBook = TestDataUtil.createTestBookEntityB();
//
//        Mockito.when(bookRepository.existsById(bookId)).thenReturn(true);
//        Mockito.when(bookMapper.mapEntityFromDto(updatedBookDto)).thenReturn(updatedBook);
//        Mockito.when(bookRepository.save(updatedBook)).thenReturn(updatedBook);
//        Mockito.when(bookMapper.mapEntityToDto(updatedBook)).thenReturn(updatedBookDto);
//
//        BookDto result = bookService.updateBook(bookId, updatedBookDto);
//
//        Assertions.assertEquals(updatedBookDto, result);
//        Mockito.verify(bookRepository, Mockito.times(1)).existsById(bookId);
//        Mockito.verify(bookRepository, Mockito.times(1)).save(updatedBook);
//    }
//
//    @Test
//    public void testUpdateBookNotFound() {
//        Long bookId = 1L;
//        BookDto updatedBookDto = TestDataUtil.createTestBookDtoB();
//
//        Mockito.when(bookRepository.existsById(bookId)).thenReturn(false);
//
//        Assertions.assertThrows(RuntimeException.class, () -> {
//            bookService.updateBook(bookId, updatedBookDto);
//        });
//
//        Mockito.verify(bookRepository, Mockito.times(1)).existsById(bookId);
//        Mockito.verify(bookRepository, Mockito.never()).save(Mockito.any(Book.class));
//    }
//
//    @Test
//    public void testDeleteBook() {
//        Long bookId = 1L;
//        Mockito.when(bookRepository.existsById(bookId)).thenReturn(true);
//
//        bookService.delete(bookId);
//
//        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(bookId);
//    }
//
//    @Test
//    public void testDeleteBookNotFound() {
//        Long bookId = 1L;
//        Mockito.when(bookRepository.existsById(bookId)).thenReturn(false);
//
//        Assertions.assertThrows(RuntimeException.class, () -> {
//            bookService.delete(bookId);
//        });
//
//        Mockito.verify(bookRepository, Mockito.times(1)).existsById(bookId);
//        Mockito.verify(bookRepository, Mockito.never()).deleteById(Mockito.anyLong());
//    }
//
//    @Test
//    public void testDeleteAllBooks() {
//        bookService.deleteAll();
//
//        Mockito.verify(bookRepository, Mockito.times(1)).deleteAll();
//    }
//
//    @Test
//    public void testIsBookBorrowedTrue() {
//        Long bookId = 1L;
//        Book borrowedBook = TestDataUtil.createTestBookEntityA();
//        borrowedBook.setBorrowed(true);
//        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(borrowedBook));
//
//        boolean result = bookService.isBorrowed(bookId);
//
//        Assertions.assertTrue(result);
//        Mockito.verify(bookRepository, Mockito.times(1)).findById(bookId);
//    }
//
//    @Test
//    public void testIsBookBorrowedFalse() {
//        Long bookId = 1L;
//        Book notBorrowedBook = TestDataUtil.createTestBookEntityA();
//        notBorrowedBook.setBorrowed(false);
//        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(notBorrowedBook));
//
//        boolean result = bookService.isBorrowed(bookId);
//
//        Assertions.assertFalse(result);
//        Mockito.verify(bookRepository, Mockito.times(1)).findById(bookId);
//    }
//}


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


