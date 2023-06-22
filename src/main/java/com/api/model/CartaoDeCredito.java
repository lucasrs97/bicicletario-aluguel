package com.api.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartaoDeCredito {

    private Long id;
    private String nomeTitular;
    private String numero;
    private Date validade;
    private String ccv;

}
