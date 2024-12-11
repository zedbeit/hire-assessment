package com.payu.hire.assessment.book_management_rest_service.repository;

import com.payu.hire.assessment.book_management_rest_service.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByIsbnNumber(String isbnNumber);
}
