package com.stager.casamaisimoveis.utilitarios;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class FerramentasBasicas {

    public static String getURL(){
        //return "http://192.168.0.27:3200/";
        //return "http://165.227.80.240:3200/";
        return "http://104.236.30.166:3200/";
    }

    public static boolean isOnline(Context context){
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String converterDataParaString(Date data, String formato){
        String dataConvertida = "";

        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
            dataConvertida = simpleDateFormat.format(data);
        }catch (Exception e){

        }

        return dataConvertida;
    }

    public static Date converterStringParaData(String data, String formato){
        try{
            SimpleDateFormat format = new SimpleDateFormat(formato);
            return format.parse(data);
        }catch (Exception e){

        }
        return new Date();
    }

    public static String trocarFormatoDataString(String data, String formatoantigo, String formatoNovo){
        try{
            SimpleDateFormat format = new SimpleDateFormat(formatoantigo);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date dataGerada = format.parse(data);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatoNovo);
            return simpleDateFormat.format(dataGerada);
        }catch (Exception e){

        }
        return "";
    }

    public static Bitmap decodificarImagem(Uri selectedImage, ContentResolver contentResolver){
        Bitmap resultBitmap = null;
        try{
            InputStream in = null;
            final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
            in = contentResolver.openInputStream(selectedImage);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);

            int scale = 1;
            while ((options.outWidth * options.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }



            in = contentResolver.openInputStream(selectedImage);
            if (scale > 1) {
                scale--;
                options = new BitmapFactory.Options();
                options.inSampleSize = scale;
                resultBitmap = BitmapFactory.decodeStream(in, null, options);

                int height = resultBitmap.getHeight();
                int width = resultBitmap.getWidth();

                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(resultBitmap, (int) x,
                        (int) y, true);
                resultBitmap.recycle();
                resultBitmap = scaledBitmap;

                System.gc();
            } else {
                resultBitmap = BitmapFactory.decodeStream(in);
            }

            in.close();
        }catch (Exception ex){
            Log.e("PickerImage",ex.getMessage());
        }
        return resultBitmap;
    }

}
