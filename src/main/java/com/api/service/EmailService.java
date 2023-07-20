package com.api.service;

import com.api.dto.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class EmailService {

    private static final String URL_EXTERNO = "https://bicicletario-pm-production.up.railway.app/";

    @Autowired
    private RestTemplate restTemplate;

    public void enviarEmail(String email, String mensagem) {
        HttpEntity<Notification> request = new HttpEntity<>(new Notification(email, "Cadastro de Ciclista", mensagem));
        ResponseEntity<Notification> response = restTemplate
                .postForEntity(URL_EXTERNO + "notificacao/enviar-email/", request, Notification.class);

        Notification body = response.getBody();
        System.out.println(body);
    }

    public boolean emailValido(String email) {
        return email != null;
    }

}
