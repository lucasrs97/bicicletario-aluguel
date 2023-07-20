package com.api.service;

import com.api.dto.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${external.api.url}")
    private String externalApiUrl;

    public void enviarEmail(String email, String mensagem) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Notification notification = new Notification(email, "Cadastro de Ciclista", mensagem);
            HttpEntity<Notification> request = new HttpEntity<>(notification, headers);

            restTemplate.postForEntity(externalApiUrl + "notificacao/enviar-email/", request, String.class);
        } catch (HttpClientErrorException ex) {
            System.err.println("Erro ao tentar enviar o e-mail. (erro " + ex.getStatusCode() + "): " + ex.getResponseBodyAsString());
        }
    }

    public boolean emailValido(String email) {
        return email != null;
    }

}
