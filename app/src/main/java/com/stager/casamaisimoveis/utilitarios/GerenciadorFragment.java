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
import com.stager.casamaisimoveis.fragments.HistoricoCaptadorFragment;
import com.stager.casamaisimoveis.fragments.MapaHistoricoFragment;
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
        }else if(nomeTela.equals("HistoricoCaptador")){
            HistoricoCaptadorFragment historicoCaptadorFragment = new HistoricoCaptadorFragment();
            ft.replace(R.id.contFragments, historicoCaptadorFragment);
        }else if(nomeTela.equals("MapaHistoricoCaptador")){
            MapaHistoricoFragment mapaHistoricoFragment = new MapaHistoricoFragment();
            ft.replace(R.id.contFragments, mapaHistoricoFragment);
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
            TelaInicialFragment telaInicialFragment = (TelaInicialFragment) fm.findFragmentByTag(nomeTela);
            ft.remove(telaInicialFragment);
        }else if(nomeTela.equals("Rota")){
            RotaFragment rotaFragment = (RotaFragment) fm.findFragmentByTag(nomeTela);
            ft.remove(rotaFragment);
        }else if(nomeTela.equals("CadastrarRota")){
            CadastrarRotaFragment cadastrarRotaFragment = (CadastrarRotaFragment) fm.findFragmentByTag(nomeTela);
            ft.remove(cadastrarRotaFragment);
        }else if(nomeTela.equals("MapaRota")){
            MapaRotaFragment mapaRotaFragment = (MapaRotaFragment) fm.findFragmentByTag(nomeTela);
            ft.remove(mapaRotaFragment);
        }else if(nomeTela.equals("CadastrarEnderecoRota")){
            CadastrarEnderecoRotaFragment cadastrarEnderecoRotaFragment = (CadastrarEnderecoRotaFragment) fm.findFragmentByTag(nomeTela);
            ft.remove(cadastrarEnderecoRotaFragment);
        }else if(nomeTela.equals("CadastrarDadosProprietario")){
            CadastrarDadosProprietarioFragment cadastrarDadosProprietarioFragment = (CadastrarDadosProprietarioFragment) fm.findFragmentByTag(nomeTela);
            ft.remove(cadastrarDadosProprietarioFragment);
        }else if(nomeTela.equals("CadastrarEnderecoImovel")){
            CadastrarEnderecoImovelFragment cadastrarEnderecoImovelFragment = (CadastrarEnderecoImovelFragment) fm.findFragmentByTag(nomeTela);
            ft.remove(cadastrarEnderecoImovelFragment);
        }else if(nomeTela.equals("CadastrarDadosAnuncio")){
            CadastrarDadosAnuncioFragment cadastrarDadosAnuncioFragment = (CadastrarDadosAnuncioFragment) fm.findFragmentByTag(nomeTela);
            ft.remove(cadastrarDadosAnuncioFragment);
        }else if(nomeTela.equals("CadastrarInformacoesImovel")){
            CadastrarInformacoesImovelFragment cadastrarInformacoesImovelFragment = (CadastrarInformacoesImovelFragment) fm.findFragmentByTag(nomeTela);
            ft.remove(cadastrarInformacoesImovelFragment);
        }else if(nomeTela.equals("CadastrarComposicaoImovel")){
            CadastrarComposicaoImovelFragment cadastrarComposicaoImovelFragment = (CadastrarComposicaoImovelFragment) fm.findFragmentByTag(nomeTela);
            ft.remove(cadastrarComposicaoImovelFragment);
        }else if(nomeTela.equals("CadastrarVisitaImovel")){
            CadastrarVisitaImovelFragment cadastrarVisitaImovelFragment = (CadastrarVisitaImovelFragment) fm.findFragmentByTag(nomeTela);
            ft.remove(cadastrarVisitaImovelFragment);
        }else if(nomeTela.equals("HistoricoCaptador")){
            HistoricoCaptadorFragment historicoCaptadorFragment = (HistoricoCaptadorFragment) fm.findFragmentByTag(nomeTela);
            ft.remove(historicoCaptadorFragment);
        }else if(nomeTela.equals("MapaHistoricoCaptador")){
            MapaHistoricoFragment mapaHistoricoFragment = (MapaHistoricoFragment) fm.findFragmentByTag(nomeTela);
            ft.remove(mapaHistoricoFragment);
        }
    }
}