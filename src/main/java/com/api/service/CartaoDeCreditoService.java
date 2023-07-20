package com.api.service;

import com.api.dao.CartaoDAO;
import com.api.dao.CiclistaDAO;
import com.api.model.CartaoDeCredito;
import com.api.model.Ciclista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartaoDeCreditoService {

    @Autowired
    private CiclistaDAO dao;

    @Autowired
    private CartaoDAO cartaoDAO;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${external.api.url}")
    private String externalApiUrl;

    public boolean cartaoDeCreditoValido(String numeroCartao) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("cartao", numeroCartao);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            restTemplate.postForEntity(externalApiUrl + "cobranca/validaCartaoDeCredito/", request, String.class);
            return true;
        } catch (HttpClientErrorException ex) {
            System.err.println("Erro ao tentar validar os dados do cartão. (erro " + ex.getStatusCode() + "): " + ex.getResponseBodyAsString());
            return false;
        }
    }

    public void alterar(CartaoDeCredito cartaoDeCredito, Long idCiclista) {
        Ciclista ciclista = dao.recuperarCiclista(idCiclista);

        // Integração 1
        if(cartaoDeCreditoValido(cartaoDeCredito.getNumero())) {
            throw new IllegalArgumentException("Cartão de crédito reprovado.");
        }

        cartaoDAO.alterarCartao(cartaoDeCredito);

        // Integração 2
        emailService.enviarEmail(ciclista.getEmail(), "Dados do cartão alterados com sucesso.");
    }

}
