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

    public String tabelaRotaCaptador() {

        String rotaCaptadorTable = "CREATE TABLE ROTACAPTADOR (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "latitude TEXT, " +
                "longitude TEXT, " +
                "data_rota TEXT," +
                "data_hora_rota TEXT," +
                "captador_id BIGINT)";

        return rotaCaptadorTable;
    }

    public String tabelaImagemUpload() {

        String imagemUploadTable = "CREATE TABLE IMAGEMUPLOAD( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "imovelId INTEGER, " +
                "imagem BLOB);";

        return imagemUploadTable;
    }

}