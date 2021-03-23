package com.stager.casamaisimoveis.banco;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.stager.casamaisimoveis.models.Autenticacao;

import java.util.ArrayList;
import java.util.List;

public class AutenticacaoManager {

    private SQLiteDatabase db;

    public AutenticacaoManager(SQLiteDatabase db) {
        this.db = db;
    }

    public boolean insertAutenticacao(Autenticacao autenticacao) {

        ContentValues args = new ContentValues();

        args.put("id", autenticacao.getId());
        args.put("token", autenticacao.getToken());
        args.put("usuario_id", autenticacao.getUsuario_id());
        args.put("captador", autenticacao.isCaptador() ? "true" : "false");
        args.put("ativo", autenticacao.isAtivo() ? "true" : "false");


        long resultado = db.insert("AUTENTICACAO", null, args);

        if (resultado > 0) {
            return true;
        }

        return false;
    }

    public boolean updateAutenticacao(Autenticacao autenticacao) {

        ContentValues args = new ContentValues();

        args.put("id", autenticacao.getId());
        args.put("token", autenticacao.getToken());
        args.put("usuario_id", autenticacao.getUsuario_id());
        args.put("captador", autenticacao.isCaptador() ? "true" : "false");
        args.put("ativo", autenticacao.isAtivo() ? "true" : "false");

        long resultado = db.update("AUTENTICACAO", args, "id=" + autenticacao.getId(), null);

        if (resultado > 0) {
            return true;
        }

        return false;
    }

    public List<Autenticacao> getAutenticacaoAtivo() {

        Cursor cursor = db.rawQuery("SELECT * FROM AUTENTICACAO WHERE ativo = 'true'", null);

        if (cursor != null) {

            List<Autenticacao> lista = new ArrayList<>();

            while (cursor.moveToNext()) {

                Autenticacao autenticacao = new Autenticacao();

                autenticacao.setId(cursor.getInt(0));
                autenticacao.setToken(cursor.getString(1));
                autenticacao.setUsuario_id(cursor.getInt(2));
                autenticacao.setCaptador(cursor.getString(3).equals("true"));
                autenticacao.setAtivo(cursor.getString(4).equals("true"));

                lista.add(autenticacao);

            }

            cursor.close();
            cursor = null;
            return lista;
        }

        return null;
    }

    public boolean deletaTudo() {

        long resultado = db.delete("AUTENTICACAO", "", new String[]{});

        if (resultado > 0) {
            return true;
        }

        return false;
    }

}
