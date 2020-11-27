package com.stager.casamaisimoveis.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TelefoneProprietario {

    private Integer id;
    private String numero;
    private Integer proprietario_id;

    public TelefoneProprietario(JSONObject resposta) {
        try {
            this.id = resposta.has("id") ? resposta.getInt("id") : 0;
            this.numero =  resposta.has("numero") ? resposta.getString("numero") : new String();
            this.proprietario_id = resposta.has("proprietario_id") ? resposta.getInt("proprietario_id") : 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public TelefoneProprietario(String numero) {
        this.numero = numero;
    }

    public TelefoneProprietario(String numero, Integer proprietario_id) {
        this.numero = numero;
        this.proprietario_id = proprietario_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getProprietario_id() {
        return proprietario_id;
    }

    public void setProprietario_id(Integer proprietario_id) {
        this.proprietario_id = proprietario_id;
    }

    public static JSONArray gerarTelefoneProprietarioJSONArray(List<TelefoneProprietario> telefones){
        JSONArray telefonesJSONArray = new JSONArray();

        for(TelefoneProprietario telefone: telefones){
            JSONObject telefoneJSONObject = new JSONObject();
            try {
                if(telefone.getId() != null)
                    telefoneJSONObject.put("id", telefone.getId());

                if(telefone.getProprietario_id() != null)
                    telefoneJSONObject.put("proprietario_id", telefone.getProprietario_id());

                telefoneJSONObject.put("numero", telefone.getNumero());
                telefonesJSONArray.put(telefoneJSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return telefonesJSONArray;
    }

    public static List<TelefoneProprietario> gerarListaTelefonesProprietarioBuscaImovel(JSONArray telefones){
        List<TelefoneProprietario> telefonesProprietario = new ArrayList<>();
        try {
            for(int i = 0; i < telefones.length(); i++){
                JSONObject itemResposta = telefones.getJSONObject(i);
                telefonesProprietario.add(new TelefoneProprietario(itemResposta));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return telefonesProprietario;
    }

}
