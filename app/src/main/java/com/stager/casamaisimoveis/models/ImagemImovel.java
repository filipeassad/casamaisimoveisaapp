package com.stager.casamaisimoveis.models;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ImagemImovel {
    private Integer id;
    private String url_imagem;
    private Integer imovel_id;
    private String hash;
    private Bitmap bitmapImagem;

    public ImagemImovel(JSONObject resposta) {
        try {
            this.id = resposta.has("id") ? resposta.getInt("id") : 0;
            this.url_imagem = resposta.has("url_imagem") ? resposta.getString("url_imagem") : "";
            this.imovel_id = resposta.has("imovel_id") ? resposta.getInt("imovel_id") : 0;
            this.hash = resposta.has("hash") ? resposta.getString("hash") : "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ImagemImovel(Integer id, String url_imagem, Integer imovel_id, String hash) {
        this.id = id;
        this.url_imagem = url_imagem;
        this.imovel_id = imovel_id;
        this.hash = hash;
    }

    public ImagemImovel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl_imagem() {
        return url_imagem;
    }

    public void setUrl_imagem(String url_imagem) {
        this.url_imagem = url_imagem;
    }

    public Integer getImovel_id() {
        return imovel_id;
    }

    public void setImovel_id(Integer imovel_id) {
        this.imovel_id = imovel_id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Bitmap getBitmapImagem() {
        return bitmapImagem;
    }

    public void setBitmapImagem(Bitmap bitmapImagem) {
        this.bitmapImagem = bitmapImagem;
    }

    public static List<ImagemImovel> gerarListaImagemImovelBuscaImovel(JSONArray imagens){
        List<ImagemImovel> imagensImovel = new ArrayList<>();
        try {
            for(int i = 0; i < imagens.length(); i++){
                JSONObject itemResposta = imagens.getJSONObject(i);
                imagensImovel.add(new ImagemImovel(itemResposta));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imagensImovel;
    }
    public static JSONArray gerarImagemImovelJsonArray(List<ImagemImovel> imagensImovel){
        JSONArray imagemImovelJSONArray = new JSONArray();

        for(ImagemImovel imagemImovel: imagensImovel){
            JSONObject imagemImovelJSONObject = new JSONObject();
            try {
                if(imagemImovel.getId()!= null)
                    imagemImovelJSONObject.put("id", imagemImovel.getId());
                if(imagemImovel.getImovel_id() != null)
                    imagemImovelJSONObject.put("imovel_id", imagemImovel.getImovel_id());
                imagemImovelJSONObject.put("url_imagem", imagemImovel.getUrl_imagem());
                imagemImovelJSONObject.put("hash", imagemImovel.getHash());
                imagemImovelJSONArray.put(imagemImovelJSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return imagemImovelJSONArray;
    }

}
