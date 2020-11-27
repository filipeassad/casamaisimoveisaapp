package com.stager.casamaisimoveis.fragments.visualizar;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.models.DadosImovel;
import com.stager.casamaisimoveis.models.ItemSpinner;
import com.stager.casamaisimoveis.utilitarios.MontarSpinners;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import java.util.List;

public class VisualizarInformacoesImovelFragment extends Fragment {

    private Button btnVoltar;
    private Button btnAvancar;
    private Spinner spTipoImovel;
    private Spinner spFaseObra;
    private Spinner spEsgoto;
    private Spinner spTipoRua;
    private EditText edtValor;
    private EditText edtHonorario;
    private EditText edtAreaTerreno;
    private EditText edtAreaConstruida;
    private EditText edtObservacao;
    private Button btnEditar;

    private List<ItemSpinner> tiposImovel;
    private List<ItemSpinner> fasesObra;
    private List<ItemSpinner> esgotos;
    private List<ItemSpinner> tiposRua;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_informacoes_imovel, container, false);

        btnVoltar  = (Button) view.findViewById(R.id.btnVoltar);
        btnAvancar  = (Button) view.findViewById(R.id.btnAvancar);

        spTipoImovel = (Spinner) view.findViewById(R.id.spTipoImovel);
        spFaseObra = (Spinner) view.findViewById(R.id.spFaseObra);
        spEsgoto = (Spinner) view.findViewById(R.id.spEsgoto);
        spTipoRua = (Spinner) view.findViewById(R.id.spTipoRua);

        edtValor = (EditText) view.findViewById(R.id.edtValor);
        edtHonorario = (EditText) view.findViewById(R.id.edtHonorario);
        edtAreaTerreno = (EditText) view.findViewById(R.id.edtAreaTerreno);
        edtAreaConstruida = (EditText) view.findViewById(R.id.edtAreaConstruida);
        edtObservacao = (EditText) view.findViewById(R.id.edtObservacao);
        btnEditar = (Button) view.findViewById(R.id.btnEditar);

        edtValor.setInputType(InputType.TYPE_NULL);
        edtHonorario.setInputType(InputType.TYPE_NULL);
        edtAreaTerreno.setInputType(InputType.TYPE_NULL);
        edtAreaConstruida.setInputType(InputType.TYPE_NULL);
        edtObservacao.setInputType(InputType.TYPE_NULL);
        btnEditar.setVisibility(View.VISIBLE);

        spTipoImovel.setEnabled(false);
        spFaseObra.setEnabled(false);
        spEsgoto.setEnabled(false);
        spTipoRua.setEnabled(false);

        eventosBotoes();
        carregarSpinners();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Visualizar");

        if(VariaveisEstaticas.getImovelBusca() != null){
            DadosImovel dadosImovel = VariaveisEstaticas.getImovelBusca().getDadosImovel();

            spTipoImovel.setSelection(dadosImovel.getTipo() != null ? dadosImovel.getTipo(): 0);
            spFaseObra.setSelection(dadosImovel.getFase_obra() != null ? dadosImovel.getTipo(): 0);
            spEsgoto.setSelection(dadosImovel.getEsgoto() != null ? dadosImovel.getEsgoto(): 0);
            spTipoRua.setSelection(dadosImovel.getTipo_rua() != null ? dadosImovel.getTipo_rua(): 0);

            edtValor.setText(dadosImovel.getValor() != null ? dadosImovel.getValor(): "");
            edtHonorario.setText(dadosImovel.getHonorario() != null ? dadosImovel.getHonorario(): "");
            edtAreaTerreno.setText(dadosImovel.getArea_terreno() != null ? dadosImovel.getArea_terreno(): "");
            edtAreaConstruida.setText(dadosImovel.getArea_construida() != null ? dadosImovel.getArea_construida(): "");
            edtObservacao.setText(dadosImovel.getObservacao() != null ? dadosImovel.getObservacao(): "");
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
                VariaveisEstaticas.getFragmentInterface().alterarFragment("VisualizarComposicaoImovel");
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().alterarFragment("AlterarInformacoesImovel");
            }
        });
    }

    private void carregarSpinners(){
        MontarSpinners montarSpinners = new MontarSpinners();
        tiposImovel = montarSpinners.listarTiposImovel();
        fasesObra = montarSpinners.listarFaseImovel();
        esgotos = montarSpinners.listarTipoEsgoto();
        tiposRua = montarSpinners.listarTipoRua();

        ArrayAdapter adapterTipoImovel = new ArrayAdapter(getContext(),
                R.layout.adapter_item_spinner,
                tiposImovel);

        adapterTipoImovel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoImovel.setAdapter(adapterTipoImovel);

        ArrayAdapter adapterFaseImovel = new ArrayAdapter(getContext(),
                R.layout.adapter_item_spinner,
                fasesObra);

        adapterFaseImovel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFaseObra.setAdapter(adapterFaseImovel);

        ArrayAdapter adapterTipoEsgoto = new ArrayAdapter(getContext(),
                R.layout.adapter_item_spinner,
                esgotos);

        adapterTipoEsgoto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEsgoto.setAdapter(adapterTipoEsgoto);

        ArrayAdapter adapterTipoRua = new ArrayAdapter(getContext(),
                R.layout.adapter_item_spinner,
                tiposRua);

        adapterTipoRua.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoRua.setAdapter(adapterTipoRua);
    }
}
