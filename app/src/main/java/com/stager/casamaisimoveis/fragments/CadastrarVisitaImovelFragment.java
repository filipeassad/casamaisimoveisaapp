package com.stager.casamaisimoveis.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.models.Captador;
import com.stager.casamaisimoveis.models.VisitaImovel;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.MascaraEditText;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import java.util.Date;

public class CadastrarVisitaImovelFragment extends Fragment {

    private Button btnVoltar;
    private Button btnSalvar;
    private TextView txtNomeCaptador;
    private TextView txtProfissao;
    private TextView txtDataVisita;
    private EditText edtDataRetorno;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visita_imovel, container, false);

        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnSalvar = (Button) view.findViewById(R.id.btnSalvar);
        txtNomeCaptador = (TextView) view.findViewById(R.id.txtNomeCaptador);
        txtProfissao = (TextView) view.findViewById(R.id.txtProfissao);
        txtDataVisita = (TextView) view.findViewById(R.id.txtDataVisita);
        edtDataRetorno = (EditText) view.findViewById(R.id.edtDataRetorno);

        edtDataRetorno.addTextChangedListener(MascaraEditText.mask(edtDataRetorno, MascaraEditText.FORMAT_DATE));

        eventosBotoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(VariaveisEstaticas.getCaptador() != null){
            Captador captador = VariaveisEstaticas.getCaptador();
            txtNomeCaptador.setText(captador.getNome());
            txtProfissao.setText("Captador");
        }

        if(VariaveisEstaticas.getVisitaImovelCadastro() != null){
            VisitaImovel visitaImovel = VariaveisEstaticas.getVisitaImovelCadastro();
            txtDataVisita.setText(visitaImovel.getData_visita());
        }else{
            txtDataVisita.setText(FerramentasBasicas.converterDataParaString(new Date(), "dd/MM/yyyy"));
        }

    }

    private void eventosBotoes(){
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VariaveisEstaticas.getFragmentInterface().voltar();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarDadosImovel();
            }
        });
    }

    private void salvarDadosImovel(){

    }
}