package com.stager.casamaisimoveis.models;

public class VisitaImovel {

    private Integer id;
    private String data_visita;
    private String retorno;
    private Integer captador_id;
    private Integer dados_imovel_id;

    public VisitaImovel(String data_visita, String retorno) {
        this.data_visita = data_visita;
        this.retorno = retorno;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData_visita() {
        return data_visita;
    }

    public void setData_visita(String data_visita) {
        this.data_visita = data_visita;
    }

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

    public Integer getCaptador_id() {
        return captador_id;
    }

    public void setCaptador_id(Integer captador_id) {
        this.captador_id = captador_id;
    }

    public Integer getDados_imovel_id() {
        return dados_imovel_id;
    }

    public void setDados_imovel_id(Integer dados_imovel_id) {
        this.dados_imovel_id = dados_imovel_id;
    }
}
