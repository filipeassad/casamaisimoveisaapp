package com.stager.casamaisimoveis.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetHttpImagemAsyncTask extends AsyncTask<String, String, Bitmap> {

    private HttpResponseInterface httpResponseInterface;
    private ProgressDialog progress;
    private Context contexto;
    private String identificadorRequisicao;

    public GetHttpImagemAsyncTask(Context contexto, HttpResponseInterface httpResponseInterface, String identificadorRequisicao) {
        this.contexto = contexto;
        this.httpResponseInterface = httpResponseInterface;
        this.identificadorRequisicao = identificadorRequisicao;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(contexto);
        progress.setMessage("Aguarde...");
        progress.show();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap img = null;
        try{
            URL url = new URL(strings[0]);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            InputStream input = conexao.getInputStream();
            img = BitmapFactory.decodeStream(input);
            return img;
        }
        catch(IOException e){}

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        progress.dismiss();
        httpResponseInterface.retornoImagemBitmap(bitmap, this.identificadorRequisicao);
    }
}
