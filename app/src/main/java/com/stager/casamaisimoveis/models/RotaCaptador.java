package com.stager.casamaisimoveis.models;

import android.util.Log;

import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RotaCaptador {

    private Integer id;
    private String latitude;
    private String longitude;
    private String data_rota;
    private String data_hora_rota;
    private Integer captador_id;

    public RotaCaptador() {
    }

    public RotaCaptador(String latitude, String longitude, String data_rota, String data_hora_rota, Integer captador_id) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.data_rota = data_rota;
        this.data_hora_rota = data_hora_rota;
        this.captador_id = captador_id;
    }

    public RotaCaptador(JSONObject resposta) {
        try {
            this.id = resposta.has("id") ? resposta.getInt("id") : 0;
            this.latitude = resposta.has("latitude") ? resposta.getString("latitude") : new String();
            this.longitude = resposta.has("longitude") ? resposta.getString("longitude") : new String();
            this.data_rota = resposta.getString("data_rota") != null ?
                    FerramentasBasicas.trocarFormatoDataString(resposta.getString("data_rota"), "yyyy-MM-dd", "dd/MM/yyyy")
                    : new String();
            this.data_hora_rota = resposta.getString("data_hora_rota") != null ?
                    FerramentasBasicas.trocarFormatoDataString(resposta.getString("data_hora_rota"), "yyyy-MM-dd'T'HH:mm:ss", "HH:mm:ss")
                    : new String();
            this.captador_id = resposta.has("captador_id") ? resposta.getInt("captador_id") : 0;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getData_rota() {
        return data_rota;
    }

    public void setData_rota(String data_rota) {
        this.data_rota = data_rota;
    }

    public String getData_hora_rota() {
        return data_hora_rota;
    }

    public void setData_hora_rota(String data_hora_rota) {
        this.data_hora_rota = data_hora_rota;
    }

    public Integer getCaptador_id() {
        return captador_id;
    }

    public void setCaptador_id(Integer captador_id) {
        this.captador_id = captador_id;
    }

    public static List<RotaCaptador> gerarRotasCaptadorDatas(JSONObject resposta){
        List<RotaCaptador> rotasCaptador = new ArrayList<>();

        try {
            if(resposta.has("array")) {
                JSONArray arrayResposta = resposta.getJSONArray("array");

                for(int i = 0; i < arrayResposta.length(); i++){
                    RotaCaptador rotaCaptador = new RotaCaptador();
                    JSONObject itemResposta = arrayResposta.getJSONObject(i);

                    String data = itemResposta.getString("datas") != null ? itemResposta.getString("datas") : new String();
                    Date dataConvertida = FerramentasBasicas.converterStringParaData(data, "yyyy-MM-dd");
                    rotaCaptador.setData_rota(FerramentasBasicas.converterDataParaString(dataConvertida, "dd/MM/yyyy"));

                    rotasCaptador.add(rotaCaptador);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rotasCaptador;
    }

    public JSONObject gerarRotaCaptadorJSON(){
        JSONObject rotaJson = new JSONObject();

        Date dataRota = FerramentasBasicas
                .converterStringParaData(this.data_rota, "dd/MM/yyyy");

        try {
            rotaJson.put("latitude", this.latitude);
            rotaJson.put("longitude", this.longitude);
            rotaJson.put("data_rota", FerramentasBasicas.converterDataParaString(dataRota, "yyyy-MM-dd"));
            rotaJson.put("data_hora_rota", this.data_hora_rota);
            rotaJson.put("captador_id", this.captador_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rotaJson;
    }

    public String getDataRotaFormatoServidor(){
        Date dataConvertida = FerramentasBasicas.converterStringParaData(this.data_rota, "dd/MM/yyyy");
        return FerramentasBasicas.converterDataParaString(dataConvertida, "yyyy-MM-dd");
    }

    public static List<RotaCaptador> listarEnderecosRotas(JSONObject resposta){
        List<RotaCaptador> rotasCaptador = new ArrayList<>();

        try {
            if(resposta.has("array")) {
                JSONArray arrayResposta = resposta.getJSONArray("array");

                for(int i = 0; i < arrayResposta.length(); i++){
                    JSONObject itemResposta = arrayResposta.getJSONObject(i);
                    rotasCaptador.add(new RotaCaptador(itemResposta));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rotasCaptador;
    }
}
