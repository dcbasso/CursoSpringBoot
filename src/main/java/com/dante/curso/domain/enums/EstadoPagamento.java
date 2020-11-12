package com.dante.curso.domain.enums;

public enum EstadoPagamento {

    PENDENTE(1, "PENDENTE"),

    QUITADO(2, "QUITADO"),

    CANCELADO(3, "CANCELADO");

    private Integer codigo;

    private String descricao;

    EstadoPagamento(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static EstadoPagamento toEnum(final Integer codigo) {
        if (codigo == null) {
            return null;
        }
        for (final EstadoPagamento tc : EstadoPagamento.values()) {
            if (codigo.equals(tc.getCodigo())) {
                return tc;
            }
        }
        throw  new IllegalArgumentException("Invalid Código EstadoPagamento: " + codigo);
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
