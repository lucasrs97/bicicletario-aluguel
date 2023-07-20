package com.api.controller;

import com.api.model.CartaoDeCredito;
import com.api.service.CartaoDeCreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.api.util.Constantes.DADOS_CARTAO_ALTERADOS_SUCESSO;
import static com.api.util.Constantes.ERRO_ALTERAR_DADOS_CARTAO;

@RestController("/cartaoDeCredito")
public class CartaoDeCreditoController {

    @Autowired
    private CartaoDeCreditoService cartaoDeCreditoService;

    /**
     * Use Case 07
     * @param cartaoDeCredito
     * @param idCiclista
     * @return
     */
    @PutMapping("/{idCiclista}")
    public ResponseEntity<String> alterarCartao(@RequestBody CartaoDeCredito cartaoDeCredito, @PathVariable Long idCiclista) {
        try {
            this.cartaoDeCreditoService.alterar(cartaoDeCredito, idCiclista);
            return new ResponseEntity<>(DADOS_CARTAO_ALTERADOS_SUCESSO, HttpStatus.OK);
        }  catch (IllegalArgumentException e) {
            return new ResponseEntity<>(ERRO_ALTERAR_DADOS_CARTAO, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
