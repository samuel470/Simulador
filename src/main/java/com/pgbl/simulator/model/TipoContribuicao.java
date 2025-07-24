package com.pgbl.simulator.model;

/**
 * Enum que representa os tipos de contribuição para PGBL
 */
public enum TipoContribuicao {
    MENSAL("Mensal"),
    ANUAL("Anual");
    
    private final String descricao;
    
    TipoContribuicao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}

