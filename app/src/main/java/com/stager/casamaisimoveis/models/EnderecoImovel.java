package com.stager.casamaisimoveis.models;

import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class EnderecoImovel {

    private Integer id;
    private String bairro;
    private String rua;
    private String numero;

    public EnderecoImovel(String bairro, String rua, String numero) {
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
    }

    public EnderecoImovel(JSONObject resposta) {
        try {

            this.id = resposta.has("id") ? resposta.getInt("id") : 0;
            this.bairro = resposta.getString("bairro") != null ? resposta.getString("bairro") : new String();
            this.rua = resposta.getString("rua") != null ? resposta.getString("rua") : new String();
            this.numero = resposta.getString("numero") != null ? resposta.getString("numero") : new String();

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

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public JSONObject gerarEnderecoImovelJSON(){
        JSONObject rotaJson = new JSONObject();
        try {
            if(this.id != null)
                rotaJson.put("id", this.id);
            rotaJson.put("bairro", this.bairro);
            rotaJson.put("rua", this.rua);
            rotaJson.put("numero", this.numero);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rotaJson;
    }

    public String getEnderecoEscrito(){
        String endereco = "Campo Grande MS, " + this.bairro + ", " + this.rua + ", " + this.numero;
        return endereco;
    }
}
