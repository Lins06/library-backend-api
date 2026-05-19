package com.attqs.library_api.service;

import com.attqs.library_api.model.Book;
import com.attqs.library_api.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository);
    }

    private Book createTestBook(String id) {
        return Book.builder()
                .id(id)
                .title("Test Book")
                .author("Test Author")
                .genre("Fiction")
                .publicationYear(2023)
                .isbn("123-456-789")
                .description("A test book")
                .coverImageUrl("http://example.com/cover.jpg")
                .build();
    }

    @Test
    void shouldSaveBookSuccessfully() {
        // Arrange
        Book bookToSave = createTestBook(null);
        Book savedBook = createTestBook("1");

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        // Act
        Book result = bookService.saveBook(bookToSave);

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Test Book", result.getTitle());

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void shouldGetAllBooksSuccessfully() {
        // Arrange
        List<Book> books = Arrays.asList(
                createTestBook("1"),
                createTestBook("2"),
                createTestBook("3")
        );

        when(bookRepository.findAll()).thenReturn(books);

        // Act
        List<Book> result = bookService.getAllBooks();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("1", result.get(0).getId());

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoBooks() {
        // Arrange
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Book> result = bookService.getAllBooks();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void shouldGetBookByIdSuccessfully() {
        // Arrange
        Book book = createTestBook("1");
        when(bookRepository.findById("1")).thenReturn(Optional.of(book));

        // Act
        Optional<Book> result = bookService.getBookById("1");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("1", result.get().getId());
        assertEquals("Test Book", result.get().getTitle());

        verify(bookRepository, times(1)).findById("1");
    }

    @Test
    void shouldReturnEmptyWhenBookNotFound() {
        // Arrange
        when(bookRepository.findById("99")).thenReturn(Optional.empty());

        // Act
        Optional<Book> result = bookService.getBookById("99");

        // Assert
        assertTrue(result.isEmpty());

        verify(bookRepository, times(1)).findById("99");
    }

    @Test
    void shouldSearchByTitleSuccessfully() {
        // Arrange
        List<Book> books = Arrays.asList(
                createTestBook("1"),
                createTestBook("2")
        );

        when(bookRepository.findByTitleContainingIgnoreCase("Test")).thenReturn(books);

        // Act
        List<Book> result = bookService.searchByTitle("Test");

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase("Test");
    }

    @Test
    void shouldReturnEmptyWhenTitleNotFound() {
        // Arrange
        when(bookRepository.findByTitleContainingIgnoreCase("NonExistent")).thenReturn(Collections.emptyList());

        // Act
        List<Book> result = bookService.searchByTitle("NonExistent");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase("NonExistent");
    }

    @Test
    void shouldSearchByAuthorSuccessfully() {
        // Arrange
        List<Book> books = Collections.singletonList(createTestBook("1"));

        when(bookRepository.findByAuthorContainingIgnoreCase("Test Author")).thenReturn(books);

        // Act
        List<Book> result = bookService.searchByAuthor("Test Author");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        verify(bookRepository, times(1)).findByAuthorContainingIgnoreCase("Test Author");
    }

    @Test
    void shouldReturnEmptyWhenAuthorNotFound() {
        // Arrange
        when(bookRepository.findByAuthorContainingIgnoreCase("NonExistent")).thenReturn(Collections.emptyList());

        // Act
        List<Book> result = bookService.searchByAuthor("NonExistent");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(bookRepository, times(1)).findByAuthorContainingIgnoreCase("NonExistent");
    }

    @Test
    void shouldUpdateBookSuccessfully() {
        // Arrange
        Book existingBook = createTestBook("1");
        Book updatedBookDetails = createTestBook(null);
        updatedBookDetails.setTitle("Updated Title");
        updatedBookDetails.setAuthor("Updated Author");

        Book savedBook = createTestBook("1");
        savedBook.setTitle("Updated Title");
        savedBook.setAuthor("Updated Author");

        when(bookRepository.findById("1")).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        // Act
        Book result = bookService.updateBook("1", updatedBookDetails);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Author", result.getAuthor());

        verify(bookRepository, times(1)).findById("1");
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentBook() {
        // Arrange
        Book bookDetails = createTestBook(null);
        when(bookRepository.findById("99")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> bookService.updateBook("99", bookDetails));

        verify(bookRepository, times(1)).findById("99");
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void shouldDeleteBookSuccessfully() {
        // Arrange
        when(bookRepository.existsById("1")).thenReturn(true);
        doNothing().when(bookRepository).deleteById("1");

        // Act
        assertDoesNotThrow(() -> bookService.deleteBook("1"));

        // Assert
        verify(bookRepository, times(1)).existsById("1");
        verify(bookRepository, times(1)).deleteById("1");
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentBook() {
        // Arrange
        when(bookRepository.existsById("99")).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> bookService.deleteBook("99"));
        assertTrue(exception.getMessage().contains("Livro não encontrado"));

        verify(bookRepository, times(1)).existsById("99");
        verify(bookRepository, never()).deleteById(anyString());
    }
}
