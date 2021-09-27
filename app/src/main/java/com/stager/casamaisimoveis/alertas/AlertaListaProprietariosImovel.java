package com.stager.casamaisimoveis.alertas;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.adapters.ProprietarioImovelAdapter;
import com.stager.casamaisimoveis.adapters.TelefoneProprietarioAdapter;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.interfaces.ProprietarioInterface;
import com.stager.casamaisimoveis.models.Imovel;
import com.stager.casamaisimoveis.models.Proprietario;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import java.util.List;

public class AlertaListaProprietariosImovel {

    public static void alertaListaProprietaiosImovel(Context contexto, ProprietarioInterface proprietarioInterface, List<Proprietario> proprietarios){
        LayoutInflater layoutInflater = LayoutInflater.from(contexto);

        View view = layoutInflater.inflate(R.layout.alerta_lista_proprietarios_imovel, null);

        ListView lvProprietarios = (ListView) view.findViewById(R.id.lvProprietarios);

        ProprietarioImovelAdapter proprietarioImovelAdapter = new ProprietarioImovelAdapter(contexto,
                R.layout.adapter_proprietario_imovel,
                proprietarios);

        lvProprietarios.setAdapter(proprietarioImovelAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
        builder.setTitle("Selecione o proprietário");
        builder.setView(view);

        final AlertDialog alerta = builder.create();
        alerta.show();

        lvProprietarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                proprietarioInterface.selecionarProprietario((Proprietario) parent.getItemAtPosition(position));
                alerta.dismiss();
            }
        });
    }
}
