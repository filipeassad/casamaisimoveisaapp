package com.stager.casamaisimoveis.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    public EnderecoRota(JSONObject jsonObject) {
        try {
            this.id = jsonObject.has("id") ? jsonObject.getInt("id") : 0;
            this.bairro = jsonObject.getString("bairro") != null ? jsonObject.getString("bairro") : new String();
            this.rua = jsonObject.getString("rua") != null ? jsonObject.getString("rua") : new String();
            this.numero = jsonObject.getString("numero") != null ? jsonObject.getString("numero") : new String();
            this.rota_id = jsonObject.has("rota_id") ? jsonObject.getInt("rota_id") : 0;
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

    public static List<EnderecoRota> listarEnderecosRotas(JSONObject resposta){
        List<EnderecoRota> enderecosRota = new ArrayList<EnderecoRota>();

        try {
            if(resposta.has("array")) {
                JSONArray arrayResposta = resposta.getJSONArray("array");

                for(int i = 0; i < arrayResposta.length(); i++){
                    JSONObject itemResposta = arrayResposta.getJSONObject(i);
                    EnderecoRota enderecoRota = new EnderecoRota(itemResposta);
                    enderecosRota.add(enderecoRota);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return enderecosRota;
    }

    public String getEndereco(){
        String endereco = "Campo Grande MS, " + this.bairro + ", " + this.rua + ", " + this.numero;
        return endereco;
    }

}