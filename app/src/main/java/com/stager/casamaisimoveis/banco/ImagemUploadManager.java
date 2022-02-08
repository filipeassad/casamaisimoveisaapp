package com.stager.casamaisimoveis.banco;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.stager.casamaisimoveis.models.ImagemUpload;
import com.stager.casamaisimoveis.models.RotaCaptador;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ImagemUploadManager {
    private SQLiteDatabase db;

    public ImagemUploadManager(SQLiteDatabase db) {
        this.db = db;
    }

    public boolean insertImagemUpload(ImagemUpload imagemUpload) {

        ContentValues args = new ContentValues();

        args.put("imovelId", imagemUpload.getImovelId());
        args.put("imagem", getBitmapAsByteArray(imagemUpload.getImagem()));

        long resultado = db.insert("IMAGEMUPLOAD", null, args);

        if (resultado > 0) {
            return true;
        }

        return false;
    }

    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public ImagemUpload getPrimeiraImagemUpload(){
        Cursor cursor = db.rawQuery("SELECT * FROM IMAGEMUPLOAD LIMIT 1", null);

        if(cursor != null){
            ImagemUpload imagemUpload = null;
            while (cursor.moveToNext()) {

                imagemUpload = new ImagemUpload();

                imagemUpload.setId(cursor.getInt(0));
                imagemUpload.setImovelId(cursor.getInt(1));
                byte[] imgByte = cursor.getBlob(2);
                imagemUpload.setImagem(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
            }
            cursor.close();
            cursor = null;

            return imagemUpload;
        }

        return null;
    }

    public List<ImagemUpload> getImagensUpload() {

        Cursor cursor = db.rawQuery("SELECT * FROM IMAGEMUPLOAD", null);

        if (cursor != null) {

            List<ImagemUpload> lista = new ArrayList<>();

            while (cursor.moveToNext()) {

                ImagemUpload imagemUpload = new ImagemUpload();

                imagemUpload.setId(cursor.getInt(0));
                imagemUpload.setImovelId(cursor.getInt(1));
                byte[] imgByte = cursor.getBlob(2);
                imagemUpload.setImagem(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));

                lista.add(imagemUpload);

            }

            cursor.close();
            cursor = null;
            return lista;
        }

        return null;
    }

    public boolean deletarImagemUpload(ImagemUpload imagemUpload) {

        long resultado = db.delete("IMAGEMUPLOAD", "id=" + imagemUpload.getId(), null);

        if (resultado > 0) {
            return true;
        }

        return false;
    }

    public void closeDatabase(){
        db.close();
    }

}