package com.api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Test
    public void emailValido_deveRetornarTrue() {
        String email = "lucas@gmail.com";
        boolean valido = emailService.emailValido(email);
        Assertions.assertTrue(valido);

    }

    @Test
    public void emailValido_deveRetornarFalse() {
        String email = null;
        boolean valido = emailService.emailValido(email);
        Assertions.assertFalse(valido);
    }

    @Test
    public void enviarEmail_deveEnviarEmail() {
        String email = "lucas@gmail.com";
        String mensaegm = "E-mail enviado.";
        assertDoesNotThrow(() -> emailService.enviarEmail(email, mensaegm));
    }

}
