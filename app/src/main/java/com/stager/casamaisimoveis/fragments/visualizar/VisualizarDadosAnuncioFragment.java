package com.stager.casamaisimoveis.fragments.visualizar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.models.DadosImovel;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

public class VisualizarDadosAnuncioFragment extends Fragment {

    private Button btnVoltar;
    private Button btnAvancar;
    private Button btnAvancarSecundario;
    private CheckBox ckDivulgacao;
    private CheckBox ckPlaca;
    private CheckBox ckExclusividade;
    private CheckBox ckTempoAutorizacao;
    private Button btnEditar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dados_anuncio, container, false);

        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnAvancar = (Button) view.findViewById(R.id.btnAvancar);
        btnAvancarSecundario = (Button) view.findViewById(R.id.btnAvancarSecundario);

        ckDivulgacao = (CheckBox) view.findViewById(R.id.ckDivulgacao);
        ckPlaca = (CheckBox) view.findViewById(R.id.ckPlaca);
        ckExclusividade = (CheckBox) view.findViewById(R.id.ckExclusividade);
        ckTempoAutorizacao = (CheckBox) view.findViewById(R.id.ckTempoAutorizacao);
        btnEditar = (Button) view.findViewById(R.id.btnEditar);

        btnEditar.setVisibility(View.VISIBLE);

        eventosBotoes();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Visualizar");

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
                VariaveisEstaticas.getFragmentInterface().alterarFragment("VisualizarInformacoesImovel");
            }
        });

        btnAvancarSecundario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().alterarFragment("VisualizarInformacoesImovel");
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().alterarFragment("AlterarDadosAnuncio");
            }
        });

    }

}
