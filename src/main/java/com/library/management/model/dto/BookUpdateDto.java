package com.library.management.model.dto;

import com.library.management.validation.anotations.ValidateAuthor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookUpdateDto {
    private String title;

    @ValidateAuthor
    private String author;

    @Min(value = 0, message = "Publication year should be a positive number")
    @NotNull(message = "Publication year is required")
    private Integer publicationYear;

    @Size(min = 10, max = 13, message = "isbn should be valid (10-13 characters)")
    @NotBlank(message = "isbn is required")
    @NotNull(message = "isbn is required")
    private String isbn;


    private Boolean borrowed;
}
