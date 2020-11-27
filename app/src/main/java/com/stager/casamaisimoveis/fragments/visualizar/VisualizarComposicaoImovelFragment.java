package com.stager.casamaisimoveis.fragments.visualizar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.adapters.ComposicaoAdapter;
import com.stager.casamaisimoveis.models.Composicao;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import java.util.ArrayList;
import java.util.List;

public class VisualizarComposicaoImovelFragment extends Fragment {

    private Button btnVoltar;
    private Button btnAvancar;
    private Button btnAdicionar;

    private Spinner spAmbiente;
    private EditText edtQuantidade;
    private ListView lvAmbientes;
    private Button btnEditar;
    private TextView txtAmbiente;
    private TextView txtQuantidade;
    private LinearLayout llDivider;

    private List<Composicao> composicoesImovel;

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
        btnEditar = (Button) view.findViewById(R.id.btnEditar);
        txtAmbiente = (TextView) view.findViewById(R.id.txtAmbiente);
        txtQuantidade = (TextView) view.findViewById(R.id.txtQuantidade);
        llDivider = (LinearLayout) view.findViewById(R.id.llDivider);

        btnEditar.setVisibility(View.VISIBLE);
        spAmbiente.setVisibility(View.GONE);
        edtQuantidade.setVisibility(View.GONE);
        btnAdicionar.setVisibility(View.GONE);
        txtAmbiente.setVisibility(View.GONE);
        txtQuantidade.setVisibility(View.GONE);
        llDivider.setVisibility(View.GONE);

        composicoesImovel = new ArrayList<>();

        eventosBotoes();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Visualizar");

        if(VariaveisEstaticas.getImovelBusca() != null){
            composicoesImovel = VariaveisEstaticas.getImovelBusca().getDadosImovel().getComposicoes();
            ComposicaoAdapter composicaoAdapter = new ComposicaoAdapter(getContext(),
                    R.layout.adapter_ambiente_imovel,
                    composicoesImovel,
                    null);
            lvAmbientes.setAdapter(composicaoAdapter);
            lvAmbientes.setLayoutParams(parametrosListView());
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
                VariaveisEstaticas.getFragmentInterface().alterarFragment("VisualizarVisitaImovel");
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().alterarFragment("AlterarComposicaoImovel");
            }
        });
    }

    private LinearLayout.LayoutParams parametrosListView(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (250 * composicoesImovel.size()));
        layoutParams.setMargins(0,50,0,10);

        return layoutParams;
    }

}
