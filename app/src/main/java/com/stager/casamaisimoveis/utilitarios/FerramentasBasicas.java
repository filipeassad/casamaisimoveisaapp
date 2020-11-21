package com.stager.casamaisimoveis.utilitarios;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

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

}
