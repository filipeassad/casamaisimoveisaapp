package com.stager.casamaisimoveis.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.api.PostHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.models.Captador;
import com.stager.casamaisimoveis.models.DadosImovel;
import com.stager.casamaisimoveis.models.EnderecoImovel;
import com.stager.casamaisimoveis.models.Imovel;
import com.stager.casamaisimoveis.models.Proprietario;
import com.stager.casamaisimoveis.models.VisitaImovel;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.MascaraEditText;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class CadastrarVisitaImovelFragment extends Fragment implements HttpResponseInterface{

    private Button btnVoltar;
    private Button btnSalvar;
    private TextView txtNomeCaptador;
    private TextView txtProfissao;
    private TextView txtDataVisita;
    private EditText edtDataRetorno;
    private HttpResponseInterface httpResponseInterface;
    private String API_IMOVEL = "api/cadastrarImovel";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visita_imovel, container, false);

        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnSalvar = (Button) view.findViewById(R.id.btnSalvar);
        txtNomeCaptador = (TextView) view.findViewById(R.id.txtNomeCaptador);
        txtProfissao = (TextView) view.findViewById(R.id.txtProfissao);
        txtDataVisita = (TextView) view.findViewById(R.id.txtDataVisita);
        edtDataRetorno = (EditText) view.findViewById(R.id.edtDataRetorno);

        edtDataRetorno.addTextChangedListener(MascaraEditText.mask(edtDataRetorno, MascaraEditText.FORMAT_DATE));

        httpResponseInterface = this;

        eventosBotoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Visita");

        if(VariaveisEstaticas.getCaptador() != null){
            Captador captador = VariaveisEstaticas.getCaptador();
            txtNomeCaptador.setText(captador.getNome());
            txtProfissao.setText("Captador");
        }

        if(VariaveisEstaticas.getVisitaImovelCadastro() != null){
            VisitaImovel visitaImovel = VariaveisEstaticas.getVisitaImovelCadastro();
            txtDataVisita.setText(visitaImovel.getData_visita());
        }else{
            txtDataVisita.setText(FerramentasBasicas.converterDataParaString(new Date(), "dd/MM/yyyy"));
        }
    }

    private void eventosBotoes(){
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VariaveisEstaticas.getFragmentInterface().voltar();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDadosVisita();
            }
        });
    }

    private void validarDadosVisita(){
        if(edtDataRetorno.getText().toString().length() != 0 && edtDataRetorno.getText().toString().length() < 10){
            edtDataRetorno.setError("Data de retorno inválida.");
            return;
        }

        VisitaImovel visitaImovel = new VisitaImovel(FerramentasBasicas.converterDataParaString(new Date(), "dd/MM/yyyy"),
                edtDataRetorno.getText().toString(),
                VariaveisEstaticas.getCaptador().getId());

        VariaveisEstaticas.setVisitaImovelCadastro(visitaImovel);
        salvarImovel();
    }

    private void salvarImovel(){
        Proprietario proprietario = VariaveisEstaticas.getProprietarioCadastro();
        EnderecoImovel enderecoImovel = VariaveisEstaticas.getEnderecoImovelCadastro();
        DadosImovel dadosImovel = VariaveisEstaticas.getDadosImovelCadastro();

        dadosImovel.setComposicoes(VariaveisEstaticas.getComposicoesImovelCadastro());
        dadosImovel.setVisitaImovel(VariaveisEstaticas.getVisitaImovelCadastro());

        Imovel imovel = new Imovel(enderecoImovel, proprietario, dadosImovel);

        PostHttpComHeaderAsyncTask postHttpComHeaderAsyncTask = new PostHttpComHeaderAsyncTask(getContext(),
                imovel.gerarImovelJson(),
                httpResponseInterface,
                API_IMOVEL);
        postHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_IMOVEL);
    }

    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {
        try {
            if(jsonObject.has("erro")){
                Toast.makeText(getContext(), jsonObject.getString("erro"), Toast.LENGTH_SHORT).show();
                return;
            }

            if(rotaApi.equals(API_IMOVEL))
                retornoImovel(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void retornoImovel(JSONObject resposta){
        if(resposta.has("sucesso")){
            try {
                Toast.makeText(getContext(), resposta.getString("mensagem"), Toast.LENGTH_SHORT).show();
                if(resposta.getBoolean("sucesso")){
                    VariaveisEstaticas.setProprietarioCadastro(null);
                    VariaveisEstaticas.setEnderecoImovelCadastro(null);
                    VariaveisEstaticas.setDadosImovelCadastro(null);
                    VariaveisEstaticas.setComposicoesImovelCadastro(null);
                    VariaveisEstaticas.setVisitaImovelCadastro(null);
                    VariaveisEstaticas.getFragmentInterface().removerFragment("CadastrarDadosProprietario");
                    VariaveisEstaticas.getFragmentInterface().removerFragment("CadastrarEnderecoImovel");
                    VariaveisEstaticas.getFragmentInterface().removerFragment("CadastrarDadosAnuncio");
                    VariaveisEstaticas.getFragmentInterface().removerFragment("CadastrarInformacoesImovel");
                    VariaveisEstaticas.getFragmentInterface().removerFragment("CadastrarComposicaoImovel");
                    VariaveisEstaticas.getFragmentInterface().removerFragment("CadastrarVisitaImovel");
                    VariaveisEstaticas.getFragmentInterface().alterarFragment("TelaInicial");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}