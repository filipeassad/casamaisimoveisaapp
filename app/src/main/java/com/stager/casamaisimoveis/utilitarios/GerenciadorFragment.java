package com.stager.casamaisimoveis.utilitarios;

import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.fragments.CadastrarEnderecoRotaFragment;
import com.stager.casamaisimoveis.fragments.CadastrarRotaFragment;
import com.stager.casamaisimoveis.fragments.MapaRotaFragment;
import com.stager.casamaisimoveis.fragments.RotaFragment;
import com.stager.casamaisimoveis.fragments.TelaInicialFragment;

public class GerenciadorFragment {

    private Animacao animacao = new Animacao();

    public void alterarFragment(FragmentManager fm, LinearLayout llNavDraw, String nomeTela, LinearLayout llBgCinza){
        FragmentTransaction ft = fm.beginTransaction();

        if(nomeTela.equals("TelaInicial")){
            TelaInicialFragment telaInicialFragment = new TelaInicialFragment();
            ft.replace(R.id.contFragments, telaInicialFragment, nomeTela);
        }else if(nomeTela.equals("Rota")){
            RotaFragment rotaFragment = new RotaFragment();
            ft.replace(R.id.contFragments, rotaFragment, nomeTela);
        }else if(nomeTela.equals("CadastrarRota")){
            CadastrarRotaFragment cadastrarRotaFragment = new CadastrarRotaFragment();
            ft.replace(R.id.contFragments, cadastrarRotaFragment, nomeTela);
        }else if(nomeTela.equals("MapaRota")){
            MapaRotaFragment mapaRotaFragment = new MapaRotaFragment();
            ft.replace(R.id.contFragments, mapaRotaFragment, nomeTela);
        }else if(nomeTela.equals("CadastrarEnderecoRota")){
            CadastrarEnderecoRotaFragment cadastrarEnderecoRotaFragment = new CadastrarEnderecoRotaFragment();
            ft.replace(R.id.contFragments, cadastrarEnderecoRotaFragment, nomeTela);
        }

        if(fm.findFragmentByTag(nomeTela) != null){
            fm.popBackStack(nomeTela,1);
        }

        ft.addToBackStack(nomeTela);
        ft.commit();
        animacao.animaSaida(llNavDraw);

        if(llBgCinza.getVisibility() == View.VISIBLE)
            animacao.fadeOutAnimation(llBgCinza);

    }
}
