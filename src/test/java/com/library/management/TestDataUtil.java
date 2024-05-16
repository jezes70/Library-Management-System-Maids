package com.library.management;

import com.library.management.model.dto.BookDto;
import com.library.management.model.dto.BorrowingRecordDto;
import com.library.management.model.dto.PatronDto;
import com.library.management.model.entities.Book;
import com.library.management.model.entities.BorrowingRecord;
import com.library.management.model.entities.Patron;

import java.time.LocalDate;

public final class TestDataUtil {
    private TestDataUtil(){
    }

    public static Book createTestBookEntityA() {
        return Book.builder()
                .title("The Shadow in the Attic")
                .author("Sila Sea")
                .publicationYear(2005)
                .isbn("9781234567890")
                .borrowed(false)
                .build();
    }

    public static BookDto createTestBookDtoA() {
        return BookDto.builder()
                .title("The Shadow in the Attic")
                .author("Sila Sea")
                .publicationYear(2005)
                .isbn("9781234567890")
                .borrowed(false)
                .build();
    }

    public static Book createTestBookEntityB() {
        return Book.builder()
                .title("Beyond the Horizon")
                .author("John Writer")
                .publicationYear(2010)
                .isbn("9782345678901")
                .borrowed(false)
                .build();
    }

    public static BookDto createTestBookDtoB() {
        return BookDto.builder()
                .title("Beyond the Horizon")
                .author("John Writer")
                .publicationYear(2010)
                .isbn("9782345678901")
                .borrowed(false)
                .build();
    }

    public static Book createTestBookEntityC() {
        return Book.builder()
                .title("Lost in the Library")
                .author("Emma Reader")
                .publicationYear(2018)
                .isbn("9783456789012")
                .borrowed(false)
                .build();
    }

    public static BookDto createTestBookDtoC() {
        return BookDto.builder()
                .title("Lost in the Library")
                .author("Emma Reader")
                .publicationYear(2018)
                .isbn("9783456789012")
                .borrowed(false)
                .build();
    }

    public static Patron createTestPatronEntityA() {
        return Patron.builder()
                .name("John Doe")
                .contactInformation("john.doe@example.com")
                .build();
    }

    public static PatronDto createTestPatronDtoA() {
        return PatronDto.builder()
                .name("John Doe")
                .contactInformation("john.doe@example.com")
                .build();
    }

    public static Patron createTestPatronEntityB() {
        return Patron.builder()
                .name("Jane Smith")
                .contactInformation("jane.smith@example.com")
                .build();
    }

    public static PatronDto createTestPatronDtoB() {
        return PatronDto.builder()
                .name("Jane Smith")
                .contactInformation("jane.smith@example.com")
                .build();
    }

    public static Patron createTestPatronEntityC() {
        return Patron.builder()
                .name("Bob Johnson")
                .contactInformation("bob.johnson@example.com")
                .build();
    }

    public static PatronDto createTestPatronDtoC() {
        return PatronDto.builder()
                .name("Bob Johnson")
                .contactInformation("bob.johnson@example.com")
                .build();
    }

    public static BorrowingRecord createTestBorrowingRecordEntityA() {
        return BorrowingRecord.builder()
                .book(createTestBookEntityA())
                .patron(createTestPatronEntityA())
                .borrowingDate(LocalDate.of(2022, 1, 1))
                .returnDate(null)
                .build();
    }

    public static BorrowingRecordDto createTestBorrowingRecordDtoA() {
        return BorrowingRecordDto.builder()
                .book(createTestBookDtoA())
                .patron(createTestPatronDtoA())
                .borrowingDate(LocalDate.of(2022, 1, 1))
                .returnDate(null)
                .build();
    }
}
