package com.stager.casamaisimoveis.fragments.visualizar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.api.GetHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.api.GetHttpImagemAsyncTask;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.models.ImagemImovel;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class VisualizarImagensImovelFragment extends Fragment implements HttpResponseInterface {

    private Button btnVoltar;
    private Button btnAvancar;
    private LinearLayout llImagensImovel;
    private Button btnAdicionarImagens;
    private List<Bitmap> imagensSelecionadas = new ArrayList<>();
    private HttpResponseInterface httpResponseInterface;
    private List<ImagemImovel> imagensImovel;
    private Button btnEditar;
    private String API_LISTAR_IMAGENS_IMOVEL = "api/listarImagensImovel/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imagens_imovel, container, false);

        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnAvancar = (Button) view.findViewById(R.id.btnAvancar);
        llImagensImovel = (LinearLayout) view.findViewById(R.id.llImagensImovel);
        btnAdicionarImagens = (Button) view.findViewById(R.id.btnAdicionarImagens);
        btnEditar = (Button) view.findViewById(R.id.btnEditar);

        btnEditar.setVisibility(View.VISIBLE);

        btnAdicionarImagens.setVisibility(View.GONE);
        httpResponseInterface = this;

        eventosBotoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Visualizar");
        imagensSelecionadas = new ArrayList<>();
        if(VariaveisEstaticas.getImovelBusca().getImagensImovel() != null){
            GetHttpComHeaderAsyncTask getHttpComHeaderAsyncTask = new GetHttpComHeaderAsyncTask(getContext(), httpResponseInterface, API_LISTAR_IMAGENS_IMOVEL);
            getHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_LISTAR_IMAGENS_IMOVEL + VariaveisEstaticas.getImovelBusca().getId());
        }
    }

    private void eventosBotoes(){
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VariaveisEstaticas.getFragmentInterface().voltar();
            }
        });

        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VariaveisEstaticas.getFragmentInterface().alterarFragment("VisualizarVisitaImovel");
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().alterarFragment("AlterarImagensImovel");
            }
        });
    }

    private void adicionarImagens(){
        llImagensImovel.removeAllViews();
        int totalLinhasLista = imagensSelecionadas.size() % 2 != 0 ? (imagensSelecionadas.size() / 2) + 1 : imagensSelecionadas.size() / 2;
        int indexImagem = 0;
        for(int i = 0; i < totalLinhasLista; i++){
            LinearLayout.LayoutParams fraLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            fraLayoutParams.setMargins(0, 10, 0,10);
            LinearLayout frameLayout = new LinearLayout(getContext());
            frameLayout.setLayoutParams(fraLayoutParams);
            frameLayout.setOrientation(LinearLayout.HORIZONTAL);

            for(int j = 0; j < 2; j++){
                if(imagensSelecionadas.size() > indexImagem){
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View imagemImovelView = inflater.inflate(R.layout.adapter_imagem_imovel, frameLayout, false);

                    ImageView ivImagemImovel = (ImageView) imagemImovelView.findViewById(R.id.ivImagemImovel);
                    ivImagemImovel.setImageBitmap(imagensSelecionadas.get(indexImagem));

                    Button btnRemoverAmbiente = (Button) imagemImovelView.findViewById(R.id.btnRemoverAmbiente);
                    btnRemoverAmbiente.setVisibility(View.GONE);

                    frameLayout.addView(imagemImovelView);
                    indexImagem++;
                }
            }
            llImagensImovel.addView(frameLayout);
        }
    }

    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {
        try {
            if(jsonObject.has("erro")){
                Toast.makeText(getContext(), jsonObject.getString("erro"), Toast.LENGTH_SHORT).show();
                return;
            }

            if(rotaApi.equals(API_LISTAR_IMAGENS_IMOVEL))
                retornoImagensImovel(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void retornoImagensImovel(JSONObject resposta){
        try {
            VariaveisEstaticas.getImovelBusca().setImagensImovel(ImagemImovel.gerarListaImagemImovelBuscaImovel(resposta.getJSONArray("array")));
            imagensImovel = new ArrayList<>();
            for(ImagemImovel imagemImovel: VariaveisEstaticas.getImovelBusca().getImagensImovel()){
                imagensImovel.add(imagemImovel);
            }
            if(imagensImovel.size() > 0){
                ImagemImovel primeiraImagem = imagensImovel.remove(0);
                GetHttpImagemAsyncTask getHttpImagemAsyncTask = new GetHttpImagemAsyncTask(getContext(),
                        httpResponseInterface,
                        "ImagensImovel");
                getHttpImagemAsyncTask.execute(primeiraImagem.getUrl_imagem());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void retornoImagemBitmap(Bitmap imagem, String rotaAPI) {
        if(imagem != null){
            imagensSelecionadas.add(imagem);
            adicionarImagens();
            if (imagensImovel.size() > 0) {
                ImagemImovel proximo = imagensImovel.remove(0);
                GetHttpImagemAsyncTask getHttpImagemAsyncTask = new GetHttpImagemAsyncTask(getContext(),
                        httpResponseInterface,
                        "ImagensImovel");
                getHttpImagemAsyncTask.execute(proximo.getUrl_imagem());
            }
        }
    }
}
