package com.stager.casamaisimoveis.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Rota {

    private Integer id;
    private String nome;
    private String bairro;
    private Integer captador_id;

    public Rota() {
    }

    public Rota(String nome, String bairro, Integer captador_id) {
        this.nome = nome;
        this.bairro = bairro;
        this.captador_id = captador_id;
    }

    public Integer getId() {
        return id;
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

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Integer getCaptador_id() {
        return captador_id;
    }

    public void setCaptador_id(Integer captador_id) {
        this.captador_id = captador_id;
    }

    public List<Rota> listarRotas(JSONObject resposta){
        List<Rota> rotasApi = new ArrayList<Rota>();

        try {
            if(resposta.has("array")) {
                JSONArray arrayResposta = resposta.getJSONArray("array");

                for(int i = 0; i < arrayResposta.length(); i++){
                    Rota rota = new Rota();
                    JSONObject itemResposta = arrayResposta.getJSONObject(i);

                    rota.setId(itemResposta.has("id") ? itemResposta.getInt("id") : 0);
                    rota.setBairro(itemResposta.getString("bairro") != null ? itemResposta.getString("bairro") : new String());
                    rota.setNome(itemResposta.getString("nome") != null ? itemResposta.getString("nome") : new String());
                    rota.setCaptador_id(itemResposta.has("captador_id") ? itemResposta.getInt("captador_id") : 0);

                    rotasApi.add(rota);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rotasApi;
    }

    public JSONObject gerarRotaJSON(){
        JSONObject rotaJson = new JSONObject();

        try {
            rotaJson.put("nome", this.nome);
            rotaJson.put("bairro", this.bairro);
            rotaJson.put("captador_id", this.captador_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rotaJson;
    }
}
