package com.stager.casamaisimoveis.banco;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.stager.casamaisimoveis.models.Autenticacao;
import com.stager.casamaisimoveis.models.RotaCaptador;

import java.util.ArrayList;
import java.util.List;

public class RotaCaptadorManager {

    private SQLiteDatabase db;

    public RotaCaptadorManager(SQLiteDatabase db) {
        this.db = db;
    }

    public boolean insertRotaCaptador(RotaCaptador rotaCaptador) {

        ContentValues args = new ContentValues();

        args.put("latitude", rotaCaptador.getLatitude());
        args.put("longitude", rotaCaptador.getLongitude());
        args.put("data_rota", rotaCaptador.getData_rota());
        args.put("data_hora_rota", rotaCaptador.getData_hora_rota());
        args.put("captador_id", rotaCaptador.getCaptador_id());

        long resultado = db.insert("ROTACAPTADOR", null, args);

        if (resultado > 0) {
            return true;
        }

        return false;
    }

    public List<RotaCaptador> getRotasCaptador() {

        Cursor cursor = db.rawQuery("SELECT * FROM ROTACAPTADOR", null);

        if (cursor != null) {

            List<RotaCaptador> lista = new ArrayList<>();

            while (cursor.moveToNext()) {

                RotaCaptador rotaCaptador = new RotaCaptador();

                rotaCaptador.setId(cursor.getInt(0));
                rotaCaptador.setLatitude(cursor.getString(1));
                rotaCaptador.setLongitude(cursor.getString(2));
                rotaCaptador.setData_rota(cursor.getString(3));
                rotaCaptador.setData_hora_rota(cursor.getString(4));
                rotaCaptador.setCaptador_id(cursor.getInt(4));

                lista.add(rotaCaptador);

            }

            cursor.close();
            cursor = null;
            return lista;
        }

        return null;
    }

    public boolean deletaTudo() {

        long resultado = db.delete("ROTACAPTADOR", "", new String[]{});

        if (resultado > 0) {
            return true;
        }

        return false;
    }

}