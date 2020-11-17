package com.stager.casamaisimoveis.utilitarios;

import com.stager.casamaisimoveis.models.Autenticacao;

public class VariaveisEstaticas {

    public static Autenticacao autenticacao;

    public static Autenticacao getAutenticacao() {
        return autenticacao;
    }

    public static void setAutenticacao(Autenticacao autenticacao) {
        VariaveisEstaticas.autenticacao = autenticacao;
    }
}