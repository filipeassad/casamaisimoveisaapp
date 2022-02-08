package com.stager.casamaisimoveis.fragments.alterar;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.stager.casamaisimoveis.api.GetHttpImagemAsyncTask;
import com.stager.casamaisimoveis.api.OkPostHttpImagem;
import com.stager.casamaisimoveis.api.PutHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.banco.DatabaseManager;
import com.stager.casamaisimoveis.banco.ImagemUploadManager;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.interfaces.ImagemImovelInterface;
import com.stager.casamaisimoveis.models.ImagemImovel;
import com.stager.casamaisimoveis.models.ImagemUpload;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AlterarImagensImovelFragment extends Fragment implements ImagemImovelInterface, HttpResponseInterface {

    private Button btnVoltar;
    private Button btnAvancar;
    private LinearLayout llImagensImovel;
    private Button btnAdicionarImagens;
    private Button btnExcluirTudo;

    private final int PICK_IMAGES = 26;

    private ImagemImovelInterface imagemImovelInterface;
    private List<ImagemImovel> imagensImovel;
    private List<ImagemImovel> imagensImovelListagem;
    private HttpResponseInterface httpResponseInterface = this;
    private ImagemImovel imagemImovelBusca;
    private List<ImagemImovel> imagensSemId;
    private String API_ALTERAR_IMAGEM_IMOVEL = "api/alterarImagensImovel";
    private String API_UPLOAD_IMAGEM_IMOVEL = "api/uploadImagemImovel";
    private List<ImagemImovel> imagensParaRemover;
    private int totalImagems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imagens_imovel, container, false);

        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnAvancar = (Button) view.findViewById(R.id.btnAvancar);
        llImagensImovel = (LinearLayout) view.findViewById(R.id.llImagensImovel);
        btnAdicionarImagens = (Button) view.findViewById(R.id.btnAdicionarImagens);
        btnExcluirTudo = (Button) view.findViewById(R.id.btnExcluirTudo);

        btnAvancar.setText("Salvar");

        imagensImovelListagem = new ArrayList<>();
        imagensParaRemover = new ArrayList<>();

        imagemImovelInterface = this;
        VariaveisEstaticas.setImagemImovelInterface(imagemImovelInterface);
        eventosBotoes();

        if(VariaveisEstaticas.getImovelBusca().getImagensImovel() != null){
            imagensImovel = new ArrayList<>();
            for(ImagemImovel imagemImovel: VariaveisEstaticas.getImovelBusca().getImagensImovel()){
                imagensImovel.add(imagemImovel);
            }
            if(imagensImovel.size() > 0){
                ImagemImovel primeiraImagem = imagensImovel.remove(0);
                imagemImovelBusca = primeiraImagem;
                GetHttpImagemAsyncTask getHttpImagemAsyncTask = new GetHttpImagemAsyncTask(getContext(),
                        httpResponseInterface,
                        "ImagensImovel");
                getHttpImagemAsyncTask.execute(primeiraImagem.getUrl_imagem());
            }
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Alterar");


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
                avancarFormulario();
            }
        });

        btnAdicionarImagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                getActivity().startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGES);
            }
        });

        btnExcluirTudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarDialogParaConfirmarExclusao();
            }
        });
    }

    private void avancarFormulario(){
        if(imagensImovelListagem.size() == 0){
            Toast.makeText(getContext(), "Insira uma imagem do imóvel", Toast.LENGTH_SHORT).show();
            return;
        }
        List<ImagemImovel> imoveisSemId = new ArrayList<>();

        for(ImagemImovel imagemList : imagensImovelListagem){
            if(imagemList.getId() == null)
                imoveisSemId.add(imagemList);
        }

        imagensSemId = imoveisSemId;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("imagensImovel", ImagemImovel.gerarImagemImovelJsonArray(imagensParaRemover));
            PutHttpComHeaderAsyncTask putHttpComHeaderAsyncTask = new PutHttpComHeaderAsyncTask(getContext(), jsonObject, httpResponseInterface, API_ALTERAR_IMAGEM_IMOVEL);
            putHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_ALTERAR_IMAGEM_IMOVEL + "/" + VariaveisEstaticas.getImovelBusca().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void retornoSelecaoImagens(List<Bitmap> imagens) {
        if(imagens.size() > 0){
            for(Bitmap imagem : imagens){
                ImagemImovel imagemImovel = new ImagemImovel();
                imagemImovel.setBitmapImagem(imagem);
                imagensImovelListagem.add(imagemImovel);
            }
            adicionarImagens();
        }
    }

    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {
        try {
            if(jsonObject.has("erro")){
                Toast.makeText(getContext(), jsonObject.getString("erro"), Toast.LENGTH_SHORT).show();
                return;
            }

            if(rotaApi.equals(API_ALTERAR_IMAGEM_IMOVEL))
                retornoAlteracaoImagemImovel(jsonObject);
            else if(rotaApi.equals(API_UPLOAD_IMAGEM_IMOVEL))
                retornoUploadoImagem();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void retornoUploadoImagem(){
        if (imagensSemId.size() != 0) {
            Bitmap primeiraImagem = imagensSemId.remove(0).getBitmapImagem();
            OkPostHttpImagem okPostHttpImagem = new OkPostHttpImagem(primeiraImagem,
                    VariaveisEstaticas.getImovelBusca().getId(),
                    getContext(),
                    httpResponseInterface,
                    API_UPLOAD_IMAGEM_IMOVEL,
                    totalImagems,
                    (totalImagems - imagensSemId.size()));
            okPostHttpImagem.execute(FerramentasBasicas.getURL() + API_UPLOAD_IMAGEM_IMOVEL);
        } else {
            Toast.makeText(getContext(), "Imagens alteradas com sucesso.", Toast.LENGTH_SHORT).show();
            VariaveisEstaticas.getFragmentInterface().voltar();
        }
    }

    private void retornoAlteracaoImagemImovel(JSONObject resposta){

        if(imagensSemId.size() != 0){
            totalImagems = imagensSemId.size();
            /*DatabaseManager databaseManager = new DatabaseManager(getContext());
            ImagemUploadManager imagemUploadManager = new ImagemUploadManager(databaseManager.getWritableDatabase());

            for (ImagemImovel imagem : imagensSemId) {
                ImagemUpload imagemUpload = new ImagemUpload(VariaveisEstaticas.getImovelBusca().getId(), imagem.getBitmapImagem());
                imagemUploadManager.insertImagemUpload(imagemUpload);
                //VariaveisEstaticas.getImagensUpload().add(imagemUpload);
            }
            imagemUploadManager.closeDatabase();
            VariaveisEstaticas.getFragmentInterface().iniciarUploadImagens();*/

            Bitmap primeiraImagem = imagensSemId.remove(0).getBitmapImagem();
            OkPostHttpImagem okPostHttpImagem = new OkPostHttpImagem(primeiraImagem,
                    VariaveisEstaticas.getImovelBusca().getId(),
                    getContext(),
                    httpResponseInterface,
                    API_UPLOAD_IMAGEM_IMOVEL,
                    totalImagems,
                    (totalImagems - imagensSemId.size()));
            okPostHttpImagem.execute(FerramentasBasicas.getURL() + API_UPLOAD_IMAGEM_IMOVEL);
        }else{
            Toast.makeText(getContext(), "Imagens alteradas com sucesso.", Toast.LENGTH_SHORT).show();
            VariaveisEstaticas.getFragmentInterface().voltar();
        }

    }

    @Override
    public void retornoImagemBitmap(Bitmap imagem, String rotaAPI) {
        if(imagem != null){
            ImagemImovel imagemImovelLista = new ImagemImovel(imagemImovelBusca.getId(),
                    imagemImovelBusca.getUrl_imagem(),
                    imagemImovelBusca.getImovel_id(),
                    imagemImovelBusca.getHash());

            imagemImovelLista.setBitmapImagem(imagem);
            imagensImovelListagem.add(imagemImovelLista);

            adicionarImagens();
            if (imagensImovel.size() > 0) {
                ImagemImovel proximo = imagensImovel.remove(0);
                imagemImovelBusca = proximo;
                GetHttpImagemAsyncTask getHttpImagemAsyncTask = new GetHttpImagemAsyncTask(getContext(),
                        httpResponseInterface,
                        "ImagensImovel");
                getHttpImagemAsyncTask.execute(proximo.getUrl_imagem());
            }
        }
    }

    private void adicionarImagens(){
        llImagensImovel.removeAllViews();

        int totalLinhasLista = imagensImovelListagem.size() % 2 != 0 ? (imagensImovelListagem.size() / 2) + 1 : imagensImovelListagem.size() / 2;
        int indexImagem = 0;

        if(imagensImovelListagem.size() > 0)
            btnExcluirTudo.setVisibility(View.VISIBLE);
        else
            btnExcluirTudo.setVisibility(View.GONE);

        for(int i = 0; i < totalLinhasLista; i++){
            LinearLayout.LayoutParams fraLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            fraLayoutParams.setMargins(0, 10, 0,10);
            LinearLayout frameLayout = new LinearLayout(getContext());
            frameLayout.setLayoutParams(fraLayoutParams);
            frameLayout.setOrientation(LinearLayout.HORIZONTAL);

            for(int j = 0; j < 2; j++){
                if(imagensImovelListagem.size() > indexImagem){
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View imagemImovelView = inflater.inflate(R.layout.adapter_imagem_imovel, frameLayout, false);

                    ImageView ivImagemImovel = (ImageView) imagemImovelView.findViewById(R.id.ivImagemImovel);
                    ivImagemImovel.setImageBitmap(imagensImovelListagem.get(indexImagem).getBitmapImagem());

                    Button btnRemoverAmbiente = (Button) imagemImovelView.findViewById(R.id.btnRemoverAmbiente);
                    btnRemoverAmbiente.setTag(indexImagem);
                    btnRemoverAmbiente.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            removerImagem((Integer) v.getTag());
                        }
                    });

                    frameLayout.addView(imagemImovelView);
                    indexImagem++;
                }
            }
            llImagensImovel.addView(frameLayout);
        }
    }

    private void removerImagem(int index){
        ImagemImovel imagemImovelRemovido = imagensImovelListagem.remove(index);
        if(imagemImovelRemovido.getId() != null)
            imagensParaRemover.add(imagemImovelRemovido);
        adicionarImagens();
    }

    private void criarDialogParaConfirmarExclusao(){
        new AlertDialog.Builder(getContext())
                .setTitle("Atenção")
                .setMessage("Você tem certeza que deseja excluir todas as imagens ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        adicionarTodasAsImagensParaRemover();
                        adicionarImagens();
                    }})
                .setNegativeButton("Não", null).show();
    }

    public void adicionarTodasAsImagensParaRemover(){
        for(ImagemImovel imagem : imagensImovelListagem){
            if(imagem.getId() != null)
                imagensParaRemover.add(imagem);
        }

        imagensImovelListagem = new ArrayList<>();
    }
}
