package com.api.dto;

import com.api.model.CartaoDeCredito;
import com.api.model.Ciclista;
import lombok.Data;

@Data
public class CadastrarCiclistaDTO {
    private Ciclista ciclista;
    private CartaoDeCredito meioDePagamento;
}
