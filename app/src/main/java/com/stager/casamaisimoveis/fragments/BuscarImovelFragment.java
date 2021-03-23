package com.stager.casamaisimoveis.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.adapters.ImovelBuscaAdapter;
import com.stager.casamaisimoveis.api.GetHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.models.Imovel;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.MascaraEditText;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BuscarImovelFragment extends Fragment implements HttpResponseInterface {

    private EditText edtNomeProprietario;
    private EditText edtCpfProprietario;
    private EditText edtTelefoneProprietario;
    private EditText edtBairroEnderecoImovel;
    private EditText edtRuaEnderecoImovel;
    private EditText edtNumeroEnderecoImovel;
    private Button btnBuscar;
    private ListView lvImoveis;

    HttpResponseInterface httpResponseInterface;
    private String API_BUSCAR_IMOVEL = "api/buscarImovel/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar_imovel, container, false);

        edtNomeProprietario = (EditText) view.findViewById(R.id.edtNomeProprietario);
        edtCpfProprietario = (EditText) view.findViewById(R.id.edtCpfProprietario);
        edtTelefoneProprietario = (EditText) view.findViewById(R.id.edtTelefoneProprietario);
        edtBairroEnderecoImovel = (EditText) view.findViewById(R.id.edtBairroEnderecoImovel);
        edtRuaEnderecoImovel = (EditText) view.findViewById(R.id.edtRuaEnderecoImovel);
        edtNumeroEnderecoImovel = (EditText) view.findViewById(R.id.edtNumeroEnderecoImovel);
        btnBuscar = (Button) view.findViewById(R.id.btnBuscar);
        lvImoveis = (ListView) view.findViewById(R.id.lvImoveis);

        edtCpfProprietario.addTextChangedListener(MascaraEditText.mask(edtCpfProprietario, MascaraEditText.FORMAT_CPF));
        edtTelefoneProprietario.addTextChangedListener(MascaraEditText.mask(edtTelefoneProprietario, MascaraEditText.FORMAT_FONE));

        httpResponseInterface = this;

        eventosBotoes();

        return view;
    }

    private void eventosBotoes(){
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarImovel();
            }
        });
    }

    private void buscarImovel(){
        VariaveisEstaticas.getFragmentInterface().fecharTeclado();
        GetHttpComHeaderAsyncTask getHttpComHeaderAsyncTask = new GetHttpComHeaderAsyncTask(getContext(),
                httpResponseInterface,
                API_BUSCAR_IMOVEL);
        getHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL()
                + API_BUSCAR_IMOVEL
                + ( edtNomeProprietario.getText().toString().trim().equals("") == false ? edtNomeProprietario.getText().toString() : "null")
                + "/" + ( edtCpfProprietario.getText().toString().trim().equals("") == false ? edtCpfProprietario.getText().toString() : "null")
                + "/" + ( edtTelefoneProprietario.getText().toString().trim().equals("") == false ? edtTelefoneProprietario.getText().toString() : "null")
                + "/" + ( edtBairroEnderecoImovel.getText().toString().trim().equals("") == false ? edtBairroEnderecoImovel.getText().toString() : "null")
                + "/" + ( edtRuaEnderecoImovel.getText().toString().trim().equals("") == false ? edtRuaEnderecoImovel.getText().toString() : "null")
                + "/" + ( edtNumeroEnderecoImovel.getText().toString().trim().equals("") == false ? edtNumeroEnderecoImovel.getText().toString() : "null"));
    }


    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {
        try {
            if(jsonObject.has("erro")){
                Toast.makeText(getContext(), jsonObject.getString("erro"), Toast.LENGTH_SHORT).show();
                return;
            }

            if(rotaApi.equals(API_BUSCAR_IMOVEL))
                retornoBuscaImovel(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void retornoImagemBitmap(Bitmap imagem, String rotaAPI) {

    }

    private void retornoBuscaImovel(JSONObject resposta){
        List<Imovel> imoveis = Imovel.gerarListaImoveisBuscaImovel(resposta);
        ImovelBuscaAdapter imovelBuscaAdapter = new ImovelBuscaAdapter(getContext(), R.layout.adapter_imovel_busca, imoveis);
        lvImoveis.setAdapter(imovelBuscaAdapter);
        lvImoveis.setLayoutParams(parametrosListView(imoveis));

        lvImoveis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VariaveisEstaticas.setImovelBusca((Imovel) parent.getItemAtPosition(position));
                VariaveisEstaticas.getFragmentInterface().alterarFragment("VisualizarDadosProprietario");
            }
        });
    }

    private LinearLayout.LayoutParams parametrosListView( List<Imovel> imoveis){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (250 * imoveis.size()));
        layoutParams.setMargins(0,50,0,20);

        return layoutParams;
    }
}
