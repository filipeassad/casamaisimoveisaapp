package com.stager.casamaisimoveis.alertas;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.api.PostHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONException;
import org.json.JSONObject;

public class AlertaBuscaProprietariosImovel {

    public static void alertaProprietaiosImovel(Context contexto, HttpResponseInterface httpResponseInterface){
        LayoutInflater layoutInflater = LayoutInflater.from(contexto);

        View view = layoutInflater.inflate(R.layout.alerta_buscar_proprietario_imovel, null);

        Button btnVoltarImovel = (Button) view.findViewById(R.id.btnVoltarImovel);
        Button btnBuscar = (Button) view.findViewById(R.id.btnBuscar);

        EditText edtNomeProprietario = (EditText) view.findViewById(R.id.edtNomeProprietario);
        EditText edtTelefoneProprietario = (EditText) view.findViewById(R.id.edtTelefoneProprietario);


        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
        builder.setTitle("Buscar o proprietário");
        builder.setView(view);

        final AlertDialog alerta = builder.create();
        alerta.show();

        btnVoltarImovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject proprietaioBusca = new JSONObject();
                try {
                    proprietaioBusca.put("nome", edtNomeProprietario.getText().toString());
                    proprietaioBusca.put("telefone", edtTelefoneProprietario.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                PostHttpComHeaderAsyncTask postHttpComHeaderAsyncTask;
                postHttpComHeaderAsyncTask = new PostHttpComHeaderAsyncTask(contexto,
                        proprietaioBusca,
                        httpResponseInterface, "api/bucarProprietario");
                postHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + "api/bucarProprietario");
                alerta.dismiss();
            }
        });
    }
}
