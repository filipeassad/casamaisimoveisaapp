package com.stager.casamaisimoveis.fragments.alterar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.api.PutHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.models.EnderecoImovel;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONException;
import org.json.JSONObject;

public class AlterarEnderecoImovelFragment extends Fragment implements HttpResponseInterface {

    private Button btnVoltar;
    private Button btnAvancar;
    private Button btnAvancarSecundario;
    private EditText edtBairroEnderecoImovel;
    private EditText edtRuaEnderecoImovel;
    private EditText edtNumeroEnderecoImovel;

    private HttpResponseInterface httpResponseInterface;
    private String API_ENDERECO_IMOVEL = "api/enderecoImovel/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_endereco_imovel, container, false);

        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnAvancar = (Button) view.findViewById(R.id.btnAvancar);
        btnAvancarSecundario = (Button) view.findViewById(R.id.btnAvancarSecundario);
        edtBairroEnderecoImovel = (EditText) view.findViewById(R.id.edtBairroEnderecoImovel);
        edtRuaEnderecoImovel = (EditText) view.findViewById(R.id.edtRuaEnderecoImovel);
        edtNumeroEnderecoImovel = (EditText) view.findViewById(R.id.edtNumeroEnderecoImovel);

        btnAvancar.setText("Salvar");
        btnAvancarSecundario.setText("Salvar");
        httpResponseInterface = this;

        eventosBotoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Alterar");

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

        EnderecoImovel enderecoImovel = VariaveisEstaticas.getImovelBusca().getEnderecoImovel();
        enderecoImovel.setBairro(edtBairroEnderecoImovel.getText().toString());
        enderecoImovel.setRua(edtRuaEnderecoImovel.getText().toString());
        enderecoImovel.setNumero(edtNumeroEnderecoImovel.getText().toString());

        PutHttpComHeaderAsyncTask putHttpComHeaderAsyncTask = new PutHttpComHeaderAsyncTask(getContext(),
                enderecoImovel.gerarEnderecoImovelJSON(),
                httpResponseInterface,
                API_ENDERECO_IMOVEL);

        putHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_ENDERECO_IMOVEL + enderecoImovel.getId());
    }

    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {
        try {
            if(jsonObject.has("erro")){
                Toast.makeText(getContext(), jsonObject.getString("erro"), Toast.LENGTH_SHORT).show();
                return;
            }

            if(rotaApi.equals(API_ENDERECO_IMOVEL))
                retornoAlteracaoEnderecoImovel(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void retornoImagemBitmap(Bitmap imagem, String rotaAPI) {

    }

    private void retornoAlteracaoEnderecoImovel(JSONObject resposta){
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
