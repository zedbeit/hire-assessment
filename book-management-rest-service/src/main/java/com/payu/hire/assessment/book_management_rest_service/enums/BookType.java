package com.payu.hire.assessment.book_management_rest_service.enums;

import com.payu.hire.assessment.book_management_rest_service.exceptions.InvalidBookTypeException;
import lombok.Getter;

@Getter
public enum BookType {
    HARD_COVER("Hard Cover"),
    SOFT_COVER("Soft Cover"),
    EBOOK("eBook");

    private final String value;

    BookType(String value) {
        this.value = value;
    }

    public static BookType from(String value) {
        for (BookType bookType : BookType.values()) {
            if (bookType.getValue().equalsIgnoreCase(value)) {
                return bookType;
            }
        }
        throw new InvalidBookTypeException("Invalid book type: " + value);
    }
}