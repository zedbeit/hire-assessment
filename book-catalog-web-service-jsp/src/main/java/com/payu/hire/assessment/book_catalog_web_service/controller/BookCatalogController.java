package com.payu.hire.assessment.book_catalog_web_service.controller;

import com.payu.hire.assessment.book_catalog_web_service.service.impl.BookCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class BookCatalogController {

    private final BookCatalogService bookCatalogService;

    @GetMapping
    public String listBooks(Model model) {
        return bookCatalogService.listAllBooks(model);
    }

    @PostMapping("/add")
    public String addBook(@RequestParam Map<String, String> bookDetails, Model model) {
        return bookCatalogService.addBook(bookDetails,model);
    }

    @PostMapping("/edit/{id}")
    public String editBook(@PathVariable Long id, @RequestParam Map<String, String> updates, Model model) {
        return bookCatalogService.editBook(id, model, updates);
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, Model model) {
        return bookCatalogService.deleteBook(id, model);
    }
}
