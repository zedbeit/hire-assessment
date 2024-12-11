package com.payu.hire.assessment.book_management_rest_service.service;

import com.payu.hire.assessment.book_management_rest_service.dto.BookDto;
import com.payu.hire.assessment.book_management_rest_service.entity.Book;
import com.payu.hire.assessment.book_management_rest_service.enums.BookType;
import com.payu.hire.assessment.book_management_rest_service.exceptions.BookManagementException;
import com.payu.hire.assessment.book_management_rest_service.exceptions.DuplicateIsbnException;
import com.payu.hire.assessment.book_management_rest_service.mapper.BookMapper;
import com.payu.hire.assessment.book_management_rest_service.repository.BookRepository;
import com.payu.hire.assessment.book_management_rest_service.service.impl.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookService bookService;

    private BookDto bookDto;
    private Book existingBook;

    @BeforeEach
    public void setup(){

        bookDto = new BookDto();

        bookDto.setId(1L);
        bookDto.setName("Clean Code");
        bookDto.setIsbnNumber("9780132350884");
        bookDto.setPublishDate("06/12/2021");
        bookDto.setPrice(45.99);
        bookDto.setBookType("Hard Cover");

        existingBook = new Book();
        existingBook.setId(2L);
        existingBook.setName("Clean Code");
        existingBook.setIsbnNumber("9780132350884");
        existingBook.setPublishDate(LocalDate.of(2008, 8, 1));
        existingBook.setPrice(40.00);
        existingBook.setBookType(BookType.HARD_COVER);
    }

    @Test
    public void testAddBook_whenIsbnAlreadyExists(){

        when(bookRepository.findByIsbnNumber(bookDto.getIsbnNumber()))
                .thenReturn(Optional.of(new Book()));

        DuplicateIsbnException exception = assertThrows(DuplicateIsbnException.class, () -> {
            bookService.addBook(bookDto);
        });

        assertEquals("A book with ISBN number 9780132350884 already exists", exception.getMessage());
    }

    @Test
    void testAddBook_whenIsbnDoesNotExist() {

        when(bookRepository.findByIsbnNumber(bookDto.getIsbnNumber()))
                .thenReturn(Optional.empty());

        Book book = new Book();

        when(bookMapper.convertToEntity(bookDto))
                .thenReturn(book);

        bookService.addBook(bookDto);

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testGetAllBooks_whenNoBooksAreFound() {

        when(bookRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<BookDto> result = bookService.getAllBooks();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllBooks_whenBooksAreFound() {

        List<Book> books = Arrays.asList(
                new Book() {{
                    setName("Clean Code");
                    setIsbnNumber("9780132350884");
                    setPublishDate(LocalDate.of(2008, 8, 1));
                    setPrice(40.00);
                    setBookType(BookType.HARD_COVER);
                }},
                new Book() {{
                    setName("Effective Java");
                    setIsbnNumber("9780134685991");
                    setPublishDate(LocalDate.of(2017, 12, 27));
                    setPrice(50.00);
                    setBookType(BookType.EBOOK);
                }}
        );

        List<BookDto> bookDtos = Arrays.asList(
                new BookDto() {{
                    setName("Clean Code");
                    setIsbnNumber("9780132350884");
                    setPublishDate("06/12/2008");
                    setPrice(40.00);
                    setBookType("Hard Cover");
                }},
                new BookDto() {{
                    setName("Effective Java");
                    setIsbnNumber("9780134685991");
                    setPublishDate("27/12/2017");
                    setPrice(50.00);
                    setBookType("eBook");
                }}
        );

        when(bookRepository.findAll())
                .thenReturn(books);

        when(bookMapper.convertToDtoList(books))
                .thenReturn(bookDtos);

        List<BookDto> result = bookService.getAllBooks();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals("Clean Code", result.get(0).getName());
    }

    @Test
    void testUpdateBook_Success() {

        HashMap<String, String> updates = new HashMap<>();
        updates.put("name", "Clean Code Updated");
        updates.put("price", "45.00");
        updates.put("bookType", "Hard Cover");

        BookDto updatedBookDto = new BookDto();
        updatedBookDto.setName("Clean Code Updated");
        updatedBookDto.setIsbnNumber("9780132350884");
        updatedBookDto.setPublishDate("01/01/2008");
        updatedBookDto.setPrice(45.00);
        updatedBookDto.setBookType("Hard Cover");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookMapper.convertToDto(any(Book.class))).thenReturn(updatedBookDto);

        BookDto result = bookService.updateBook(1L, updates);

        assertNotNull(result);
        assertEquals("Clean Code Updated", result.getName());
        assertEquals(45.00, result.getPrice());
        assertEquals("Hard Cover", result.getBookType());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void testUpdateBook_whenInvalidId() {

        HashMap<String, String> updates = new HashMap<>();
        updates.put("name", "Clean Code Updated");


        BookManagementException exception = assertThrows(BookManagementException.class, () -> {
            bookService.updateBook(-2L, updates);
        });
        assertEquals("Invalid or missing ID. Please provide a valid book ID.", exception.getMessage());
    }

    @Test
    void testUpdateBook_whenEmptyUpdates() {

        HashMap<String, String> updates = new HashMap<>();

        BookManagementException exception = assertThrows(BookManagementException.class, () -> {
            bookService.updateBook(2L, updates);
        });
        assertEquals("No fields provided to update. Please provide at least one field.", exception.getMessage());
    }

    @Test
    void testUpdateBook_whenInvalidField() {

        HashMap<String, String> updates = new HashMap<>();
        updates.put("nonExistentField", "SomeValue");

        Book book = new Book();
        book.setId(1L);
        book.setName("Existing Book");

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        BookManagementException exception = assertThrows(BookManagementException.class, () -> {
            bookService.updateBook(1L, updates);
        });

        assertEquals("Invalid field: nonExistentField", exception.getMessage());
    }

    @Test
    void testUpdateBook_whenBookNotFound() {

        HashMap<String, String> updates = new HashMap<>();
        updates.put("name", "New Name");

        when(bookRepository.findById(1L))
                .thenReturn(Optional.empty());

        BookManagementException exception = assertThrows(BookManagementException.class, () -> {
            bookService.updateBook(1L, updates);
        });

        assertEquals("Book not found with id: 1", exception.getMessage());
    }

    @Test
    void testUpdateBook_whenInvalidDateFormat() {

        HashMap<String, String> updates = new HashMap<>();

        // Invalid date format
        updates.put("publishDate", "01-01-2008");

        Book book = new Book();
        book.setId(1L);
        book.setName("Existing Book");

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        BookManagementException exception = assertThrows(BookManagementException.class, () -> {
            bookService.updateBook(1L, updates);
        });

        assertEquals("Invalid date format for publishDate. Use dd/MM/yyyy.", exception.getMessage());
    }

    @Test
    void testUpdateBook_whenCannotUpdateIdOrIsbn() {

        HashMap<String, String> updates = new HashMap<>();

        updates.put("id", "2");

        BookManagementException exception = assertThrows(BookManagementException.class, () -> {
            bookService.updateBook(1L, updates);
        });
        assertEquals("Cannot update Id.", exception.getMessage());
    }

    @Test
    void testDeleteBook_ShouldCallDeleteById() {
        Long bookId = 1L;

        bookService.deleteBook(bookId);

        verify(bookRepository, times(1)).deleteById(bookId);
    }
}
