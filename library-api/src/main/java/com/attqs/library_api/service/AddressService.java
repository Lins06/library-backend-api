package com.attqs.library_api.service;

import com.attqs.library_api.dto.AddressResponseDTO;
import com.attqs.library_api.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class AddressService {

    private static final String VIACEP_URL = "https://viacep.com.br/ws/";

    private final RestTemplate restTemplate;

    public AddressService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Busca informações de endereço pelo CEP usando ViaCEP API
     * @param cep CEP sem formatação (ex: 12345678)
     * @return AddressResponseDTO com os dados do endereço
     * @throws ResourceNotFoundException se o CEP não for encontrado
     */
    public AddressResponseDTO findAddressByCep(String cep) {
        try {
            // Remove caracteres não numéricos
            String cleanCep = cep.replaceAll("[^0-9]", "");

            if (cleanCep.length() != 8) {
                throw new ResourceNotFoundException("CEP deve ter 8 dígitos");
            }

            String url = VIACEP_URL + cleanCep + "/json/";
            AddressResponseDTO address = restTemplate.getForObject(url, AddressResponseDTO.class);

            if (address != null && Boolean.TRUE.equals(address.getError())) {
                throw new ResourceNotFoundException("CEP não encontrado: " + cep);
            }

            log.info("Endereço encontrado para CEP: {}", cep);
            return address;

        } catch (Exception e) {
            log.error("Erro ao buscar CEP: {}", cep, e);
            throw new ResourceNotFoundException("Erro ao buscar CEP: " + e.getMessage());
        }
    }
}
