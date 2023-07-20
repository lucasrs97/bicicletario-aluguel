package com.api.service;

import com.api.dao.CartaoDAO;
import com.api.dao.CiclistaDAO;
import com.api.model.CartaoDeCredito;
import com.api.model.Ciclista;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CartaoDeCreditoServiceTest {

    @InjectMocks
    private CartaoDeCreditoService cartaoDeCreditoService;

    @Mock
    private CiclistaDAO ciclistaDAO;

    @Mock
    private CartaoDAO cartaoDAO;

    @Mock
    private EmailService emailService;

    @Test
    void cartaoDeCreditoValido_deveRetornarTrueOuFalse() {
        CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();
        cartaoDeCredito.setNumero("4539148803436467");
        assertTrue(cartaoDeCreditoService.cartaoDeCreditoValido(cartaoDeCredito.getNumero()));
    }

    @Test
    void alterarCartao_comDadosInvalidos_deveAlterarComSucesso() {
        CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();
        Long idCiclista = 1L;

        Ciclista ciclista = new Ciclista();
        String email = "lucas@email.com";
        ciclista.setEmail(email);

        when(ciclistaDAO.recuperarCiclista(idCiclista)).thenReturn(ciclista);

        assertThrows(IllegalArgumentException.class, () -> cartaoDeCreditoService.alterar(cartaoDeCredito, idCiclista));
        verify(cartaoDAO, never()).alterarCartao(cartaoDeCredito);
        verify(emailService, never()).enviarEmail(email, "Dados do cartão alterados com sucesso.");
    }

    @Test
    void alterarCartao_comDadosValidos_deveAlterarComSucesso() {
        CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();
        Long idCiclista = 1L;

        Ciclista ciclista = new Ciclista();
        String email = "lucas@email.com";
        ciclista.setEmail(email);

        cartaoDeCredito.setNomeTitular("Lucas");
        cartaoDeCredito.setNumero("1234567890");
        cartaoDeCredito.setValidade(new Date());
        cartaoDeCredito.setCcv("123");

        when(ciclistaDAO.recuperarCiclista(idCiclista)).thenReturn(ciclista);

        assertDoesNotThrow(() -> cartaoDeCreditoService.alterar(cartaoDeCredito, idCiclista));
        verify(cartaoDAO, times(1)).alterarCartao(cartaoDeCredito);
        verify(emailService, times(1)).enviarEmail(email, "Dados do cartão alterados com sucesso.");
    }

}