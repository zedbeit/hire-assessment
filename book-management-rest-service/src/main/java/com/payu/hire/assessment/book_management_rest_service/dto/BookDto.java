package com.payu.hire.assessment.book_management_rest_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.*;

@Getter
@Setter
public class BookDto {

    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 100, message = "Name must be between 3 and 100 characters")
    @Schema(description = "Name of the book", example = "Good Code Bad Code")
    private String name;

    @NotEmpty(message = "ISBN number cannot be empty")
    @Schema(description = "Unique ISBN number", example = "978316148410")
    private String isbnNumber;

    @NotNull(message = "Publish date cannot be null")
    @Schema(description = "Date when the book was published", example = "01/01/2001")
    private String publishDate;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive value")
    @Schema(description = "Price of the book in ZAR", example = "15.99")
    private Double price;

    @NotNull(message = "Book type cannot be null")
    @Schema(description = "Type of the book", example = "Hard Cover")
    private String bookType;
}
