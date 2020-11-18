package com.stager.casamaisimoveis.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.interfaces.TelaInicialInterface;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

public class TelaInicialFragment extends Fragment implements TelaInicialInterface {

    private TextView txtNomeUsuario;
    private TextView txtProfissaoUsuario;
    private LinearLayout llRota;

    private String FRAGMENT_ROTA = "Rota";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tela_inicial, container, false);

        txtNomeUsuario = (TextView) view.findViewById(R.id.txtNomeUsuario);
        txtProfissaoUsuario = (TextView) view.findViewById(R.id.txtProfissaoUsuario);
        llRota = (LinearLayout) view.findViewById(R.id.llRota);

        VariaveisEstaticas.setTelaInicialInterface(this);
        eventosBotoes();

        return view;
    }

    private void eventosBotoes(){
        llRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().alterarFragment(FRAGMENT_ROTA);
            }
        });
    }

    @Override
    public void carregarDadosUsuario() {
        if(VariaveisEstaticas.getCaptador() == null){
            txtNomeUsuario.setText(VariaveisEstaticas.getCoordenador().getNome());
            txtProfissaoUsuario.setText("Coordenador");
        }else{
            txtNomeUsuario.setText(VariaveisEstaticas.getCaptador().getNome());
            txtProfissaoUsuario.setText("Captador");
        }
    }
}
