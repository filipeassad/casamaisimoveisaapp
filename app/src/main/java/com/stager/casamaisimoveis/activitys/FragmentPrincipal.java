package com.stager.casamaisimoveis.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.adapters.MenuAdapter;
import com.stager.casamaisimoveis.fragments.TelaInicialFragment;
import com.stager.casamaisimoveis.interfaces.FragmentInterface;
import com.stager.casamaisimoveis.utilitarios.Animacao;
import com.stager.casamaisimoveis.utilitarios.GerenciadorFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentPrincipal extends FragmentActivity implements FragmentInterface {

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

    Animacao animacao = new Animacao();

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

        getNavMenu();
        inserirPrimeiroFragment();

        eventosBotoes();
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

        /*if(VariaveisEstaticas.getUsuario() != null){
            txtNomeUsuario.setText(VariaveisEstaticas.getUsuario().getNome() != null ? VariaveisEstaticas.getUsuario().getNome() : "Sem Nome");
            txtEmailUsuario.setText(VariaveisEstaticas.getUsuario().getEmail() != null ? VariaveisEstaticas.getUsuario().getEmail() : "Sem e-mail");
        }*/

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
}