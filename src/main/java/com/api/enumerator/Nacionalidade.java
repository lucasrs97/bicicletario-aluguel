package com.api.enumerator;

public enum Nacionalidade {

    BRASILEIRO("B", "Brasileiro"),
    ESTRANGEIRO("E", "Estrangeiro");

    private String codigo;
    private String descricao;

    Nacionalidade(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

}
