package com.stager.casamaisimoveis.utilitarios;

public class TabelasBanco {

    public String tabelaAutenticacao(){

        String autenticacaoTable = "CREATE TABLE AUTENTICACAO (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "token TEXT, " +
                "usuario_id TEXT, " +
                "captador TEXT," +
                "ativo TEXT)";

        return autenticacaoTable;
    }

}