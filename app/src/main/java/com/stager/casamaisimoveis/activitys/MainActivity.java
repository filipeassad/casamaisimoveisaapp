package com.stager.casamaisimoveis.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.api.GetHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.api.PostHttpSemHeaderAsyncTask;
import com.stager.casamaisimoveis.banco.AutenticacaoManager;
import com.stager.casamaisimoveis.banco.DatabaseManager;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.models.Autenticacao;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends Activity implements HttpResponseInterface{

    private Button btnEntrar;
    private EditText edtLogin;
    private EditText edtSenha;
    private DatabaseManager databaseManager;
    private AutenticacaoManager autenticacaoManager;
    private HttpResponseInterface httpResponseInterface;
    private Autenticacao autenticacao;

    private final String API_LOGIN = "api/login";
    private final String API_VALIDAR_TOKEN = "api/valida_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtSenha = (EditText) findViewById(R.id.edtSenha);

        httpResponseInterface = this;
        databaseManager = new DatabaseManager(this);
        autenticacaoManager = new AutenticacaoManager(databaseManager.getWritableDatabase());

        verificarAutenticacao();

        eventosBotoes();
    }

    private void eventosBotoes(){
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtLogin.getText().toString().trim().equals("")){
                    edtLogin.setError("Digite o login.");
                    return;
                }

                if(edtSenha.getText().toString().trim().equals("")){
                    edtSenha.setError("Digite a senha.");
                    return;
                }

                autenticacao = new Autenticacao();

                JSONObject jsonLogin = autenticacao.getJsonLogin(edtLogin.getText().toString(), edtSenha.getText().toString());
                PostHttpSemHeaderAsyncTask postHttpSemHeaderAsyncTask = new PostHttpSemHeaderAsyncTask(view.getContext(), jsonLogin, httpResponseInterface, API_LOGIN);
                postHttpSemHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_LOGIN);
            }
        });
    }

    private void verificarAutenticacao(){
        List<Autenticacao> lista = autenticacaoManager.getAutenticacaoAtivo();

        if(!lista.isEmpty()){

            if(FerramentasBasicas.isOnline(this)){
                autenticacao = lista.get(0);
                VariaveisEstaticas.setAutenticacao(autenticacao);
                GetHttpComHeaderAsyncTask getHttpComHeaderAsyncTask = new GetHttpComHeaderAsyncTask(this, httpResponseInterface, API_VALIDAR_TOKEN);
                getHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_VALIDAR_TOKEN);
            }else{
                entrarAplicativo();
            }
        }
    }

    private void entrarAplicativo(){
        VariaveisEstaticas.setAutenticacao(autenticacao);
        Intent intent = new Intent(this, FragmentPrincipal.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {

        try {
            if(jsonObject.has("erro")){
                Toast.makeText(this, jsonObject.getString("erro"), Toast.LENGTH_SHORT).show();
                return;
            }

            if(rotaApi.equals(API_LOGIN))
                retornoApiLogin(jsonObject);
            else if(rotaApi.equals(API_VALIDAR_TOKEN))
                retornoValidacaoToken(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void retornoImagemBitmap(Bitmap imagem, String rotaAPI) {

    }

    private void retornoApiLogin(JSONObject resposta){
        autenticacao.setAutenticacao(resposta);
        if(autenticacao.getToken() != null){
            autenticacaoManager.insertAutenticacao(autenticacao);
            entrarAplicativo();
        }else{
            Toast.makeText(this, "Login ou senha inválida.", Toast.LENGTH_SHORT).show();
        }
    }

    private void retornoValidacaoToken(JSONObject resposta){
        if(autenticacao.tokenEhValido(resposta))
            entrarAplicativo();
        else
            Toast.makeText(this, "Token inválido.", Toast.LENGTH_SHORT).show();
    }
}