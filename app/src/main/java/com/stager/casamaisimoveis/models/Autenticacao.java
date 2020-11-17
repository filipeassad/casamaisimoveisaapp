package com.stager.casamaisimoveis.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Autenticacao {

    private Integer id;
    private String token;
    private Integer usuario_id;
    private boolean captador;
    private boolean ativo;

    public Autenticacao() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    public boolean isCaptador() {
        return captador;
    }

    public void setCaptador(boolean captador) {
        this.captador = captador;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public JSONObject getJsonLogin(String login, String senha){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("login", login);
            jsonObject.put("senha", senha);
        } catch (JSONException e) {
            return null;
        }

        return jsonObject;
    }

    public void setAutenticacao(JSONObject jsonObject){
        try {
            this.token = jsonObject.getString("token") != null ? jsonObject.getString("token") : new String();
            this.captador = jsonObject.getBoolean("captador");
            this.usuario_id = jsonObject.has("usuario_id") ? jsonObject.getInt("usuario_id") : 0;
            this.ativo = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean tokenEhValido(JSONObject jsonObject){
        try {
            if(jsonObject.has("sucesso")){
                return jsonObject.getBoolean("sucesso");
            }else{
                return false;
            }
        } catch (JSONException e) {
            return false;
        }
    }
}