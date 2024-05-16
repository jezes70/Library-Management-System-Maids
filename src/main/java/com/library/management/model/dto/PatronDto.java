package com.library.management.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatronDto {

    @Valid

    private Long id;
    @NotBlank(message = "Patron name is required")
    @NotNull(message = "Patron name is required")
    private String name;
    @NotBlank(message = "Contact Information is required")
    @NotNull(message = "Contact Information is required")
    private String contactInformation;

}