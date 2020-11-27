package com.stager.casamaisimoveis.fragments.alterar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.api.PutHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.models.DadosImovel;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONException;
import org.json.JSONObject;

public class AlterarDadosAnuncioFragment extends Fragment implements HttpResponseInterface {

    private Button btnVoltar;
    private Button btnAvancar;
    private CheckBox ckDivulgacao;
    private CheckBox ckPlaca;
    private CheckBox ckExclusividade;
    private CheckBox ckTempoAutorizacao;
    private FrameLayout flDivulgacao;
    private FrameLayout flPlaca;
    private FrameLayout flExclusividade;
    private FrameLayout flTempoAutorizacao;

    private HttpResponseInterface httpResponseInterface;
    private String API_DADOS_IMOVEL = "api/dadosImovel/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dados_anuncio, container, false);

        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnAvancar = (Button) view.findViewById(R.id.btnAvancar);

        ckDivulgacao = (CheckBox) view.findViewById(R.id.ckDivulgacao);
        ckPlaca = (CheckBox) view.findViewById(R.id.ckPlaca);
        ckExclusividade = (CheckBox) view.findViewById(R.id.ckExclusividade);
        ckTempoAutorizacao = (CheckBox) view.findViewById(R.id.ckTempoAutorizacao);

        flDivulgacao = (FrameLayout) view.findViewById(R.id.flDivulgacao);
        flPlaca = (FrameLayout) view.findViewById(R.id.flPlaca);
        flExclusividade = (FrameLayout) view.findViewById(R.id.flExclusividade);
        flTempoAutorizacao = (FrameLayout) view.findViewById(R.id.flTempoAutorizacao);

        btnAvancar.setText("Salvar");

        httpResponseInterface = this;

        eventosBotoes();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Alterar");

        if(VariaveisEstaticas.getImovelBusca() != null){
            DadosImovel dadosImovel = VariaveisEstaticas.getImovelBusca().getDadosImovel();
            ckDivulgacao.setChecked(dadosImovel.isDivulgacao());
            ckPlaca.setChecked(dadosImovel.isPlaca());
            ckExclusividade.setChecked(dadosImovel.isExclusividade());
            ckTempoAutorizacao.setChecked(dadosImovel.isAutorizacao_ate_venda());
        }
    }

    private void eventosBotoes(){

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().voltar();
            }
        });

        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avancarFormulario();
            }
        });

        flDivulgacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ckDivulgacao.setChecked(!ckDivulgacao.isChecked());
            }
        });

        flPlaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ckPlaca.setChecked(!ckPlaca.isChecked());
            }
        });

        flExclusividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ckExclusividade.setChecked(!ckExclusividade.isChecked());
            }
        });

        flTempoAutorizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ckTempoAutorizacao.setChecked(!ckTempoAutorizacao.isChecked());
            }
        });

    }

    private void avancarFormulario(){

        DadosImovel dadosImovel = VariaveisEstaticas.getImovelBusca().getDadosImovel();
        dadosImovel.setDivulgacao(ckDivulgacao.isChecked());
        dadosImovel.setPlaca(ckPlaca.isChecked());
        dadosImovel.setExclusividade(ckExclusividade.isChecked());
        dadosImovel.setAutorizacao_ate_venda(ckTempoAutorizacao.isChecked());

        PutHttpComHeaderAsyncTask putHttpComHeaderAsyncTask = new PutHttpComHeaderAsyncTask(getContext(),
                dadosImovel.gerarDadosImovelJson(),
                httpResponseInterface,
                API_DADOS_IMOVEL);

        putHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_DADOS_IMOVEL + dadosImovel.getId());
    }

    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {
        try {
            if(jsonObject.has("erro")){
                Toast.makeText(getContext(), jsonObject.getString("erro"), Toast.LENGTH_SHORT).show();
                return;
            }

            if(rotaApi.equals(API_DADOS_IMOVEL))
                retornoAlteracaoDadosImovel(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void retornoImagemBitmap(Bitmap imagem, String rotaAPI) {

    }

    private void retornoAlteracaoDadosImovel(JSONObject resposta){
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
