package com.api.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class ConstantesUtilTest {

    @Test
    void testMensagemAtivacaoCadastro() {
        assertEquals("Clique no link abaixo para ativar seu cadastro.", Constantes.MENSAGEM_ATIVACAO_CADASTRO);
    }

    @Test
    void testDadosAlteradosSucesso() {
        assertEquals("Dados alterados com sucesso.", Constantes.DADOS_ALTERADOS_SUCESSO);
    }

    @Test
    void testCiclistaCadastradoSucesso() {
        assertEquals("Ciclista cadastrado com sucesso. Aguardando o e-mail ser confirmado.", Constantes.CICLISTA_CADASTRADO_SUCESSO);
    }

    @Test
    void testErroCadastrarCiclista() {
        assertEquals("Erro ao cadastrar o ciclista", Constantes.ERRO_CADASTRAR_CICLISTA);
    }

    @Test
    void testEmailConfirmadoSucesso() {
        assertEquals("E-mail confirmado com sucesso.", Constantes.EMAIL_CONFIRMADO_SUCESSO);
    }

    @Test
    void testErroAtivarCiclista() {
        assertEquals("Erro ao ativar o ciclista.", Constantes.ERRO_ATIVAR_CICLISATA);
    }

    @Test
    void testCiclistaAlteradoSucesso() {
        assertEquals("Ciclista alterado com sucesso.", Constantes.CICLISTA_ALTERADO_SUCESSO);
    }

    @Test
    void testErroAlterarCiclista() {
        assertEquals("Erro ao alterar o ciclista.", Constantes.ERRO_ALTERAR_CICLISTA);
    }

    @Test
    void testErroRecuperarCiclista() {
        assertEquals("Erro ao recuperar o ciclista.", Constantes.ERRO_RECUPERAR_CICLISTA);
    }

    @Test
    void testDadosCartaoAlteradosSucesso() {
        assertEquals("Dados do cartão alterados com sucesso", Constantes.DADOS_CARTAO_ALTERADOS_SUCESSO);
    }

    @Test
    void testErroAlterarDadosCartao() {
        assertEquals("Erro ao alterar os dados do cartão de crédito.", Constantes.ERRO_ALTERAR_DADOS_CARTAO);
    }

}
