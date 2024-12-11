package com.payu.hire.assessment.book_catalog_web_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
    private Long id;
    private String name;
    private String isbnNumber;
    private String publishDate;
    private Double price;
    private String bookType;
}
