package com.stager.casamaisimoveis.fragments;

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
import com.stager.casamaisimoveis.adapters.HistoricoAdapter;
import com.stager.casamaisimoveis.api.GetHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.models.RotaCaptador;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class HistoricoCaptadorFragment extends Fragment implements HttpResponseInterface {

    private ListView lvHistorico;

    private String API_DATAS_CAPTADOR = "api/datasCaptador/";
    private HttpResponseInterface httpResponseInterface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historico_captador, container, false);

        lvHistorico = (ListView) view.findViewById(R.id.lvHistorico);
        httpResponseInterface = this;

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Histórico Captador");
        buscarDatas();
    }

    private void buscarDatas(){
        GetHttpComHeaderAsyncTask getHttpComHeaderAsyncTask = new GetHttpComHeaderAsyncTask(getContext(), httpResponseInterface, API_DATAS_CAPTADOR);
        getHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_DATAS_CAPTADOR + VariaveisEstaticas.getCaptador().getId());
    }

    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {
        try {
            if(jsonObject.has("erro")){
                Toast.makeText(getContext(), jsonObject.getString("erro"), Toast.LENGTH_SHORT).show();
                return;
            }

            if(rotaApi.equals(API_DATAS_CAPTADOR))
                retornoHistorico(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void retornoHistorico(JSONObject resposta){
        List<RotaCaptador> rotasCaptador = RotaCaptador.gerarRotasCaptadorDatas(resposta);

        HistoricoAdapter historicoAdapter = new HistoricoAdapter(getContext(), R.layout.adapter_historico_captador, rotasCaptador);
        lvHistorico.setAdapter(historicoAdapter);

        lvHistorico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VariaveisEstaticas.setRotaCaptadorHistoricoSelecionado((RotaCaptador) parent.getItemAtPosition(position));
                VariaveisEstaticas.getFragmentInterface().alterarFragment("MapaHistoricoCaptador");
            }
        });
    }
}
