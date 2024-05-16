package com.library.management.model.dto;

import com.library.management.validation.anotations.ValidateAuthor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {

    @Valid

    private Long id;

    private String title;
    @NotNull(message = "Author  is required")
    private String author;

    @Min(value = 0, message = "Publication year should be a positive number")
    @NotNull(message = "Publication year is required")
    private Integer publicationYear;

    @Size(min = 10, max = 15, message = "isbn should be valid (10-15 characters)")
    @NotBlank(message = "isbn is required")
    @NotNull(message = "isbn is required")
    private String isbn;


    private Boolean borrowed;
}