package com.stager.casamaisimoveis.utilitarios;

import com.stager.casamaisimoveis.interfaces.FragmentInterface;
import com.stager.casamaisimoveis.interfaces.TelaInicialInterface;
import com.stager.casamaisimoveis.models.Autenticacao;
import com.stager.casamaisimoveis.models.Captador;
import com.stager.casamaisimoveis.models.Composicao;
import com.stager.casamaisimoveis.models.Coordenador;
import com.stager.casamaisimoveis.models.DadosImovel;
import com.stager.casamaisimoveis.models.EnderecoImovel;
import com.stager.casamaisimoveis.models.Proprietario;
import com.stager.casamaisimoveis.models.Rota;

import java.util.List;

public class VariaveisEstaticas {

    private static Autenticacao autenticacao;
    private static Captador captador;
    private static Coordenador coordenador;
    private static Rota rotaSelecionada;
    private static Proprietario proprietarioCadastro;
    private static EnderecoImovel enderecoImovelCadastro;
    private static DadosImovel dadosImovelCadastro;
    private static List<Composicao> composicoesImovelCadastro;

    private static TelaInicialInterface telaInicialInterface;
    private static FragmentInterface fragmentInterface;

    public static List<Composicao> getComposicoesImovelCadastro() {
        return composicoesImovelCadastro;
    }

    public static void setComposicoesImovelCadastro(List<Composicao> composicoesImovelCadastro) {
        VariaveisEstaticas.composicoesImovelCadastro = composicoesImovelCadastro;
    }

    public static DadosImovel getDadosImovelCadastro() {
        return dadosImovelCadastro;
    }

    public static void setDadosImovelCadastro(DadosImovel dadosImovelCadastro) {
        VariaveisEstaticas.dadosImovelCadastro = dadosImovelCadastro;
    }

    public static EnderecoImovel getEnderecoImovelCadastro() {
        return enderecoImovelCadastro;
    }

    public static void setEnderecoImovelCadastro(EnderecoImovel enderecoImovelCadastro) {
        VariaveisEstaticas.enderecoImovelCadastro = enderecoImovelCadastro;
    }

    public static Proprietario getProprietarioCadastro() {
        return proprietarioCadastro;
    }

    public static void setProprietarioCadastro(Proprietario proprietarioCadastro) {
        VariaveisEstaticas.proprietarioCadastro = proprietarioCadastro;
    }

    public static Rota getRotaSelecionada() {
        return rotaSelecionada;
    }

    public static void setRotaSelecionada(Rota rotaSelecionada) {
        VariaveisEstaticas.rotaSelecionada = rotaSelecionada;
    }

    public static FragmentInterface getFragmentInterface() {
        return fragmentInterface;
    }

    public static void setFragmentInterface(FragmentInterface fragmentInterface) {
        VariaveisEstaticas.fragmentInterface = fragmentInterface;
    }

    public static TelaInicialInterface getTelaInicialInterface() {
        return telaInicialInterface;
    }

    public static void setTelaInicialInterface(TelaInicialInterface telaInicialInterface) {
        VariaveisEstaticas.telaInicialInterface = telaInicialInterface;
    }

    public static Captador getCaptador() {
        return captador;
    }

    public static void setCaptador(Captador captador) {
        VariaveisEstaticas.captador = captador;
    }

    public static Coordenador getCoordenador() {
        return coordenador;
    }

    public static void setCoordenador(Coordenador coordenador) {
        VariaveisEstaticas.coordenador = coordenador;
    }

    public static Autenticacao getAutenticacao() {
        return autenticacao;
    }

    public static void setAutenticacao(Autenticacao autenticacao) {
        VariaveisEstaticas.autenticacao = autenticacao;
    }
}