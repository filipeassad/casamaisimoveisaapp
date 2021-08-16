package com.stager.casamaisimoveis.fragments.alterar;

import android.graphics.Bitmap;
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

public class AlterarComposicaoImovelFragment extends Fragment implements HttpResponseInterface {

    private Button btnVoltar;
    private Button btnAvancar;
    private Button btnAvancarSecundario;
    private LinearLayout llListaCheckboxAmbiente;

    private List<ItemSpinner> ambientesCheckBox;
    private List<Composicao> composicoesImovel;

    private HttpResponseInterface httpResponseInterface;
    private String API_COMPOSICAO_IMOVEL = "api/alterarComposicoes/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_composicao_imovel, container, false);

        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnAvancar = (Button) view.findViewById(R.id.btnAvancar);
        btnAvancarSecundario = (Button) view.findViewById(R.id.btnAvancarSecundario);
        llListaCheckboxAmbiente = (LinearLayout) view.findViewById(R.id.llListaCheckboxAmbiente);

        btnAvancar.setText("Salvar");
        btnAvancarSecundario.setText("Salvar");

        composicoesImovel = new ArrayList<>();
        httpResponseInterface = this;

        eventosBotoes();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        DadosImovel dadosImovel = VariaveisEstaticas.getImovelBusca().getDadosImovel();
        dadosImovel.setComposicoes(composicoesImovel);
    }

    @Override
    public void onResume() {
        super.onResume();

        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Alterar");
        MontarSpinners montarSpinners = new MontarSpinners();
        ambientesCheckBox = montarSpinners.listarAmbientesCheckbox();

        if(VariaveisEstaticas.getImovelBusca() != null){
            composicoesImovel = new ArrayList<>();
            for(Composicao composicao : VariaveisEstaticas.getImovelBusca().getDadosImovel().getComposicoes()){
                composicoesImovel.add(composicao);
            }
        }

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
                avancarFormulario();
            }
        });

        btnAvancarSecundario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avancarFormulario();
            }
        });
    }

    private void avancarFormulario(){
        VariaveisEstaticas.getFragmentInterface().fecharTeclado();

        if(VariaveisEstaticas.getImovelBusca().getSituacao_anuncio() != 4 && VariaveisEstaticas.getImovelBusca().getSituacao_anuncio() != 5){
            if(composicoesImovel.size() == 0){
                Toast.makeText(getContext(), "Adicione pelo menos uma composição", Toast.LENGTH_SHORT).show();
                return;
            }
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

    @Override
    public void retornoImagemBitmap(Bitmap imagem, String rotaAPI) {

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
                if(ambienteEstaPresenteNaComposicao(ambienteCheckbox))
                    checkBox.setChecked(true);

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if(isChecked)
                            composicoesImovel.add(new Composicao((ItemSpinner) compoundButton.getTag()));
                        else{
                            int index = 0;

                            for(Composicao composicao :composicoesImovel){
                                if(composicao.getAmbiente_id() == ((ItemSpinner) compoundButton.getTag()).getId()){
                                    index = composicoesImovel.indexOf(composicao);
                                    break;
                                }
                            }

                            composicoesImovel.remove(index);
                        }
                    }
                });

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
