package com.api.model;

import com.api.enumerator.CiclistaStatus;
import com.api.enumerator.Nacionalidade;
import com.api.vo.Passaporte;
import lombok.*;

import java.util.Date;

@Getter
@Setter
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
