package com.payu.hire.assessment.book_management_rest_service.mapper;

import com.payu.hire.assessment.book_management_rest_service.dto.BookDto;
import com.payu.hire.assessment.book_management_rest_service.entity.Book;
import com.payu.hire.assessment.book_management_rest_service.exceptions.BookManagementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class BookMapperTests {
    private BookMapper bookMapper;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        bookMapper = new BookMapper();

        bookDto = new BookDto();

        bookDto.setId(1L);
        bookDto.setName("Clean Code");
        bookDto.setIsbnNumber("9780132350884");
        bookDto.setPublishDate("06/12/2021");
        bookDto.setPrice(45.99);
        bookDto.setBookType("Hard Cover");

    }

    @Test
    @DisplayName("JUnit test for convertToEntity when publish date is in the future")
    void testConvertToEntityWhenPublishDateIsInTheFuture() {

        bookDto.setPublishDate("06/12/2025");

        BookManagementException exception = assertThrows(BookManagementException.class, () -> {
            bookMapper.convertToEntity(bookDto);
        });

        assertEquals("Publish date cannot be in the future.", exception.getMessage());
    }

    @Test
    @DisplayName("JUnit test for convertToEntity when publish date format is invalid")
    void testConvertToEntityWhenPublishDateFormatIsInvalid() {

        bookDto.setPublishDate("2021/12/06");

        BookManagementException exception = assertThrows(BookManagementException.class, () -> {
            bookMapper.convertToEntity(bookDto);
        });

        assertEquals("Invalid date format for publishDate. Use dd/MM/yyyy.", exception.getMessage());
    }

    @Test
    @DisplayName("JUnit test for convertToEntity when valid")
    void testConvertToEntityWhenValid() {

        Book book = bookMapper.convertToEntity(bookDto);

        assertNotNull(book);
        assertEquals(bookDto.getName(), book.getName());
        assertEquals(bookDto.getId(), book.getId());
        assertEquals(LocalDate.parse("06/12/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")), book.getPublishDate());
        assertEquals(bookDto.getBookType(), book.getBookType().getValue());
    }
}
