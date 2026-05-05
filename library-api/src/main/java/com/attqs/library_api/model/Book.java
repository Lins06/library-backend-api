package com.attqs.library_api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data                   
@Builder                
@NoArgsConstructor      
@AllArgsConstructor     
@Document(collection = "books") 
public class Book {

    @Id
    private String id; 
    @NotBlank(message = "O título do livro é obrigatório")
    private String title;

    @NotBlank(message = "O autor do livro é obrigatório")
    private String author;

    private String genre;

    @NotNull(message = "O ano de publicação não pode ser nulo")
    @Positive(message = "O ano deve ser um número positivo")
    private Integer publicationYear;

    private String isbn;
}