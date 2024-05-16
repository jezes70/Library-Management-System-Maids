package com.library.management.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @NotNull(message = "Title is required")
    private String title;
    @NotBlank(message = "Author is required")
    @NotNull(message = "Author is required")
    private String author;

    @Min(value = 0, message = "Publication year should be a positive number")
    @NotNull(message = "Publication year is required")
    private Integer publicationYear;

    @Size(min = 10, max = 15, message = "isbn should be valid (10-15 characters)")
    @NotBlank(message = "isbn is required")
    @NotNull(message = "isbn is required")
    @Column(unique = true)
    private String isbn;

    @NotNull(message = "Borrowing Status is required")
    private Boolean borrowed;
}
