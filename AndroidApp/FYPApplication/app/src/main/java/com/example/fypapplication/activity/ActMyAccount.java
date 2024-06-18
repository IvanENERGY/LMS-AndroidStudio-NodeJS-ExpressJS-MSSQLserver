package com.example.fypapplication.activity;

import static com.example.fypapplication.FYPStatic.*;
import static com.example.fypapplication.FYPStatic.initCurrentContext;
import static com.example.fypapplication.FYPStatic.initToolBar;
import static com.example.fypapplication.FYPStatic.methods;
import static com.example.fypapplication.FYPStatic.sCurrentUserName;
import static com.example.fypapplication.FYPStatic.showErrorMsgDialogOK;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fypapplication.R;
import com.example.fypapplication.webService.AccountInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActMyAccount extends AppCompatActivity {

    private TextView tvType,tvFirstName,tvLastName,tvGender,tvHKID,tvDOB,tvAddress,tvEmail,tvContactNo,tvQuota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_my_account);
        initCurrentContext(this);

        initToolBar("My Account", this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    finish();
                }
            }
        });
        tvType=findViewById(R.id.tvType);
        tvFirstName=findViewById(R.id.tvFirstName);
        tvLastName=findViewById(R.id.tvLastName);
        tvGender=findViewById(R.id.tvGender);
        tvHKID=findViewById(R.id.tvHKID);
        tvDOB=findViewById(R.id.tvDOB);
        tvAddress=findViewById(R.id.tvAddress);
        tvEmail=findViewById(R.id.tvEmail);
        tvContactNo=findViewById(R.id.tvContactNo);
        tvQuota=findViewById(R.id.tvQuota);

        getAccountInfo(sCurrentUserName);

    }
    @Override
    protected void onResume() {
        super.onResume();
        initCurrentContext(this);

    }

    private void getAccountInfo(String sCurrentUserName) {
        Call<AccountInfo> call = methods.getAcInfo(sCurrentUserName);
        call.enqueue(new Callback<AccountInfo>() {//execute the call and get the response;network op. need to be run in background thread
            @Override
            public void onResponse(Call<AccountInfo> call, Response<AccountInfo> response) {
                AccountInfo accountInfo= response.body();
                if (response.isSuccessful()) {
                    tvType.setText(charAccountTypeToString(cCurrentUserAccountType));
                    tvFirstName.setText(accountInfo.getFirstName());
                    tvLastName.setText(accountInfo.getLastName());
                    tvGender.setText(accountInfo.getGender());
                    tvHKID.setText(accountInfo.getHkid());
                    tvDOB.setText(accountInfo.getDob());
                    tvAddress.setText(accountInfo.getAddress());
                    tvEmail.setText(accountInfo.getEmailAddress());
                    tvContactNo.setText(accountInfo.getContactNo());
                    tvQuota.setText(accountInfo.getMaxQuota());


                } else {
                    showErrorMsgDialogOK(context, "getAccountInfo:response not successful\n");
                }

            }

            @Override
            public void onFailure(Call<AccountInfo> call, Throwable t) {
                showErrorMsgDialogOK(context, "getAccountInfo: failed getting response\n " + t.getMessage());

            }
        });
    }

    protected static String charAccountTypeToString(char cCurrentUserAccountType) {
        if (cCurrentUserAccountType=='R'){
            return "Reader";
        }
        else{
            return "Staff";
        }
    }
}