package com.library.management.repositories;

import com.library.management.TestDataUtil;
import com.library.management.model.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class BookRepositoryIntegrationTests {

    private BookRepository underTest;

    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    public BookRepositoryIntegrationTests(
            BookRepository underTest,
            BorrowingRecordRepository borrowingRecordRepository
            ) {
        this.underTest = underTest;
        this.borrowingRecordRepository = borrowingRecordRepository;
    }

    @BeforeEach
    public void setUp() {
        borrowingRecordRepository.deleteAll();
        underTest.deleteAll();
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {

        Book book = TestDataUtil.createTestBookEntityA();

        underTest.save(book);

        Optional<Book> result = underTest.findById(book.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        Book bookB = TestDataUtil.createTestBookEntityB();
        Book bookC = TestDataUtil.createTestBookEntityC();

        underTest.save(bookB);
        underTest.save(bookC);

        Iterable<Book> result = underTest.findAll();
        assertThat(result)
                .hasSize(2)
                .containsExactly(bookB, bookC);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        Book bookA = TestDataUtil.createTestBookEntityA();
        underTest.save(bookA);

        bookA.setTitle("UPDATED");
        underTest.save(bookA);

        Optional<Book> result = underTest.findById(bookA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookA);
    }


    @Test
    public void testThatBookCanBeDeleted() {
        Book bookA = TestDataUtil.createTestBookEntityA();
        underTest.save(bookA);

        underTest.deleteById(bookA.getId());

        Optional<Book> result = underTest.findById(bookA.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testFindByIsbn() {
        Book book = TestDataUtil.createTestBookEntityC();
        underTest.save(book);

        Optional<Book> result = underTest.findByIsbn(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }
}
