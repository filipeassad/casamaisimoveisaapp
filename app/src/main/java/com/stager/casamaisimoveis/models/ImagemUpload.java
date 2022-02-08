package com.stager.casamaisimoveis.models;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

public class ImagemUpload {

    private Integer id;
    private Integer imovelId;
    private Bitmap imagem;

    public ImagemUpload() {
    }

    public ImagemUpload(Integer imovelId, Bitmap imagem) {
        this.imovelId = imovelId;
        this.imagem = imagem;
    }

    public Integer getImovelId() {
        return imovelId;
    }

    public void setImovelId(Integer imovelId) {
        this.imovelId = imovelId;
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JSONObject gerarJSONImagemUpload(String hash) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("url_imagem", "https://casamaisimoveis.s3.us-east-2.amazonaws.com/" + hash + ".png");
            jsonObject.put("imovel_id", this.imovelId);
            jsonObject.put("hash", hash + ".png");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
