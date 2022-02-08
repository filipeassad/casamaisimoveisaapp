package com.stager.casamaisimoveis.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.utilitarios.FerramentasHttp;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkPostHttpImagem extends AsyncTask<String, String, JSONObject> {

    private Bitmap bitmap;
    private Integer usuarioId;
    private Context contexto;
    private ProgressDialog progress;
    private HttpResponseInterface httpResponseInterface;
    private String API_ROTA;
    private int quantidadeImagem;
    private int posicaoAtualImagem;

    public OkPostHttpImagem(Bitmap bitmap, Integer usuarioId, Context contexto, HttpResponseInterface httpResponseInterface, String API_ROTA, int quantidadeImagem, int posicaoAtualImagem) {
        this.bitmap = bitmap;
        this.usuarioId = usuarioId;
        this.contexto = contexto;
        this.httpResponseInterface = httpResponseInterface;
        this.API_ROTA = API_ROTA;
        this.quantidadeImagem = quantidadeImagem;
        this.posicaoAtualImagem = posicaoAtualImagem;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(contexto);
        progress.setMessage("Upload Imagem " + posicaoAtualImagem + "/" + quantidadeImagem + "...\nAguarde! Não feche o aplicativo!");
        progress.show();
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject resposta = new JSONObject();
        try {
            FerramentasHttp ferramentasHttp = new FerramentasHttp();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, outStream);
            byte[] arrayByte = outStream.toByteArray();

            String parentName = "usuarioId";
            String nomeImagem = "imagemUsario.png";
            String nomeTag = "imagemUsuario";
            if(API_ROTA.equals("api/uploadImagemImovel")){
                nomeImagem = "imagemImovel.png";
                parentName = "imovelId";
                nomeTag = "imagemImovel";
            }

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart(nomeTag,
                            nomeImagem,
                            RequestBody.create(MediaType.parse("image/png"), arrayByte))
                    .addFormDataPart( parentName, usuarioId + "")
                    .build();

            Request request = new Request.Builder()
                    .url(strings[0])
                    .method("POST", body)
                    .addHeader("x-access-token", VariaveisEstaticas.getAutenticacao().getToken())
                    .addHeader("Content-Type","multipart/form-data")
                    .build();

            Response response = client.newCall(request).execute();

            String responseString = ferramentasHttp.readStream(response.body().byteStream());
            resposta = new JSONObject(responseString);
            return resposta;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resposta;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        progress.dismiss();
        httpResponseInterface.retornoJsonObject(jsonObject, API_ROTA);
    }
}


