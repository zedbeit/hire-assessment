package com.payu.hire.assessment.book_catalog_web_service.service;

import org.springframework.ui.Model;
import java.util.Map;

public interface IBookCatalogService {
    String listAllBooks(Model model);
    String addBook(Map<String, String> bookDetails, Model model);
    String editBook(Long id, Model model, Map<String, String> updates);
    String deleteBook(Long id, Model model);
}