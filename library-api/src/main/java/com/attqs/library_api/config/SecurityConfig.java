package com.attqs.library_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Aplica as regras de liberação de porta do seu CorsConfig.java
            .cors(Customizer.withDefaults())
            
            // 2. Desabilita o CSRF (obrigatório para APIs REST integradas com React)
            .csrf(csrf -> csrf.disable())
            
            // 3. Configura o acesso das rotas
            .authorizeHttpRequests(auth -> auth
                // Libera as requisições de teste que o navegador faz automaticamente (OPTIONS)
                .requestMatchers(org.springframework.web.cors.CorsUtils::isPreFlightRequest).permitAll()
                
                // Libera os endpoints de autenticação (registro, login, validação)
                .requestMatchers("/api/auth/**").permitAll() 
                
                // Libera a raiz (/api/books) para POST/GET e sub-rotas para buscas/deletar
                .requestMatchers("/api/books", "/api/books/**").permitAll()
                
                // Qualquer outra requisição precisará de autenticação
                .anyRequest().authenticated()
            )
            
            // 4. Desabilita as telas e popups de login padrão do Spring
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable());

        return http.build();
    }
}