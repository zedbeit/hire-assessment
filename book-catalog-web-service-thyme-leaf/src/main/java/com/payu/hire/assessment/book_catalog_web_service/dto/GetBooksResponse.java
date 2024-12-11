package com.payu.hire.assessment.book_catalog_web_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class GetBooksResponse {
    private String responseStatus;
    private List<BookDto> responseDetails;
}


