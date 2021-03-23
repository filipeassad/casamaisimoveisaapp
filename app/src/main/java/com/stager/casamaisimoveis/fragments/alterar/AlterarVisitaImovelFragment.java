package com.stager.casamaisimoveis.fragments.alterar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlterarVisitaImovelFragment extends Fragment implements HttpResponseInterface{
    private Button btnVoltar;
    private Button btnSalvar;
    private TextView txtNomeCaptador;
    private TextView txtProfissao;
    private TextView txtDataVisita;
    private EditText edtDataRetorno;
    private ImageView ivImagemCaptador;
    private HttpResponseInterface httpResponseInterface;
    private String API_VISITA = "api/visitaImovel";

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
        ivImagemCaptador = (ImageView) view.findViewById(R.id.ivImagemCaptador);

        edtDataRetorno.addTextChangedListener(MascaraEditText.mask(edtDataRetorno, MascaraEditText.FORMAT_DATE));

        httpResponseInterface = this;

        eventosBotoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Cadastrar");

        if(VariaveisEstaticas.getCaptador() != null){
            Captador captador = VariaveisEstaticas.getCaptador();
            txtNomeCaptador.setText(captador.getNome());
            txtProfissao.setText("Captador");

            if(VariaveisEstaticas.getAutenticacao().getImagemUsuario() != null)
                ivImagemCaptador.setImageBitmap(VariaveisEstaticas.getAutenticacao().getImagemUsuario());
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

        DadosImovel dadosImovel = VariaveisEstaticas.getImovelBusca().getDadosImovel();

        VisitaImovel visitaImovel = new VisitaImovel(FerramentasBasicas.converterDataParaString(new Date(), "dd/MM/yyyy"),
                edtDataRetorno.getText().toString(),
                VariaveisEstaticas.getCaptador().getId(),
                dadosImovel.getId(),
                VariaveisEstaticas.getCaptador());
        dadosImovel.getVisitasImovel().add(visitaImovel);
        PostHttpComHeaderAsyncTask postHttpComHeaderAsyncTask = new PostHttpComHeaderAsyncTask(getContext(),
                visitaImovel.gerarVisitaJson(),
                httpResponseInterface,
                API_VISITA);
        postHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_VISITA);
    }



    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {
        try {
            if(jsonObject.has("erro")){
                Toast.makeText(getContext(), jsonObject.getString("erro"), Toast.LENGTH_SHORT).show();
                return;
            }

            if(rotaApi.equals(API_VISITA))
                retornoCadastroVisita(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void retornoImagemBitmap(Bitmap imagem, String rotaAPI) {

    }

    private void retornoCadastroVisita(JSONObject resposta){
        if(resposta.has("sucesso")){
            try {
                Toast.makeText(getContext(), resposta.getString("mensagem"), Toast.LENGTH_SHORT).show();
                if(resposta.getBoolean("sucesso")){
                   VariaveisEstaticas.getFragmentInterface().voltar();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
