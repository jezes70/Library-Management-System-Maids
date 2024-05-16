//package com.library.management.services;
//
//import com.library.management.TestDataUtil;
//import com.library.management.mappers.Mapper;
//import com.library.management.model.dto.BorrowingRecordDto;
//import com.library.management.model.entities.Book;
//import com.library.management.model.entities.BorrowingRecord;
//import com.library.management.model.entities.Patron;
//import com.library.management.repositories.BookRepository;
//import com.library.management.repositories.BorrowingRecordRepository;
//import com.library.management.repositories.PatronRepository;
//import com.library.management.services.impl.BorrowingRecordServiceImpl;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
////@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//public class BorrowingRecordServiceImplTests {
//
//    @Mock
//    private BorrowingRecordRepository borrowingRecordRepository;
//
//    @Mock
//    private Mapper<BorrowingRecord, BorrowingRecordDto> borrowingRecordMapper;
//
//    @Mock
//    private BookRepository bookRepository;
//
//    @Mock
//    private PatronRepository patronRepository;
//
//    @InjectMocks
//    private BorrowingRecordServiceImpl borrowingRecordService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @Transactional
//    public void testBorrowBook() {
//        Long bookId = 1L;
//        Long patronId = 667L;
//
//        Book book = TestDataUtil.createTestBookEntityA();
//        Patron patron = TestDataUtil.createTestPatronEntityA();
//        BorrowingRecord borrowingRecord = TestDataUtil.createTestBorrowingRecordEntityA();
//        BorrowingRecordDto expectedBorrowingRecordDto = TestDataUtil.createTestBorrowingRecordDtoA();
//
//        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
//        Mockito.when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
//
//        Mockito.when(borrowingRecordMapper.mapEntityToDto(Mockito.any()))
//                .thenReturn(expectedBorrowingRecordDto);
//        bookRepository.save(book);
//
//        BorrowingRecordDto result = borrowingRecordService.borrowBook(bookId, patronId);
//
//        Assertions.assertEquals(expectedBorrowingRecordDto, result);
//
//        Mockito.verify(bookRepository, Mockito.times(1)).save(book);
//        Mockito.verify(borrowingRecordRepository, Mockito.times(1)).save(Mockito.any(BorrowingRecord.class));
//    }
//
//    @Test
//    public void testReturnBook() {
//        Long bookId = 1L;
//        Long patronId = 667L;
//
//        Book book = TestDataUtil.createTestBookEntityA();
//        Patron patron = TestDataUtil.createTestPatronEntityA();
//        BorrowingRecord borrowingRecord = TestDataUtil.createTestBorrowingRecordEntityA();
//        BorrowingRecordDto expectedBorrowingRecordDto = TestDataUtil.createTestBorrowingRecordDtoA();
//
//        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
//        Mockito.when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
//        Mockito.when(borrowingRecordRepository.findByBookAndPatronAndReturnDateIsNull(book, patron))
//                .thenReturn(Optional.of(borrowingRecord));
//        Mockito.when(borrowingRecordMapper.mapEntityToDto(Mockito.any()))
//                .thenReturn(expectedBorrowingRecordDto);
//
//        BorrowingRecordDto result = borrowingRecordService.returnBook(bookId, patronId);
//        System.out.println("result = "+result);
//
//        Assertions.assertEquals(expectedBorrowingRecordDto, result);
//        Mockito.verify(bookRepository, Mockito.times(1)).save(book);
//        Mockito.verify(borrowingRecordRepository, Mockito.times(1)).save(borrowingRecord);
//    }
//
//    @Test
//    public void testReturnBookRecordNotFound() {
//        Long bookId = 1L;
//        Long patronId = 1L;
//
//        Book book = TestDataUtil.createTestBookEntityA();
//        Patron patron = TestDataUtil.createTestPatronEntityA();
//
//        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
//        Mockito.when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
//        Mockito.when(borrowingRecordRepository.findByBookAndPatronAndReturnDateIsNull(book, patron))
//                .thenReturn(Optional.empty());
//
//        Assertions.assertThrows(RuntimeException.class, () -> {
//            borrowingRecordService.returnBook(bookId, patronId);
//        });
//
//        Mockito.verify(bookRepository, Mockito.never()).save(Mockito.any(Book.class));
//        Mockito.verify(borrowingRecordRepository, Mockito.never()).save(Mockito.any(BorrowingRecord.class));
//    }
//
//    @Test
//    public void testDeleteAllBorrowingRecords() {
//        borrowingRecordService.deleteAll();
//
//        Mockito.verify(borrowingRecordRepository, Mockito.times(1)).deleteAll();
//    }
//}


package com.library.management.services;

import com.library.management.TestDataUtil;
import com.library.management.mappers.Mapper;
import com.library.management.model.dto.BorrowingRecordDto;
import com.library.management.model.entities.Book;
import com.library.management.model.entities.BorrowingRecord;
import com.library.management.model.entities.Patron;
import com.library.management.repositories.BookRepository;
import com.library.management.repositories.BorrowingRecordRepository;
import com.library.management.repositories.PatronRepository;
import com.library.management.services.impl.BorrowingRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BorrowingRecordServiceImplTests {

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private Mapper<BorrowingRecord, BorrowingRecordDto> borrowingRecordMapper;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private BorrowingRecordServiceImpl borrowingRecordService;

    private Book book;
    private Patron patron;
    private BorrowingRecord borrowingRecord;
    private BorrowingRecordDto borrowingRecordDto;

    @Captor
    private ArgumentCaptor<BorrowingRecord> borrowingRecordCaptor;

    @BeforeEach
    void setUp() {
        book = TestDataUtil.createTestBookEntityA();
        patron = TestDataUtil.createTestPatronEntityA();
        borrowingRecord = TestDataUtil.createTestBorrowingRecordEntityA();
        borrowingRecordDto = TestDataUtil.createTestBorrowingRecordDtoA();
    }

    @Test
    @Transactional
    public void testBorrowBook() {
        Long bookId = 1L;
        Long patronId = 667L;

        Book book = TestDataUtil.createTestBookEntityA();
        Patron patron = TestDataUtil.createTestPatronEntityA();
        BorrowingRecord borrowingRecord = TestDataUtil.createTestBorrowingRecordEntityA();
        BorrowingRecordDto expectedBorrowingRecordDto = TestDataUtil.createTestBorrowingRecordDtoA();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        when(borrowingRecordMapper.mapEntityToDto(any())).thenReturn(expectedBorrowingRecordDto);

        BorrowingRecordDto result = borrowingRecordService.borrowBook(bookId, patronId);

        assertEquals(expectedBorrowingRecordDto, result);
        verify(bookRepository, times(1)).save(book);
        verify(borrowingRecordRepository, times(1)).save(any(BorrowingRecord.class));
    }

    @Test
    @Transactional
    void testReturnBook() {
        book.setBorrowed(true);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.findByBookAndPatronAndReturnDateIsNull(book, patron)).thenReturn(Optional.of(borrowingRecord));
        when(borrowingRecordMapper.mapEntityToDto(any(BorrowingRecord.class))).thenReturn(borrowingRecordDto);
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        BorrowingRecordDto result = borrowingRecordService.returnBook(1L, 1L);

        assertNotNull(result);
        assertEquals(borrowingRecordDto, result);
        verify(bookRepository).findById(1L);
        verify(patronRepository).findById(1L);
        verify(borrowingRecordRepository).findByBookAndPatronAndReturnDateIsNull(book, patron);
        verify(bookRepository).save(book);
        verify(borrowingRecordRepository).save(borrowingRecordCaptor.capture());
        assertFalse(borrowingRecordCaptor.getValue().getBook().getBorrowed());
        assertNotNull(borrowingRecordCaptor.getValue().getReturnDate());
    }

    @Test
    void testDeleteAll() {
        borrowingRecordService.deleteAll();
        verify(borrowingRecordRepository).deleteAll();
    }
}




