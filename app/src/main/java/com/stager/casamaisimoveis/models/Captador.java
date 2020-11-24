package com.stager.casamaisimoveis.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Captador {

    private Integer id;
    private String nome;
    private Integer usuario_id;

    public Captador() {
    }

    public Integer getId() {
        return id;
    }

    public Captador(JSONObject resposta) {
        try {
            this.id = resposta.has("id") ? resposta.getInt("id") : 0;
            this.nome = resposta.getString("nome") != null ? resposta.getString("nome") : new String();
            this.usuario_id = resposta.has("usuario_id") ? resposta.getInt("usuario_id") : 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    public void setCaptador(JSONObject jsonObject){
        try {
            if(jsonObject.has("array")) {
                JSONArray arrayResposta = jsonObject.getJSONArray("array");
                JSONObject primeiroElemento = arrayResposta.getJSONObject(0);
                this.id = primeiroElemento.has("id") ? primeiroElemento.getInt("id") : 0;
                this.nome = primeiroElemento.getString("nome") != null ? primeiroElemento.getString("nome") : new String();
                this.usuario_id = primeiroElemento.has("usuario_id") ? primeiroElemento.getInt("usuario_id") : 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}