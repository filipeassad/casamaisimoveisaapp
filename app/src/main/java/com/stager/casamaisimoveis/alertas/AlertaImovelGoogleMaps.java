package com.stager.casamaisimoveis.alertas;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.interfaces.MapaRotaInterface;
import com.stager.casamaisimoveis.models.Imovel;
import com.stager.casamaisimoveis.models.ItemSpinner;
import com.stager.casamaisimoveis.utilitarios.MontarSpinners;

import java.util.List;

public class AlertaImovelGoogleMaps {

    public static void alertaGoogleMaps(Context contexto, final Imovel imovel, final MapaRotaInterface mapaRotaInterface){

        LayoutInflater layoutInflater = LayoutInflater.from(contexto);

        View view = layoutInflater.inflate(R.layout.alerta_imovel_mapa, null);

        TextView txtBairroMapa = (TextView) view.findViewById(R.id.txtBairroMapa);
        TextView txtRuaMapa = (TextView) view.findViewById(R.id.txtRuaMapa);
        TextView txtNumeroMapa = (TextView) view.findViewById(R.id.txtNumeroMapa);
        TextView txtFaseObra = (TextView) view.findViewById(R.id.txtFaseObra);
        Button btnVisualizarImovel = (Button) view.findViewById(R.id.btnVisualizarImovel);

        txtBairroMapa.setText(imovel.getEnderecoImovel().getBairro());
        txtRuaMapa.setText(imovel.getEnderecoImovel().getRua());
        txtNumeroMapa.setText(imovel.getEnderecoImovel().getNumero());

        MontarSpinners montarSpinners = new MontarSpinners();
        List<ItemSpinner> fasesObra = montarSpinners.listarFaseImovel();

        for(ItemSpinner faseObra : fasesObra){
            if(faseObra.getId() == imovel.getDadosImovel().getFase_obra())
                txtFaseObra.setText(faseObra.getDescricao());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
        builder.setTitle("Endereço Imóvel");
        builder.setView(view);

        final AlertDialog alerta = builder.create();
        alerta.show();

        btnVisualizarImovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
                mapaRotaInterface.visualizarImovel(imovel);
            }
        });



    }
}
