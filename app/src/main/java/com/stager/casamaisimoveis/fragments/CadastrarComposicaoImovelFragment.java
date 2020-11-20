package com.stager.casamaisimoveis.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

public class CadastrarComposicaoImovelFragment extends Fragment {

    private Button btnVoltar;
    private Button btnAvancar;
    private Button btnAdicionar;

    private Spinner spAmbiente;
    private EditText edtQuantidade;
    private ListView lvAmbientes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_composicao_imovel, container, false);

        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnAvancar = (Button) view.findViewById(R.id.btnAvancar);
        btnAdicionar = (Button) view.findViewById(R.id.btnAdicionar);

        spAmbiente = (Spinner) view.findViewById(R.id.spAmbiente);
        edtQuantidade = (EditText) view.findViewById(R.id.edtQuantidade);
        lvAmbientes = (ListView) view.findViewById(R.id.lvAmbientes);

        eventosBotoes();
        carregarSpinner();
        return view;
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

            }
        });

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void carregarSpinner(){

    }
}
