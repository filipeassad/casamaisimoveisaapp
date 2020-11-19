package com.stager.casamaisimoveis.activitys;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.SupportMapFragment;
import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.adapters.MenuAdapter;
import com.stager.casamaisimoveis.api.GetHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.fragments.TelaInicialFragment;
import com.stager.casamaisimoveis.interfaces.FragmentInterface;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.models.Autenticacao;
import com.stager.casamaisimoveis.models.Captador;
import com.stager.casamaisimoveis.models.Coordenador;
import com.stager.casamaisimoveis.utilitarios.Animacao;
import com.stager.casamaisimoveis.utilitarios.FerramentasBasicas;
import com.stager.casamaisimoveis.utilitarios.GerenciadorFragment;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentPrincipal extends FragmentActivity implements FragmentInterface, HttpResponseInterface {

    static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 24;

    private LinearLayout llMenu;
    private TextView ttTituloFragment;
    private LinearLayout llBgCinza;
    private LinearLayout llNavDraw;
    private LinearLayout contFragments;

    private ImageView ivImagemUsuario;
    private TextView txtNomeUsuario;
    private TextView txtProfissao;
    private ListView lvMenus;

    private Bundle savedInstanceState;
    private FragmentManager fm = getSupportFragmentManager();
    private GerenciadorFragment gerenciadorFragment = new GerenciadorFragment();

    private Animacao animacao = new Animacao();
    private HttpResponseInterface httpResponseInterface;

    private final String API_CAPTADOR = "api/captador";
    private final String API_COORDENACAO = "api/coordenador";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.fragment_activity);

        llMenu = (LinearLayout) findViewById(R.id.llMenu);
        ttTituloFragment = (TextView) findViewById(R.id.ttTituloFragment);
        llBgCinza = (LinearLayout) findViewById(R.id.llBgCinza);
        llNavDraw = (LinearLayout) findViewById(R.id.llNavDraw);
        contFragments = (LinearLayout) findViewById(R.id.contFragments);

        httpResponseInterface = this;
        VariaveisEstaticas.setFragmentInterface(this);

        getNavMenu();
        inserirPrimeiroFragment();

        buscarDadosUsuario(VariaveisEstaticas.getAutenticacao());

        eventosBotoes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        verificarPermissaoLocalizacao();
    }

    private void buscarDadosUsuario(Autenticacao autenticacao){
        if(autenticacao.isCaptador()){
            GetHttpComHeaderAsyncTask getHttpComHeaderAsyncTask = new GetHttpComHeaderAsyncTask(this, httpResponseInterface, API_CAPTADOR);
            getHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_CAPTADOR + "/" + autenticacao.getUsuario_id());
        }else{
            GetHttpComHeaderAsyncTask getHttpComHeaderAsyncTask = new GetHttpComHeaderAsyncTask(this, httpResponseInterface, API_COORDENACAO);
            getHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_CAPTADOR + "/" + autenticacao.getUsuario_id());
        }
    }

    private void inserirPrimeiroFragment(){
        if(savedInstanceState == null){
            TelaInicialFragment telaInicialFragment = new TelaInicialFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.contFragments, telaInicialFragment, "TelaInicial");
            ft.commit();
        }
    }

    private void getNavMenu(){

        View nav_layout =  getLayoutInflater().inflate(R.layout.nav_menu, llNavDraw);

        ivImagemUsuario = (ImageView) nav_layout.findViewById(R.id.ivImagemUsuario);
        txtNomeUsuario = (TextView) nav_layout.findViewById(R.id.txtNomeUsuario);
        txtProfissao = (TextView) nav_layout.findViewById(R.id.txtProfissao);

        lvMenus = (ListView) nav_layout.findViewById(R.id.lvMenus);

        List<String> listString = new ArrayList<>();

        listString.add("Rota");
        listString.add("Histórico");
        listString.add("Imóveis");
        listString.add("Sair");

        MenuAdapter menuAdapter = new MenuAdapter(this, R.layout.adapter_menu, listString);
        lvMenus.setAdapter(menuAdapter);

        lvMenus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(((String) parent.getItemAtPosition(position)).equals("Rota")){
                    alterarFragment("Rota");
                }else if(((String) parent.getItemAtPosition(position)).equals("Histórico")){
                    alterarFragment("Histórico");
                }else if(((String) parent.getItemAtPosition(position)).equals("Imóveis")){
                    alterarFragment("Imóveis");
                }else if(((String) parent.getItemAtPosition(position)).equals("Sair")){
                    sair();
                }
            }
        });
    }

    private void eventosBotoes(){

        llMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llNavDraw.getVisibility() == View.VISIBLE){
                    animacao.animaSaida(llNavDraw);
                    animacao.fadeOutAnimation(llBgCinza);
                }else{
                    llNavDraw.setVisibility(View.VISIBLE);
                    llBgCinza.setVisibility(View.VISIBLE);
                    animacao.animaEntrada(llNavDraw);
                    animacao.fadeInAnimatio(llBgCinza);
                }
            }
        });

        llBgCinza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(llNavDraw.getVisibility() == View.VISIBLE){
                    animacao.animaSaida(llNavDraw);
                    animacao.fadeOutAnimation(llBgCinza);
                }else{
                    llNavDraw.setVisibility(View.VISIBLE);
                    llBgCinza.setVisibility(View.VISIBLE);
                    animacao.animaEntrada(llNavDraw);
                    animacao.fadeInAnimatio(llBgCinza);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(llNavDraw.getVisibility() == View.VISIBLE){
            animacao.animaSaida(llNavDraw);
            if(llBgCinza.getVisibility() == View.VISIBLE)
                animacao.fadeOutAnimation(llBgCinza);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void alterarFragment(String nomeFragment) {
        gerenciadorFragment.alterarFragment(fm, llNavDraw, nomeFragment, llBgCinza);
    }

    @Override
    public void alterarTitulo(String nometitulo) {
        ttTituloFragment.setText(nometitulo);
    }

    @Override
    public void voltar() {
        onBackPressed();
    }

    @Override
    public void sair() {
        finish();
    }

    @Override
    public void fecharTeclado() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        if(getCurrentFocus() != null){
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {
        try {
            if(jsonObject.has("erro")){
                Toast.makeText(this, jsonObject.getString("erro"), Toast.LENGTH_SHORT).show();
                return;
            }

            if(rotaApi.equals(API_CAPTADOR))
                retornoCaptador(jsonObject);
            else if(rotaApi.equals(API_COORDENACAO))
                retornoCoordenador(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void retornoCaptador(JSONObject resposta){
        Captador captadorLogado = new Captador();
        captadorLogado.setCaptador(resposta);

        if(captadorLogado.getId() != null){
            VariaveisEstaticas.setCaptador(captadorLogado);
            inserirDadosUsuario(captadorLogado.getNome(), "Captador");
            VariaveisEstaticas.getTelaInicialInterface().carregarDadosUsuario();
        }
    }

    private void retornoCoordenador(JSONObject resposta){
        Coordenador coordenadorLogado = new Coordenador();
        coordenadorLogado.setCoordenador(resposta);

        if(coordenadorLogado.getId() != null){
            VariaveisEstaticas.setCoordenador(coordenadorLogado);
            inserirDadosUsuario(coordenadorLogado.getNome(), "Coordenador");
            VariaveisEstaticas.getTelaInicialInterface().carregarDadosUsuario();
        }
    }

    private void inserirDadosUsuario(String nome, String profissao){
        txtNomeUsuario.setText(nome);
        txtProfissao.setText(profissao);
    }

    public void verificarPermissaoLocalizacao(){
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION )
                != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    this.finish();
                }
                return;
            }
        }
    }

}