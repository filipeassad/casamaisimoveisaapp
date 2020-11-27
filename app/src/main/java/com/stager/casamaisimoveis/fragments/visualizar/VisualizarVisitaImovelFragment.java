package com.stager.casamaisimoveis.fragments.visualizar;

import android.os.Bundle;
import android.text.InputType;
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
import com.stager.casamaisimoveis.models.VisitaImovel;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import java.util.List;

public class VisualizarVisitaImovelFragment extends Fragment {

    private Button btnVoltar;
    private Button btnSalvar;
    private TextView txtNomeCaptador;
    private TextView txtProfissao;
    private TextView txtDataVisita;
    private EditText edtDataRetorno;
    private Button btnEditar;

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
        btnEditar = (Button) view.findViewById(R.id.btnEditar);

        btnSalvar.setText("Sair");
        btnEditar.setText("Nova Visita");

        edtDataRetorno.setInputType(InputType.TYPE_NULL);
        btnEditar.setVisibility(View.VISIBLE);
        eventosBotoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Visualizar");

        if(VariaveisEstaticas.getImovelBusca() != null){
            List<VisitaImovel> visitas = VariaveisEstaticas.getImovelBusca().getDadosImovel().getVisitasImovel();
            VisitaImovel visitaImovel = visitas.get(0);

            txtDataVisita.setText(visitaImovel.getData_visita());
            edtDataRetorno.setText(visitaImovel.getRetorno());
            txtNomeCaptador.setText(visitaImovel.getCaptador().getNome());
            txtProfissao.setText("Captador");
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
                VariaveisEstaticas.getFragmentInterface().alterarFragment("BuscarImovel");
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().alterarFragment("AlterarVisitaImovel");
            }
        });
    }
}
