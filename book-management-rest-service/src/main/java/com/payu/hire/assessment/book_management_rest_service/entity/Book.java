package com.payu.hire.assessment.book_management_rest_service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.payu.hire.assessment.book_management_rest_service.enums.BookType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "BOOK")
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name ="isbn_number", unique = true, nullable = false)
    private String isbnNumber;

    @Column(name ="publish_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate publishDate;

    private Double price;

    @Column(name ="book_type")
    @Enumerated(EnumType.STRING)
    private BookType bookType;
}