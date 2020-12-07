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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.adapters.TelefoneProprietarioAdapter;
import com.stager.casamaisimoveis.api.PutHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.interfaces.TelefoneProprietarioAdapterInterface;
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
    private EditText edtNomeProprietario;
    private EditText edtCpfProprietario;
    private EditText edtTelefoneProprietario;
    private Button btnAdicionar;
    private ListView lvTelefoneProprietario;

    private List<TelefoneProprietario> telefonesProprietario;

    private TelefoneProprietarioAdapterInterface telefoneProprietarioAdapterInterface;
    private HttpResponseInterface httpResponseInterface;

    private String API_ALTERAR_PROPRIETARIO = "api/proprietario";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dados_proprietario, container, false);

        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnAvancar = (Button) view.findViewById(R.id.btnAvancar);

        edtNomeProprietario = (EditText) view.findViewById(R.id.edtNomeProprietario);
        edtCpfProprietario = (EditText) view.findViewById(R.id.edtCpfProprietario);
        edtTelefoneProprietario = (EditText) view.findViewById(R.id.edtTelefoneProprietario);
        btnAdicionar = (Button) view.findViewById(R.id.btnAdicionar);
        lvTelefoneProprietario = (ListView) view.findViewById(R.id.lvTelefoneProprietario);

        edtCpfProprietario.addTextChangedListener(MascaraEditText.mask(edtCpfProprietario, MascaraEditText.FORMAT_CPF));
        edtTelefoneProprietario.addTextChangedListener(MascaraEditText.mask(edtTelefoneProprietario, MascaraEditText.FORMAT_FONE));

        btnAvancar.setText("Salvar");

        httpResponseInterface = this;
        telefoneProprietarioAdapterInterface = this;

        telefonesProprietario = new ArrayList<>();

        eventosBotoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Alterar");

        if(VariaveisEstaticas.getImovelBusca() != null){
            Proprietario proprietario = VariaveisEstaticas.getImovelBusca().getProprietario();

            edtNomeProprietario.setText(proprietario.getNome());
            edtCpfProprietario.setText(proprietario.getCpf());

            telefonesProprietario = proprietario.getTelefones();
            TelefoneProprietarioAdapter telefoneProprietarioAdapter = new TelefoneProprietarioAdapter(getContext(),
                    R.layout.adapter_telefone_item,
                    telefonesProprietario,
                    telefoneProprietarioAdapterInterface);
            lvTelefoneProprietario.setAdapter(telefoneProprietarioAdapter);
            lvTelefoneProprietario.setLayoutParams(parametrosListView());
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
                adicionarTelefone();
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
                    VariaveisEstaticas.getFragmentInterface().voltar();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
