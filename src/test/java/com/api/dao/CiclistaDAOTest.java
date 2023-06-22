package com.api.dao;

import com.api.enumerator.CiclistaStatus;
import com.api.enumerator.Nacionalidade;
import com.api.model.Ciclista;
import com.api.vo.Passaporte;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CiclistaDAOTest {

    @InjectMocks
    private CiclistaDAO ciclistaDAO;

    @Test
    public void salvarCiclista_naoDeveLancarException() {
        Ciclista ciclista = new Ciclista();
        ciclista.setNome("Lucas");

        assertDoesNotThrow(() -> ciclistaDAO.salvarCiclista(ciclista));
    }

    @Test
    public void alterarCiclista_naoDeveLancarException() {
        Ciclista ciclista = new Ciclista();
        ciclista.setNome("Lucas");

        assertDoesNotThrow(() -> ciclistaDAO.alterarCiclista(ciclista));
    }

    @Test
    public void recuperarCiclista_deveRecuperarCiclista() {
        Ciclista ciclista = new Ciclista();
        ciclista.setId(1L);
        ciclista.setStatus(CiclistaStatus.ATIVO);
        ciclista.setNome("Lucas");
        ciclista.setCpf("123.456.789-10");
        ciclista.setNacionalidade(Nacionalidade.BRASILEIRO);
        ciclista.setEmail("Lucas@gmail.com");
        ciclista.setUrlFotoDocumento("api.foto/img001");

        String dataNascimento = "22/02/1997";
        String dataValidadePassaporte = "15/10/2030";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            ciclista.setNascimento(dateFormat.parse(dataNascimento));
            ciclista.setPassaporte(new Passaporte("123,", dateFormat.parse(dataValidadePassaporte), "BR"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Ciclista ciclistaRecuperado = ciclistaDAO.recuperarCiclista(1L);

        assertEquals(ciclista.getId(), ciclistaRecuperado.getId());
        assertEquals(ciclista.getNome(), ciclistaRecuperado.getNome());
        assertEquals(ciclista.getEmail(), ciclistaRecuperado.getEmail());
        assertEquals(ciclista.getCpf(), ciclistaRecuperado.getCpf());
        assertEquals(ciclista.getStatus(), ciclistaRecuperado.getStatus());
        assertEquals(ciclista.getNascimento(), ciclistaRecuperado.getNascimento());
        assertEquals(ciclista.getPassaporte().getNumero(), ciclistaRecuperado.getPassaporte().getNumero());
        assertEquals(ciclista.getPassaporte().getValidade(), ciclistaRecuperado.getPassaporte().getValidade());
        assertEquals(ciclista.getPassaporte().getPais(), ciclistaRecuperado.getPassaporte().getPais());
        assertEquals(ciclista.getNacionalidade(), ciclistaRecuperado.getNacionalidade());
        assertEquals(ciclista.getUrlFotoDocumento(), ciclistaRecuperado.getUrlFotoDocumento());
    }

}
