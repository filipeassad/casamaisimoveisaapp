package com.stager.casamaisimoveis.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.utilitarios.FerramentasHttp;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostHttpComHeaderAsyncTask extends AsyncTask<String, String, JSONObject> {

    private Context contexto;
    private ProgressDialog progress;
    private HttpResponseInterface httpResponseInterface;
    private JSONObject jsonObject;
    private String rotaApi;

    public PostHttpComHeaderAsyncTask(Context contexto, JSONObject jsonObject, HttpResponseInterface httpResponseInterface, String rotaApi) {
        this.contexto = contexto;
        this.jsonObject = jsonObject;
        this.httpResponseInterface = httpResponseInterface;
        this.rotaApi = rotaApi;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(contexto);
        progress.setMessage("Aguarde...");
        progress.show();
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        int httpResponse = 0;
        JSONObject jsonErro = new JSONObject();
        FerramentasHttp ferramentasHttp = new FerramentasHttp();

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type","application/json");
            conn.addRequestProperty("x-access-token", VariaveisEstaticas.getAutenticacao().getToken());

            conn.setConnectTimeout(20000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonObject.toString());
            writer.flush();
            writer.close();
            os.close();

            httpResponse = conn.getResponseCode();
            JSONObject response = new JSONObject();

            if(httpResponse == HttpURLConnection.HTTP_OK || httpResponse == 201){

                String responseString = ferramentasHttp.readStream(conn.getInputStream());
                Object dataJson = new JSONTokener(responseString).nextValue();
                if(dataJson instanceof JSONArray)
                    response.put("array", new JSONArray(responseString));
                else
                    response =  new JSONObject(responseString);

                return response;

            }else if(httpResponse == HttpURLConnection.HTTP_UNAUTHORIZED){
                jsonErro.put("erro", "sem credenciais");
                return jsonErro;
            }else{
                jsonErro.put("erro", "nao conectou");
                return jsonErro;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonErro.put("erro", "nao conectou");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonErro;
    }

    @Override
    protected void onPostExecute(JSONObject resposta) {
        progress.dismiss();
        httpResponseInterface.retornoJsonObject(resposta, rotaApi);
    }
}
