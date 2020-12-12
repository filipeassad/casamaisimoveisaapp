package com.stager.casamaisimoveis.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.interfaces.TelaInicialInterface;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

public class TelaInicialCoordenadorFragment extends Fragment implements TelaInicialInterface {

    private TextView txtNomeUsuario;
    private TextView txtProfissaoUsuario;
    private LinearLayout llRota;
    private LinearLayout llHitorico;
    private LinearLayout llBuscarImovel;
    private LinearLayout llPublicarImoveis;
    private ImageView ivImagemUsuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tela_inicial, container, false);

        txtNomeUsuario = (TextView) view.findViewById(R.id.txtNomeUsuario);
        txtProfissaoUsuario = (TextView) view.findViewById(R.id.txtProfissaoUsuario);
        llRota = (LinearLayout) view.findViewById(R.id.llRota);
        llHitorico = (LinearLayout) view.findViewById(R.id.llHitorico);
        llBuscarImovel = (LinearLayout) view.findViewById(R.id.llBuscarImovel);
        llPublicarImoveis = (LinearLayout) view.findViewById(R.id.llPublicarImoveis);
        ivImagemUsuario = (ImageView) view.findViewById(R.id.ivImagemUsuario);

        llRota.setVisibility(View.GONE);

        VariaveisEstaticas.setTelaInicialInterface(this);
        eventosBotoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Tela Inicial");
    }

    private void eventosBotoes(){

        llPublicarImoveis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        llHitorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().alterarFragment("ListaCaptadoresHistorico");
            }
        });

        llBuscarImovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().alterarFragment("BuscarImovel");
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

        if(VariaveisEstaticas.getAutenticacao().getImagemUsuario() != null){
            ivImagemUsuario.setImageBitmap(VariaveisEstaticas.getAutenticacao().getImagemUsuario());
        }
    }
}
