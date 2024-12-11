package com.payu.hire.assessment.book_management_rest_service.mapper;

import com.payu.hire.assessment.book_management_rest_service.dto.BookDto;
import com.payu.hire.assessment.book_management_rest_service.entity.Book;
import com.payu.hire.assessment.book_management_rest_service.enums.BookType;
import com.payu.hire.assessment.book_management_rest_service.exceptions.BookManagementException;
import com.payu.hire.assessment.book_management_rest_service.exceptions.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {
    public BookDto convertToDto(Book book) {
        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(book, bookDto,"bookType");
        bookDto.setBookType(book.getBookType().getValue());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        bookDto.setPublishDate(book.getPublishDate().format(formatter));

        return bookDto;
    }

    public Book convertToEntity(BookDto bookDto) {

        BookType bookType = BookType.from(bookDto.getBookType());

        Book book = new Book();

        try {
            LocalDate date = LocalDate.parse(bookDto.getPublishDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            if (date.isAfter(LocalDate.now())) {
                throw new BookManagementException("Publish date cannot be in the future.");
            }

            book.setPublishDate(date);

        } catch (DateTimeParseException e) {
            throw new BookManagementException("Invalid date format for publishDate. Use dd/MM/yyyy.");
        }  catch (BookManagementException e) {
            throw new BookManagementException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }

        BeanUtils.copyProperties(bookDto, book,"bookType");

        book.setBookType(bookType);

        return book;
    }

    public List<BookDto> convertToDtoList(List<Book> books) {
        return books.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<Book> convertToEntityList(List<BookDto> bookDtos) {
        return bookDtos.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }
}
