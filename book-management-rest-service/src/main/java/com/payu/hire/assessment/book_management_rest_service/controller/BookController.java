package com.payu.hire.assessment.book_management_rest_service.controller;

import com.payu.hire.assessment.book_management_rest_service.dto.ApiResponse;
import com.payu.hire.assessment.book_management_rest_service.dto.BookDto;
import com.payu.hire.assessment.book_management_rest_service.service.impl.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Validated
@Tag(name = "Books", description = "Operations related to books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all books", description = "Retrieve a list of all books")
    public ApiResponse getBooks() {
        List<BookDto> books = bookService.getAllBooks();
        return ApiResponse.success(books);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a book", description = "Create a new book entry")
    public ApiResponse addBook(@RequestBody @Valid BookDto dto) {
        bookService.addBook(dto);
        return ApiResponse.success("Book successfully created");
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Update a book",
            description = "Update an existing book by Id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updates to apply to the book",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "  \"name\": \"Clean Code\",\n" +
                                            "  \"isbnNumber\": \"978316148410\",\n" +
                                            "  \"publishDate\": \"01/01/2005\",\n" +
                                            "  \"price\": 100.0,\n" +
                                            "  \"bookType\": \"Soft Cover\"\n" +
                                            "}"
                            )
                    )
            )
    )
    public ApiResponse updateBook(@PathVariable Long id, @RequestBody HashMap<String, String> updates) {
        BookDto bookDto = bookService.updateBook(id, updates);
        return ApiResponse.success(bookDto, "Book successfully updated");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a book", description = "Delete a book by Id")
    public ApiResponse deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ApiResponse.success("Book successfully deleted");
    }
}
