package com.stager.casamaisimoveis.fragments.visualizar;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.adapters.TelefoneProprietarioAdapter;
import com.stager.casamaisimoveis.models.Proprietario;
import com.stager.casamaisimoveis.models.TelefoneProprietario;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import java.util.ArrayList;
import java.util.List;

public class VisualizarDadosProprietarioFragment extends Fragment {

    private Button btnVoltar;
    private Button btnAvancar;
    private Button btnAvancarSecundario;
    private EditText edtNomeProprietario;
    private EditText edtCpfProprietario;
    private EditText edtTelefoneProprietario;
    private Button btnAdicionar;
    private TextView txtTelefone;
    private ListView lvTelefoneProprietario;
    private Button btnEditar;
    private RadioGroup rgrpSituacaoAnuncio;

    private RadioButton rbtnPendente;
    private RadioButton rbtnAtualizar;
    private RadioButton rbtnOk;


    private List<TelefoneProprietario> telefonesProprietario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dados_proprietario, container, false);

        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnAvancar = (Button) view.findViewById(R.id.btnAvancar);
        btnAvancarSecundario = (Button) view.findViewById(R.id.btnAvancarSecundario);

        edtNomeProprietario = (EditText) view.findViewById(R.id.edtNomeProprietario);
        edtCpfProprietario = (EditText) view.findViewById(R.id.edtCpfProprietario);
        edtTelefoneProprietario = (EditText) view.findViewById(R.id.edtTelefoneProprietario);
        btnAdicionar = (Button) view.findViewById(R.id.btnAdicionar);
        lvTelefoneProprietario = (ListView) view.findViewById(R.id.lvTelefoneProprietario);
        txtTelefone = (TextView) view.findViewById(R.id.txtTelefone);
        btnEditar = (Button) view.findViewById(R.id.btnEditar);
        rgrpSituacaoAnuncio = (RadioGroup) view.findViewById(R.id.rgrpSituacaoAnuncio);
        rbtnPendente = (RadioButton) view.findViewById(R.id.rbtnPendente);
        rbtnAtualizar = (RadioButton) view.findViewById(R.id.rbtnAtualizar);
        rbtnOk = (RadioButton) view.findViewById(R.id.rbtnOk);

        edtNomeProprietario.setInputType(InputType.TYPE_NULL);
        edtCpfProprietario.setInputType(InputType.TYPE_NULL);

        edtTelefoneProprietario.setVisibility(View.GONE);
        btnAdicionar.setVisibility(View.GONE);
        btnEditar.setVisibility(View.VISIBLE);

        rbtnPendente.setEnabled(false);
        rbtnAtualizar.setEnabled(false);
        rbtnOk.setEnabled(false);

        telefonesProprietario = new ArrayList<>();

        eventosBotoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Visualizar");
        txtTelefone.setText("Telefone(s):");

        if(VariaveisEstaticas.getImovelBusca() != null){
            Proprietario proprietario = VariaveisEstaticas.getImovelBusca().getProprietario();
            edtNomeProprietario.setText(proprietario.getNome());
            edtCpfProprietario.setText(proprietario.getCpf());

            telefonesProprietario = proprietario.getTelefones();
            TelefoneProprietarioAdapter telefoneProprietarioAdapter = new TelefoneProprietarioAdapter(getContext(),
                    R.layout.adapter_telefone_item,
                    telefonesProprietario,
                    null);
            lvTelefoneProprietario.setAdapter(telefoneProprietarioAdapter);
            lvTelefoneProprietario.setLayoutParams(parametrosListView());
            carregarSituacaoAnuncio();
        }
    }

    private void carregarSituacaoAnuncio(){
        switch (VariaveisEstaticas.getImovelBusca().getSituacao_anuncio()){
            case 0:
                rgrpSituacaoAnuncio.check(R.id.rbtnPendente);
                break;
            case 1:
                rgrpSituacaoAnuncio.check(R.id.rbtnAtualizar);
                break;
            case 2:
                rgrpSituacaoAnuncio.check(R.id.rbtnOk);
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
                VariaveisEstaticas.getFragmentInterface().alterarFragment("VisualizarEnderecoImovel");
            }
        });

        btnAvancarSecundario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().alterarFragment("VisualizarEnderecoImovel");
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().alterarFragment("AlterarDadosProprietario");
            }
        });
    }

    private LinearLayout.LayoutParams parametrosListView(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (200 * telefonesProprietario.size()));
        layoutParams.setMargins(0,50,0,20);

        return layoutParams;
    }
}
