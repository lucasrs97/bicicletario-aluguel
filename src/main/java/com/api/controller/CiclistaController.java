package com.api.controller;

import com.api.dto.CadastrarCiclistaDTO;
import com.api.model.Ciclista;
import com.api.service.CiclistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.api.util.Constantes.*;

@RestController
@RequestMapping("/ciclista")
public class CiclistaController {

    @Autowired
    private CiclistaService ciclistaService;

    /**
     * Use Case 01
     * @param cadastro
     * @return
     */
    @PostMapping
    public ResponseEntity<String> cadastrarCiclista(@RequestBody CadastrarCiclistaDTO cadastro) {
        try {
            this.ciclistaService.cadastrarCiclista(cadastro);
            return new ResponseEntity<>(CICLISTA_CADASTRADO_SUCESSO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(ERRO_CADASTRAR_CICLISTA, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Use Case 02
     * @param idCiclista
     * @return
     */
    @PostMapping("/{idCiclista}/ativar")
    public ResponseEntity<String> confirmarEmail(@PathVariable Long idCiclista) {
        try {
            this.ciclistaService.ativarCiclista(idCiclista);
            return new ResponseEntity<>(EMAIL_CONFIRMADO_SUCESSO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(ERRO_ATIVAR_CICLISATA, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Use Case 03
     * @param idCiclista
     * @param trancaInicio
     * @return
     */
    @PostMapping("/{idCiclista}/{trancaInicio}")
    public ResponseEntity<String> alugarBicicleta(@PathVariable Long idCiclista, Long trancaInicio) {
        try {
            this.ciclistaService.alugarBicicleta(idCiclista, trancaInicio);
            return new ResponseEntity<>(BICICLETA_LIBERADA, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(ERRO_ALUGAR_BICICLETA, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Use Case 04
     * @param idCiclista
     * @param idTraca
     * @return
     */
    @PostMapping("/{idCiclista}/{trancaInicio}")
    public ResponseEntity<String> devolverBicicleta(@PathVariable Long idCiclista, Long idTraca) {
        try {
            this.ciclistaService.devolverBicicleta(idCiclista, idTraca);
            return new ResponseEntity<>(BICICLETA_DEVOLVIDA, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(ERRO_DEVOLVER_BICICLETA, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Use Case 06
     * @param ciclista
     * @return
     */
    @PutMapping
    public ResponseEntity<String> alterarDadosCiclista(@RequestBody Ciclista ciclista) {
        try {
            this.ciclistaService.alterarCiclista(ciclista);
            return new ResponseEntity<>(CICLISTA_ALTERADO_SUCESSO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(ERRO_ALTERAR_CICLISTA, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
