package com.stager.casamaisimoveis.fragments.cadastrar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.interfaces.ImagemImovelInterface;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CadastrarImagensImovelFragment extends Fragment implements ImagemImovelInterface {

    private Button btnVoltar;
    private Button btnAvancar;
    private LinearLayout llImagensImovel;
    private Button btnAdicionarImagens;
    private Button btnExcluirTudo;

    private final int PICK_IMAGES = 26;

    private List<Bitmap> imagensSelecionadas = new ArrayList<>();
    private ImagemImovelInterface imagemImovelInterface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imagens_imovel, container, false);

        btnVoltar = (Button) view.findViewById(R.id.btnVoltar);
        btnAvancar = (Button) view.findViewById(R.id.btnAvancar);
        llImagensImovel = (LinearLayout) view.findViewById(R.id.llImagensImovel);
        btnAdicionarImagens = (Button) view.findViewById(R.id.btnAdicionarImagens);
        btnExcluirTudo = (Button) view.findViewById(R.id.btnExcluirTudo);

        imagemImovelInterface = this;
        VariaveisEstaticas.setImagemImovelInterface(imagemImovelInterface);
        eventosBotoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Cadastrar");
        if(VariaveisEstaticas.getImagensImovelCadastro() != null){
            imagensSelecionadas = VariaveisEstaticas.getImagensImovelCadastro();
            adicionarImagens();
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
        if(imagensSelecionadas.size() == 0){
            Toast.makeText(getContext(), "Insira uma imagem do imóvel", Toast.LENGTH_SHORT).show();
            return;
        }
        VariaveisEstaticas.setImagensImovelCadastro(imagensSelecionadas);
        VariaveisEstaticas.getFragmentInterface().alterarFragment("CadastrarVisitaImovel");
    }

    @Override
    public void retornoSelecaoImagens(List<Bitmap> imagens) {
        if(imagens.size() > 0){
            for(Bitmap imagem : imagens){
                imagensSelecionadas.add(imagem);
            }
            adicionarImagens();
        }
    }

    private void adicionarImagens(){
        llImagensImovel.removeAllViews();

        int totalLinhasLista = imagensSelecionadas.size() % 2 != 0 ? (imagensSelecionadas.size() / 2) + 1 : imagensSelecionadas.size() / 2;
        int indexImagem = 0;

        if(imagensSelecionadas.size() > 0)
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
                if(imagensSelecionadas.size() > indexImagem){
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View imagemImovelView = inflater.inflate(R.layout.adapter_imagem_imovel, frameLayout, false);

                    ImageView ivImagemImovel = (ImageView) imagemImovelView.findViewById(R.id.ivImagemImovel);
                    ivImagemImovel.setImageBitmap(imagensSelecionadas.get(indexImagem));

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
        imagensSelecionadas.remove(index);
        adicionarImagens();
    }

    private void criarDialogParaConfirmarExclusao(){
        new AlertDialog.Builder(getContext())
                .setTitle("Atenção")
                .setMessage("Você tem certeza que deseja excluir todas as imagens ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        imagensSelecionadas = new ArrayList<>();
                        adicionarImagens();
                    }})
                .setNegativeButton("Não", null).show();
    }
}
