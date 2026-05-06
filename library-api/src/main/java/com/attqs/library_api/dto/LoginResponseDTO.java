package com.attqs.library_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private String id;
    private String name;
    private String email;
    private boolean active;
    private String message;
    private String token;
}
