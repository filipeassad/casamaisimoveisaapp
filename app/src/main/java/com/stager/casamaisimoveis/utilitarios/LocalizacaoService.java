package com.stager.casamaisimoveis.utilitarios;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.stager.casamaisimoveis.api.PostHttpComHeaderServiceAsyncTask;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.models.RotaCaptador;

import org.json.JSONObject;

import java.util.Date;

public class LocalizacaoService extends Service {

    private static final String TAG = "BOOMBOOMTESTGPS";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;
    private static Integer captadorID = 0;
    LocationListener[] mLocationListeners;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        captadorID = intent.getExtras().getInt("captadorId");
        mLocationListeners = new LocationListener[]{
                new LocationListener(LocationManager.GPS_PROVIDER, this, captadorID),
                new LocationListener(LocationManager.NETWORK_PROVIDER, this, captadorID)
        };

        super.onStartCommand(intent, flags, startId);

        final Handler handler = new Handler();
        final int[] count = {0};

        pegarLocalizacao();

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        removerLocationListeners();
    }

    private void removerLocationListeners() {
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void pegarLocalizacao() {

        mLocationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);

        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.e(TAG, "GPS ta liberado.");
            Criteria criteria = new Criteria();
            String provider = mLocationManager.getBestProvider(criteria, false);

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            Log.e(TAG, "accesss_fine_location.");
            try {
                mLocationManager.requestLocationUpdates(
                        provider, LOCATION_INTERVAL, LOCATION_DISTANCE,
                        mLocationListeners[0]);
            } catch (java.lang.SecurityException ex) {
                Log.i(TAG, "fail to request location update, ignore", ex);
            } catch (IllegalArgumentException ex) {
                Log.d(TAG, "gps provider does not exist " + ex.getMessage());
            }
        }
    }

    private class LocationListener implements android.location.LocationListener, HttpResponseInterface {
        Location mLastLocation;
        private Context myContext;
        private HttpResponseInterface httpResponseInterface;
        private Integer captadorId;

        private String API_ROTA_CAPTADO = "api/rotaCaptador";

        public LocationListener(String provider, Context context, Integer captadorId) {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
            this.myContext = context;
            httpResponseInterface = this;
            this.captadorId = captadorId;
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
            if (FerramentasBasicas.isOnline(this.myContext)) {
                RotaCaptador rotaCaptador = new RotaCaptador(location.getLatitude() + "",
                        location.getLongitude() + "",
                        FerramentasBasicas.converterDataParaString(new Date(),
                                "dd/MM/yyyy"),
                        FerramentasBasicas.converterDataParaString(new Date(), "yyyy-MM-dd'T'HH:mm:ss'Z'"),
                        captadorId);
                PostHttpComHeaderServiceAsyncTask postHttpComHeaderAsyncTask = new PostHttpComHeaderServiceAsyncTask(myContext,
                        httpResponseInterface,
                        rotaCaptador.gerarRotaCaptadorJSON(),
                        API_ROTA_CAPTADO);

                postHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_ROTA_CAPTADO);
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }

        @Override
        public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {

        }

        @Override
        public void retornoImagemBitmap(Bitmap imagem, String rotaAPI) {

        }
    }
}