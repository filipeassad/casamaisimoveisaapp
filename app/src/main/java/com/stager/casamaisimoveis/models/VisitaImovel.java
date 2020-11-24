package com.stager.casamaisimoveis.models;

import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VisitaImovel {

    private Integer id;
    private String data_visita;
    private String retorno;
    private Integer captador_id;
    private Integer dados_imovel_id;

    private Captador captador;

    public VisitaImovel(String data_visita, String retorno, Integer captador_id) {
        this.data_visita = data_visita;
        this.retorno = retorno;
        this.captador_id = captador_id;
    }

    public VisitaImovel(JSONObject resposta) {
        try {
            this.id = resposta.has("id") ? resposta.getInt("id") : 0;
            this.data_visita = resposta.getString("data_visita") != null ?
                    FerramentasBasicas.trocarFormatoDataString(resposta.getString("data_visita"), "yyyy-MM-dd", "dd/MM/yyyy")
                    : new String();
            this.retorno = resposta.getString("retorno") != null ?
                    FerramentasBasicas.trocarFormatoDataString(resposta.getString("retorno"), "yyyy-MM-dd", "dd/MM/yyyy")
                    : new String();
            this.captador_id = resposta.has("captador_id") ? resposta.getInt("captador_id") : 0;
            this.dados_imovel_id = resposta.has("dados_imovel_id") ? resposta.getInt("dados_imovel_id") : 0;
            this.captador = resposta.has("captador") ? new Captador(resposta.getJSONObject("captador")) : new Captador();
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

    public Captador getCaptador() {
        return captador;
    }

    public void setCaptador(Captador captador) {
        this.captador = captador;
    }

    public JSONObject gerarVisitaJson(){
        JSONObject jsonObject = new JSONObject();

        Date dataVisita = FerramentasBasicas
                .converterStringParaData(this.data_visita, "dd/MM/yyyy");

        Date dataRetorno = FerramentasBasicas
                .converterStringParaData(this.retorno, "dd/MM/yyyy");

        try {
            jsonObject.put("data_visita", FerramentasBasicas.converterDataParaString(dataVisita, "yyyy-MM-dd"));
            if(retorno.trim().equals("") == false)
                jsonObject.put("retorno", FerramentasBasicas.converterDataParaString(dataRetorno, "yyyy-MM-dd"));
            jsonObject.put("captador_id", this.captador_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static JSONArray gerarVisitaImovelJsonArray(List<VisitaImovel> visitas){
        JSONArray composicoesJSONArray = new JSONArray();

        for(VisitaImovel visita: visitas){
            composicoesJSONArray.put(visita.gerarVisitaJson());
        }

        return composicoesJSONArray;
    }

    public static List<VisitaImovel> gerarListaVisitasImovelBuscaImovel(JSONArray composicoes){
        List<VisitaImovel> visitasImovel = new ArrayList<>();
        try {
            for(int i = 0; i < composicoes.length(); i++){
                JSONObject itemResposta = composicoes.getJSONObject(i);
                visitasImovel.add(new VisitaImovel(itemResposta));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return visitasImovel;
    }

}
