package com.dante.curso.domain.enums;

public enum TipoCliente {

    PESSOA_FISICA(1, "PESSOA_FISICA"),

    PESSOA_JURIDICA(2, "PESSOA_JURIDICA");

    private Integer codigo;

    private String descricao;

    TipoCliente(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static TipoCliente toEnum(final Integer codigo) {
        if (codigo == null) {
            return null;
        }
        for (final TipoCliente tc : TipoCliente.values()) {
            if (codigo.equals(tc.getCodigo())) {
                return tc;
            }
        }
        throw  new IllegalArgumentException("Invalid Código TipoCliente: " + codigo);
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
