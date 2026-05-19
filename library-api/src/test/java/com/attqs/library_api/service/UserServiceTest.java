package com.attqs.library_api.service;

import com.attqs.library_api.dto.LoginRequestDTO;
import com.attqs.library_api.dto.LoginResponseDTO;
import com.attqs.library_api.dto.RegisterRequestDTO;
import com.attqs.library_api.exception.EmailAlreadyExistsException;
import com.attqs.library_api.exception.InvalidCredentialsException;
import com.attqs.library_api.exception.UserNotFoundException;
import com.attqs.library_api.model.User;
import com.attqs.library_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository);
    }

    private User createTestUser(String id) {
        return User.builder()
                .id(id)
                .name("Test User")
                .email("test@test.com")
                .password(passwordEncoder.encode("password123"))
                .active(true)
                .build();
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        // Arrange
        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .name("New User")
                .email("new@test.com")
                .password("password123")
                .confirmPassword("password123")
                .build();

        User savedUser = User.builder()
                .id("1")
                .name("New User")
                .email("new@test.com")
                .password(passwordEncoder.encode("password123"))
                .active(true)
                .build();

        when(userRepository.existsByEmail("new@test.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        LoginResponseDTO response = userService.register(request);

        // Assert
        assertNotNull(response);
        assertEquals("1", response.getId());
        assertEquals("New User", response.getName());
        assertEquals("new@test.com", response.getEmail());
        assertTrue(response.isActive());
        assertEquals("Registrado com sucesso", response.getMessage());
        assertNotNull(response.getToken());

        verify(userRepository, times(1)).existsByEmail("new@test.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .name("Existing User")
                .email("existing@test.com")
                .password("password123")
                .confirmPassword("password123")
                .build();

        when(userRepository.existsByEmail("existing@test.com")).thenReturn(true);

        // Act & Assert
        assertThrows(EmailAlreadyExistsException.class, () -> userService.register(request));

        verify(userRepository, times(1)).existsByEmail("existing@test.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldLoginUserSuccessfully() {
        // Arrange
        LoginRequestDTO request = LoginRequestDTO.builder()
                .email("test@test.com")
                .password("password123")
                .build();

        User user = createTestUser("1");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        // Act
        LoginResponseDTO response = userService.login(request);

        // Assert
        assertNotNull(response);
        assertEquals("1", response.getId());
        assertEquals("Test User", response.getName());
        assertEquals("test@test.com", response.getEmail());
        assertEquals("Login realizado com sucesso", response.getMessage());

        verify(userRepository, times(1)).findByEmail("test@test.com");
    }

    @Test
    void shouldThrowExceptionWhenEmailNotFound() {
        // Arrange
        LoginRequestDTO request = LoginRequestDTO.builder()
                .email("nonexistent@test.com")
                .password("password123")
                .build();

        when(userRepository.findByEmail("nonexistent@test.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> userService.login(request));

        verify(userRepository, times(1)).findByEmail("nonexistent@test.com");
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsInvalid() {
        // Arrange
        LoginRequestDTO request = LoginRequestDTO.builder()
                .email("test@test.com")
                .password("wrongpassword")
                .build();

        User user = createTestUser("1");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> userService.login(request));

        verify(userRepository, times(1)).findByEmail("test@test.com");
    }

    @Test
    void shouldThrowExceptionWhenUserIsInactive() {
        // Arrange
        LoginRequestDTO request = LoginRequestDTO.builder()
                .email("inactive@test.com")
                .password("password123")
                .build();

        User inactiveUser = User.builder()
                .id("1")
                .name("Inactive User")
                .email("inactive@test.com")
                .password(passwordEncoder.encode("password123"))
                .active(false)
                .build();

        when(userRepository.findByEmail("inactive@test.com")).thenReturn(Optional.of(inactiveUser));

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> userService.login(request));

        verify(userRepository, times(1)).findByEmail("inactive@test.com");
    }

    @Test
    void shouldGetUserByIdSuccessfully() {
        // Arrange
        User user = createTestUser("1");
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById("1");

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Test User", result.getName());

        verify(userRepository, times(1)).findById("1");
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(userRepository.findById("99")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserById("99"));

        verify(userRepository, times(1)).findById("99");
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        // Arrange
        User user = createTestUser("1");
        User updatedUser = createTestUser("1");
        updatedUser.setName("Updated Name");

        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Act
        User result = userService.updateUser("1", "Updated Name");

        // Assert
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());

        verify(userRepository, times(1)).findById("1");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentUser() {
        // Arrange
        when(userRepository.findById("99")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.updateUser("99", "New Name"));

        verify(userRepository, times(1)).findById("99");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        // Arrange
        when(userRepository.existsById("1")).thenReturn(true);
        doNothing().when(userRepository).deleteById("1");

        // Act
        assertDoesNotThrow(() -> userService.deleteUser("1"));

        // Assert
        verify(userRepository, times(1)).existsById("1");
        verify(userRepository, times(1)).deleteById("1");
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        // Arrange
        when(userRepository.existsById("99")).thenReturn(false);

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser("99"));

        verify(userRepository, times(1)).existsById("99");
        verify(userRepository, never()).deleteById(anyString());
    }
}
