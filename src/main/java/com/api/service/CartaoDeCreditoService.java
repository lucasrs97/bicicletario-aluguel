package com.api.service;

import com.api.dao.CartaoDAO;
import com.api.dao.CiclistaDAO;
import com.api.model.CartaoDeCredito;
import com.api.model.Ciclista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    private static final String URL_EXTERNOS = "https://bicicletario-pm-production.up.railway.app/";


    public boolean cartaoDeCreditoInvalido(CartaoDeCredito cartaoDeCredito) {

        HttpEntity<String> request = new HttpEntity<>(cartaoDeCredito.getNumero());
        ResponseEntity<String> response = restTemplate
                .postForEntity(URL_EXTERNOS + "cobranca/validaCartaoDeCredito/", request, String.class);

        String body = response.getBody();
        System.out.println(body);
        return true;

    }

    public void alterar(CartaoDeCredito cartaoDeCredito, Long idCiclista) {
        Ciclista ciclista = dao.recuperarCiclista(idCiclista);

        // Integração 1
        if(cartaoDeCreditoInvalido(cartaoDeCredito)) {
            throw new IllegalArgumentException("Cartão de crédito reprovado.");
        }

        cartaoDAO.alterarCartao(cartaoDeCredito);

        // Integração 2
        emailService.enviarEmail(ciclista.getEmail(), "Dados do cartão alterados com sucesso.");
    }

}
