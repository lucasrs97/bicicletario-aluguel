package com.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.api.util.Constantes.ERRO_ENVIAR_EMAIL;

@Slf4j
@Service
public class EmailService {

    public void enviarEmail(String email, String mensagem) {
        try {
            log.info("E-mail enviado com sucesso. " + "\n"
                    + "Destinatário: " + email + '\n'
                    + "Mensagem: " + mensagem + '\n');
        } catch (Exception e) {
            throw new IllegalArgumentException(ERRO_ENVIAR_EMAIL);
        }
    }

    public boolean emailValido(String email) {
        return email != null;
    }

}
