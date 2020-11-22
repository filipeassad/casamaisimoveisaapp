package com.stager.casamaisimoveis.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DadosImovel {

    private Integer id;
    private boolean divulgacao;
    private boolean placa;
    private boolean exclusividade;
    private boolean autorizacao_ate_venda;
    private Integer tipo;
    private String valor;
    private String honorario;
    private Integer fase_obra;
    private String area_terreno;
    private String area_construida;
    private String observacao;
    private List<Composicao> composicoes;
    private VisitaImovel visitaImovel;

    public DadosImovel(boolean divulgacao, boolean placa, boolean exclusividade, boolean autorizacao_ate_venda) {
        this.divulgacao = divulgacao;
        this.placa = placa;
        this.exclusividade = exclusividade;
        this.autorizacao_ate_venda = autorizacao_ate_venda;
    }

    public DadosImovel(Integer tipo, Integer fase_obra, String valor, String honorario, String area_terreno, String area_construida, String observacao) {
        this.tipo = tipo;
        this.valor = valor;
        this.honorario = honorario;
        this.fase_obra = fase_obra;
        this.area_terreno = area_terreno;
        this.area_construida = area_construida;
        this.observacao = observacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isDivulgacao() {
        return divulgacao;
    }

    public void setDivulgacao(boolean divulgacao) {
        this.divulgacao = divulgacao;
    }

    public boolean isPlaca() {
        return placa;
    }

    public void setPlaca(boolean placa) {
        this.placa = placa;
    }

    public boolean isExclusividade() {
        return exclusividade;
    }

    public void setExclusividade(boolean exclusividade) {
        this.exclusividade = exclusividade;
    }

    public boolean isAutorizacao_ate_venda() {
        return autorizacao_ate_venda;
    }

    public void setAutorizacao_ate_venda(boolean autorizacao_ate_venda) {
        this.autorizacao_ate_venda = autorizacao_ate_venda;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getHonorario() {
        return honorario;
    }

    public void setHonorario(String honorario) {
        this.honorario = honorario;
    }

    public Integer getFase_obra() {
        return fase_obra;
    }

    public void setFase_obra(Integer fase_obra) {
        this.fase_obra = fase_obra;
    }

    public String getArea_terreno() {
        return area_terreno;
    }

    public void setArea_terreno(String area_terreno) {
        this.area_terreno = area_terreno;
    }

    public String getArea_construida() {
        return area_construida;
    }

    public void setArea_construida(String area_construida) {
        this.area_construida = area_construida;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<Composicao> getComposicoes() {
        return composicoes;
    }

    public void setComposicoes(List<Composicao> composicoes) {
        this.composicoes = composicoes;
    }

    public VisitaImovel getVisitaImovel() {
        return visitaImovel;
    }

    public void setVisitaImovel(VisitaImovel visitaImovel) {
        this.visitaImovel = visitaImovel;
    }

    public void setDadosImovel(Integer tipo, Integer fase_obra, String valor, String honorario, String area_terreno, String area_construida, String observacao){
        this.tipo = tipo;
        this.fase_obra = fase_obra;
        this.valor = valor;
        this.honorario = honorario;
        this.area_terreno = area_terreno;
        this.area_construida = area_construida;
        this.observacao = observacao;
    }

    public JSONObject gerarDadosImovelJson(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("divulgacao", this.divulgacao);
            jsonObject.put("placa", this.placa);
            jsonObject.put("exclusividade", this.exclusividade);
            jsonObject.put("autorizacao_ate_venda", this.autorizacao_ate_venda);
            jsonObject.put("tipo", this.tipo);
            jsonObject.put("valor", this.valor);
            jsonObject.put("honorario", this.honorario);
            jsonObject.put("fase_obra", this.fase_obra);
            jsonObject.put("area_terreno", this.area_terreno);
            jsonObject.put("area_construida", this.area_construida);
            jsonObject.put("observacao", this.observacao);
            jsonObject.put("composicoesImovel", Composicao.gerarComposicaoJsonArray(this.composicoes));
            jsonObject.put("visitaImovel", this.visitaImovel.gerarVisitaJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
