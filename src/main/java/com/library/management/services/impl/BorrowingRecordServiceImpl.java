package com.library.management.services.impl;

import com.library.management.mappers.Mapper;
import com.library.management.model.dto.BorrowingRecordDto;
import com.library.management.model.entities.Book;
import com.library.management.model.entities.BorrowingRecord;
import com.library.management.model.entities.Patron;
import com.library.management.repositories.BookRepository;
import com.library.management.repositories.BorrowingRecordRepository;
import com.library.management.repositories.PatronRepository;
import com.library.management.services.BorrowingRecordService;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final Mapper<BorrowingRecord, BorrowingRecordDto> borrowingRecordMapper;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;

    public BorrowingRecordServiceImpl(
            BorrowingRecordRepository borrowingRecordRepository,
            Mapper<BorrowingRecord, BorrowingRecordDto> borrowingRecordMapper,
            BookRepository bookRepository,
            PatronRepository patronRepository
    ) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.borrowingRecordMapper = borrowingRecordMapper;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"bookList", "findBookById"}, allEntries = true)
    public BorrowingRecordDto borrowBook(Long bookId, Long patronId) {
        Book bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new RuntimeException("Patron not found"));

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(bookEntity);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(LocalDate.now());

        bookEntity.setBorrowed(true);
        bookRepository.save(bookEntity);

        BorrowingRecord book = borrowingRecordRepository.save(borrowingRecord);
        return borrowingRecordMapper.mapEntityToDto(book);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"bookList", "findBookById"}, allEntries = true)
    public BorrowingRecordDto returnBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new RuntimeException("Patron not found"));

        Optional<BorrowingRecord> borrowingRecordOptional = borrowingRecordRepository
                .findByBookAndPatronAndReturnDateIsNull(book, patron);

        return borrowingRecordOptional.map(borrowingRecordEntity -> {
            borrowingRecordEntity.setReturnDate(LocalDate.now());

            book.setBorrowed(false);
            bookRepository.save(book);


            return borrowingRecordMapper.mapEntityToDto(borrowingRecordRepository.save(borrowingRecordEntity));
        }).orElseThrow(() -> new RuntimeException("Borrowing record not found"));
    }

    @Override
    public void deleteAll() {
        borrowingRecordRepository.deleteAll();
    }
}
