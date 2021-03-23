package com.stager.casamaisimoveis.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Captador {

    private Integer id;
    private String nome;
    private Integer usuario_id;
    private Autenticacao autenticacao;

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
            if(resposta.has("usuario")){
                JSONObject usuarioJson = resposta.getJSONObject("usuario");
                this.autenticacao = new Autenticacao();
                if(usuarioJson.has("imagemUsuario")){
                    JSONObject imagemUsuarioJson = usuarioJson.getJSONObject("imagemUsuario");
                    this.autenticacao.setLinkImagem(imagemUsuarioJson.has("url_imagem") ? imagemUsuarioJson.getString("url_imagem") : "");
                }
            }
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

    public Autenticacao getAutenticacao() {
        return autenticacao;
    }

    public void setAutenticacao(Autenticacao autenticacao) {
        this.autenticacao = autenticacao;
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

    public static List<Captador> getListCaptadores(JSONObject resposta){
        List<Captador> captadores = new ArrayList<>();

        try {
            if(resposta.has("array")) {
                JSONArray arrayResposta = resposta.getJSONArray("array");
                for(int i = 0; i < arrayResposta.length(); i++){
                    JSONObject itemResposta = arrayResposta.getJSONObject(i);
                    captadores.add(new Captador(itemResposta));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return captadores;
    }
}