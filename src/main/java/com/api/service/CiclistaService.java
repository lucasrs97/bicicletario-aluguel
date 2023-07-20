package com.api.service;

import com.api.dao.CartaoDAO;
import com.api.dao.CiclistaDAO;
import com.api.dto.CadastrarCiclistaDTO;
import com.api.enumerator.CiclistaStatus;
import com.api.model.CartaoDeCredito;
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

    public void cadastrarCiclista(CadastrarCiclistaDTO cadastrarCiclistaDTO) {
        if(cadastrarCiclistaDTO == null) {
            throw new IllegalArgumentException(ERRO_CADASTRAR_CICLISTA);
        }

        if(cadastrarCiclistaDTO.getCiclista() == null || cadastrarCiclistaDTO.getMeioDePagamento() == null) {
            throw new IllegalArgumentException(ERRO_CADASTRAR_CICLISTA);
        }

        Ciclista ciclista = cadastrarCiclistaDTO.getCiclista();
        CartaoDeCredito cartaoDeCredito = cadastrarCiclistaDTO.getMeioDePagamento();

        if(!emailService.emailValido(ciclista.getEmail())) {
            throw new IllegalArgumentException(ERRO_CADASTRAR_CICLISTA);
        }

        // Integração 1
        if(cartaoDeCreditoService.cartaoDeCreditoInvalido(cartaoDeCredito)) {
            throw new IllegalArgumentException(ERRO_CADASTRAR_CICLISTA);
        }
        this.validarDados(ciclista);

        ciclista.setStatus(CiclistaStatus.AGUARDANDO_CONFIRMACAO);

        dao.salvarCiclista(ciclista);

        // Integração 2
        emailService.enviarEmail(ciclista.getEmail(), MENSAGEM_ATIVACAO_CADASTRO);

    }

    private void validarDados(Ciclista ciclista) {
        if (ciclista.getNome() == null
                || ciclista.getNascimento() == null
                || ciclista.getCpf() == null
                || ciclista.getPassaporte() == null
                || ciclista.getNacionalidade () == null
                || ciclista.getEmail() == null
                || ciclista.getUrlFotoDocumento() == null) {
            throw new IllegalArgumentException(ERRO_CADASTRAR_CICLISTA);
        }
    }

    public void ativarCiclista(Long idCiclista) {
        Ciclista ciclista = recuperarCiclista(idCiclista);
        if(!registroPendente(ciclista.getStatus())) {
            throw new IllegalArgumentException(ERRO_ATIVAR_CICLISATA);
        }

        ciclista.setStatus(CiclistaStatus.ATIVO);
    }

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

    private boolean registroPendente(CiclistaStatus status) {
        return status == CiclistaStatus.AGUARDANDO_CONFIRMACAO;
    }

}
