package com.stager.casamaisimoveis.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.stager.casamaisimoveis.utilitarios.TabelasBanco;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "casamaisimoveis";

    public DatabaseManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        TabelasBanco tabelasBanco = new TabelasBanco();

        sqLiteDatabase.execSQL(tabelasBanco.tabelaAutenticacao());
        sqLiteDatabase.execSQL(tabelasBanco.tabelaRotaCaptador());
        sqLiteDatabase.execSQL(tabelasBanco.tabelaImagemUpload());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        TabelasBanco tabelasBanco = new TabelasBanco();

        if(oldVersion < 2){
            sqLiteDatabase.execSQL(tabelasBanco.tabelaRotaCaptador());
            sqLiteDatabase.execSQL(tabelasBanco.tabelaImagemUpload());
        }else if(oldVersion < 3) {
            sqLiteDatabase.execSQL(tabelasBanco.tabelaImagemUpload());
        }
    }
}
