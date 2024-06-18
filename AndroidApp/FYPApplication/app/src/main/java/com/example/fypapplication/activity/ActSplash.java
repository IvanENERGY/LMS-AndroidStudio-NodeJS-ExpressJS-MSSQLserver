package com.example.fypapplication.activity;

import static android.Manifest.permission.CAMERA;
import static com.example.fypapplication.FYPStatic.context;
import static com.example.fypapplication.FYPStatic.initCurrentContext;
import static com.example.fypapplication.FYPStatic.methods;
import static com.example.fypapplication.FYPStatic.showInfoMsgDialogOK;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.fypapplication.R;
import com.example.fypapplication.webService.Methods;
import com.example.fypapplication.webService.RetrofitClient;

public class ActSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_splash);
        initCurrentContext(this);
        initRetrofitClient();
        checkAppPermission();


    }

    private void checkAppPermission() {

        int cameraPermission = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);

        if(cameraPermission== PackageManager.PERMISSION_DENIED){
            showInfoMsgDialogOK(context, "The app requires camera permission for barcode scanning. Providing such permission can enhance your experience using the app.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{ CAMERA}, 1);
                }
            });

        }else{
            startActLogin();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 1:
                if (grantResults.length > 0) {
                    boolean camera_granted = (grantResults[0] == PackageManager.PERMISSION_GRANTED);

                    if ( !camera_granted ) {
                        showInfoMsgDialogOK(context, "By denying permission for camera, " +
                                "Scanning function will not be available.\n" +
                                "However, you can still enjoy other features.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                startActLogin();
                            }
                        });
                    }else{
                        startActLogin();
                    }
                }
        }
    }

    private void startActLogin() {
        Intent i=new Intent(context,ActLogin.class);
        startActivity(i);
    }

    private static void initRetrofitClient() {
        methods = RetrofitClient.getRetrofitInstance().create(Methods.class); //retrofit create the implementation of methods
    }


}