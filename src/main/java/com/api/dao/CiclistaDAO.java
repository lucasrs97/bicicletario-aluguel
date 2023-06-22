package com.api.dao;

import com.api.enumerator.CiclistaStatus;
import com.api.enumerator.Nacionalidade;
import com.api.model.Ciclista;
import com.api.vo.Passaporte;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Slf4j
@Repository
public class CiclistaDAO {

    public void salvarCiclista(Ciclista ciclista) {
        log.info("Salvando o ciclista: " + ciclista.toString());
    }

    public void alterarCiclista(Ciclista ciclista) {
        log.info("Alterando o ciclista: " + ciclista.toString());
    }

    public Ciclista recuperarCiclista(Long id) {
        Ciclista ciclista = new Ciclista();
        ciclista.setId(id);
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

        return ciclista;
    }
}
