package com.stager.casamaisimoveis.interfaces;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONObject;

public interface HttpResponseInterface {
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi);
    public void retornoImagemBitmap(Bitmap imagem, String rotaAPI);
}
