package com.stager.casamaisimoveis.fragments.visualizar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputType;
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
import com.stager.casamaisimoveis.api.GetHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.api.GetHttpImagemAsyncTask;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.models.VisitaImovel;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class VisualizarVisitaImovelFragment extends Fragment implements HttpResponseInterface {

    private Button btnVoltar;
    private Button btnSalvar;
    private TextView txtNomeCaptador;
    private TextView txtProfissao;
    private TextView txtDataVisita;
    private EditText edtDataRetorno;
    private Button btnEditar;
    private ImageView ivImagemCaptador;

    HttpResponseInterface httpResponseInterface;
    private String API_IMAGEM_CAPTADOR = "api/captador/imagemUsuario/";

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
        btnEditar = (Button) view.findViewById(R.id.btnEditar);
        ivImagemCaptador = (ImageView) view.findViewById(R.id.ivImagemCaptador);

        btnSalvar.setText("Sair");
        btnEditar.setText("Nova Visita");

        httpResponseInterface = this;

        edtDataRetorno.setInputType(InputType.TYPE_NULL);
        btnEditar.setVisibility(View.VISIBLE);
        eventosBotoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Visualizar");

        if(VariaveisEstaticas.getImovelBusca() != null){
            List<VisitaImovel> visitas = VariaveisEstaticas.getImovelBusca().getDadosImovel().getVisitasImovel();
            VisitaImovel visitaImovel = visitas.get(0);

            txtDataVisita.setText(visitaImovel.getData_visita());
            edtDataRetorno.setText(visitaImovel.getRetorno());
            txtNomeCaptador.setText(visitaImovel.getCaptador().getNome());
            txtProfissao.setText("Captador");

            GetHttpComHeaderAsyncTask getHttpComHeaderAsyncTask = new GetHttpComHeaderAsyncTask(getContext(),httpResponseInterface, API_IMAGEM_CAPTADOR);
            getHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_IMAGEM_CAPTADOR + visitaImovel.getCaptador().getId());
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
                VariaveisEstaticas.getFragmentInterface().alterarFragment("BuscarImovel");
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().alterarFragment("AlterarVisitaImovel");
            }
        });
    }

    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {
        try {
            if(jsonObject.has("erro")){
                Toast.makeText(getContext(), jsonObject.getString("erro"), Toast.LENGTH_SHORT).show();
                return;
            }

            if(rotaApi.equals(API_IMAGEM_CAPTADOR))
                retornoCadastroVisita(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void retornoImagemBitmap(Bitmap imagem, String rotaAPI) {
        if(imagem != null){
            ivImagemCaptador.setImageBitmap(imagem);
        }
    }

    private void retornoCadastroVisita(JSONObject resposta){

        if(resposta.has("imagemUsuario")){
            try {
                JSONObject imagemUsuario = resposta.getJSONObject("imagemUsuario");
                if(imagemUsuario != null){
                    GetHttpImagemAsyncTask  getHttpImagemAsyncTask  = new GetHttpImagemAsyncTask(getContext(), httpResponseInterface, imagemUsuario.getString("url_imagem"));
                    getHttpImagemAsyncTask.execute(imagemUsuario.getString("url_imagem"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
