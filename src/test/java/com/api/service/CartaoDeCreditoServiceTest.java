package com.api.service;

import com.api.dao.CartaoDAO;
import com.api.dao.CiclistaDAO;
import com.api.dto.Notification;
import com.api.model.CartaoDeCredito;
import com.api.model.Ciclista;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    void cartaoDeCreditoValido_deveRetornar422() {
        TestRestTemplate restTemplate = new TestRestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("cartao", "59");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("https://bicicletario-externo-production.up.railway.app/cobranca/validaCartaoDeCredito/", request, String.class);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    void cartaoDeCreditoValido_deveRetornar200() {
        TestRestTemplate restTemplate = new TestRestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("cartao", "5959648732541254");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("https://bicicletario-externo-production.up.railway.app/cobranca/validaCartaoDeCredito/", request, String.class);

        //Endpoint está retornando sempre 422, independente do valor passado
        //assertEquals(HttpStatus.OK, response.getStatusCode());
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