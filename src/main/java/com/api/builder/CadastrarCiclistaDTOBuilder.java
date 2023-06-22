package com.api.builder;

import com.api.dto.CadastrarCiclistaDTO;
import com.api.enumerator.Nacionalidade;
import com.api.model.CartaoDeCredito;
import com.api.model.Ciclista;
import com.api.vo.Passaporte;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CadastrarCiclistaDTOBuilder {

    public static CadastrarCiclistaDTO build() {
        CadastrarCiclistaDTO cadastro = new CadastrarCiclistaDTO();

        Ciclista ciclista = new Ciclista();
        ciclista.setNome("Lucas");
        ciclista.setCpf("123.456.789-10");
        ciclista.setNacionalidade(Nacionalidade.BRASILEIRO);
        ciclista.setEmail("Lucas@gmail.com");
        ciclista.setUrlFotoDocumento("api.foto/img001");

        CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();
        cartaoDeCredito.setNumero("1234-5678-9012-3456");
        cartaoDeCredito.setNomeTitular("Lucas");
        cartaoDeCredito.setCcv("123");

        String dataNascimento = "22/02/1997";
        String dataValidadePassaporte = "15/10/2030";
        String dataValidadeCartao = "01/08/2032";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            ciclista.setNascimento(dateFormat.parse(dataNascimento));
            ciclista.setPassaporte(new Passaporte("123,", dateFormat.parse(dataValidadePassaporte), "BR"));
            cartaoDeCredito.setValidade(dateFormat.parse(dataValidadeCartao));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        cadastro.setCiclista(ciclista);
        cadastro.setMeioDePagamento(cartaoDeCredito);

        return cadastro;
    }

}
