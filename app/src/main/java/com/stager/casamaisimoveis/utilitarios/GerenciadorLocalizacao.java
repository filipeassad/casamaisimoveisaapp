package com.stager.casamaisimoveis.utilitarios;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.stager.casamaisimoveis.api.PostHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.models.RotaCaptador;

import org.json.JSONObject;

import java.util.Date;

public class GerenciadorLocalizacao implements LocationListener, HttpResponseInterface {

    private Location mLastLocation;
    private Context myContext;
    private HttpResponseInterface httpResponseInterface;

    private String API_ROTA_CAPTADO = "api/rotaCaptador";

    public GerenciadorLocalizacao(String provider, Context context) {
        mLastLocation = new Location(provider);
        this.myContext = context;
        httpResponseInterface = this;
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation.set(location);

        if(FerramentasBasicas.isOnline(this.myContext)){
            RotaCaptador rotaCaptador = new RotaCaptador(location.getLatitude() + "",
                    location.getLongitude() + "",
                    FerramentasBasicas.converterDataParaString(new Date(),
                            "dd/MM/yyyy HH:mm"),
                    FerramentasBasicas.converterDataParaString(new Date(), "yyyy-MM-dd'T'HH:mm:ss"),
                    VariaveisEstaticas.getCaptador().getId());

            PostHttpComHeaderAsyncTask postHttpComHeaderAsyncTask = new PostHttpComHeaderAsyncTask(myContext,
                    rotaCaptador.gerarRotaCaptadorJSON(),
                    httpResponseInterface, API_ROTA_CAPTADO);
            postHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_ROTA_CAPTADO);
        }
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {

    }

    @Override
    public void retornoImagemBitmap(Bitmap imagem, String rotaAPI) {

    }
}
