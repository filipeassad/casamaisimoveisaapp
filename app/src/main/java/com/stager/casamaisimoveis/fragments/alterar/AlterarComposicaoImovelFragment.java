package com.stager.casamaisimoveis.fragments.alterar;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.adapters.ComposicaoAdapter;
import com.stager.casamaisimoveis.api.PutHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.interfaces.ComposicaoInterface;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.models.Composicao;
import com.stager.casamaisimoveis.models.DadosImovel;
import com.stager.casamaisimoveis.models.ItemSpinner;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.MontarSpinners;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AlterarComposicaoImovelFragment extends Fragment implements ComposicaoInterface, HttpResponseInterface {

    private Button btnVoltar;
    private Button btnAvancar;
    private Button btnAdicionar;

    private Spinner spAmbiente;
    private EditText edtQuantidade;
    private ListView lvAmbientes;

    private List<ItemSpinner> ambientes;
    private ItemSpinner ambienteSelecionado;
    private List<Composicao> composicoesImovel;

    private ComposicaoInterface composicaoInterface;
    private HttpResponseInterface httpResponseInterface;
    private String API_COMPOSICAO_IMOVEL = "api/alterarComposicoes/";

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

        btnAvancar.setText("Salvar");

        composicoesImovel = new ArrayList<>();
        composicaoInterface = this;
        httpResponseInterface = this;

        eventosBotoes();
        carregarSpinner();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Alterar");

        if(VariaveisEstaticas.getImovelBusca() != null){
            composicoesImovel = VariaveisEstaticas.getImovelBusca().getDadosImovel().getComposicoes();
            ComposicaoAdapter composicaoAdapter = new ComposicaoAdapter(getContext(),
                    R.layout.adapter_ambiente_imovel,
                    composicoesImovel,
                    composicaoInterface);
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
                avancarFormulario();
            }
        });

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarComposicao();
            }
        });
    }

    private void avancarFormulario(){
        VariaveisEstaticas.getFragmentInterface().fecharTeclado();

        if(composicoesImovel.size() == 0){
            Toast.makeText(getContext(), "Adicione pelo menos uma composição", Toast.LENGTH_SHORT).show();
            return;
        }

        DadosImovel dadosImovel = VariaveisEstaticas.getImovelBusca().getDadosImovel();
        dadosImovel.setComposicoes(composicoesImovel);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("composicoes", Composicao.gerarComposicaoJsonArray(dadosImovel.getComposicoes()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        PutHttpComHeaderAsyncTask putHttpComHeaderAsyncTask = new PutHttpComHeaderAsyncTask(getContext(),
                jsonObject,
                httpResponseInterface,
                API_COMPOSICAO_IMOVEL);

        putHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_COMPOSICAO_IMOVEL + dadosImovel.getId());
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

    private void adicionarComposicao(){
        VariaveisEstaticas.getFragmentInterface().fecharTeclado();

        if(ambienteSelecionado == null || (ambienteSelecionado != null && ambienteSelecionado.getId() == 0)){
            Toast.makeText(getContext(), "Selecione o ambiente", Toast.LENGTH_SHORT).show();
            return;
        }

        if(edtQuantidade.getText().toString().trim().equals("")){
            edtQuantidade.setError("Digite a quantidade.");
            return;
        }

        Composicao composicao = new Composicao(ambienteSelecionado.getId(), Integer.parseInt(edtQuantidade.getText().toString()), VariaveisEstaticas.getImovelBusca().getDadosImovel().getId());
        composicoesImovel.add(composicao);

        ComposicaoAdapter composicaoAdapter = new ComposicaoAdapter(getContext(),
                R.layout.adapter_ambiente_imovel,
                composicoesImovel,
                composicaoInterface);
        lvAmbientes.setAdapter(composicaoAdapter);
        lvAmbientes.setLayoutParams(parametrosListView());

        spAmbiente.setSelection(0);
        edtQuantidade.setText(new String());
    }

    private LinearLayout.LayoutParams parametrosListView(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (250 * composicoesImovel.size()));
        layoutParams.setMargins(0,50,0,10);

        return layoutParams;
    }

    @Override
    public void removerComposicao(Composicao composicao) {
        composicoesImovel.remove(composicao);
        ComposicaoAdapter composicaoAdapter = new ComposicaoAdapter(getContext(),
                R.layout.adapter_ambiente_imovel,
                composicoesImovel,
                composicaoInterface);
        lvAmbientes.setAdapter(composicaoAdapter);
        lvAmbientes.setLayoutParams(parametrosListView());
    }

    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {
        try {
            if(jsonObject.has("erro")){
                Toast.makeText(getContext(), jsonObject.getString("erro"), Toast.LENGTH_SHORT).show();
                return;
            }

            if(rotaApi.equals(API_COMPOSICAO_IMOVEL))
                retornoAlteracaoComposicoes(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void retornoAlteracaoComposicoes(JSONObject resposta){
        if(resposta.has("sucesso")){
            try {
                Toast.makeText(getContext(), resposta.getString("mensagem"), Toast.LENGTH_SHORT).show();
                if(resposta.getBoolean("sucesso"))
                    VariaveisEstaticas.getFragmentInterface().voltar();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
