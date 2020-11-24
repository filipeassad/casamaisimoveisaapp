package com.stager.casamaisimoveis.utilitarios;

import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.fragments.BuscarImovelFragment;
import com.stager.casamaisimoveis.fragments.cadastrar.CadastrarComposicaoImovelFragment;
import com.stager.casamaisimoveis.fragments.cadastrar.CadastrarDadosAnuncioFragment;
import com.stager.casamaisimoveis.fragments.cadastrar.CadastrarDadosProprietarioFragment;
import com.stager.casamaisimoveis.fragments.rota.CadastrarEnderecoRotaFragment;
import com.stager.casamaisimoveis.fragments.cadastrar.CadastrarInformacoesImovelFragment;
import com.stager.casamaisimoveis.fragments.rota.CadastrarRotaFragment;
import com.stager.casamaisimoveis.fragments.cadastrar.CadastrarVisitaImovelFragment;
import com.stager.casamaisimoveis.fragments.cadastrar.CadastrarEnderecoImovelFragment;
import com.stager.casamaisimoveis.fragments.historico.HistoricoCaptadorFragment;
import com.stager.casamaisimoveis.fragments.historico.MapaHistoricoFragment;
import com.stager.casamaisimoveis.fragments.rota.MapaRotaFragment;
import com.stager.casamaisimoveis.fragments.rota.RotaFragment;
import com.stager.casamaisimoveis.fragments.TelaInicialFragment;
import com.stager.casamaisimoveis.fragments.visualizar.VisualizarDadosProprietarioFragment;
import com.stager.casamaisimoveis.fragments.visualizar.VisualizarEnderecoImovelFragment;

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
        }else if(nomeTela.equals("BuscarImovel")){
            BuscarImovelFragment buscarImovelFragment = new BuscarImovelFragment();
            ft.replace(R.id.contFragments, buscarImovelFragment);
        }else if(nomeTela.equals("VisualizarDadosProprietario")){
            VisualizarDadosProprietarioFragment visualizarDadosProprietarioFragment = new VisualizarDadosProprietarioFragment();
            ft.replace(R.id.contFragments, visualizarDadosProprietarioFragment);
        }else if(nomeTela.equals("VisualizarEnderecoImovel")){
            VisualizarEnderecoImovelFragment visualizarEnderecoImovelFragment = new VisualizarEnderecoImovelFragment();
            ft.replace(R.id.contFragments, visualizarEnderecoImovelFragment);
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
        fm.popBackStack(nomeTela,1);
    }
}