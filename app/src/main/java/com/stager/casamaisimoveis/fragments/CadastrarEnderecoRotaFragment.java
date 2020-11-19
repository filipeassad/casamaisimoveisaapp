package com.stager.casamaisimoveis.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.api.PostHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.models.EnderecoRota;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONException;
import org.json.JSONObject;

public class CadastrarEnderecoRotaFragment extends Fragment implements HttpResponseInterface {

    private EditText edtBairroEnderecoRota;
    private EditText edtRuaEnderecoRota;
    private EditText edtNumeroEnderecoRota;
    private Button btnCadastrarEnderecoRota;

    private String API_ENDERECO_ROTA = "api/enderecoRota";

    HttpResponseInterface httpResponseInterface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_endereco_rota, container, false);

        edtBairroEnderecoRota = (EditText) view.findViewById(R.id.edtBairroEnderecoRota);
        edtRuaEnderecoRota = (EditText) view.findViewById(R.id.edtRuaEnderecoRota);
        edtNumeroEnderecoRota = (EditText) view.findViewById(R.id.edtNumeroEnderecoRota);
        btnCadastrarEnderecoRota = (Button) view.findViewById(R.id.btnCadastrarEnderecoRota);

        httpResponseInterface = this;

        eventosBotoes();

        return view;
    }

    private void eventosBotoes(){

        btnCadastrarEnderecoRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().fecharTeclado();

                if(edtBairroEnderecoRota.getText().toString().trim().equals("")){
                    edtBairroEnderecoRota.setError("Digite o bairro.");
                    return;
                }

                if(edtRuaEnderecoRota.getText().toString().trim().equals("")){
                    edtRuaEnderecoRota.setError("Digite a rua.");
                    return;
                }

                if(edtNumeroEnderecoRota.getText().toString().trim().equals("")){
                    edtNumeroEnderecoRota.setError("Digite o número.");
                    return;
                }

                EnderecoRota enderecoRota = new EnderecoRota(edtBairroEnderecoRota.getText().toString(),
                        edtRuaEnderecoRota.getText().toString(),
                        edtNumeroEnderecoRota.getText().toString(),
                        VariaveisEstaticas.getRotaSelecionada().getId());

                PostHttpComHeaderAsyncTask postHttpComHeaderAsyncTask = new PostHttpComHeaderAsyncTask(getContext(), enderecoRota.gerarEnderecoRotaJSON(), httpResponseInterface, API_ENDERECO_ROTA);
                postHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_ENDERECO_ROTA);
            }
        });

    }

    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {
        try {
            if(jsonObject.has("erro")){
                Toast.makeText(getContext(), jsonObject.getString("erro"), Toast.LENGTH_SHORT).show();
                return;
            }

            if(rotaApi.equals(API_ENDERECO_ROTA))
                retornoEnderecoRota(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void retornoEnderecoRota(JSONObject resposta){
        if(resposta.has("sucesso")){
            try {
                Toast.makeText(getContext(), resposta.getString("mensagem"), Toast.LENGTH_SHORT).show();
                if(resposta.getBoolean("sucesso"))
                    VariaveisEstaticas.getFragmentInterface().voltar();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}