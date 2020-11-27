package com.stager.casamaisimoveis.fragments.historico;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.api.GetHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.models.RotaCaptador;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MapaHistoricoFragment extends Fragment implements OnMapReadyCallback, HttpResponseInterface {

    private GoogleMap googleMapsFragmentHistorico;
    private TextView txtDataHistorico;
    private TextView txtNomeUsuario;

    private HttpResponseInterface httpResponseInterface;
    private String API_ROTAS_CAPTADOR = "api/rotasCaptador/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa_historico, container, false);

        txtDataHistorico = (TextView) view.findViewById(R.id.txtDataHistorico);
        txtNomeUsuario = (TextView) view.findViewById(R.id.txtNomeUsuario);



        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMapsFragmentHistorico);
        mapFragment.getMapAsync(this);

        httpResponseInterface = this;

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Mapa Histórico");
        txtDataHistorico.setText(VariaveisEstaticas.getRotaCaptadorHistoricoSelecionado().getData_rota());
        txtNomeUsuario.setText(VariaveisEstaticas.getCaptador().getNome());
    }

    private void carregarRotasCaptador(){
        GetHttpComHeaderAsyncTask getHttpComHeaderAsyncTask = new GetHttpComHeaderAsyncTask(getContext(),
                httpResponseInterface,
                API_ROTAS_CAPTADOR);
        getHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL()
                + API_ROTAS_CAPTADOR
                + VariaveisEstaticas.getRotaCaptadorHistoricoSelecionado().getDataRotaFormatoServidor()
                + "/"
                + VariaveisEstaticas.getCaptador().getId());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapsFragmentHistorico = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMapsFragmentHistorico.setMyLocationEnabled(true);
        carregarRotasCaptador();
    }

    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {
        try {
            if(jsonObject.has("erro")){
                Toast.makeText(getContext(), jsonObject.getString("erro"), Toast.LENGTH_SHORT).show();
                return;
            }

            if(rotaApi.equals(API_ROTAS_CAPTADOR))
                retornoRotaCaptador(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void retornoImagemBitmap(Bitmap imagem, String rotaAPI) {

    }

    private void retornoRotaCaptador(JSONObject resposta){
        List<RotaCaptador> rotasCaptador = RotaCaptador.listarEnderecosRotas(resposta);

        if(rotasCaptador.size() > 0){
            PolylineOptions options = new PolylineOptions();
            options.color( Color.parseColor( "#FF6347" ) );
            options.width( 7 );
            options.visible( true );

            RotaCaptador primeiroRotaCaptador = rotasCaptador.get(0);

            LatLng latlng = new LatLng(Double.parseDouble(primeiroRotaCaptador.getLatitude()),
                    Double.parseDouble(primeiroRotaCaptador.getLongitude()));

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                    latlng, 15);

            googleMapsFragmentHistorico.animateCamera(cameraUpdate);

            for(RotaCaptador rotaCaptador : rotasCaptador){
                LatLng latlngRota = new LatLng(Double.parseDouble(rotaCaptador.getLatitude()),
                        Double.parseDouble(rotaCaptador.getLongitude()));

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latlngRota);

                markerOptions.title(rotaCaptador.getData_hora_rota());

                options.add(latlngRota);
                googleMapsFragmentHistorico.addMarker(markerOptions);
            }

            googleMapsFragmentHistorico.addPolyline( options );
        }
    }
}
