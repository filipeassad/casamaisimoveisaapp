package com.stager.casamaisimoveis.utilitarios;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.activitys.MainActivity;
import com.stager.casamaisimoveis.api.PostHttpComHeaderServiceAsyncTask;
import com.stager.casamaisimoveis.banco.DatabaseManager;
import com.stager.casamaisimoveis.banco.RotaCaptadorManager;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.models.RotaCaptador;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LocalizacaoService extends Service {

    public static final String BROADCAST_ACTION = "Hello World";
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    public LocationManager locationManager;
    public MyLocationListener listener;
    public Location previousBestLocation = null;

    private PowerManager.WakeLock wakeLock = null;
    private boolean isServiceStarted = false;

    private static Integer captadorID = 0;

    Intent intent;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new MyLocationListener();

        captadorID = intent.getExtras().getInt("captadorId");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return START_NOT_STICKY;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 2, (LocationListener) listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 2, listener);

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyApp::MyWakelockTag");
        wakeLock.acquire();

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);
        Notification notification = criarNotificacao();
        startForeground(101, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("STOP_SERVICE", "SERVIDOR DE LOCALIZAÇÃO PAROU");
        if(locationManager != null && listener != null)
            locationManager.removeUpdates(listener);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    @Override
    public ComponentName startForegroundService(Intent service) {
        return super.startForegroundService(service);
    }

    public class MyLocationListener implements LocationListener, HttpResponseInterface {

        private String API_ROTA_CAPTADO = "api/inserirListaRotasCaptador";
        private HttpResponseInterface httpResponseInterface = this;
        private DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        private RotaCaptadorManager rotaCaptadorManager = new RotaCaptadorManager(databaseManager.getWritableDatabase());

        public void onLocationChanged(final Location loc)
        {
            Log.e("*****", "Location changed");
            if(isBetterLocation(loc, previousBestLocation)) {

                Calendar calendarHorarioManha = Calendar.getInstance();
                calendarHorarioManha.set(Calendar.HOUR_OF_DAY, 7);
                calendarHorarioManha.set(Calendar.MINUTE, 30);
                calendarHorarioManha.set(Calendar.SECOND, 0);
                calendarHorarioManha.set(Calendar.MILLISECOND, 0);

                Date periodoManha = calendarHorarioManha.getTime();

                Calendar calendarHorarioTarde = Calendar.getInstance();
                calendarHorarioTarde.set(Calendar.HOUR_OF_DAY, 17);
                calendarHorarioTarde.set(Calendar.MINUTE, 30);
                calendarHorarioTarde.set(Calendar.SECOND, 0);
                calendarHorarioTarde.set(Calendar.MILLISECOND, 0);

                Date periodoTarde = calendarHorarioTarde.getTime();

                Date dataHoje = new Date();

                Log.e("localização ------------------->", loc.getLatitude() + ":" + loc.getLongitude());
                intent.putExtra("Latitude", loc.getLatitude());
                intent.putExtra("Longitude", loc.getLongitude());
                intent.putExtra("Provider", loc.getProvider());

                if(!periodoManha.after(dataHoje) && !periodoTarde.before(dataHoje)) {
                    RotaCaptador rotaCaptador = new RotaCaptador(loc.getLatitude() + "",
                            loc.getLongitude() + "",
                            FerramentasBasicas.converterDataParaString(new Date(),
                                    "dd/MM/yyyy"),
                            FerramentasBasicas.converterDataParaString(new Date(), "yyyy-MM-dd'T'HH:mm:ss'Z'"),
                            captadorID);

                    rotaCaptadorManager.insertRotaCaptador(rotaCaptador);

                    if (FerramentasBasicas.isOnline(getApplicationContext())) {
                        Log.e("VariaveisEstaticas.getContadorEnvioRotaCaptador()", VariaveisEstaticas.getContadorEnvioRotaCaptador() + "");
                        if(VariaveisEstaticas.getContadorEnvioRotaCaptador() == 100){
                            List<RotaCaptador> rotasCaptador = rotaCaptadorManager.getRotasCaptador();
                            PostHttpComHeaderServiceAsyncTask postHttpComHeaderAsyncTask = new PostHttpComHeaderServiceAsyncTask(getApplicationContext(),
                                    httpResponseInterface,
                                    RotaCaptador.gerarListaRotaCaptadorJSON(rotasCaptador),
                                    API_ROTA_CAPTADO);

                            postHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_ROTA_CAPTADO);
                            rotaCaptadorManager.deletaTudo();
                            VariaveisEstaticas.zerarContadorEnvioRotaCaptador();
                        }else
                            VariaveisEstaticas.setContadorEnvioRotaCaptador();
                    }
                }
                sendBroadcast(intent);
            }
        }

        /*
             PostHttpComHeaderServiceAsyncTask postHttpComHeaderAsyncTask = new PostHttpComHeaderServiceAsyncTask(getApplicationContext(),
                                httpResponseInterface,
                                rotaCaptador.gerarRotaCaptadorJSON(),
                                API_ROTA_CAPTADO);

                        postHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_ROTA_CAPTADO);
         */

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        public void onProviderDisabled(String provider)
        {
            //Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
        }


        public void onProviderEnabled(String provider)
        {
            //Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {

        }

        @Override
        public void retornoImagemBitmap(Bitmap imagem, String rotaAPI) {

        }
    }

    private Notification criarNotificacao(){
        String canalNotificacaoId = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            canalNotificacaoId = createNotificationChannel("localizacao_service", "Localização Service");
        else
            canalNotificacaoId = "";


        //Intent intent = new Intent(this, MainActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        /*return new NotificationCompat.Builder(this, canalNotificacaoId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();*/

        return new NotificationCompat.Builder(this, canalNotificacaoId).setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(String channelId, String channelName){
        NotificationChannel chan = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        service.createNotificationChannel(chan);

        return channelId;
    }

    /*private class LocationListener implements android.location.LocationListener, HttpResponseInterface {
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
    }*/
}