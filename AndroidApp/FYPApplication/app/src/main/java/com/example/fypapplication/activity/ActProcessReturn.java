package com.example.fypapplication.activity;

import static com.example.fypapplication.FYPConst.BOOKCOPIES_STATUS_BORROWOUT;
import static com.example.fypapplication.FYPConst.BOOKCOPIES_STATUS_ONRETREQ;
import static com.example.fypapplication.FYPConst.BOOKCOPIES_STATUS_ONSHELF;
import static com.example.fypapplication.FYPStatic.context;
import static com.example.fypapplication.FYPStatic.initCurrentContext;
import static com.example.fypapplication.FYPStatic.initToolBar;
import static com.example.fypapplication.FYPStatic.methods;
import static com.example.fypapplication.FYPStatic.sCurrentUserName;
import static com.example.fypapplication.FYPStatic.showErrorMsgDialogOK;
import static com.example.fypapplication.FYPStatic.showInfoMsgDialogOK;
import static com.example.fypapplication.FYPStatic.showQuestionDialogYesNo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.fypapplication.R;

import com.example.fypapplication.webService.BorrowRetTrans;
import com.example.fypapplication.webService.GetBookCopiesInfoTrans;
import com.example.fypapplication.webService.ProcessRetTrans;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActProcessReturn extends AppCompatActivity {

    private EditText etBarcode;
    private ImageView ivErase, ivScan;
    private final ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            etBarcode.setText(result.getContents());
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_process_return);
        initCurrentContext(this);

        etBarcode = findViewById(R.id.etBarcode);
        ivErase = findViewById(R.id.ivErase);
        ivScan = findViewById(R.id.ivScan);

        initToolBar("Process Return", this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    finish();
                }
            }
        });

        etBarcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 10) {
                    getBookCopiesStatus();
                }
            }
        });
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
    }
    @Override
    protected void onResume() {
        super.onResume();
        initCurrentContext(this);

    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume Up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(ActCapture.class);
        barLaucher.launch(options);

    }

    private void getBookCopiesStatus() {
        String sBarcode = etBarcode.getText().toString();

        Call<GetBookCopiesInfoTrans> call = methods.getBookCopiesInfo(sBarcode);
        call.enqueue(new Callback<GetBookCopiesInfoTrans>() {//execute the call and get the response;network op. need to be run in background thread
            @Override
            public void onResponse(Call<GetBookCopiesInfoTrans> call, Response<GetBookCopiesInfoTrans> response) {
                GetBookCopiesInfoTrans getBookCopiesInfoTrans = response.body();
                if (response.isSuccessful()) {
                    if (!getBookCopiesInfoTrans.getTranState() .equals("SUCCESS") ) {
                        showErrorMsgDialogOK(context, "Some error occurs in getBookCopiesStatus transaction.\n" + getBookCopiesInfoTrans.getTranState());
                    }
                    else{
                        switch (getBookCopiesInfoTrans.getStatus()) {
                            case BOOKCOPIES_STATUS_ONRETREQ:
                                showQuestionDialogYesNo(context, "Confirmation", "Do u want to PUT this book on shelf?\n" +
                                                "Title: " + getBookCopiesInfoTrans.getTitle(),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                processBookReturnReq(sBarcode);
                                            }
                                        },
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                                etBarcode.setText("");
                                            }
                                        });
                                break;

                            default:
                                showErrorMsgDialogOK(context,"The book is invalid for Return Processing.");
                                break;

                        }
                    }


                } else {
                    showErrorMsgDialogOK(context, "bookcopiesstatus: response not successful\n");
                }
            }

            @Override
            public void onFailure(Call<GetBookCopiesInfoTrans> call, Throwable t) {
                showErrorMsgDialogOK(context, "bookcopiesstatus: failed getting response\n " + t.getMessage());
            }
        });

    }

    private void processBookReturnReq(String sBarcode) {
        Call<ProcessRetTrans> call = methods.processRet(sBarcode);
        call.enqueue(new Callback<ProcessRetTrans>() {//execute the call and get the response;network op. need to be run in background thread
            @Override
            public void onResponse(Call<ProcessRetTrans> call, Response<ProcessRetTrans> response) {
                ProcessRetTrans processRetTrans= response.body();
                if (response.isSuccessful()) {
                    if (!processRetTrans.getTranState() .equals("SUCCESS") ) {
                        showErrorMsgDialogOK(context, "Some error occurs in processingRet transaction.\n" + processRetTrans.getTranState());
                    }
                    else {
                        showInfoMsgDialogOK(context, "Success.", (dialogInterface, i) -> dialogInterface.dismiss());
                    }


                } else {
                    showErrorMsgDialogOK(context, "processBookReturnReq:response not successful\n");
                }

            }

            @Override
            public void onFailure(Call<ProcessRetTrans> call, Throwable t) {
                showErrorMsgDialogOK(context, "processBookReturnReq: failed getting response\n " + t.getMessage());

            }
        });

    }


}