package com.stager.casamaisimoveis.utilitarios;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.stager.casamaisimoveis.activitys.FragmentPrincipal;
import com.stager.casamaisimoveis.api.PostHttpComHeaderAsyncTask;
import com.stager.casamaisimoveis.api.PostHttpComHeaderServicesAsyncTask;
import com.stager.casamaisimoveis.banco.DatabaseManager;
import com.stager.casamaisimoveis.banco.ImagemUploadManager;
import com.stager.casamaisimoveis.interfaces.HttpResponseInterface;
import com.stager.casamaisimoveis.models.ImagemUpload;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UploadImagemService extends Service implements HttpResponseInterface {

    private HttpResponseInterface httpResponseInterface = this;
    private String API_UPLOAD_IMAGEM_IMOVEL = "api/imagemImovel";
    private String poolId = "us-east-2:81030e5c-c14e-405a-abac-a5e4f9133506";
    private static final int JOB_ID = 102;
    private ImagemUpload imagemUploadBanco;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("Upload Imagem Service", "Upload iniciado");
        uploadtos3();
    }

    public void uploadtos3() {
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ImagemUploadManager imagemUploadManager = new ImagemUploadManager(databaseManager.getWritableDatabase());
        imagemUploadBanco = imagemUploadManager.getPrimeiraImagemUpload();
        imagemUploadManager.closeDatabase();

        Log.v("Upload Imagem Service","Iniciando um upload +++");
        if (imagemUploadBanco != null){
            final String fileName = "casamaisimoveis-" + UUID.randomUUID().toString();

            Bitmap imagemParaUpload = imagemUploadBanco.getImagem();

            final File file = createFile(getApplicationContext(), fileName, imagemParaUpload);

            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                    getApplicationContext(),
                    poolId, // Identity pool ID
                    Regions.US_EAST_2 // Region
            );

            AmazonS3 s3 = new AmazonS3Client(credentialsProvider, Region.getRegion(Regions.US_EAST_2));

            TransferNetworkLossHandler.getInstance(getApplicationContext());

            TransferUtility transferUtility = new TransferUtility(s3, getApplicationContext());
            final TransferObserver observer = transferUtility.upload(
                    "casamaisimoveis", //enter bucket name
                    fileName+".png",
                    file,
                    CannedAccessControlList.BucketOwnerFullControl
            );
            observer.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (state.equals(TransferState.COMPLETED)) {
                        PostHttpComHeaderServicesAsyncTask postHttpComHeaderAsyncTask = new PostHttpComHeaderServicesAsyncTask(getApplicationContext(),
                                imagemUploadBanco.gerarJSONImagemUpload(fileName),
                                httpResponseInterface,
                                API_UPLOAD_IMAGEM_IMOVEL);

                        postHttpComHeaderAsyncTask.execute(FerramentasBasicas.getURL() + API_UPLOAD_IMAGEM_IMOVEL);
                        file.delete();
                        //Toast.makeText(context,"Success",Toast.LENGTH_LONG).show();
                    } else if (state.equals(TransferState.FAILED)) {
                        file.delete();
                        //Toast.makeText(context,"Failed to upload",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    Log.v("Upload Imagem Service", ((bytesTotal - bytesCurrent) / 2048) / 1024 +"%");
                }

                @Override
                public void onError(int id, Exception ex) {
                    Log.v("Upload Imagem Service",ex.getMessage());
                }
            });
        }else {
            Log.v("Upload Imagem Service", "##### Service sendo stopada.");
            stopSelf();
        }
    }

    private File createFile(Context context, String name, Bitmap bitmap) {
        File filesDir = context.getFilesDir();
        File imageFile = new File(filesDir, name + ".png");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
            return imageFile;
        } catch (Exception e) {
            Log.e("Upload Imagem Service", "Error writing bitmap", e);
            return null;
        }
    }

    @Override
    public void retornoJsonObject(JSONObject jsonObject, String rotaApi) {
        try {
            if(jsonObject.has("erro")){
                Toast.makeText(getApplicationContext(), jsonObject.getString("erro"), Toast.LENGTH_SHORT).show();
                return;
            }

            if(rotaApi.equals(API_UPLOAD_IMAGEM_IMOVEL))
                removerImagemDaListagem();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void retornoImagemBitmap(Bitmap imagem, String rotaAPI) {

    }

    private void removerImagemDaListagem() {
        Log.v("Service Upload", "Removendo Imagem: " + imagemUploadBanco.getId());
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ImagemUploadManager imagemUploadManager = new ImagemUploadManager(databaseManager.getWritableDatabase());
        imagemUploadManager.deletarImagemUpload(imagemUploadBanco);
        imagemUploadManager.closeDatabase();
        uploadtos3();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("Service Upload", "Service Finalizada");
    }
}
