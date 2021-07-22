package com.stager.casamaisimoveis.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private Integer esgoto;
    private Integer tipo_rua;
    private String area_terreno;
    private String area_construida;
    private String observacao;
    private List<Composicao> composicoes;
    private List<VisitaImovel> visitasImovel;
    Locale ptBr = new Locale("pt", "BR");

    public DadosImovel(boolean divulgacao, boolean placa, boolean exclusividade, boolean autorizacao_ate_venda) {
        this.divulgacao = divulgacao;
        this.placa = placa;
        this.exclusividade = exclusividade;
        this.autorizacao_ate_venda = autorizacao_ate_venda;
    }

    public DadosImovel(Integer tipo, Integer fase_obra, Integer esgoto, Integer tipo_rua, String valor, String honorario, String area_terreno, String area_construida, String observacao) {

        this.tipo = tipo;
        this.valor = valor;
        this.honorario = honorario;
        this.fase_obra = fase_obra;
        this.esgoto = esgoto;
        this.tipo_rua = tipo_rua;
        this.area_terreno = area_terreno;
        this.area_construida = area_construida;
        this.observacao = observacao;
    }

    public DadosImovel(JSONObject resposta) {
        try {
            this.id = resposta.has("id") ? resposta.getInt("id") : 0;
            this.divulgacao = resposta.has("divulgacao") ? resposta.getBoolean("divulgacao") : false;
            this.placa = resposta.has("placa") ? resposta.getBoolean("placa") : false;
            this.exclusividade = resposta.has("exclusividade") ? resposta.getBoolean("exclusividade") : false;
            this.autorizacao_ate_venda = resposta.has("autorizacao_ate_venda") ? resposta.getBoolean("autorizacao_ate_venda") : false;
            this.tipo = resposta.has("tipo") ? resposta.getInt("tipo") : 0;
            this.valor = resposta.has("valor") ? NumberFormat.getCurrencyInstance(ptBr).format(Double.parseDouble(resposta.getString("valor"))) : new String();
            this.honorario = resposta.has("honorario") ? NumberFormat.getCurrencyInstance(ptBr).format(Double.parseDouble(resposta.getString("honorario"))) : new String();
            this.fase_obra = resposta.has("fase_obra") ? resposta.getInt("fase_obra") : 0;
            this.esgoto = resposta.has("esgoto") ? resposta.getInt("esgoto") : 0;
            this.tipo_rua = resposta.has("tipo_rua") ? resposta.getInt("tipo_rua") : 0;
            this.area_terreno = resposta.has("area_terreno") ? resposta.getString("area_terreno") : new String();
            this.area_construida = resposta.has("area_construida") ? resposta.getString("area_construida") : new String();
            this.observacao = resposta.has("observacao") ? resposta.getString("observacao") : new String();
            this.composicoes = resposta.has("composicoesImovel") ? Composicao.gerarListaComposicoesImovelBuscaImovel(resposta.getJSONArray("composicoesImovel")) : new ArrayList<Composicao>();
            this.visitasImovel = resposta.has("visitasImovel") ? VisitaImovel.gerarListaVisitasImovelBuscaImovel(resposta.getJSONArray("visitasImovel")) : new ArrayList<VisitaImovel>();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public Integer getEsgoto() {
        return esgoto;
    }

    public void setEsgoto(Integer esgoto) {
        this.esgoto = esgoto;
    }

    public Integer getTipo_rua() {
        return tipo_rua;
    }

    public void setTipo_rua(Integer tipo_rua) {
        this.tipo_rua = tipo_rua;
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

    public List<VisitaImovel> getVisitasImovel() {
        return visitasImovel;
    }

    public void setVisitasImovel(List<VisitaImovel> visitasImovel) {
        this.visitasImovel = visitasImovel;
    }

    public void setDadosImovel(Integer tipo, Integer fase_obra, Integer esgoto, Integer tipo_rua, String valor, String honorario, String area_terreno, String area_construida, String observacao){
        this.tipo = tipo;
        this.fase_obra = fase_obra;
        this.esgoto = esgoto;
        this.tipo_rua = tipo_rua;
        this.valor = valor;
        this.honorario = honorario;
        this.area_terreno = area_terreno;
        this.area_construida = area_construida;
        this.observacao = observacao;
    }

    public JSONObject gerarDadosImovelJson(){
        JSONObject jsonObject = new JSONObject();
        try {
            if(this.id != null)
                jsonObject.put("id", this.id);
            jsonObject.put("divulgacao", this.divulgacao);
            jsonObject.put("placa", this.placa);
            jsonObject.put("exclusividade", this.exclusividade);
            jsonObject.put("autorizacao_ate_venda", this.autorizacao_ate_venda);
            jsonObject.put("tipo", this.tipo);
            jsonObject.put("valor", converterPtBrParaUS(this.valor));
            jsonObject.put("honorario", converterPtBrParaUS(this.honorario));
            jsonObject.put("fase_obra", this.fase_obra);
            jsonObject.put("esgoto", this.esgoto);
            jsonObject.put("tipo_rua", this.tipo_rua);
            jsonObject.put("area_terreno", this.area_terreno);
            jsonObject.put("area_construida", this.area_construida);
            jsonObject.put("observacao", this.observacao);
            jsonObject.put("composicoesImovel", Composicao.gerarComposicaoJsonArray(this.composicoes));
            jsonObject.put("visitasImovel", VisitaImovel.gerarVisitaImovelJsonArray(this.visitasImovel));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    private String converterPtBrParaUS(String valor){
        if (valor.isEmpty()) return "";

        String cleanString = valor.replaceAll("[R$%.,\\s+]", "");
        BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
        String formatted = NumberFormat.getCurrencyInstance(Locale.US).format(parsed);
        formatted = formatted.replaceAll("[R$%,\\s+]", "");
        return formatted;
    }
}
