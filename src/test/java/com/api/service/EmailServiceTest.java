package com.api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
public class EmailServiceTest {

    @Mock
    private EmailService emailService;

    @Test
    public void emailValido_deveRetornarTrue() {
        String email = "lucas@gmail.com";
        Mockito.when(emailService.emailValido(email)).thenReturn(true);
        boolean valido = emailService.emailValido(email);
        Assertions.assertTrue(valido);

    }

    @Test
    public void emailValido_deveRetornarFalse() {
        String email = null;
        Mockito.when(emailService.emailValido(email)).thenReturn(false);
        boolean valido = emailService.emailValido(email);
        Assertions.assertFalse(valido);
    }

    @Test
    public void enviarEmail_naoDeveLancarException() {
        String email = "lucas@gmail.com";
        String mensaegm = "E-mail enviado.";
        assertDoesNotThrow(() -> emailService.enviarEmail(email, mensaegm));
    }

    @Test
    public void enviarEmail_deveLancarException() {
        String email = "lucas@gmail.com";
        String mensaegm = "E-mail enviado.";

        Mockito.doThrow(IllegalArgumentException.class).when(emailService).enviarEmail(Mockito.anyString(), Mockito.anyString());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            emailService.enviarEmail(email, mensaegm);
        });

    }

}
