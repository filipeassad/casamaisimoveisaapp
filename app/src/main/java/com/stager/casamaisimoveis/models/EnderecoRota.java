package com.stager.casamaisimoveis.models;

import org.json.JSONException;
import org.json.JSONObject;

public class EnderecoRota {

    private Integer id;
    private String bairro;
    private String rua;
    private String numero;
    private Integer rota_id;

    public EnderecoRota(String bairro, String rua, String numero, Integer rota_id) {
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.rota_id = rota_id;
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

    public Integer getRota_id() {
        return rota_id;
    }

    public void setRota_id(Integer rota_id) {
        this.rota_id = rota_id;
    }

    public JSONObject gerarEnderecoRotaJSON(){
        JSONObject rotaJson = new JSONObject();

        try {
            rotaJson.put("bairro", this.bairro);
            rotaJson.put("rua", this.rua);
            rotaJson.put("numero", this.numero);
            rotaJson.put("rota_id", this.rota_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rotaJson;
    }
}
