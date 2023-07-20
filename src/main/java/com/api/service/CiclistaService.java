package com.api.service;

import com.api.dao.CartaoDAO;
import com.api.dao.CiclistaDAO;
import com.api.dto.CadastrarCiclistaDTO;
import com.api.enumerator.CiclistaStatus;
import com.api.model.Ciclista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.api.util.Constantes.*;

@Service
public class CiclistaService {

    @Autowired
    private CartaoDeCreditoService cartaoDeCreditoService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CiclistaDAO dao;

    @Autowired
    private CartaoDAO cartaoDAO;

    /**
     * Use Case 01
     * @param cadastrarCiclistaDTO
     */
    public void cadastrarCiclista(CadastrarCiclistaDTO cadastrarCiclistaDTO) {
        if(cadastrarCiclistaDTO == null) {
            throw new IllegalArgumentException(ERRO_CADASTRAR_CICLISTA);
        }

        if(cadastrarCiclistaDTO.getCiclista() == null || cadastrarCiclistaDTO.getMeioDePagamento() == null) {
            throw new IllegalArgumentException(ERRO_CADASTRAR_CICLISTA);
        }

        Ciclista ciclista = cadastrarCiclistaDTO.getCiclista();
        String numeroCartaoCredito = cadastrarCiclistaDTO.getMeioDePagamento().getNumero();

        if(!emailService.emailValido(ciclista.getEmail())) {
            throw new IllegalArgumentException(ERRO_CADASTRAR_CICLISTA);
        }

        // Integração 1
        if(!cartaoDeCreditoService.cartaoDeCreditoValido(numeroCartaoCredito)) {
            throw new IllegalArgumentException(ERRO_CADASTRAR_CICLISTA);
        }

        if (ciclista.getNome() == null
                || ciclista.getNascimento() == null
                || ciclista.getCpf() == null
                || ciclista.getPassaporte() == null
                || ciclista.getNacionalidade () == null
                || ciclista.getEmail() == null
                || ciclista.getUrlFotoDocumento() == null) {
            throw new IllegalArgumentException(ERRO_CADASTRAR_CICLISTA);
        }

        ciclista.setStatus(CiclistaStatus.AGUARDANDO_CONFIRMACAO);

        // Integração 2
        emailService.enviarEmail(ciclista.getEmail(), MENSAGEM_ATIVACAO_CADASTRO);
        dao.salvarCiclista(ciclista);
    }

    /**
     * Use Case 02
     * @param idCiclista
     */
    public void ativarCiclista(Long idCiclista) {
        Ciclista ciclista = recuperarCiclista(idCiclista);

        ciclista.setStatus(CiclistaStatus.ATIVO);
    }

    /**
     * Use Case 03
     * @param idCiclista
     * @param trancaInicio
     */
    public void alugarBicicleta(Long idCiclista, Long trancaInicio) {
        System.out.println("Bicicleta Liberada.");
    }

    /**
     * Use Case 04
     * @param idCiclista
     * @param trancaInicio
     */
    public void devolverBicicleta(Long idCiclista, Long trancaInicio) {
        System.out.println("Bicicleta devolvida.");
    }

    /**
     * Use Case 06
     * @param ciclista
     */
    public void alterarCiclista(Ciclista ciclista) {
        if(ciclista == null) {
            throw new IllegalArgumentException(ERRO_ALTERAR_CICLISTA);
        }

        dao.alterarCiclista(ciclista);

        // Integração 2
        emailService.enviarEmail(ciclista.getEmail(), DADOS_ALTERADOS_SUCESSO);
    }

    public Ciclista recuperarCiclista(Long idCiclista) {
        if(idCiclista == null) {
            throw new IllegalArgumentException(ERRO_RECUPERAR_CICLISTA);
        }

        return dao.recuperarCiclista(idCiclista);
    }

}
