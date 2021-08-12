package com.stager.casamaisimoveis.fragments.alterar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.adapters.TelefoneProprietarioAdapter;
import com.stager.casamaisimoveis.api.PutHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.interfaces.TelefoneProprietarioAdapterInterface;
import com.stager.casamaisimoveis.models.Imovel;
import com.stager.casamaisimoveis.models.Proprietario;
import com.stager.casamaisimoveis.models.TelefoneProprietario;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.MascaraEditText;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AlterarDadosProprietarioFragment extends Fragment implements TelefoneProprietarioAdapterInterface, HttpResponseInterface {

    private Button btnVoltar;
    private Button btnAvancar;
    private Button btnAvancarSecundario;
    private EditText edtNomeProprietario;
    private EditText edtCpfProprietario;
    private EditText edtObservacaoCoordenador;
    private EditText edtTelefoneProprietario;
    private Button btnAdicionar;
    private ListView lvTelefoneProprietario;
    private RadioGroup rgrpSituacaoAnuncio1;
    private RadioGroup rgrpSituacaoAnuncio2;

    private List<TelefoneProprietario> telefonesProprietario;
    private int situacaoAnuncioSelecionado = 0;

    private TelefoneProprietarioAdapterInterface telefoneProprietarioAdapterInterface;
    private HttpResponseInterface httpResponseInterface;

    private String API_ALTERAR_PROPRIETARIO = "api/proprietario";
    private String API_ALTERAR_IMOVEL = "api/imovel";
    private boolean isChecking = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dados_proprietario, container, false);

        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnAvancar = (Button) view.findViewById(R.id.btnAvancar);
        btnAvancarSecundario = (Button) view.findViewById(R.id.btnAvancarSecundario);

        edtNomeProprietario = (EditText) view.findViewById(R.id.edtNomeProprietario);
        edtCpfProprietario = (EditText) view.findViewById(R.id.edtCpfProprietario);
        edtObservacaoCoordenador = (EditText) view.findViewById(R.id.edtObservacaoCoordenador);
        edtTelefoneProprietario = (EditText) view.findViewById(R.id.edtTelefoneProprietario);
        btnAdicionar = (Button) view.findViewById(R.id.btnAdicionar);
        lvTelefoneProprietario = (ListView) view.findViewById(R.id.lvTelefoneProprietario);
        rgrpSituacaoAnuncio1 = (RadioGroup) view.findViewById(R.id.rgrpSituacaoAnuncio1);
        rgrpSituacaoAnuncio2 = (RadioGroup) view.findViewById(R.id.rgrpSituacaoAnuncio2);

        edtCpfProprietario.addTextChangedListener(MascaraEditText.mask(edtCpfProprietario, MascaraEditText.FORMAT_CPF));
        edtTelefoneProprietario.addTextChangedListener(MascaraEditText.mask(edtTelefoneProprietario, MascaraEditText.FORMAT_FONE));

        btnAvancar.setText("Salvar");
        btnAvancarSecundario.setText("Salvar");

        httpResponseInterface = this;
        telefoneProprietarioAdapterInterface = this;

        telefonesProprietario = new ArrayList<>();

        eventosBotoes();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Proprietario proprietarioAltaracao = VariaveisEstaticas.getImovelBusca().getProprietario();

        proprietarioAltaracao.setNome(edtNomeProprietario.getText().toString());
        proprietarioAltaracao.setCpf(edtCpfProprietario.getText().toString());
        proprietarioAltaracao.setObservacao_coordenador(edtObservacaoCoordenador.getText().toString());
        proprietarioAltaracao.setTelefones(telefonesProprietario);
    }

    @Override
    public void onResume() {
        super.onResume();

        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Alterar");

        if(VariaveisEstaticas.getImovelBusca() != null){
            Proprietario proprietario = VariaveisEstaticas.getImovelBusca().getProprietario();

            edtNomeProprietario.setText(proprietario.getNome());
            edtCpfProprietario.setText(proprietario.getCpf());
            edtObservacaoCoordenador.setText(proprietario.getObservacao_coordenador());

            telefonesProprietario = proprietario.getTelefones();
            TelefoneProprietarioAdapter telefoneProprietarioAdapter = new TelefoneProprietarioAdapter(getContext(),
                    R.layout.adapter_telefone_item,
                    telefonesProprietario,
                    telefoneProprietarioAdapterInterface);
            lvTelefoneProprietario.setAdapter(telefoneProprietarioAdapter);
            lvTelefoneProprietario.setLayoutParams(parametrosListView());
            carregarSituacaoAnuncio();
        }
    }

    private void carregarSituacaoAnuncio(){

        rgrpSituacaoAnuncio1.clearCheck();
        rgrpSituacaoAnuncio2.clearCheck();
        switch (VariaveisEstaticas.getImovelBusca().getSituacao_anuncio()){
            case 0:
                rgrpSituacaoAnuncio1.check(R.id.rbtnPendente);
                break;
            case 1:
                rgrpSituacaoAnuncio1.check(R.id.rbtnAtualizar);
                break;
            case 2:
                rgrpSituacaoAnuncio1.check(R.id.rbtnOk);
                break;
            case 3:
                rgrpSituacaoAnuncio2.check(R.id.rbtnVendido);
                break;
            case 4:
                rgrpSituacaoAnuncio2.check(R.id.rbtnExclusividade);
                break;
            case 5:
                rgrpSituacaoAnuncio2.check(R.id.rbtnParticular);
                break;
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

        btnAvancarSecundario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avancarFormulario();
            }
        });

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarTelefone();
            }
        });

        rgrpSituacaoAnuncio1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    switch(checkedId){
                        case R.id.rbtnPendente:
                            situacaoAnuncioSelecionado = 0;
                            break;
                        case R.id.rbtnAtualizar:
                            situacaoAnuncioSelecionado = 1;
                            break;
                        case R.id.rbtnOk:
                            situacaoAnuncioSelecionado = 2;
                            break;
                    }

                    isChecking = false;
                    rgrpSituacaoAnuncio2.clearCheck();
                }
                isChecking = true;
            }
        });

        rgrpSituacaoAnuncio2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    switch(checkedId){
                        case R.id.rbtnVendido:
                            situacaoAnuncioSelecionado = 3;
                            break;
                        case R.id.rbtnExclusividade:
                            situacaoAnuncioSelecionado = 4;
                            break;
                        case R.id.rbtnParticular:
                            situacaoAnuncioSelecionado = 5;
                            break;
                    }

                    isChecking = false;
                    rgrpSituacaoAnuncio1.clearCheck();
                }
                isChecking = true;
            }
        });
    }

    private void adicionarTelefone(){
        VariaveisEstaticas.getFragmentInterface().fecharTeclado();

        if(edtTelefoneProprietario.getText().toString().trim().equals("")){
            edtTelefoneProprietario.setError("Digite o telefone.");
            return;
        }

        TelefoneProprietario telefoneProprietario = new TelefoneProprietario(edtTelefoneProprietario.getText().toString(),
                VariaveisEstaticas.getImovelBusca().getProprietario().getId());
        telefonesProprietario.add(telefoneProprietario);
        TelefoneProprietarioAdapter telefoneProprietarioAdapter = new TelefoneProprietarioAdapter(getContext(),
                R.layout.adapter_telefone_item,
                telefonesProprietario,
                telefoneProprietarioAdapterInterface);
        lvTelefoneProprietario.setAdapter(telefoneProprietarioAdapter);
        lvTelefoneProprietario.setLayoutParams(parametrosListView());

        edtTelefoneProprietario.setText(new String());
    }

    public void avancarFormulario(){
        VariaveisEstaticas.getFragmentInterface().fecharTeclado();

        if(edtNomeProprietario.getText().toString().trim().equals("")){
            edtNomeProprietario.setError("Digite o nome.");
            return;
        }

        Proprietario proprietarioAltaracao = VariaveisEstaticas.getImovelBusca().getProprietario();

        proprietarioAltaracao.setNome(edtNomeProprietario.getText().toString());
        proprietarioAltaracao.setCpf(edtCpfProprietario.getText().toString());
        proprietarioAltaracao.setObservacao_coordenador(edtObservacaoCoordenador.getText().toString());
        proprietarioAltaracao.setTelefones(telefonesProprietario);

        PutHttpComHeaderAsyncTask putHttpComHeaderAsyncTask = new PutHttpComHeaderAsyncTask(getContext(),
                proprietarioAltaracao.gerarProprietarioJSON(),
                httpResponseInterface,
                API_ALTERAR_PROPRIETARIO);

        putHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_ALTERAR_PROPRIETARIO + "/" + proprietarioAltaracao.getId());
    }

    private LinearLayout.LayoutParams parametrosListView(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (200 * telefonesProprietario.size()));
        layoutParams.setMargins(0,50,0,0);

        return layoutParams;
    }

    @Override
    public void removerTelefone(TelefoneProprietario telefoneProprietario) {
        telefonesProprietario.remove(telefoneProprietario);
        TelefoneProprietarioAdapter telefoneProprietarioAdapter = new TelefoneProprietarioAdapter(getContext(),
                R.layout.adapter_telefone_item,
                telefonesProprietario,
                telefoneProprietarioAdapterInterface);
        lvTelefoneProprietario.setAdapter(telefoneProprietarioAdapter);
        lvTelefoneProprietario.setLayoutParams(parametrosListView());
    }

    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {
        try {
            if(jsonObject.has("erro")){
                Toast.makeText(getContext(), jsonObject.getString("erro"), Toast.LENGTH_SHORT).show();
                return;
            }

            if(rotaApi.equals(API_ALTERAR_PROPRIETARIO))
                retornoAlteracaoProprietario(jsonObject);
            if(rotaApi.equals(API_ALTERAR_IMOVEL))
                retornoAlteracaoImovel(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void retornoImagemBitmap(Bitmap imagem, String rotaAPI) {

    }

    private void retornoAlteracaoProprietario(JSONObject resposta){
        if(resposta.has("sucesso")){
            try {
                Toast.makeText(getContext(), resposta.getString("mensagem"), Toast.LENGTH_SHORT).show();
                if(resposta.getBoolean("sucesso"))
                    salvarSituacaoImovel();
                    //VariaveisEstaticas.getFragmentInterface().voltar();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void retornoAlteracaoImovel(JSONObject resposta){
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

    private void salvarSituacaoImovel(){

        Imovel imovel = VariaveisEstaticas.getImovelBusca();

        imovel.setSituacao_anuncio(situacaoAnuncioSelecionado);
        PutHttpComHeaderAsyncTask putHttpComHeaderAsyncTask = new PutHttpComHeaderAsyncTask(getContext(),
                imovel.gerarImovelSituacaoAnuncioJson(),
                httpResponseInterface,
                API_ALTERAR_IMOVEL);

        putHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_ALTERAR_IMOVEL + "/" + imovel.getId());
    }
}
