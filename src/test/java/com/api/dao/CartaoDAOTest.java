package com.api.dao;

import com.api.model.CartaoDeCredito;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

@ExtendWith(SpringExtension.class)
public class CartaoDAOTest {

    @InjectMocks
    private CartaoDAO cartaoDAO;

    @Mock
    private Log log;

    @Test
    public void alterarCartao_naoDeveLancarException() {
        CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();
        cartaoDeCredito.setNomeTitular("Lucas");
        cartaoDeCredito.setNumero("1234567890");
        cartaoDeCredito.setValidade(new Date());
        cartaoDeCredito.setCcv("123");

        Assertions.assertDoesNotThrow(() -> cartaoDAO.alterarCartao(cartaoDeCredito));
    }

}
