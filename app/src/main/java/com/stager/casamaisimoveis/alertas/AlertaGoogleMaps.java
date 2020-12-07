package com.stager.casamaisimoveis.alertas;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.interfaces.MapaRotaInterface;
import com.stager.casamaisimoveis.models.EnderecoRota;

public class AlertaGoogleMaps {

    public static void alertaGoogleMaps(Context contexto, final EnderecoRota enderecoRota, final MapaRotaInterface mapaRotaInterface){

        LayoutInflater layoutInflater = LayoutInflater.from(contexto);

        View view = layoutInflater.inflate(R.layout.alerta_rota_map, null);

        TextView txtBairroMapa = (TextView) view.findViewById(R.id.txtBairroMapa);
        TextView txtRuaMapa = (TextView) view.findViewById(R.id.txtRuaMapa);
        TextView txtNumeroMapa = (TextView) view.findViewById(R.id.txtNumeroMapa);

        Button btnRota = (Button) view.findViewById(R.id.btnRota);
        Button btnCadastrarImovel = (Button) view.findViewById(R.id.btnCadastrarImovel);
        Button btnExcluirEndereco = (Button) view.findViewById(R.id.btnExcluirEndereco);

        txtBairroMapa.setText(enderecoRota.getBairro());
        txtRuaMapa.setText(enderecoRota.getRua());
        txtNumeroMapa.setText(enderecoRota.getNumero());

        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
        builder.setTitle("Endereço Rota");
        builder.setView(view);

        final AlertDialog alerta = builder.create();
        alerta.show();

        btnRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
                mapaRotaInterface.gerarRota(enderecoRota);
            }
        });

        btnCadastrarImovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
                mapaRotaInterface.criarImovel(enderecoRota);
            }
        });

        btnExcluirEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
                mapaRotaInterface.excluirEndereco(enderecoRota);
            }
        });



    }

}
