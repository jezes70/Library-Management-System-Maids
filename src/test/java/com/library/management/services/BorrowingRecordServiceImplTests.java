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




