package com.stager.casamaisimoveis.utilitarios;

import com.stager.casamaisimoveis.interfaces.FragmentInterface;
import com.stager.casamaisimoveis.interfaces.TelaInicialInterface;
import com.stager.casamaisimoveis.models.Autenticacao;
import com.stager.casamaisimoveis.models.Captador;
import com.stager.casamaisimoveis.models.Coordenador;

public class VariaveisEstaticas {

    private static Autenticacao autenticacao;
    private static Captador captador;
    private static Coordenador coordenador;

    private static TelaInicialInterface telaInicialInterface;
    private static FragmentInterface fragmentInterface;

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