package com.attqs.library_api.controller;

import com.attqs.library_api.model.Book;
import com.attqs.library_api.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

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
    void shouldCreateBookSuccessfully() throws Exception {
        // Arrange
        Book bookToCreate = createTestBook(null);
        Book createdBook = createTestBook("1");

        when(bookService.saveBook(any(Book.class))).thenReturn(createdBook);

        // Act & Assert
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"));

        verify(bookService, times(1)).saveBook(any(Book.class));
    }

    @Test
    void shouldGetAllBooksSuccessfully() throws Exception {
        // Arrange
        List<Book> books = Arrays.asList(
                createTestBook("1"),
                createTestBook("2")
        );

        when(bookService.getAllBooks()).thenReturn(books);

        // Act & Assert
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$.length()").value(2));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void shouldGetAllBooksWhenListIsEmpty() throws Exception {
        // Arrange
        when(bookService.getAllBooks()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void shouldGetBookByIdSuccessfully() throws Exception {
        // Arrange
        Book book = createTestBook("1");
        when(bookService.getBookById("1")).thenReturn(Optional.of(book));

        // Act & Assert
        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test Book"));

        verify(bookService, times(1)).getBookById("1");
    }

    @Test
    void shouldReturnNotFoundWhenBookDoesNotExist() throws Exception {
        // Arrange
        when(bookService.getBookById("99")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/books/99"))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).getBookById("99");
    }

    @Test
    void shouldSearchBooksByTitleSuccessfully() throws Exception {
        // Arrange
        List<Book> books = Arrays.asList(
                createTestBook("1"),
                createTestBook("2")
        );

        when(bookService.searchByTitle("Test")).thenReturn(books);

        // Act & Assert
        mockMvc.perform(get("/api/books/search/title")
                .param("title", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(bookService, times(1)).searchByTitle("Test");
    }

    @Test
    void shouldReturnEmptyListWhenNoTitlesMatch() throws Exception {
        // Arrange
        when(bookService.searchByTitle("NonExistent")).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/books/search/title")
                .param("title", "NonExistent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(bookService, times(1)).searchByTitle("NonExistent");
    }

    @Test
    void shouldSearchBooksByAuthorSuccessfully() throws Exception {
        // Arrange
        List<Book> books = Collections.singletonList(createTestBook("1"));

        when(bookService.searchByAuthor("Test Author")).thenReturn(books);

        // Act & Assert
        mockMvc.perform(get("/api/books/search/author")
                .param("author", "Test Author"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(bookService, times(1)).searchByAuthor("Test Author");
    }

    @Test
    void shouldUpdateBookSuccessfully() throws Exception {
        // Arrange
        Book updatedBook = createTestBook("1");
        updatedBook.setTitle("Updated Title");

        when(bookService.updateBook(eq("1"), any(Book.class))).thenReturn(updatedBook);

        // Act & Assert
        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Updated Title"));

        verify(bookService, times(1)).updateBook(eq("1"), any(Book.class));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentBook() throws Exception {
        // Arrange
        Book book = createTestBook(null);
        when(bookService.updateBook(eq("99"), any(Book.class)))
                .thenThrow(new RuntimeException("Livro não encontrado"));

        // Act & Assert
        mockMvc.perform(put("/api/books/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).updateBook(eq("99"), any(Book.class));
    }

    @Test
    void shouldDeleteBookSuccessfully() throws Exception {
        // Arrange
        doNothing().when(bookService).deleteBook("1");

        // Act & Assert
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteBook("1");
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentBook() throws Exception {
        // Arrange
        doThrow(new RuntimeException("Livro não encontrado"))
                .when(bookService).deleteBook("99");

        // Act & Assert
        mockMvc.perform(delete("/api/books/99"))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).deleteBook("99");
    }
}
