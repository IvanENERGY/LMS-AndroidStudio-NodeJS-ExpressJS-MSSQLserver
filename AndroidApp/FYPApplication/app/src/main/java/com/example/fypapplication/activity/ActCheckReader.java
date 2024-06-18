package com.example.fypapplication.activity;

import static com.example.fypapplication.FYPStatic.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fypapplication.R;
import com.example.fypapplication.webService.AccountInfo;
import com.example.fypapplication.webService.BorrowedBook;
import com.example.fypapplication.webService.ReaderInfo;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActCheckReader extends AppCompatActivity {

    private TextView tvFirstName,tvLastName,tvGender,tvHKID,tvDOB,tvAddress,tvEmail,tvContactNo,tvQuota;
    private ListView lvBorrowedBooks;
    private EditText etBarcode;
    private ImageView ivErase,ivScan;

    private List<Map<String, String>> lmRecords = new ArrayList<>(); //borrowedbook
    private final ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            etBarcode.setText(result.getContents());
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_check_reader);
        initCurrentContext(this);
        initToolBar("Check Reader", this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    finish();
                }
            }
        });
        tvFirstName=findViewById(R.id.tvFirstName);
        tvLastName=findViewById(R.id.tvLastName);
        tvGender=findViewById(R.id.tvGender);
        tvHKID=findViewById(R.id.tvHKID);
        tvDOB=findViewById(R.id.tvDOB);
        tvAddress=findViewById(R.id.tvAddress);
        tvEmail=findViewById(R.id.tvEmail);
        tvContactNo=findViewById(R.id.tvContactNo);
        tvQuota=findViewById(R.id.tvQuota);



        etBarcode = findViewById(R.id.etBarcode);
        etBarcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 4) {
                    getReaderInfo(etBarcode.getText().toString());
                }
                else{
                    clearInfoView();
                }
            }
        });
        ivErase = findViewById(R.id.ivErase);
        ivScan = findViewById(R.id.ivScan);
        ivErase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etBarcode.setText("");
            }
        });
        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });
        lvBorrowedBooks=findViewById(R.id.lvBorrowedBooks);


    }
    @Override
    protected void onResume() {
        super.onResume();
        initCurrentContext(this);

    }

    private void clearInfoView() {
        tvFirstName.setText("");
        tvLastName.setText("");
        tvGender.setText("");
        tvHKID.setText("");
        tvDOB.setText("");
        tvAddress.setText("");
        tvEmail.setText("");
        tvContactNo.setText("");
        tvQuota.setText("");
        lvBorrowedBooks.setAdapter(null);
    }

    private void getReaderInfo(String readerId) {
        Call<ReaderInfo> call = methods.getReaderInfo(readerId);
        call.enqueue(new Callback<ReaderInfo>() {//execute the call and get the response;network op. need to be run in background thread
            @Override
            public void onResponse(Call<ReaderInfo> call, Response<ReaderInfo> response) {
                ReaderInfo readerInfo= response.body();
                if (response.isSuccessful()) {
                    if (!readerInfo.getTranState() .equals("SUCCESS") ) {
                        showErrorMsgDialogOK(context, "Some error occurs in get reader info transaction.\n" + readerInfo.getTranState());
                    }
                    else {
                        tvFirstName.setText(readerInfo.getFirstName());
                        tvLastName.setText(readerInfo.getLastName());
                        tvGender.setText(readerInfo.getGender());
                        tvHKID.setText(readerInfo.getHkid());
                        tvDOB.setText(readerInfo.getDob());
                        tvAddress.setText(readerInfo.getAddress());
                        tvEmail.setText(readerInfo.getEmailAddress());
                        tvContactNo.setText(readerInfo.getContactNo());
                        tvQuota.setText(readerInfo.getMaxQuota());
                        getBorrowedBook(readerInfo.getAc()); ;
                    }


                } else {
                    showErrorMsgDialogOK(context, "getReaderInfo:response not successful\n");
                }

            }

            @Override
            public void onFailure(Call<ReaderInfo> call, Throwable t) {
                showErrorMsgDialogOK(context, "getReaderInfo: failed getting response\n " + t.getMessage());

            }
        });
    }

    private void getBorrowedBook(String ac) {


        Call<List<BorrowedBook>> call = methods.borrowedbooks(ac);
        call.enqueue(new Callback<List<BorrowedBook>>() {//execute the call and get the response;network op. need to be run in background thread
            @Override
            public void onResponse(Call<List<BorrowedBook>> call, Response<List<BorrowedBook>> response) {
                List<BorrowedBook> borrowedBooks = response.body();
                if (response.isSuccessful()) {
                    lmRecords.clear();
                    for(BorrowedBook b:borrowedBooks){


                        String title=b.getTitle();
                        String borrowDate=b.getBorrowDate();
                        String dueDate=b.getDueDate();
                        String renewalTimes=b.getRenewalTimes();

                        Map<String, String> mRecord = new HashMap<>();
                        mRecord.put("title",title);
                        mRecord.put("borrowDate",borrowDate);
                        mRecord.put("dueDate",dueDate);
                        mRecord.put("renewalTimes",renewalTimes);

                        lmRecords.add(mRecord);
                    }
                    SimpleAdapter adapter = new SimpleAdapter(context, lmRecords, R.layout.view_borrowedbook_cell, new String[]{"title","borrowDate","dueDate","renewalTimes"}, new int[]{R.id.tvTitle,R.id.tvBorrowDate,R.id.tvDueDate,R.id.tvRenewalTimes});
                    lvBorrowedBooks.setAdapter(adapter);

                } else {
                    showErrorMsgDialogOK(context, "getBorrowedBook:response not successful\n");
                }
            }

            @Override
            public void onFailure(Call<List<BorrowedBook>> call, Throwable t) {
                showErrorMsgDialogOK(context, "getBorrowedBook: failed getting response\n " + t.getMessage());
            }
        });


    }
    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume Up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(ActCapture.class);
        barLaucher.launch(options);
    }
}