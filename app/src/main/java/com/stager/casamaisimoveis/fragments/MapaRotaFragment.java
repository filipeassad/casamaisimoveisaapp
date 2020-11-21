package com.stager.casamaisimoveis.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.api.GetHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONObject;

public class MapaRotaFragment extends Fragment implements OnMapReadyCallback, HttpResponseInterface {

    private GoogleMap googleMapsFragment;

    private Button btnNovoEndereco;
    private Button btnNovoImovel;
    private HttpResponseInterface httpResponseInterface;
    private String API_ENDERECOS_ROTAS = "api/endereco_rota/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa_rota, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMapsFragment);
        mapFragment.getMapAsync(this);

        httpResponseInterface = this;

        btnNovoEndereco = (Button) view.findViewById(R.id.btnNovoEndereco);
        btnNovoImovel = (Button) view.findViewById(R.id.btnNovoImovel);

        eventosBotoes();
        carregarEnderecos();

        return view;
    }

    private void carregarEnderecos(){
        GetHttpComHeaderAsyncTask getHttpComHeaderAsyncTask = new GetHttpComHeaderAsyncTask(getContext(),
                httpResponseInterface,
                API_ENDERECOS_ROTAS + VariaveisEstaticas.getRotaSelecionada().getId());
        getHttpComHeaderAsyncTask.execute(API_ENDERECOS_ROTAS + VariaveisEstaticas.getRotaSelecionada().getId());
    }

    private void eventosBotoes() {
        btnNovoEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().alterarFragment("CadastrarEnderecoRota");
            }
        });

        btnNovoImovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().alterarFragment("CadastrarDadosProprietario");
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapsFragment = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           return;
        }
        googleMapsFragment.setMyLocationEnabled(true);
        googleMapsFragment.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng latlng = new LatLng(location.getLatitude(),location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latlng);

                markerOptions.title("My Marker");
                googleMapsFragment.clear();
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        latlng, 15);
                googleMapsFragment.animateCamera(cameraUpdate);
                googleMapsFragment.addMarker(markerOptions);
            }
        });
    }

    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {

    }
}