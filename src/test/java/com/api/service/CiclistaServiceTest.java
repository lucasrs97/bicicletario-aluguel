package com.api.service;

import com.api.dao.CiclistaDAO;
import com.api.dto.CadastrarCiclistaDTO;
import com.api.enumerator.CiclistaStatus;
import com.api.model.CartaoDeCredito;
import com.api.model.Ciclista;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.api.util.Constantes.DADOS_ALTERADOS_SUCESSO;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CiclistaServiceTest {

    @Mock
    private CadastrarCiclistaDTO cadastrarCiclistaDTO;

    @Mock
    private Ciclista ciclista;

    @Mock
    private CartaoDeCredito cartaoDeCredito;

    @Mock
    private EmailService emailService;

    @Mock
    private CartaoDeCreditoService cartaoDeCreditoService;

    @Mock
    private CiclistaDAO dao;

    @InjectMocks
    private CiclistaService ciclistaService;

    @Test
    void cadastrarCiclista_quandoadastrarCiclistaDTOForNulo_deveLancarExcecao() {
        CadastrarCiclistaDTO cadastrarCiclistaDTO = null;

        assertThrows(IllegalArgumentException.class, () -> ciclistaService.cadastrarCiclista(cadastrarCiclistaDTO));

        verifyNoInteractions(emailService);
        verifyNoInteractions(cartaoDeCreditoService);
        verifyNoInteractions(dao);
    }

    @Test
    void cadastrarCiclista_quandoadastrarCiclistaDTOForParcialmenteNulo_deveLancarExcecao() {
        CadastrarCiclistaDTO cadastrarCiclistaDTO = new CadastrarCiclistaDTO();

        Ciclista ciclista = new Ciclista();
        ciclista.setNome("Lucas");
        cadastrarCiclistaDTO.setCiclista(ciclista);

        assertThrows(IllegalArgumentException.class, () -> ciclistaService.cadastrarCiclista(cadastrarCiclistaDTO));

        verifyNoInteractions(emailService);
        verifyNoInteractions(cartaoDeCreditoService);
        verifyNoInteractions(dao);
    }

    @Test
    void cadastrarCiclista_quandoEmailForInvalido_deveLancarExcecao() {
        when(cadastrarCiclistaDTO.getCiclista()).thenReturn(ciclista);
        when(cadastrarCiclistaDTO.getMeioDePagamento()).thenReturn(cartaoDeCredito);
        when(emailService.emailValido(ciclista.getEmail())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> ciclistaService.cadastrarCiclista(cadastrarCiclistaDTO));

        verify(emailService).emailValido(ciclista.getEmail());
        verifyNoMoreInteractions(emailService);
        verifyNoInteractions(cartaoDeCreditoService);
        verifyNoInteractions(dao);
    }

    @Test
    void cadastrarCiclista_quandoCartaoDeCreditoForInvalido_deveLancarExcecao() {
        when(cadastrarCiclistaDTO.getCiclista()).thenReturn(ciclista);
        when(cadastrarCiclistaDTO.getMeioDePagamento()).thenReturn(cartaoDeCredito);
        when(emailService.emailValido(ciclista.getEmail())).thenReturn(true);
        when(cartaoDeCreditoService.cartaoDeCreditoInvalido(cartaoDeCredito)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> ciclistaService.cadastrarCiclista(cadastrarCiclistaDTO));

        verify(emailService).emailValido(ciclista.getEmail());
        verify(cartaoDeCreditoService).cartaoDeCreditoInvalido(cartaoDeCredito);
        verifyNoMoreInteractions(emailService);
        verifyNoMoreInteractions(cartaoDeCreditoService);
        verifyNoInteractions(dao);
    }

    @Test
    void ativarCiclista_naoDeveLancarException_quandoStatusPendente() {
        Ciclista ciclista = new Ciclista();
        ciclista.setId(1L);
        ciclista.setNome("Lucas");
        ciclista.setStatus(CiclistaStatus.AGUARDANDO_CONFIRMACAO);

        when(dao.recuperarCiclista(1L)).thenReturn(ciclista);

        Assertions.assertDoesNotThrow(() -> {
            ciclistaService.ativarCiclista(1L);
        });
    }

    @Test
    void ativarCiclista_deveLancarException_quandoStatusNaoPendente() {
        Ciclista ciclista = new Ciclista();
        ciclista.setId(1L);
        ciclista.setNome("Lucas");
        ciclista.setStatus(CiclistaStatus.ATIVO);

        when(dao.recuperarCiclista(anyLong())).thenReturn(ciclista);

        Assertions.assertThrows(IllegalArgumentException.class, () -> ciclistaService.ativarCiclista(1L));
    }

    @Test
    void alterarCiclista_deveLancarException_quandoCiclistaNulo() {
        Ciclista ciclista = null;
        assertThrows(IllegalArgumentException.class, () -> ciclistaService.alterarCiclista(ciclista));
    }

    @Test
    void alterarCiclista_deveAlterarComSucesso() {
        Ciclista ciclista = new Ciclista();
        ciclista.setId(1L);
        ciclista.setNome("Lucas");
        ciclista.setEmail("lucas@gmail.com");

        Mockito.doNothing().when(dao).alterarCiclista(ciclista);
        Mockito.doNothing().when(emailService).enviarEmail(ciclista.getEmail(), DADOS_ALTERADOS_SUCESSO);

        ciclistaService.alterarCiclista(ciclista);

        verify(dao, Mockito.times(1)).alterarCiclista(ciclista);
        verify(emailService, Mockito.times(1)).enviarEmail(ciclista.getEmail(), DADOS_ALTERADOS_SUCESSO);
    }

    @Test
    void recuperarCiclista_deveLancarException_quandoIdCiclistaForNulo() {
        Long idCiclista = null;
        assertThrows(IllegalArgumentException.class, () -> ciclistaService.recuperarCiclista(idCiclista));
    }

    @Test
    void recuperarCiclista_naoDeveLancarException_quandoIdCiclistaForValido() {
        Long idCiclista = 1L;
        Ciclista ciclista = new Ciclista();
        ciclista.setNome("Lucas");
        ciclista.setId(idCiclista);

        when(dao.recuperarCiclista(idCiclista)).thenReturn(ciclista);
        ciclistaService.recuperarCiclista(idCiclista);
        verify(dao, Mockito.times(1)).recuperarCiclista(idCiclista);
    }
}
