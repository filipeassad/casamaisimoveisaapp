package com.stager.casamaisimoveis.fragments;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

public class MapaRotaFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMapsFragment;

    private Button btnNovoEndereco;
    private Button btnNovoImovel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa_rota, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.googleMapsFragment);
        mapFragment.getMapAsync(this);

        btnNovoEndereco = (Button) view.findViewById(R.id.btnNovoEndereco);
        btnNovoImovel = (Button) view.findViewById(R.id.btnNovoImovel);

        eventosBotoes();

        return view;
    }

    private void eventosBotoes(){
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
}