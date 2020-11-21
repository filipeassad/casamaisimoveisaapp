package com.stager.casamaisimoveis.models;

import org.json.JSONException;
import org.json.JSONObject;

public class RotaCaptador {

    private Integer id;
    private String latitude;
    private String longitude;
    private String data_rota;
    private Integer captador_id;

    public RotaCaptador(String latitude, String longitude, String data_rota, Integer captador_id) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.data_rota = data_rota;
        this.captador_id = captador_id;
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

    public Integer getCaptador_id() {
        return captador_id;
    }

    public void setCaptador_id(Integer captador_id) {
        this.captador_id = captador_id;
    }

    public JSONObject gerarRotaCaptadorJSON(){
        JSONObject rotaJson = new JSONObject();

        try {
            rotaJson.put("latitude", this.latitude);
            rotaJson.put("longitude", this.longitude);
            rotaJson.put("data_rota", this.data_rota);
            rotaJson.put("captador_id", this.captador_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rotaJson;
    }
}
