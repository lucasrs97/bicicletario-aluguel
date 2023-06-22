package com.api.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CartaoDeCreditoTest {

    @Test
    public void testa_construtor_e_gets() {
        Long id = 1L;
        String nomeTitular = "Lucas";
        String numero = "1234567890123456";
        Date validade = new Date();
        String ccv = "123";

        CartaoDeCredito cartaoDeCredito = new CartaoDeCredito(id, nomeTitular, numero, validade, ccv);

        Assertions.assertEquals(id, cartaoDeCredito.getId());
        Assertions.assertEquals(nomeTitular, cartaoDeCredito.getNomeTitular());
        Assertions.assertEquals(numero, cartaoDeCredito.getNumero());
        Assertions.assertEquals(validade, cartaoDeCredito.getValidade());
        Assertions.assertEquals(ccv, cartaoDeCredito.getCcv());
    }

    @Test
    public void testa_sets() {
        CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();

        Long id = 1L;
        cartaoDeCredito.setId(id);

        String nomeTitular = "Lucas";
        cartaoDeCredito.setNomeTitular(nomeTitular);

        String numero = "1234567890123456";
        cartaoDeCredito.setNumero(numero);

        Date validade = new Date();
        cartaoDeCredito.setValidade(validade);

        String ccv = "123";
        cartaoDeCredito.setCcv(ccv);

        Assertions.assertEquals(id, cartaoDeCredito.getId());
        Assertions.assertEquals(nomeTitular, cartaoDeCredito.getNomeTitular());
        Assertions.assertEquals(numero, cartaoDeCredito.getNumero());
        Assertions.assertEquals(validade, cartaoDeCredito.getValidade());
        Assertions.assertEquals(ccv, cartaoDeCredito.getCcv());
    }

}
