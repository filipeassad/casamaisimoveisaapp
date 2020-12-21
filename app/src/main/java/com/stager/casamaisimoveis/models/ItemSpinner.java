package com.stager.casamaisimoveis.models;

public class ItemSpinner {

    private Integer id;
    private String descricao;
    private Integer quantidade;

    public ItemSpinner(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public ItemSpinner(Integer id, String descricao, Integer quantidade) {
        this.id = id;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return descricao;
    }
}