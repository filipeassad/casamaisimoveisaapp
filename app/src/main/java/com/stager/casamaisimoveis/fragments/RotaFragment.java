package com.stager.casamaisimoveis.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.adapters.RotaAdapter;
import com.stager.casamaisimoveis.api.GetHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.models.Rota;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RotaFragment extends Fragment implements HttpResponseInterface {

    private ListView lvRota;
    private Button btnCadastrarRota;

    private String FRAGMENT_CADASTRO_ROTA = "CadastrarRota";
    private String ROTA_API = "api/rotasCaptador";

    private HttpResponseInterface httpResponseInterface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rota, container, false);

        lvRota = (ListView) view.findViewById(R.id.lvRota);
        btnCadastrarRota = (Button) view.findViewById(R.id.btnCadastrarRota);

        httpResponseInterface = this;

        eventosBotoes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        buscarRotas();
    }

    private void buscarRotas(){
        GetHttpComHeaderAsyncTask getHttpComHeaderAsyncTask = new GetHttpComHeaderAsyncTask(getContext(), httpResponseInterface, ROTA_API);
        getHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + ROTA_API + "/" + VariaveisEstaticas.getCaptador().getId());
    }

    private void eventosBotoes(){
        btnCadastrarRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().alterarFragment(FRAGMENT_CADASTRO_ROTA);
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

            if(rotaApi.equals(ROTA_API))
                retornRota(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void retornRota(JSONObject resposta){
        Rota rota = new Rota();
        List<Rota> rotas = rota.listarRotas(resposta);

        RotaAdapter rotaAdapter = new RotaAdapter(getContext(), R.layout.adapter_rota, rotas);

        lvRota.setAdapter(rotaAdapter);

        lvRota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                VariaveisEstaticas.getFragmentInterface().alterarFragment("MapaRota");

            }
        });
    }
}
