package com.stager.casamaisimoveis.models;

import org.json.JSONException;
import org.json.JSONObject;

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
            rotaJson.put("bairro", this.bairro);
            rotaJson.put("rua", this.rua);
            rotaJson.put("numero", this.numero);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rotaJson;
    }
}
