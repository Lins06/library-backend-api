package com.attqs.library_api.controller;

import com.attqs.library_api.dto.AddressResponseDTO;
import com.attqs.library_api.dto.LoginRequestDTO;
import com.attqs.library_api.dto.LoginResponseDTO;
import com.attqs.library_api.dto.RegisterRequestDTO;
import com.attqs.library_api.service.AddressService;
import com.attqs.library_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final AddressService addressService;

    public AuthController(UserService userService, AddressService addressService) {
        this.userService = userService;
        this.addressService = addressService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(userService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validate() {
        return ResponseEntity.ok("Autenticação válida");
    }

    @GetMapping("/address/{cep}")
    public ResponseEntity<AddressResponseDTO> searchByCep(@PathVariable String cep) {
        return ResponseEntity.ok(addressService.findAddressByCep(cep));
    }
}
