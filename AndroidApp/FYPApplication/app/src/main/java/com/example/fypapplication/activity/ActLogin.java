package com.example.fypapplication.activity;

import static android.Manifest.permission.CAMERA;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.fypapplication.FYPStatic.*;

import com.example.fypapplication.webService.Account;
import com.example.fypapplication.webService.Methods;
import com.example.fypapplication.R;
import com.example.fypapplication.webService.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActLogin extends AppCompatActivity {
    private EditText etUserName, etPw;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /******every act should have******/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        initCurrentContext(this);
        /*******/
//        checkAppPermission();
//
//        initRetrofitClient();
        etUserName = findViewById(R.id.etUsername);
        etPw = findViewById(R.id.etPw);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etUserName.getText().toString().equals("") || etPw.getText().toString().equals("")) {
                    showErrorMsgDialogOK(context, "Please enter Username and Password!");
                } else {
                    login();
                }
            }
        });


    }


    @Override
    public void onBackPressed() {

    }

    //    private void checkAppPermission() {
//
//        int cameraPermission = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
//
//        if(cameraPermission== PackageManager.PERMISSION_DENIED){
//            showInfoMsgDialogOK(context, "The app requires camera permission for barcode scanning. Providing such permission can enhance your experience using the app.", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    ActivityCompat.requestPermissions((Activity) context, new String[]{ CAMERA}, 1);
//                }
//            });
//
//        }
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//
//            case 1:
//                if (grantResults.length > 0) {
//                    boolean camera_granted = (grantResults[0] == PackageManager.PERMISSION_GRANTED);
//
//                    if ( !camera_granted ) {
//                        showInfoMsgDialogOK(context, "By denying permission for camera, " +
//                                "Scanning function will not be available.\n" +
//                                "However, you can still enjoy other features.", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        });
//                    }
//                }
//        }
//    }


    @Override
    protected void onResume() {
        super.onResume();
        initCurrentContext(this);
        etPw.setText("");
    }

//    private static void initRetrofitClient() {
//        methods = RetrofitClient.getRetrofitInstance().create(Methods.class); //retrofit create the implementation of methods
//    }

    private void login() {
        String username = etUserName.getText().toString();
        String pw = etPw.getText().toString();

        Call<Account> call = methods.login(username, pw);
        call.enqueue(new Callback<Account>() {//execute the call and get the response;network op. need to be run in background thread
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                Account account = response.body();
                if (response.isSuccessful()) {

                    if (account.getErrorMsgLogin() != null) {
                        showErrorMsgDialogOK(context, "Please enter a valid username and password\n" + account.getErrorMsgLogin());
                    } else {
                        sCurrentUserName = username;
                        cCurrentUserAccountType = account.getAccountType();
                        Intent i = null;
                        if (cCurrentUserAccountType == 'R') {
                            i = new Intent(context, ActMainPageReader.class);
                        } else if (cCurrentUserAccountType == 'S') {
                            i = new Intent(context, ActMainPageStaff.class);
                        }
                        startActivity(i);

                    }
                } else {
                    showErrorMsgDialogOK(context, "login: response not successful\n");
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                showErrorMsgDialogOK(context, "login: failed getting response\n" + t.getMessage());
            }
        });
    }
}