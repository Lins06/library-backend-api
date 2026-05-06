package com.attqs.library_api.service;

import com.attqs.library_api.dto.LoginRequestDTO;
import com.attqs.library_api.dto.LoginResponseDTO;
import com.attqs.library_api.dto.RegisterRequestDTO;
import com.attqs.library_api.exception.EmailAlreadyExistsException;
import com.attqs.library_api.exception.InvalidCredentialsException;
import com.attqs.library_api.exception.UserNotFoundException;
import com.attqs.library_api.model.User;
import com.attqs.library_api.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public LoginResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email já cadastrado no sistema");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        return buildLoginResponse(savedUser, "Registrado com sucesso");
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Email ou senha inválidos");
        }

        if (!user.isActive()) {
            throw new InvalidCredentialsException("Usuário inativo");
        }

        return buildLoginResponse(user, "Login realizado com sucesso");
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    public User updateUser(String id, String name) {
        User user = getUserById(id);
        user.setName(name);
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Usuário não encontrado");
        }
        userRepository.deleteById(id);
    }

    private LoginResponseDTO buildLoginResponse(User user, String message) {
        String token = UUID.randomUUID().toString();
        return LoginResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .active(user.isActive())
                .message(message)
                .token(token)
                .build();
    }
}
