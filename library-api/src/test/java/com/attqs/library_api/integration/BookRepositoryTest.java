package com.attqs.library_api.integration;

import com.attqs.library_api.model.Book;
import com.attqs.library_api.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class BookRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Deve salvar um livro no MongoDB real dentro do container")
    void shouldSaveBook() {
        Book book = Book.builder()
                .title("Código Limpo")
                .author("Robert C. Martin")
                .publicationYear(2008)
                .build();

        Book savedBook = bookRepository.save(book);

        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("Código Limpo");
        assertThat(bookRepository.count()).isGreaterThan(0);
    }
}