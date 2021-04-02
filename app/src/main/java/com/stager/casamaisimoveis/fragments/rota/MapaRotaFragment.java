package com.stager.casamaisimoveis.fragments.rota;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.alertas.AlertaGoogleMaps;
import com.stager.casamaisimoveis.alertas.AlertaImovelGoogleMaps;
import com.stager.casamaisimoveis.api.DeleteHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.api.GetHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.interfaces.MapaRotaInterface;
import com.stager.casamaisimoveis.models.EnderecoRota;
import com.stager.casamaisimoveis.models.Imovel;
import com.stager.casamaisimoveis.models.Rota;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapaRotaFragment extends Fragment implements OnMapReadyCallback, HttpResponseInterface, MapaRotaInterface {

    private GoogleMap googleMapsFragment;
    private TextView edtBairroRota;
    private TextView txtImoveisNovos;
    private TextView txtImoveisRetorno;
    private TextView txtImoveisFinalizados;
    private Button btnNovoEndereco;

    private HttpResponseInterface httpResponseInterface;
    private String API_ENDERECOS_ROTAS = "api/enderecosRota/";
    private String API_ENDERECO_ROTA = "api/enderecoRota/";
    private String API_IMOVEIS_BAIRRO = "api/buscarImovelPorBairro/";
    private final int FASE_OBRA_PRONTA = 6;
    private List<EnderecoRota> enderecosRota;

    private MapaRotaInterface mapaRotaInterface;
    private HashMap<String, EnderecoRota> marcasEnderecoRota;
    private HashMap<String, Imovel> marcasImoveis;

    private ProgressDialog progress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa_rota, container, false);

        edtBairroRota = (TextView) view.findViewById(R.id.edtBairroRota);
        txtImoveisNovos = (TextView) view.findViewById(R.id.txtImoveisNovos);
        txtImoveisRetorno = (TextView) view.findViewById(R.id.txtImoveisRetorno);
        txtImoveisFinalizados = (TextView) view.findViewById(R.id.txtImoveisFinalizados);
        btnNovoEndereco = (Button) view.findViewById(R.id.btnNovoEndereco);

        httpResponseInterface = this;
        mapaRotaInterface = this;

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMapsFragment);
        mapFragment.getMapAsync(this);

        enderecosRota = new ArrayList<>();
        marcasEnderecoRota = new HashMap<>();
        marcasImoveis = new HashMap<>();

        eventosBotoes();
        iniciarLoading();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Mapa Bairro");

        if(VariaveisEstaticas.getRotaSelecionada() != null){
            edtBairroRota.setText("Bairro: " + VariaveisEstaticas.getRotaSelecionada().getBairro());
        }
    }

    private void carregarEnderecos(){
        GetHttpComHeaderAsyncTask getHttpComHeaderAsyncTask = new GetHttpComHeaderAsyncTask(getContext(),
                httpResponseInterface,
                API_ENDERECOS_ROTAS);
        getHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_ENDERECOS_ROTAS + VariaveisEstaticas.getRotaSelecionada().getId());
    }

    private void buscarImoveisJahCadastrados(){
        GetHttpComHeaderAsyncTask getHttpComHeaderAsyncTask = new GetHttpComHeaderAsyncTask(getContext(),
                httpResponseInterface,
                API_IMOVEIS_BAIRRO);
        getHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_IMOVEIS_BAIRRO + VariaveisEstaticas.getRotaSelecionada().getBairro());
    }

    private void eventosBotoes() {
        btnNovoEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().alterarFragment("CadastrarEnderecoRota");
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

        Rota rota = VariaveisEstaticas.getRotaSelecionada();
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocationName(rota.getEndereco(), 1);
            if(addresses.size() > 0) {
                LatLng latlng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        latlng, 14);
                googleMapsFragment.animateCamera(cameraUpdate);
                googleMapsFragment.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        EnderecoRota enderecoRota = marcasEnderecoRota.get(marker.getTitle());
                        if(enderecoRota != null)
                            AlertaGoogleMaps.alertaGoogleMaps(getContext(), enderecoRota, mapaRotaInterface);
                        else{
                            Imovel imovelSelecionado = marcasImoveis.get(marker.getTitle());
                            if(imovelSelecionado != null)
                                AlertaImovelGoogleMaps.alertaGoogleMaps(getContext(), imovelSelecionado, mapaRotaInterface);
                        }

                        return false;
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        finalizarLoading();
        carregarEnderecos();
    }

    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {
        try {
            if(jsonObject.has("erro")){
                Toast.makeText(getContext(), jsonObject.getString("erro"), Toast.LENGTH_SHORT).show();
                return;
            }

            if(rotaApi.equals(API_ENDERECOS_ROTAS))
                retornoEnderecosRota(jsonObject);
            if(rotaApi.equals(API_ENDERECO_ROTA))
                retornoEnderecoRota(jsonObject);
            if(rotaApi.equals(API_IMOVEIS_BAIRRO))
                retornoImoveisJaCadastrados(jsonObject);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void retornoImagemBitmap(Bitmap imagem, String rotaAPI) {

    }

    private void retornoEnderecosRota(JSONObject resposta){

        enderecosRota = EnderecoRota.listarEnderecosRotas(resposta);
        Rota rota = VariaveisEstaticas.getRotaSelecionada();
        int i = 1;
        iniciarLoading();
        int contadorEnderecoNovos = 0;
        for(EnderecoRota enderecoRota: enderecosRota){
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> addresses;
            try {
                addresses = geocoder.getFromLocationName(enderecoRota.getEndereco(), 1);
                if(addresses.size() > 0) {
                    LatLng latlng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latlng);
                    markerOptions.title(rota.getNome() + ": " + i);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    marcasEnderecoRota.put(markerOptions.getTitle(), enderecoRota);
                    googleMapsFragment.addMarker(markerOptions);
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            contadorEnderecoNovos++;
        }
        txtImoveisNovos.setText("Pendente: " + contadorEnderecoNovos);
        finalizarLoading();
        buscarImoveisJahCadastrados();
    }

    private void retornoEnderecoRota(JSONObject resposta){

        if(resposta.has("sucesso")){
            try {
                Toast.makeText(getContext(), resposta.getString("mensagem"), Toast.LENGTH_SHORT).show();
                if(resposta.getBoolean("sucesso")){
                    marcasEnderecoRota = new HashMap<>();
                    googleMapsFragment.clear();
                    carregarEnderecos();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void retornoImoveisJaCadastrados(JSONObject resposta){
        List<Imovel> imoveis = Imovel.gerarListaImoveisBuscaImovel(resposta);
        iniciarLoading();
        int contadorImoveisRetorno = 0;
        int contadorImoveisFinalizados = 0;
        for(Imovel imovel: imoveis){
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> addresses;
            try {
                addresses = geocoder.getFromLocationName(imovel.getEnderecoImovel().getEnderecoEscrito(), 1);
                if(addresses.size() > 0) {
                    LatLng latlng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latlng);
                    markerOptions.title(imovel.getEnderecoImovel().getEnderecoEscrito());
                    if(imovel.getSituacao_anuncio() == 0){
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    }
                    else if(imovel.getSituacao_anuncio() == 1) {
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                        contadorImoveisRetorno++;
                    }
                    else {
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        contadorImoveisFinalizados++;
                    }
                    marcasImoveis.put(markerOptions.getTitle(), imovel);
                    googleMapsFragment.addMarker(markerOptions);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        txtImoveisRetorno.setText("Atualizar: " + contadorImoveisRetorno);
        txtImoveisFinalizados.setText("OK: " + contadorImoveisFinalizados);
        finalizarLoading();
    }

    @Override
    public void gerarRota(EnderecoRota enderecoRota) {
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName(enderecoRota.getEndereco(), 1);

            Uri gmmIntentUri = Uri.parse("google.streetview:cbll="
                    + addresses.get(0).getLatitude()
                    + ","
                    + addresses.get(0).getLongitude());

            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            startActivity(mapIntent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void criarImovel(EnderecoRota enderecoRota) {
        VariaveisEstaticas.setEnderecoRotaSelecionado(enderecoRota);
        VariaveisEstaticas.getFragmentInterface().alterarFragment("CadastrarDadosProprietario");
    }

    @Override
    public void excluirEndereco(final EnderecoRota enderecoRota) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Você deseja excluir este endereço ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Sim",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeleteHttpComHeaderAsyncTask deleteHttpComHeaderAsyncTask = new DeleteHttpComHeaderAsyncTask(getContext(),
                                httpResponseInterface,
                                API_ENDERECO_ROTA);
                        deleteHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_ENDERECO_ROTA + enderecoRota.getId());
                        dialog.dismiss();
                    }
                });

        builder1.setNegativeButton(
                "Não",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void visualizarImovel(Imovel imovel) {
        VariaveisEstaticas.setImovelBusca(imovel);
        VariaveisEstaticas.getFragmentInterface().alterarFragment("VisualizarDadosProprietario");
    }

    @Override
    public void editarImovel(Imovel imovel) {
        VariaveisEstaticas.setImovelBusca(imovel);
        VariaveisEstaticas.getFragmentInterface().alterarFragment("AlterarDadosProprietario");
    }

    private void iniciarLoading(){
        progress = new ProgressDialog(getContext());
        progress.setMessage("Aguarde...");
        progress.setCancelable(false);
        progress.show();
    }

    private void finalizarLoading(){
        progress.dismiss();
    }

}