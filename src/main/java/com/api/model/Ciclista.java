package com.api.model;

import com.api.enumerator.CiclistaStatus;
import com.api.enumerator.Nacionalidade;
import com.api.vo.Passaporte;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ciclista {
    private Long id;
    private CiclistaStatus status;
    private String nome;
    private Date nascimento;
    private String cpf;
    private Passaporte passaporte;
    private Nacionalidade nacionalidade;
    private String email;
    private String urlFotoDocumento;

}
