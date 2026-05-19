package com.attqs.library_api.service;

import com.attqs.library_api.dto.AddressResponseDTO;
import com.attqs.library_api.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    private AddressService addressService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        addressService = new AddressService(restTemplate);
    }

    private AddressResponseDTO createTestAddress() {
        return AddressResponseDTO.builder()
                .cep("12345-678")
                .street("Rua Principal")
                .neighborhood("Centro")
                .city("São Paulo")
                .state("SP")
                .error(false)
                .build();
    }

    @Test
    void shouldFindAddressByCepSuccessfully() {
        // Arrange
        AddressResponseDTO expectedAddress = createTestAddress();
        when(restTemplate.getForObject(anyString(), eq(AddressResponseDTO.class)))
                .thenReturn(expectedAddress);

        // Act
        AddressResponseDTO result = addressService.findAddressByCep("12345-678");

        // Assert
        assertNotNull(result);
        assertEquals("12345-678", result.getCep());
        assertEquals("Rua Principal", result.getStreet());
        assertEquals("São Paulo", result.getCity());
        assertFalse(result.getError());

        verify(restTemplate, times(1)).getForObject(
                "https://viacep.com.br/ws/12345678/json/",
                AddressResponseDTO.class
        );
    }

    @Test
    void shouldFindAddressByCepWithFormattedInput() {
        // Arrange
        AddressResponseDTO expectedAddress = createTestAddress();
        when(restTemplate.getForObject(anyString(), eq(AddressResponseDTO.class)))
                .thenReturn(expectedAddress);

        // Act
        AddressResponseDTO result = addressService.findAddressByCep("12345-678");

        // Assert
        assertNotNull(result);
        assertEquals("12345-678", result.getCep());

        verify(restTemplate, times(1)).getForObject(
                "https://viacep.com.br/ws/12345678/json/",
                AddressResponseDTO.class
        );
    }

    @Test
    void shouldThrowExceptionWhenCepHasInvalidLength() {
        // Arrange
        String invalidCep = "1234"; // Less than 8 digits

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> addressService.findAddressByCep(invalidCep));

        verify(restTemplate, never()).getForObject(anyString(), eq(AddressResponseDTO.class));
    }

    @Test
    void shouldThrowExceptionWhenCepNotFound() {
        // Arrange
        AddressResponseDTO errorResponse = AddressResponseDTO.builder()
                .error(true)
                .build();

        when(restTemplate.getForObject(anyString(), eq(AddressResponseDTO.class)))
                .thenReturn(errorResponse);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> addressService.findAddressByCep("00000-000"));

        verify(restTemplate, times(1)).getForObject(
                "https://viacep.com.br/ws/00000000/json/",
                AddressResponseDTO.class
        );
    }

    @Test
    void shouldThrowExceptionWhenRestTemplateThrowsException() {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(AddressResponseDTO.class)))
                .thenThrow(new RuntimeException("Connection error"));

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> addressService.findAddressByCep("12345-678"));

        verify(restTemplate, times(1)).getForObject(anyString(), eq(AddressResponseDTO.class));
    }

    @Test
    void shouldHandleCepWithSpecialCharacters() {
        // Arrange
        AddressResponseDTO expectedAddress = createTestAddress();
        when(restTemplate.getForObject(anyString(), eq(AddressResponseDTO.class)))
                .thenReturn(expectedAddress);

        // Act
        AddressResponseDTO result = addressService.findAddressByCep("12.345-678");

        // Assert
        assertNotNull(result);
        assertEquals("12345-678", result.getCep());

        verify(restTemplate, times(1)).getForObject(
                "https://viacep.com.br/ws/12345678/json/",
                AddressResponseDTO.class
        );
    }

    @Test
    void shouldReturnAddressWithAllFields() {
        // Arrange
        AddressResponseDTO fullAddress = AddressResponseDTO.builder()
                .cep("01310-100")
                .street("Avenida Paulista")
                .neighborhood("Bela Vista")
                .city("São Paulo")
                .state("SP")
                .error(false)
                .build();

        when(restTemplate.getForObject(anyString(), eq(AddressResponseDTO.class)))
                .thenReturn(fullAddress);

        // Act
        AddressResponseDTO result = addressService.findAddressByCep("01310-100");

        // Assert
        assertNotNull(result);
        assertEquals("01310-100", result.getCep());
        assertEquals("Avenida Paulista", result.getStreet());
        assertEquals("Bela Vista", result.getNeighborhood());
        assertEquals("São Paulo", result.getCity());
        assertEquals("SP", result.getState());
        assertFalse(result.getError());
    }
}
