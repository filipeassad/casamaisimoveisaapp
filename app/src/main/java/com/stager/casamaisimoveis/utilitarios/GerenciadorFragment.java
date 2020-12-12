package com.stager.casamaisimoveis.utilitarios;

import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.fragments.BuscarImovelFragment;
import com.stager.casamaisimoveis.fragments.TelaInicialCoordenadorFragment;
import com.stager.casamaisimoveis.fragments.alterar.AlterarComposicaoImovelFragment;
import com.stager.casamaisimoveis.fragments.alterar.AlterarDadosAnuncioFragment;
import com.stager.casamaisimoveis.fragments.alterar.AlterarDadosProprietarioFragment;
import com.stager.casamaisimoveis.fragments.alterar.AlterarEnderecoImovelFragment;
import com.stager.casamaisimoveis.fragments.alterar.AlterarImagensImovelFragment;
import com.stager.casamaisimoveis.fragments.alterar.AlterarInformacoesImovelFragment;
import com.stager.casamaisimoveis.fragments.alterar.AlterarVisitaImovelFragment;
import com.stager.casamaisimoveis.fragments.cadastrar.CadastrarComposicaoImovelFragment;
import com.stager.casamaisimoveis.fragments.cadastrar.CadastrarDadosAnuncioFragment;
import com.stager.casamaisimoveis.fragments.cadastrar.CadastrarDadosProprietarioFragment;
import com.stager.casamaisimoveis.fragments.cadastrar.CadastrarImagensImovelFragment;
import com.stager.casamaisimoveis.fragments.historico.ListaCaptadoresHistoricoFragment;
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
import com.stager.casamaisimoveis.fragments.visualizar.VisualizarComposicaoImovelFragment;
import com.stager.casamaisimoveis.fragments.visualizar.VisualizarDadosAnuncioFragment;
import com.stager.casamaisimoveis.fragments.visualizar.VisualizarDadosProprietarioFragment;
import com.stager.casamaisimoveis.fragments.visualizar.VisualizarEnderecoImovelFragment;
import com.stager.casamaisimoveis.fragments.visualizar.VisualizarImagensImovelFragment;
import com.stager.casamaisimoveis.fragments.visualizar.VisualizarInformacoesImovelFragment;
import com.stager.casamaisimoveis.fragments.visualizar.VisualizarVisitaImovelFragment;

public class GerenciadorFragment {

    private Animacao animacao = new Animacao();

    public void alterarFragment(FragmentManager fm, LinearLayout llNavDraw, String nomeTela, LinearLayout llBgCinza){
        FragmentTransaction ft = fm.beginTransaction();

        if(nomeTela.equals("TelaInicial")){
            TelaInicialFragment telaInicialFragment = new TelaInicialFragment();
            ft.replace(R.id.contFragments, telaInicialFragment, nomeTela);
        }else if(nomeTela.equals("TelaInicialCoordenador")){
            TelaInicialCoordenadorFragment telaInicialCoordenadorFragment = new TelaInicialCoordenadorFragment();
            ft.replace(R.id.contFragments, telaInicialCoordenadorFragment, nomeTela);
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
        }else if(nomeTela.equals("VisualizarDadosAnuncio")){
            VisualizarDadosAnuncioFragment visualizarDadosAnuncioFragment = new VisualizarDadosAnuncioFragment();
            ft.replace(R.id.contFragments, visualizarDadosAnuncioFragment);
        }else if(nomeTela.equals("VisualizarInformacoesImovel")){
            VisualizarInformacoesImovelFragment visualizarInformacoesImovelFragment = new VisualizarInformacoesImovelFragment();
            ft.replace(R.id.contFragments, visualizarInformacoesImovelFragment);
        }else if(nomeTela.equals("VisualizarComposicaoImovel")){
            VisualizarComposicaoImovelFragment visualizarComposicaoImovelFragment = new VisualizarComposicaoImovelFragment();
            ft.replace(R.id.contFragments, visualizarComposicaoImovelFragment);
        }else if(nomeTela.equals("VisualizarVisitaImovel")){
            VisualizarVisitaImovelFragment visualizarVisitaImovelFragment = new VisualizarVisitaImovelFragment();
            ft.replace(R.id.contFragments, visualizarVisitaImovelFragment);
        }else if(nomeTela.equals("AlterarDadosProprietario")){
            AlterarDadosProprietarioFragment alterarDadosProprietarioFragment = new AlterarDadosProprietarioFragment();
            ft.replace(R.id.contFragments, alterarDadosProprietarioFragment);
        }else if(nomeTela.equals("AlterarEnderecoImovel")){
            AlterarEnderecoImovelFragment alterarEnderecoImovelFragment = new AlterarEnderecoImovelFragment();
            ft.replace(R.id.contFragments, alterarEnderecoImovelFragment);
        }else if(nomeTela.equals("AlterarDadosAnuncio")){
            AlterarDadosAnuncioFragment alterarDadosAnuncioFragment = new AlterarDadosAnuncioFragment();
            ft.replace(R.id.contFragments, alterarDadosAnuncioFragment);
        }else if(nomeTela.equals("AlterarInformacoesImovel")){
            AlterarInformacoesImovelFragment alterarInformacoesImovelFragment = new AlterarInformacoesImovelFragment();
            ft.replace(R.id.contFragments, alterarInformacoesImovelFragment);
        }else if(nomeTela.equals("AlterarInformacoesImovel")){
            AlterarInformacoesImovelFragment alterarInformacoesImovelFragment = new AlterarInformacoesImovelFragment();
            ft.replace(R.id.contFragments, alterarInformacoesImovelFragment);
        }else if(nomeTela.equals("AlterarComposicaoImovel")){
            AlterarComposicaoImovelFragment alterarComposicaoImovelFragment = new AlterarComposicaoImovelFragment();
            ft.replace(R.id.contFragments, alterarComposicaoImovelFragment);
        }else if(nomeTela.equals("AlterarVisitaImovel")){
            AlterarVisitaImovelFragment alterarVisitaImovelFragment = new AlterarVisitaImovelFragment();
            ft.replace(R.id.contFragments, alterarVisitaImovelFragment);
        }else if(nomeTela.equals("CadastrarImagensImovel")){
            CadastrarImagensImovelFragment cadastrarImagensImovelFragment = new CadastrarImagensImovelFragment();
            ft.replace(R.id.contFragments, cadastrarImagensImovelFragment);
        }else if(nomeTela.equals("VisualizarImagensImovel")){
            VisualizarImagensImovelFragment visualizarImagensImovelFragment = new VisualizarImagensImovelFragment();
            ft.replace(R.id.contFragments, visualizarImagensImovelFragment);
        }else if(nomeTela.equals("AlterarImagensImovel")){
            AlterarImagensImovelFragment alterarImagensImovelFragment = new AlterarImagensImovelFragment();
            ft.replace(R.id.contFragments, alterarImagensImovelFragment);
        }else if(nomeTela.equals("ListaCaptadoresHistorico")){
            ListaCaptadoresHistoricoFragment listaCaptadoresHistoricoFragment = new ListaCaptadoresHistoricoFragment();
            ft.replace(R.id.contFragments, listaCaptadoresHistoricoFragment);
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