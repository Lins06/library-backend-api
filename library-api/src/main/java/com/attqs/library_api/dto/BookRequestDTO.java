package com.attqs.library_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDTO {

    @NotBlank(message = "O título do livro é obrigatório")
    private String title;

    @NotBlank(message = "O autor do livro é obrigatório")
    private String author;

    private String genre;

    @NotNull(message = "O ano de publicação não pode ser nulo")
    @Positive(message = "O ano deve ser um número positivo")
    private Integer publicationYear;

    private String isbn;

    private String coverImageUrl;

    private String description;
}
