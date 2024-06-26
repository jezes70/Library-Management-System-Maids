package com.library.management.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.management.model.dto.BookDto;
import com.library.management.TestDataUtil;
import com.library.management.model.entities.Book;
import com.library.management.services.BookService;
import com.library.management.services.BorrowingRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@Transactional
public class BookControllerIntegrationTests {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private BookService bookService;
    private BorrowingRecordService borrowingRecordService;

    @Autowired
    public BookControllerIntegrationTests(
            MockMvc mockMvc,
            BookService bookService,
            BorrowingRecordService borrowingRecordService,
            ObjectMapper objectMapper
    ) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.borrowingRecordService=borrowingRecordService;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    public void setUp() {
        borrowingRecordService.deleteAll();
        bookService.deleteAll();
    }

    @Test
    public void testThatGetAllBooksReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAllBooksReturnsListOfBooks() throws Exception {
        BookDto testBookDtoB = TestDataUtil.createTestBookDtoB();
        bookService.save(testBookDtoB);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(testBookDtoB.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].author").value(testBookDtoB.getAuthor())
        );
    }

    @Test
    public void testThatGetBookReturnsHttpStatus200WhenBookExists() throws Exception {
        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA();
        BookDto savedBookDto =bookService.save(testBookDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/books/{id}", savedBookDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetBookReturnsBookWhenBookExists() throws Exception {

        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA();
        BookDto savedBookDto =bookService.save(testBookDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/books/{id}", savedBookDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedBookDto.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(savedBookDto.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").value(savedBookDto.getAuthor())
        );
    }

    @Test
    public void testThatGetBookReturnsHttpStatus404WhenNoBookExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/books/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsHttp201Created() throws Exception {
        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA();
        String bookJson = objectMapper.writeValueAsString(testBookDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }


    @Test
    public void testThatCreateBookSuccessfullyReturnsSavedBook() throws Exception {
        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA();
        String bookJson = objectMapper.writeValueAsString(testBookDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookDtoA.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").value(testBookDtoA.getAuthor())
        );
    }

    @Test
    public void testThatCreateBookWithExistingISBNReturnsHttpConflict() throws Exception {

        BookDto testBookDtoB = TestDataUtil.createTestBookDtoB();
        bookService.save(testBookDtoB);
        String bookJson = objectMapper.writeValueAsString(testBookDtoB);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isConflict()
        );
    }

    @Test
    public void testThatUpdateBookReturnsHttpStatus404WhenNoBookExists() throws Exception {
        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA();
        testBookDtoA.setId(1L);
        String bookDtoJson = objectMapper.writeValueAsString(testBookDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/books/{id}",testBookDtoA.getId()+1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatUpdateBookReturnsHttpStatus200WhenBookExists() throws Exception {
        BookDto testBookDtoB = TestDataUtil.createTestBookDtoB();
        BookDto savedBook = bookService.save(testBookDtoB);

        String bookDtoJson = objectMapper.writeValueAsString(testBookDtoB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/books/" + savedBook.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatUpdateBookUpdatesExistingBook() throws Exception {
        BookDto testBookDtoC = TestDataUtil.createTestBookDtoC();
        BookDto savedBook = bookService.save(testBookDtoC);

        Book bookDto = TestDataUtil.createTestBookEntityB();
        bookDto.setId(savedBook.getId());

        String bookDtoUpdateJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/books/" + savedBook.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDtoUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedBook.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").value(bookDto.getAuthor())
        );
    }

    @Test
    public void testThatDeleteBookReturnsHttpStatus204ForNonExistingBook() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/books/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatDeleteBookReturnsHttpStatus204ForExistingBook() throws Exception {
        BookDto testBookDtoB = TestDataUtil.createTestBookDtoB();
        BookDto savedBook = bookService.save(testBookDtoB);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/books/" + savedBook.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
