package com.stager.casamaisimoveis.utilitarios;

import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.fragments.CadastrarComposicaoImovelFragment;
import com.stager.casamaisimoveis.fragments.CadastrarDadosAnuncioFragment;
import com.stager.casamaisimoveis.fragments.CadastrarDadosProprietarioFragment;
import com.stager.casamaisimoveis.fragments.CadastrarEnderecoRotaFragment;
import com.stager.casamaisimoveis.fragments.CadastrarInformacoesImovelFragment;
import com.stager.casamaisimoveis.fragments.CadastrarRotaFragment;
import com.stager.casamaisimoveis.fragments.CadastrarVisitaImovelFragment;
import com.stager.casamaisimoveis.fragments.CadastrarEnderecoImovelFragment;
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
        }else if(nomeTela.equals("CadastrarDadosProprietario")){
            CadastrarDadosProprietarioFragment cadastrarDadosProprietarioFragment = new CadastrarDadosProprietarioFragment();
            ft.replace(R.id.contFragments, cadastrarDadosProprietarioFragment, nomeTela);
        }else if(nomeTela.equals("CadastrarEnderecoImovel")){
            CadastrarEnderecoImovelFragment cadastrarEnderecoImovelFragment = new CadastrarEnderecoImovelFragment();
            ft.replace(R.id.contFragments, cadastrarEnderecoImovelFragment, nomeTela);
        }else if(nomeTela.equals("CadastrarDadosAnuncio")){
            CadastrarDadosAnuncioFragment cadastrarDadosAnuncioFragment = new CadastrarDadosAnuncioFragment();
            ft.replace(R.id.contFragments, cadastrarDadosAnuncioFragment, nomeTela);
        }else if(nomeTela.equals("CadastrarInformacoesImovel")){
            CadastrarInformacoesImovelFragment cadastrarInformacoesImovelFragment = new CadastrarInformacoesImovelFragment();
            ft.replace(R.id.contFragments, cadastrarInformacoesImovelFragment, nomeTela);
        }else if(nomeTela.equals("CadastrarComposicaoImovel")){
            CadastrarComposicaoImovelFragment cadastrarComposicaoImovelFragment = new CadastrarComposicaoImovelFragment();
            ft.replace(R.id.contFragments, cadastrarComposicaoImovelFragment, nomeTela);
        }else if(nomeTela.equals("CadastrarVisitaImovel")){
            CadastrarVisitaImovelFragment cadastrarVisitaImovelFragment = new CadastrarVisitaImovelFragment();
            ft.replace(R.id.contFragments, cadastrarVisitaImovelFragment, nomeTela);
        }

        if(fm.findFragmentByTag(nomeTela) != null)
            fm.popBackStack(nomeTela,1);

        ft.addToBackStack(nomeTela);
        ft.commit();
        animacao.animaSaida(llNavDraw);

        if(llBgCinza.getVisibility() == View.VISIBLE)
            animacao.fadeOutAnimation(llBgCinza);
    }

    public void removerFragmentParaVoltar(FragmentManager fm, String nomeTela){
        FragmentTransaction ft = fm.beginTransaction();

        if(nomeTela.equals("TelaInicial")){
            TelaInicialFragment telaInicialFragment = new TelaInicialFragment();
            ft.remove(telaInicialFragment);
        }else if(nomeTela.equals("Rota")){
            RotaFragment rotaFragment = new RotaFragment();
            ft.remove(rotaFragment);
        }else if(nomeTela.equals("CadastrarRota")){
            CadastrarRotaFragment cadastrarRotaFragment = new CadastrarRotaFragment();
            ft.remove(cadastrarRotaFragment);
        }else if(nomeTela.equals("MapaRota")){
            MapaRotaFragment mapaRotaFragment = new MapaRotaFragment();
            ft.remove(mapaRotaFragment);
        }else if(nomeTela.equals("CadastrarEnderecoRota")){
            CadastrarEnderecoRotaFragment cadastrarEnderecoRotaFragment = new CadastrarEnderecoRotaFragment();
            ft.remove(cadastrarEnderecoRotaFragment);
        }else if(nomeTela.equals("CadastrarDadosProprietario")){
            CadastrarDadosProprietarioFragment cadastrarDadosProprietarioFragment = new CadastrarDadosProprietarioFragment();
            ft.remove(cadastrarDadosProprietarioFragment);
        }else if(nomeTela.equals("CadastrarEnderecoImovel")){
            CadastrarEnderecoImovelFragment cadastrarEnderecoImovelFragment = new CadastrarEnderecoImovelFragment();
            ft.remove(cadastrarEnderecoImovelFragment);
        }else if(nomeTela.equals("CadastrarDadosAnuncio")){
            CadastrarDadosAnuncioFragment cadastrarDadosAnuncioFragment = new CadastrarDadosAnuncioFragment();
            ft.remove(cadastrarDadosAnuncioFragment);
        }else if(nomeTela.equals("CadastrarInformacoesImovel")){
            CadastrarInformacoesImovelFragment cadastrarInformacoesImovelFragment = new CadastrarInformacoesImovelFragment();
            ft.remove(cadastrarInformacoesImovelFragment);
        }else if(nomeTela.equals("CadastrarComposicaoImovel")){
            CadastrarComposicaoImovelFragment cadastrarComposicaoImovelFragment = new CadastrarComposicaoImovelFragment();
            ft.remove(cadastrarComposicaoImovelFragment);
        }else if(nomeTela.equals("CadastrarVisitaImovel")){
            CadastrarVisitaImovelFragment cadastrarVisitaImovelFragment = new CadastrarVisitaImovelFragment();
            ft.remove(cadastrarVisitaImovelFragment);
        }
    }
}