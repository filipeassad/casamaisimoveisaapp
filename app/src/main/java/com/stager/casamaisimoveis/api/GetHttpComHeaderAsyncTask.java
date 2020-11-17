package com.stager.casamaisimoveis.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.utilitarios.FerramentasHttp;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.HttpURLConnection;
import java.net.URL;

public class GetHttpComHeaderAsyncTask extends AsyncTask<String, String, JSONObject> {

    private Context contexto;
    private ProgressDialog progress;
    private HttpResponseInterface httpResponseInterface;
    private String rotaApi;

    public GetHttpComHeaderAsyncTask(Context contexto, HttpResponseInterface httpResponseInterface, String rotaApi) {
        this.contexto = contexto;
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
        URL url;
        HttpURLConnection urlConnection = null;
        FerramentasHttp ferramentasHttp = new FerramentasHttp();
        JSONObject jsonResposta = new JSONObject();

        try {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.addRequestProperty("x-access-token", VariaveisEstaticas.getAutenticacao().getToken());
            int responseCode = urlConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String responseString = ferramentasHttp.readStream(urlConnection.getInputStream());
                Object dataJson = new JSONTokener(responseString).nextValue();

                if(dataJson instanceof JSONArray)
                    jsonResposta.put("array", new JSONArray(responseString));
                else
                    jsonResposta =  new JSONObject(responseString);

            }else if(responseCode == HttpURLConnection.HTTP_UNAUTHORIZED){
                jsonResposta.put("erro", "sem credenciais");
                return jsonResposta;
            }else{
                jsonResposta.put("erro", "nao conectou");
                return jsonResposta;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
        return jsonResposta;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        progress.dismiss();
        httpResponseInterface.retornoJsonObject(jsonObject, rotaApi);
    }
}