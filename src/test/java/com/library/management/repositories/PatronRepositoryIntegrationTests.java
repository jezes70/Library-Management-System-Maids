package com.library.management.repositories;

import com.library.management.TestDataUtil;
import com.library.management.model.entities.Patron;
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
public class PatronRepositoryIntegrationTests {

    private PatronRepository underTest;

    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    public PatronRepositoryIntegrationTests(
            PatronRepository underTest,
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
    public void testThatPatronCanBeCreatedAndRecalled() {
        Patron patron = TestDataUtil.createTestPatronEntityA();

        underTest.save(patron);

        Optional<Patron> result = underTest.findById(patron.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(patron);
    }

    @Test
    public void testThatMultiplePatronsCanBeCreatedAndRecalled() {
        Patron patronA = TestDataUtil.createTestPatronEntityA();
        Patron patronB = TestDataUtil.createTestPatronEntityB();
        Patron patronC = TestDataUtil.createTestPatronEntityC();

        underTest.save(patronA);
        underTest.save(patronB);
        underTest.save(patronC);

        Iterable<Patron> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(patronA, patronB, patronC);
    }

    @Test
    public void testThatPatronCanBeUpdated() {
        Patron patronA = TestDataUtil.createTestPatronEntityA();
        underTest.save(patronA);

        patronA.setName("UPDATED");
        underTest.save(patronA);

        Optional<Patron> result = underTest.findById(patronA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(patronA);
    }

    @Test
    public void testThatPatronCanBeDeleted() {
        Patron patronB = TestDataUtil.createTestPatronEntityB();
        underTest.save(patronB);

        underTest.deleteById(patronB.getId());

        Optional<Patron> result = underTest.findById(patronB.getId());
        assertThat(result).isEmpty();
    }
}
