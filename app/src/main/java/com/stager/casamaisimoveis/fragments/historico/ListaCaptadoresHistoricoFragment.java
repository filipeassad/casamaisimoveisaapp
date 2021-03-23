package com.stager.casamaisimoveis.fragments.historico;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.adapters.CaptadorHistoricoAdapter;
import com.stager.casamaisimoveis.api.GetHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.api.GetHttpImagemAsyncTask;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.models.Captador;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaCaptadoresHistoricoFragment extends Fragment implements HttpResponseInterface {

    private ListView lvCaptadores;
    private HttpResponseInterface httpResponseInterface;
    private String API_BUSCAR_CAPTADORES_VALIDOS = "api/todosCaptadoresValidos";
    private final String API_IMAGEM_USUARIO = "getImagemUsuario";
    private List<Captador> captadoresBuscarImagem;
    private List<Captador> captadoresAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_captadores_historicos, container, false);
        lvCaptadores = (ListView) view.findViewById(R.id.lvCaptadores);

        httpResponseInterface = this;
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Lista Captadores");
        captadoresAdapter = new ArrayList<>();
        buscarCaptadoresValidos();
    }

    private void buscarCaptadoresValidos(){
        GetHttpComHeaderAsyncTask getHttpComHeaderAsyncTask = new GetHttpComHeaderAsyncTask(getContext(), httpResponseInterface, API_BUSCAR_CAPTADORES_VALIDOS);
        getHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_BUSCAR_CAPTADORES_VALIDOS);
    }

    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {
        try {
            if(jsonObject.has("erro")){
                Toast.makeText(getContext(), jsonObject.getString("erro"), Toast.LENGTH_SHORT).show();
                return;
            }

            if(rotaApi.equals(API_BUSCAR_CAPTADORES_VALIDOS))
                retornoCaptadoresValidos(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void retornoImagemBitmap(Bitmap imagem, String rotaAPI) {
        Captador captador = captadoresBuscarImagem.remove(0);

        if(imagem != null && rotaAPI.equals(API_IMAGEM_USUARIO))
           captador.getAutenticacao().setImagemUsuario(imagem);

        captadoresAdapter.add(captador);

        if(captadoresBuscarImagem.size() == 0){
            CaptadorHistoricoAdapter captadorHistoricoAdapter = new CaptadorHistoricoAdapter(getContext(), R.layout.adapter_captadores_historico, captadoresAdapter);
            lvCaptadores.setAdapter(captadorHistoricoAdapter);

            lvCaptadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    VariaveisEstaticas.setCaptadorHistorico((Captador) parent.getItemAtPosition(position));
                    VariaveisEstaticas.getFragmentInterface().alterarFragment("HistoricoCaptador");
                }
            });
        }else{
            Captador captadorSelecionado = captadoresBuscarImagem.get(0);

            GetHttpImagemAsyncTask getHttpImagemAsyncTask = new GetHttpImagemAsyncTask(getContext(),
                    httpResponseInterface,
                    API_IMAGEM_USUARIO);
            getHttpImagemAsyncTask.execute(captadorSelecionado.getAutenticacao().getLinkImagem());
        }
    }

    private void retornoCaptadoresValidos(JSONObject resposta){
        captadoresBuscarImagem = Captador.getListCaptadores(resposta);
        if(captadoresBuscarImagem.size() != 0){
            Captador captadorSelecionado = captadoresBuscarImagem.get(0);
            GetHttpImagemAsyncTask getHttpImagemAsyncTask = new GetHttpImagemAsyncTask(getContext(),
                    httpResponseInterface,
                    API_IMAGEM_USUARIO);
            getHttpImagemAsyncTask.execute(captadorSelecionado.getAutenticacao().getLinkImagem());
        }
    }
}
