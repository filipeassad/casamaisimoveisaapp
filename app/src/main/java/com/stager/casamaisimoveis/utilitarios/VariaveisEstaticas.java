package com.stager.casamaisimoveis.utilitarios;

import com.stager.casamaisimoveis.models.Autenticacao;
import com.stager.casamaisimoveis.models.Captador;
import com.stager.casamaisimoveis.models.Coordenador;

public class VariaveisEstaticas {

    public static Autenticacao autenticacao;
    public static Captador captador;
    public static Coordenador coordenador;

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