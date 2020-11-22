package com.stager.casamaisimoveis.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.models.EnderecoImovel;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

public class CadastrarEnderecoImovelFragment extends Fragment {

    private Button btnVoltar;
    private Button btnAvancar;
    private EditText edtBairroEnderecoImovel;
    private EditText edtRuaEnderecoImovel;
    private EditText edtNumeroEnderecoImovel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_endereco_imovel, container, false);

        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnAvancar = (Button) view.findViewById(R.id.btnAvancar);
        edtBairroEnderecoImovel = (EditText) view.findViewById(R.id.edtBairroEnderecoImovel);
        edtRuaEnderecoImovel = (EditText) view.findViewById(R.id.edtRuaEnderecoImovel);
        edtNumeroEnderecoImovel = (EditText) view.findViewById(R.id.edtNumeroEnderecoImovel);

        eventosBotoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Endereço");

        if(VariaveisEstaticas.getEnderecoImovelCadastro() != null){
            EnderecoImovel enderecoImovel = VariaveisEstaticas.getEnderecoImovelCadastro();

            edtBairroEnderecoImovel.setText(enderecoImovel.getBairro());
            edtRuaEnderecoImovel.setText(enderecoImovel.getRua());
            edtNumeroEnderecoImovel.setText(enderecoImovel.getNumero());
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

    }

    private void avancarFormulario(){
        VariaveisEstaticas.getFragmentInterface().fecharTeclado();

        if(edtBairroEnderecoImovel.getText().toString().trim().equals("")){
            edtBairroEnderecoImovel.setError("Digite o bairro.");
            return;
        }

        if(edtRuaEnderecoImovel.getText().toString().trim().equals("")){
            edtRuaEnderecoImovel.setError("Digite a rua.");
            return;
        }

        if(edtNumeroEnderecoImovel.getText().toString().trim().equals("")){
            edtNumeroEnderecoImovel.setError("Digite o número.");
            return;
        }

        EnderecoImovel enderecoImovel = new EnderecoImovel(edtBairroEnderecoImovel.getText().toString(),
                edtRuaEnderecoImovel.getText().toString(),
                edtNumeroEnderecoImovel.getText().toString());
        VariaveisEstaticas.setEnderecoImovelCadastro(enderecoImovel);
        VariaveisEstaticas.getFragmentInterface().alterarFragment("CadastrarDadosAnuncio");
    }
}
