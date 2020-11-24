package com.stager.casamaisimoveis.fragments.visualizar;

import android.os.Bundle;
import android.text.InputType;
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


public class VisualizarEnderecoImovelFragment extends Fragment {

    private Button btnVoltar;
    private Button btnAvancar;
    private EditText edtBairroEnderecoImovel;
    private EditText edtRuaEnderecoImovel;
    private EditText edtNumeroEnderecoImovel;
    private Button btnEditar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_endereco_imovel, container, false);

        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnAvancar = (Button) view.findViewById(R.id.btnAvancar);
        edtBairroEnderecoImovel = (EditText) view.findViewById(R.id.edtBairroEnderecoImovel);
        edtRuaEnderecoImovel = (EditText) view.findViewById(R.id.edtRuaEnderecoImovel);
        edtNumeroEnderecoImovel = (EditText) view.findViewById(R.id.edtNumeroEnderecoImovel);
        btnEditar = (Button) view.findViewById(R.id.btnEditar);

        edtBairroEnderecoImovel.setInputType(InputType.TYPE_NULL);
        edtRuaEnderecoImovel.setInputType(InputType.TYPE_NULL);
        edtNumeroEnderecoImovel.setInputType(InputType.TYPE_NULL);

        btnEditar.setVisibility(View.VISIBLE);

        eventosBotoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Visualizar");
        if(VariaveisEstaticas.getImovelBusca() != null){
            EnderecoImovel enderecoImovel = VariaveisEstaticas.getImovelBusca().getEnderecoImovel();

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
                VariaveisEstaticas.getFragmentInterface().alterarFragment("");
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
