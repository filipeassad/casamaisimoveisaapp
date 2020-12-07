package com.stager.casamaisimoveis.fragments.cadastrar;

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
import com.stager.casamaisimoveis.models.ItemSpinner;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import java.util.List;

public class CadastrarDadosAnuncioFragment extends Fragment {

    private Button btnVoltar;
    private Button btnAvancar;
    private Button btnAvancarSecundario;
    private CheckBox ckDivulgacao;
    private CheckBox ckPlaca;
    private CheckBox ckExclusividade;
    private CheckBox ckTempoAutorizacao;
    private FrameLayout flDivulgacao;
    private FrameLayout flPlaca;
    private FrameLayout flExclusividade;
    private FrameLayout flTempoAutorizacao;

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

        flDivulgacao = (FrameLayout) view.findViewById(R.id.flDivulgacao);
        flPlaca = (FrameLayout) view.findViewById(R.id.flPlaca);
        flExclusividade = (FrameLayout) view.findViewById(R.id.flExclusividade);
        flTempoAutorizacao = (FrameLayout) view.findViewById(R.id.flTempoAutorizacao);

        eventosBotoes();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Alterar");

        if(VariaveisEstaticas.getDadosImovelCadastro() != null){
            DadosImovel dadosImovel = VariaveisEstaticas.getDadosImovelCadastro();
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

        btnAvancarSecundario.setOnClickListener(new View.OnClickListener() {
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

        DadosImovel dadosImovel = new DadosImovel(ckDivulgacao.isChecked(),
                ckPlaca.isChecked(),
                ckExclusividade.isChecked(),
                ckTempoAutorizacao.isChecked());

        if(VariaveisEstaticas.getDadosImovelCadastro() != null){
            VariaveisEstaticas.getDadosImovelCadastro().setDivulgacao(dadosImovel.isDivulgacao());
            VariaveisEstaticas.getDadosImovelCadastro().setPlaca(dadosImovel.isPlaca());
            VariaveisEstaticas.getDadosImovelCadastro().setExclusividade(dadosImovel.isExclusividade());
            VariaveisEstaticas.getDadosImovelCadastro().setAutorizacao_ate_venda(dadosImovel.isAutorizacao_ate_venda());
        }else{
            VariaveisEstaticas.setDadosImovelCadastro(dadosImovel);
        }

        VariaveisEstaticas.getFragmentInterface().alterarFragment("CadastrarInformacoesImovel");

    }
}
