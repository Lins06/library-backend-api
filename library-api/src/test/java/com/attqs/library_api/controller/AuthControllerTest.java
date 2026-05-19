package com.attqs.library_api.controller;

import com.attqs.library_api.dto.AddressResponseDTO;
import com.attqs.library_api.dto.LoginRequestDTO;
import com.attqs.library_api.dto.LoginResponseDTO;
import com.attqs.library_api.dto.RegisterRequestDTO;
import com.attqs.library_api.exception.EmailAlreadyExistsException;
import com.attqs.library_api.exception.InvalidCredentialsException;
import com.attqs.library_api.service.AddressService;
import com.attqs.library_api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private AddressService addressService;

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        // Arrange
        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .name("John Doe")
                .email("john@test.com")
                .password("password123")
                .confirmPassword("password123")
                .build();

        LoginResponseDTO response = LoginResponseDTO.builder()
                .id("123")
                .name("John Doe")
                .email("john@test.com")
                .active(true)
                .message("Registrado com sucesso")
                .token("token123")
                .build();

        when(userService.register(any(RegisterRequestDTO.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@test.com"));

        verify(userService, times(1)).register(any(RegisterRequestDTO.class));
    }

    @Test
    void shouldReturnBadRequestWhenPasswordsDoNotMatch() throws Exception {
        // Arrange
        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .name("John Doe")
                .email("john@test.com")
                .password("password123")
                .confirmPassword("password456")
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).register(any());
    }

    @Test
    void shouldReturnConflictWhenEmailAlreadyExists() throws Exception {
        // Arrange
        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .name("John Doe")
                .email("existing@test.com")
                .password("password123")
                .confirmPassword("password123")
                .build();

        when(userService.register(any(RegisterRequestDTO.class)))
                .thenThrow(new EmailAlreadyExistsException("Email já cadastrado"));

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());

        verify(userService, times(1)).register(any(RegisterRequestDTO.class));
    }

    @Test
    void shouldLoginUserSuccessfully() throws Exception {
        // Arrange
        LoginRequestDTO request = LoginRequestDTO.builder()
                .email("john@test.com")
                .password("password123")
                .build();

        LoginResponseDTO response = LoginResponseDTO.builder()
                .id("123")
                .name("John Doe")
                .email("john@test.com")
                .active(true)
                .message("Login realizado com sucesso")
                .token("token123")
                .build();

        when(userService.login(any(LoginRequestDTO.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.message").value("Login realizado com sucesso"));

        verify(userService, times(1)).login(any(LoginRequestDTO.class));
    }

    @Test
    void shouldReturnUnauthorizedWhenLoginFails() throws Exception {
        // Arrange
        LoginRequestDTO request = LoginRequestDTO.builder()
                .email("invalid@test.com")
                .password("wrongpassword")
                .build();

        when(userService.login(any(LoginRequestDTO.class)))
                .thenThrow(new InvalidCredentialsException("Email ou senha inválidos"));

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());

        verify(userService, times(1)).login(any(LoginRequestDTO.class));
    }

    @Test
    void shouldValidateAuthenticationSuccessfully() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/auth/validate"))
                .andExpect(status().isOk())
                .andExpect(content().string("Autenticação válida"));
    }

    @Test
    void shouldSearchAddressByCepSuccessfully() throws Exception {
        // Arrange
        AddressResponseDTO addressResponse = AddressResponseDTO.builder()
                .cep("12345-678")
                .street("Rua Principal")
                .neighborhood("Centro")
                .city("São Paulo")
                .state("SP")
                .error(false)
                .build();

        when(addressService.findAddressByCep(anyString())).thenReturn(addressResponse);

        // Act & Assert
        mockMvc.perform(get("/api/auth/address/12345-678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cep").value("12345-678"))
                .andExpect(jsonPath("$.localidade").value("São Paulo"));

        verify(addressService, times(1)).findAddressByCep("12345-678");
    }
}
