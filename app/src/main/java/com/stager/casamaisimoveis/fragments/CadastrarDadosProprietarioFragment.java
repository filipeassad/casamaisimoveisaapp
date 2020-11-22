package com.stager.casamaisimoveis.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.adapters.TelefoneProprietarioAdapter;
import com.stager.casamaisimoveis.interfaces.TelefoneProprietarioAdapterInterface;
import com.stager.casamaisimoveis.models.Proprietario;
import com.stager.casamaisimoveis.models.TelefoneProprietario;
import com.stager.casamaisimoveis.utilitarios.MascaraEditText;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import java.util.ArrayList;
import java.util.List;

public class CadastrarDadosProprietarioFragment extends Fragment implements TelefoneProprietarioAdapterInterface {

    private Button btnVoltar;
    private Button btnAvancar;
    private EditText edtNomeProprietario;
    private EditText edtCpfProprietario;
    private EditText edtTelefoneProprietario;
    private Button btnAdicionar;
    private ListView lvTelefoneProprietario;

    private List<TelefoneProprietario> telefonesProprietario;

    private TelefoneProprietarioAdapterInterface telefoneProprietarioAdapterInterface;

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

        telefoneProprietarioAdapterInterface = this;

        telefonesProprietario = new ArrayList<>();

        eventosBotoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Proprietário");

        if(VariaveisEstaticas.getProprietarioCadastro() != null){
            Proprietario proprietario = VariaveisEstaticas.getProprietarioCadastro();

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

        TelefoneProprietario telefoneProprietario = new TelefoneProprietario(edtTelefoneProprietario.getText().toString());
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

        if(edtCpfProprietario.getText().toString().trim().equals("")){
            edtCpfProprietario.setError("Digite o CPF.");
            return;
        }

        Proprietario proprietario = new Proprietario(edtNomeProprietario.getText().toString(),
                edtCpfProprietario.getText().toString(),
                telefonesProprietario);
        VariaveisEstaticas.setProprietarioCadastro(proprietario);
        VariaveisEstaticas.getFragmentInterface().alterarFragment("CadastrarEnderecoImovel");
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

    private LinearLayout.LayoutParams parametrosListView(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (200 * telefonesProprietario.size()));
        layoutParams.setMargins(0,50,0,0);

        return layoutParams;
    }
}
