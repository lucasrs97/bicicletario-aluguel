package com.api.service;

import com.api.dao.CartaoDAO;
import com.api.dao.CiclistaDAO;
import com.api.model.CartaoDeCredito;
import com.api.model.Ciclista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartaoDeCreditoService {

    @Autowired
    private CiclistaDAO dao;

    @Autowired
    private CartaoDAO cartaoDAO;

    @Autowired
    private EmailService emailService;

    public boolean cartaoDeCreditoInvalido(CartaoDeCredito cartaoDeCredito) {
        return cartaoDeCredito.getNomeTitular() == null
                || cartaoDeCredito.getNumero() == null
                || cartaoDeCredito.getValidade() == null
                || cartaoDeCredito.getCcv() == null;
    }

    public void alterar(CartaoDeCredito cartaoDeCredito, Long idCiclista) {
        Ciclista ciclista = dao.recuperarCiclista(idCiclista);

        // [A2] Cartão reprovado
        if(cartaoDeCreditoInvalido(cartaoDeCredito)) {
            throw new IllegalArgumentException("Cartão de crédito reprovado.");
        }

        cartaoDAO.alterarCartao(cartaoDeCredito);
        emailService.enviarEmail(ciclista.getEmail(), "Dados do cartão alterados com sucesso.");
    }

}
