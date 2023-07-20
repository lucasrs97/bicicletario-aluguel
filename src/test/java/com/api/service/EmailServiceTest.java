package com.api.service;

import com.api.dto.Notification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Test
    void emailValido_deveRetornarTrue() {
        String email = "lucas@gmail.com";
        boolean valido = emailService.emailValido(email);
        Assertions.assertTrue(valido);

    }

    @Test
    void emailValido_deveRetornarFalse() {
        String email = null;
        boolean valido = emailService.emailValido(email);
        Assertions.assertFalse(valido);
    }

    @Test
    void enviarEmailIntegracao_deveEnviarEmail() {
        TestRestTemplate restTemplate = new TestRestTemplate();

        String email = "lucas@gmail.com";
        String mensagem = "E-mail enviado.";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Notification notification = new Notification(email, "Cadastro de Ciclista", mensagem);
        HttpEntity<Notification> request = new HttpEntity<>(notification, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("https://bicicletario-externo-production.up.railway.app/notificacao/enviar-email/", request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
