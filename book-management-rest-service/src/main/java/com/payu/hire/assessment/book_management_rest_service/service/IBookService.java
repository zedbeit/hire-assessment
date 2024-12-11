package com.payu.hire.assessment.book_management_rest_service.service;

import com.payu.hire.assessment.book_management_rest_service.dto.BookDto;

import java.util.HashMap;
import java.util.List;

public interface IBookService {
    List<BookDto> getAllBooks();
    void addBook(BookDto book);
    BookDto updateBook(Long id, HashMap<String, String> updates);
    void deleteBook(Long id);
}
