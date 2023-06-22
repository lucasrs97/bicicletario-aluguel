package com.api.dto;

import com.api.model.CartaoDeCredito;
import com.api.model.Ciclista;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CadastrarCiclistaDTOTest {

    @Test
    public void getCiclista() {
        Ciclista ciclista = new Ciclista();
        CadastrarCiclistaDTO cadastrarCiclistaDTO = new CadastrarCiclistaDTO();

        cadastrarCiclistaDTO.setCiclista(ciclista);

        Assertions.assertEquals(ciclista, cadastrarCiclistaDTO.getCiclista());
    }

    @Test
    public void getSetMeioDePagamento() {
        CartaoDeCredito meioDePagamento = new CartaoDeCredito();
        CadastrarCiclistaDTO cadastrarCiclistaDTO = new CadastrarCiclistaDTO();

        cadastrarCiclistaDTO.setMeioDePagamento(meioDePagamento);

        Assertions.assertEquals(meioDePagamento, cadastrarCiclistaDTO.getMeioDePagamento());
    }

}
