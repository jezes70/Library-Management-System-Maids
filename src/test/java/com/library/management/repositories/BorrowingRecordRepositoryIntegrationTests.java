package com.library.management.repositories;

import com.library.management.TestDataUtil;
import com.library.management.model.entities.Book;
import com.library.management.model.entities.BorrowingRecord;
import com.library.management.model.entities.Patron;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BorrowingRecordRepositoryIntegrationTests {

    private BorrowingRecordRepository underTest;
    private BookRepository bookRepository;
    private PatronRepository patronRepository;

    @Autowired
    public BorrowingRecordRepositoryIntegrationTests(
            BorrowingRecordRepository underTest,
            BookRepository bookRepository,
            PatronRepository patronRepository) {
        this.underTest = underTest;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
    }

    @BeforeEach
    public void setUp() {
        underTest.deleteAll();
        bookRepository.deleteAll();
        patronRepository.deleteAll();
    }

    @Test
    public void testThatBorrowingRecordCanBeCreatedAndRecalled() {
        Book book = TestDataUtil.createTestBookEntityB();
        Patron patron = TestDataUtil.createTestPatronEntityA();
        bookRepository.save(book);
        patronRepository.save(patron);

        BorrowingRecord borrowingRecord = BorrowingRecord.builder()
                .book(book)
                .patron(patron)
                .borrowingDate(LocalDate.now())
                .build();

        underTest.save(borrowingRecord);
        Optional<BorrowingRecord> result = underTest.findById(borrowingRecord.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(borrowingRecord);
    }

    @Test
    public void testThatBorrowingRecordCanBeUpdated() {
        Book book = TestDataUtil.createTestBookEntityA();
        Patron patron = TestDataUtil.createTestPatronEntityA();
        bookRepository.save(book);
        patronRepository.save(patron);

        BorrowingRecord borrowingRecord = BorrowingRecord.builder()
                .book(book)
                .patron(patron)
                .borrowingDate(LocalDate.now())
                .build();
        underTest.save(borrowingRecord);

        borrowingRecord.setReturnDate(LocalDate.now());
        underTest.save(borrowingRecord);

        Optional<BorrowingRecord> result = underTest.findById(borrowingRecord.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(borrowingRecord);
    }

    @Test
    public void testThatAllBorrowingRecordsCanBeDeleted() {
        Book book = TestDataUtil.createTestBookEntityA();
        Patron patron = TestDataUtil.createTestPatronEntityA();
        bookRepository.save(book);
        patronRepository.save(patron);

        BorrowingRecord borrowingRecord = BorrowingRecord.builder()
                .book(book)
                .patron(patron)
                .borrowingDate(LocalDate.now())
                .build();
        underTest.save(borrowingRecord);

        underTest.deleteAll();

        Optional<BorrowingRecord> result = underTest.findById(borrowingRecord.getId());
        assertThat(result).isEmpty();
    }
}


