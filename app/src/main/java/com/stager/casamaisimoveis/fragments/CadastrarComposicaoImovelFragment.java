package com.stager.casamaisimoveis.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.models.ItemSpinner;
import com.stager.casamaisimoveis.utilitarios.MontarSpinners;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import java.util.List;

public class CadastrarComposicaoImovelFragment extends Fragment {

    private Button btnVoltar;
    private Button btnAvancar;
    private Button btnAdicionar;

    private Spinner spAmbiente;
    private EditText edtQuantidade;
    private ListView lvAmbientes;

    private List<ItemSpinner> ambientes;
    private ItemSpinner ambienteSelecionado;

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
        MontarSpinners montarSpinners = new MontarSpinners();

        ambientes = montarSpinners.listarAmbiente();
        ArrayAdapter adapterAmbienteImovel = new ArrayAdapter(getContext(),
                R.layout.adapter_item_spinner,
                ambientes);

        adapterAmbienteImovel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAmbiente.setAdapter(adapterAmbienteImovel);

        spAmbiente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicao, long l) {
                ambienteSelecionado = (ItemSpinner) adapterView.getItemAtPosition(posicao);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
