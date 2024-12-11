package com.payu.hire.assessment.book_catalog_web_service.service.impl;

import com.payu.hire.assessment.book_catalog_web_service.dto.BookDto;
import com.payu.hire.assessment.book_catalog_web_service.dto.ErrorResponse;
import com.payu.hire.assessment.book_catalog_web_service.dto.GetBooksResponse;
import com.payu.hire.assessment.book_catalog_web_service.service.IBookCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import static com.payu.hire.assessment.book_catalog_web_service.constants.ApplicationConstants.ERROR_OCCURRED_MSG;
import static com.payu.hire.assessment.book_catalog_web_service.constants.ApplicationConstants.INVALID_DATE_FORMAT_MSG;
import static com.payu.hire.assessment.book_catalog_web_service.util.UtilClass.validateAndFormatPublishDate;

@Service
@RequiredArgsConstructor
public class BookCatalogService implements IBookCatalogService {

    @Value("${book.management.rest.service.url}")
    private String bookManagementServiceUrl;

    private final Client client;

    @Override
    public String listAllBooks(Model model) {

        try (Response response = client.target(bookManagementServiceUrl)
                .request(MediaType.APPLICATION_JSON_VALUE)
                .get()) {

            if (response.getStatus() == 200) {

                GetBooksResponse booksResponse = response.readEntity(GetBooksResponse.class);

                List<BookDto> books = booksResponse.getResponseDetails();

                model.addAttribute("books", books);

                return "index";

            } else if (response.getStatus() == 400) {

                ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
                model.addAttribute("errorMessage", errorResponse.getResponseMessage());

                return "error";
            } else {
                model.addAttribute("errorMessage", "An error occurred while fetching list of books.");
                return "error";
            }
        } catch (ProcessingException e) {
            return "bootstrap-error";
        } catch (Exception e) {
            model.addAttribute("errorMessage", ERROR_OCCURRED_MSG);
            return "error";
        }
    }

    @Override
    public String addBook(Map<String, String> bookDetails, Model model) {

        try {
            String formattedPublishDate = validateAndFormatPublishDate(bookDetails.get("publishDate"));

            bookDetails.put("publishDate", formattedPublishDate);

        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "error";
        }

        try (Response response = client.target(bookManagementServiceUrl)
                .request(MediaType.APPLICATION_JSON_VALUE)
                .post(Entity.json(bookDetails))) {

            if (response.getStatus() == 201) {
                return "redirect:/";
            } else if (response.getStatus() == 400) {

                ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
                model.addAttribute("errorMessage", errorResponse.getResponseMessage());

                return "error";
            } else {

                model.addAttribute("errorMessage", ERROR_OCCURRED_MSG);
                return "error";
            }

        } catch (ProcessingException e) {
            return "bootstrap-error";
        } catch (Exception e) {
            model.addAttribute("errorMessage", ERROR_OCCURRED_MSG);
            return "error";
        }
    }

    @Override
    public String editBook(Long id, Model model, Map<String, String> updates) {

        try {

            String publishDate = updates.get("publishDate");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate date = LocalDate.parse(publishDate, formatter);

            if (date.isAfter(LocalDate.now())) {
                model.addAttribute("errorMessage", "Publish date cannot be in the future.");
                return "error";
            }

        } catch (DateTimeParseException e) {
            model.addAttribute("errorMessage",INVALID_DATE_FORMAT_MSG);
            return "error";
        }

        try (Response response = client.target(bookManagementServiceUrl + "/" + id)
                .request(MediaType.APPLICATION_JSON_VALUE)
                .put(Entity.json(updates))) {

            if (response.getStatus() == 200) {
                return "redirect:/";
            } else if (response.getStatus() == 400) {

                ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
                model.addAttribute("errorMessage", errorResponse.getResponseMessage());

                return "error";
            } else {

                model.addAttribute("errorMessage", ERROR_OCCURRED_MSG);
                return "error";
            }

        } catch (ProcessingException e) {
            return "bootstrap-error";
        } catch (Exception e) {
            model.addAttribute("errorMessage", ERROR_OCCURRED_MSG);
            return "error";
        }
    }
    @Override
    public String deleteBook(Long id, Model model) {

        try (Response response = client.target(bookManagementServiceUrl + "/" + id)
                .request(MediaType.APPLICATION_JSON_VALUE)
                .delete()) {

            if (response.getStatus() == 200) {

                return "redirect:/";
            } else if (response.getStatus() == 400) {

                ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
                model.addAttribute("errorMessage", errorResponse.getResponseMessage());

                return "error";
            } else {

                model.addAttribute("errorMessage", ERROR_OCCURRED_MSG);
                return "error";
            }

        } catch (ProcessingException e) {
            return "bootstrap-error";
        } catch (Exception e) {
            model.addAttribute("errorMessage", ERROR_OCCURRED_MSG);

            return "error";
        }
    }
}
