package com.payu.hire.assessment.book_management_rest_service.service.impl;

import com.payu.hire.assessment.book_management_rest_service.dto.BookDto;
import com.payu.hire.assessment.book_management_rest_service.entity.Book;
import com.payu.hire.assessment.book_management_rest_service.enums.BookType;
import com.payu.hire.assessment.book_management_rest_service.exceptions.BookManagementException;
import com.payu.hire.assessment.book_management_rest_service.exceptions.DuplicateIsbnException;
import com.payu.hire.assessment.book_management_rest_service.mapper.BookMapper;
import com.payu.hire.assessment.book_management_rest_service.repository.BookRepository;
import com.payu.hire.assessment.book_management_rest_service.service.IBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.payu.hire.assessment.book_management_rest_service.utils.UtilClass.validateIsbn;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService implements IBookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookDto> getAllBooks() {

        log.info(":: Inside getAllBooks ::");

        List<Book> books = bookRepository.findAll();

        if(books.isEmpty())
            return Collections.emptyList();

        return bookMapper.convertToDtoList(books);
    }

    @Override
    public void addBook(BookDto dto) {

        log.info(":: Inside addBook ::");

        String isbnNumber = dto.getIsbnNumber().replace("-", "");

        validateIsbn(isbnNumber);

        bookRepository.findByIsbnNumber(isbnNumber)
            .ifPresent(book -> {
                throw new DuplicateIsbnException("A book with ISBN number " + isbnNumber + " already exists");
            });

        dto.setIsbnNumber(isbnNumber);

        Book book = bookMapper.convertToEntity(dto);

        bookRepository.save(book);
    }

    public BookDto updateBook(Long id, HashMap<String, String> updates) {

        log.info(":: Inside updateBook ::");

        if (id == null || id <= 0) {
            throw new BookManagementException("Invalid or missing ID. Please provide a valid book ID.");
        }

        if (updates == null || updates.isEmpty()) {
            throw new BookManagementException("No fields provided to update. Please provide at least one field.");
        }

        if (updates.containsKey("id")) {
            throw new BookManagementException("Cannot update Id.");
        }

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookManagementException("Book not found with id: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    book.setName(value);
                    break;
                case "publishDate":
                    try {
                        LocalDate date = LocalDate.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        book.setPublishDate(date);
                    } catch (Exception e) {
                        throw new BookManagementException("Invalid date format for publishDate. Use dd/MM/yyyy.");
                    }
                    break;
                case "price":
                    book.setPrice(Double.valueOf(value));
                    break;
                case "bookType":
                    BookType bookType = BookType.from(value);
                    book.setBookType(bookType);
                    break;
                case "isbnNumber":
                    String isbn = value.replace("-", "");

                    if(book.getIsbnNumber().equals(isbn))
                        break;

                    validateIsbn(isbn);
                    bookRepository.findByIsbnNumber(isbn)
                        .ifPresent(b -> {
                            throw new DuplicateIsbnException("A book with ISBN number " + isbn + " already exists");
                        });
                    book.setIsbnNumber(isbn);
                    break;
                default:
                    throw new BookManagementException("Invalid field: " + key);
            }
        });

        bookRepository.save(book);

        return bookMapper.convertToDto(book);
    }

    @Override
    public void deleteBook(Long id) {

        log.info(":: Inside deleteBook ::");

        if (id == null || id <= 0) {
            throw new BookManagementException("Invalid or missing ID. Please provide a valid book ID.");
        }

        try {
            bookRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("No book found with ID {} to delete.", id);
            throw new BookManagementException("Book with ID " + id + " does not exist.");
        }
    }
}
