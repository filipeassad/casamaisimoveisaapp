package com.stager.casamaisimoveis.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TelefoneProprietario {

    private Integer id;
    private String numero;
    private Integer proprietario_id;

    public TelefoneProprietario(String numero) {
        this.numero = numero;
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
                telefoneJSONObject.put("numero", telefone.getNumero());
                telefonesJSONArray.put(telefoneJSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return telefonesJSONArray;
    }

}
