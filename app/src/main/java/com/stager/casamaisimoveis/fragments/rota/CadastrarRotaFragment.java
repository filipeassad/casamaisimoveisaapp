package com.stager.casamaisimoveis.fragments.rota;

import android.graphics.Bitmap;
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
import com.stager.casamaisimoveis.models.Rota;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONException;
import org.json.JSONObject;

public class CadastrarRotaFragment extends Fragment implements HttpResponseInterface {

    private EditText edtNomeRota;
    private EditText edtBairroRota;
    private Button btnCadastrarRota;

    private String API_ROTA = "api/rota";

    HttpResponseInterface httpResponseInterface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_rota, container, false);

        edtNomeRota = (EditText) view.findViewById(R.id.edtNomeRota);
        edtBairroRota = (EditText) view.findViewById(R.id.edtBairroRota);
        btnCadastrarRota = (Button) view.findViewById(R.id.btnCadastrarRota);

        httpResponseInterface = this;

        eventosBotoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Rota");
    }

    private void eventosBotoes(){
        btnCadastrarRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VariaveisEstaticas.getFragmentInterface().fecharTeclado();

                if(edtNomeRota.getText().toString().trim().equals("")){
                    edtNomeRota.setError("Digite o nome da rota.");
                    return;
                }

                if(edtBairroRota.getText().toString().trim().equals("")){
                    edtBairroRota.setError("Digite o bairro da rota.");
                    return;
                }

                Rota rota = new Rota(edtNomeRota.getText().toString(), edtBairroRota.getText().toString(), VariaveisEstaticas.getCaptador().getId());
                PostHttpComHeaderAsyncTask postHttpComHeaderAsyncTask = new PostHttpComHeaderAsyncTask(getContext(), rota.gerarRotaJSON(), httpResponseInterface, API_ROTA);
                postHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_ROTA);
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

            if(rotaApi.equals(API_ROTA))
                retornRota(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void retornoImagemBitmap(Bitmap imagem, String rotaAPI) {

    }

    private void retornRota(JSONObject resposta){
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