package com.stager.casamaisimoveis.models;

import com.stager.casamaisimoveis.utilitarios.MontarSpinners;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Composicao {

    private Integer id;
    private Integer ambiente_id;
    private Integer quantidade;
    private Integer dados_imovel_id;

    public Composicao(Integer ambiente_id, Integer quantidade) {
        this.ambiente_id = ambiente_id;
        this.quantidade = quantidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmbiente_id() {
        return ambiente_id;
    }

    public void setAmbiente_id(Integer ambiente_id) {
        this.ambiente_id = ambiente_id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getDados_imovel_id() {
        return dados_imovel_id;
    }

    public void setDados_imovel_id(Integer dados_imovel_id) {
        this.dados_imovel_id = dados_imovel_id;
    }

    public String getNomeComposicao(){
        MontarSpinners montarSpinners = new MontarSpinners();
        return montarSpinners.listarAmbiente().get(this.ambiente_id).getDescricao();
    }

    public static JSONArray gerarComposicaoJsonArray(List<Composicao> composicoes){
        JSONArray composicoesJSONArray = new JSONArray();

        for(Composicao composicao: composicoes){
            JSONObject composicaoJSONObject = new JSONObject();
            try {
                composicaoJSONObject.put("ambiente_id", composicao.getAmbiente_id());
                composicaoJSONObject.put("quantidade", composicao.getQuantidade());
                composicoesJSONArray.put(composicaoJSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return composicoesJSONArray;
    }
}