package com.stager.casamaisimoveis.fragments.visualizar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.stager.casamaisimoveis.models.ItemSpinner;
import com.stager.casamaisimoveis.utilitarios.MontarSpinners;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import java.util.ArrayList;
import java.util.List;

public class VisualizarComposicaoImovelFragment extends Fragment {

    private Button btnVoltar;
    private Button btnAvancar;
    private Button btnAvancarSecundario;
    private Button btnEditar;

    private List<Composicao> composicoesImovel;
    private LinearLayout llListaCheckboxAmbiente;
    private List<ItemSpinner> ambientesCheckBox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_composicao_imovel, container, false);

        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnAvancar = (Button) view.findViewById(R.id.btnAvancar);
        btnAvancarSecundario = (Button) view.findViewById(R.id.btnAvancarSecundario);
        btnEditar = (Button) view.findViewById(R.id.btnEditar);
        llListaCheckboxAmbiente = (LinearLayout) view.findViewById(R.id.llListaCheckboxAmbiente);

        btnEditar.setVisibility(View.VISIBLE);

        composicoesImovel = new ArrayList<>();

        eventosBotoes();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Visualizar");
        MontarSpinners montarSpinners = new MontarSpinners();
        ambientesCheckBox = montarSpinners.listarAmbientesCheckbox();

        if(VariaveisEstaticas.getImovelBusca() != null)
            composicoesImovel = VariaveisEstaticas.getImovelBusca().getDadosImovel().getComposicoes();

        carregarCheckbox();
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
                VariaveisEstaticas.getFragmentInterface().alterarFragment("VisualizarImagensImovel");
            }
        });


        btnAvancarSecundario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().alterarFragment("VisualizarImagensImovel");
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().alterarFragment("AlterarComposicaoImovel");
            }
        });
    }

    private void carregarCheckbox(){

        int numeroInteracoes = ambientesCheckBox.size() / 3;
        int contador = 0;

        for(int i = 0; i < numeroInteracoes; i++){
            LinearLayout adapterCheckboxComposicao = (LinearLayout) getLayoutInflater().inflate(R.layout.adapter_checkbox_composicao, llListaCheckboxAmbiente, false);

            for(int j = 0; j < 3; j++){
                if(contador >= ambientesCheckBox.size())
                    break;

                ItemSpinner ambienteCheckbox = ambientesCheckBox.get(contador);
                CheckBox checkBox;
                if(j == 0)
                    checkBox = adapterCheckboxComposicao.findViewById(R.id.ckPrimeiroAmbiente);
                else if(j == 1)
                    checkBox = adapterCheckboxComposicao.findViewById(R.id.ckSegundoAmbiente);
                else
                    checkBox = adapterCheckboxComposicao.findViewById(R.id.ckTerceiroAmbiente);

                checkBox.setTag(ambienteCheckbox);
                checkBox.setText(ambienteCheckbox.getDescricao());
                checkBox.setClickable(false);
                if(ambienteEstaPresenteNaComposicao(ambienteCheckbox))
                    checkBox.setChecked(true);

                contador++;
            }
            llListaCheckboxAmbiente.addView(adapterCheckboxComposicao);
        }
    }

    private boolean ambienteEstaPresenteNaComposicao(ItemSpinner ambiente){

        for (Composicao composicao : composicoesImovel){
            if(composicao.getAmbiente_id() == ambiente.getId()
                    && composicao.getQuantidade() == ambiente.getQuantidade())
                return true;
        }
        return false;
    }
}
