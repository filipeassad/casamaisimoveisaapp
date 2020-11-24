package com.stager.casamaisimoveis.utilitarios;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class FerramentasBasicas {

    public static String getURL(){
        return "http://192.168.0.27:3000/";
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

}
