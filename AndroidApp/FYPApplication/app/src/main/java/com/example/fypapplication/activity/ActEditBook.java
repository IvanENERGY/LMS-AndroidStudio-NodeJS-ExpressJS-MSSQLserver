package com.example.fypapplication.activity;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.fypapplication.R;
import com.example.fypapplication.webService.DeleteBookCopiesTrans;
import com.example.fypapplication.webService.GetBookCopiesInfoTrans;
import com.example.fypapplication.webService.Library;
import com.example.fypapplication.webService.UpdateBookTrans;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActEditBook extends AppCompatActivity {
    private EditText etTitle,etAuthor,etIsbn,etPublisher,etPublishingYear,etBarcode;
    private ImageView ivErase,ivScan;
    private Spinner spBranch;
    private Button btnDelete,btnUpdate;
    private ArrayList<Map<String,String>> lmRecords2=new ArrayList<>();
    private final ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            etBarcode.setText(result.getContents());
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_edit_book);
        initCurrentContext(this);
        initToolBar("Edit Book", this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    finish();
                }
            }
        });
        etTitle=findViewById(R.id.etTitle);
        etAuthor=findViewById(R.id.etAuthor);
        etIsbn=findViewById(R.id.etISBN);
        etPublisher=findViewById(R.id.etPublisher);
        etPublishingYear=findViewById(R.id.etPublishingYear);
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
                if (editable.length() == 10) {
                    getBookCopiesInfo(etBarcode.getText().toString());
                }
                else{
                    clearForm();
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

        spBranch=findViewById(R.id.spBranch);
        getAllLib();
        btnDelete=findViewById(R.id.btnDelete);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String barcode=etBarcode.getText().toString();
                if(!barcode.equals("")){
                    updateBook(barcode);
                }
                else{
                    showErrorMsgDialogOK(context,"Please fill in the barcode");
                }

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String barcode=etBarcode.getText().toString();
                if(!barcode.equals("")){
                    showQuestionDialogYesNo(context, "Confirmation", "Are you sure you want to delete this book copy?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            delBook(barcode);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {//nth
                        }
                    });


                }
                else{
                    showErrorMsgDialogOK(context,"Please fill in the barcode");
                }
            }
        });
    }

    private void clearForm() {
        etTitle.setText("");
        etIsbn.setText("");
        etAuthor.setText("");
        etPublisher.setText("");
        etPublishingYear.setText("");
        spBranch.setSelection(0);
        btnDelete.setEnabled(false);
        btnUpdate.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCurrentContext(this);

    }
    private void delBook(String barcode) {
        Call<DeleteBookCopiesTrans> call = methods.delBookCopies(barcode,sCurrentUserName);
        call.enqueue(new Callback<DeleteBookCopiesTrans>() {//execute the call and get the response;network op. need to be run in background thread
            @Override
            public void onResponse(Call<DeleteBookCopiesTrans> call, Response<DeleteBookCopiesTrans> response) {
                DeleteBookCopiesTrans deleteBookCopiesTrans= response.body();
                if (response.isSuccessful()) {
                    if (!deleteBookCopiesTrans.getTranState() .equals("SUCCESS") ) {
                        showErrorMsgDialogOK(context, "Some error occurs in del book transaction.\n" + deleteBookCopiesTrans.getTranState());
                    }
                    else {
                        showInfoMsgDialogOK(context, "Success.", (dialogInterface, i) -> dialogInterface.dismiss());
                    }



                } else {
                    showErrorMsgDialogOK(context, "delBook:response not successful\n");
                }

            }

            @Override
            public void onFailure(Call<DeleteBookCopiesTrans> call, Throwable t) {
                showErrorMsgDialogOK(context, "delBook: failed getting response\n " + t.getMessage());

            }
        });
    }

    private void updateBook(String barcode) {
        String title=etTitle.getText().toString();
        String author=etAuthor.getText().toString();
        String publisher=etPublisher.getText().toString();
        String publishingYear=etPublishingYear.getText().toString();
        String locString= lmRecords2.get(spBranch.getSelectedItemPosition()-1).get("location");
        Map<String,String> fields= new HashMap<>();
        fields.put("BarcodeID",barcode);
        fields.put("Title",title);
        fields.put("Author",author);
        fields.put("Publisher",publisher);
        fields.put("PublishingYear",publishingYear);
        fields.put("Ac",sCurrentUserName);
        fields.put("Location",locString);

        Call<UpdateBookTrans> call = methods.updateBook(fields);
        call.enqueue(new Callback<UpdateBookTrans>() {//execute the call and get the response;network op. need to be run in background thread
            @Override
            public void onResponse(Call<UpdateBookTrans> call, Response<UpdateBookTrans> response) {
                UpdateBookTrans updateBookTrans= response.body();
                if (response.isSuccessful()) {
                    if (!updateBookTrans.getTranState() .equals("SUCCESS") ) {
                        showErrorMsgDialogOK(context, "Some error occurs in update book transaction.\n" + updateBookTrans.getTranState());
                    }
                    else {
                        String barcode= updateBookTrans.getBarcodeId();
                        String title= updateBookTrans.getTitle();
                        String author= updateBookTrans.getAuthor();
                        String publisher= updateBookTrans.getPublisher();
                        String publishingYear=updateBookTrans.getPublishingYear();
                        String location=updateBookTrans.getLocation();

                        showInfoMsgDialogOK(context, String.format("Success.\nBarcode:%s\nTitle:%s\nAuthor:%s\nPublisher:%s\nPublishingYear:%s\nLocation:%s",barcode,title,author,publisher,publishingYear,location), (dialogInterface, i) -> dialogInterface.dismiss());
                    }


                } else {
                    showErrorMsgDialogOK(context, "update book:response not successful\n");
                }

            }

            @Override
            public void onFailure(Call<UpdateBookTrans> call, Throwable t) {
                showErrorMsgDialogOK(context, "update book: failed getting response\n " + t.getMessage());

            }
        });


    }

    private void getBookCopiesInfo(String barcode) {
        Call<GetBookCopiesInfoTrans> call= methods.getBookCopiesInfo(barcode)  ;
        call.enqueue(new Callback<GetBookCopiesInfoTrans>() {//execute the call and get the response;network op. need to be run in background thread
            @Override
            public void onResponse(Call<GetBookCopiesInfoTrans> call, Response<GetBookCopiesInfoTrans> response) {
                GetBookCopiesInfoTrans getBookCopiesInfoTrans=response.body();
                if (response.isSuccessful()) {
                    if (!getBookCopiesInfoTrans.getTranState() .equals("SUCCESS") ) {
                        etBarcode.setText("");
                        showErrorMsgDialogOK(context, "Some error occurs in getBookCopiesInfo transaction.\n" + getBookCopiesInfoTrans.getTranState());
                    }
                    else{

                        populateBookCopiesView(getBookCopiesInfoTrans);
                    }

                } else {
                    showErrorMsgDialogOK(context, "getBookCopiesInfo:response not successful\n");
                }


            }

            @Override
            public void onFailure(Call<GetBookCopiesInfoTrans> call, Throwable t) {
                showErrorMsgDialogOK(context, "getBookCopiesInfo: failed getting response\n " + t.getMessage());
            }
        });

    }

    private void populateBookCopiesView(GetBookCopiesInfoTrans getBookCopiesInfoTrans) {
        String title=getBookCopiesInfoTrans.getTitle();
        String isbn=getBookCopiesInfoTrans.getIsbn();
        String author=getBookCopiesInfoTrans.getAuthor();
        String publisher=getBookCopiesInfoTrans.getPublisher();
        String publishingYear=getBookCopiesInfoTrans.getPublishingYear();
        String branch=getBookCopiesInfoTrans.getLocation();

        etTitle.setText(title);
        etIsbn.setText(isbn);
        etAuthor.setText(author);
        etPublisher.setText(publisher);
        etPublishingYear.setText(publishingYear);
        spBranch.setSelection(locIdxFromlmRecord2(branch)+1);
        btnDelete.setEnabled(true);
        btnUpdate.setEnabled(true);


    }


    protected int locIdxFromlmRecord2(String branch) {

        int sizeLibs=lmRecords2.size();
        for(int i=0;i<sizeLibs;i++){
            if(lmRecords2.get(i).get("location").equals(branch)){

                return i;
            }
        }
        return -1;
    }


    private void getAllLib() {
        Call<List<Library>> call= methods.getAllLib()  ;
        call.enqueue(new Callback<List<Library>>() {//execute the call and get the response;network op. need to be run in background thread
            @Override
            public void onResponse(Call<List<Library>> call, Response<List<Library>> response) {
                List<Library> libs=response.body();
                if (response.isSuccessful()) {
                    createlmRecords2(libs);
                    initLocSpinner(retrieveLocationListFrom(lmRecords2));

                } else {
                    showErrorMsgDialogOK(context, "getAllLibrary:response not successful\n");
                }


            }

            @Override
            public void onFailure(Call<List<Library>> call, Throwable t) {
                showErrorMsgDialogOK(context, "getAllLibrary: failed getting response\n " + t.getMessage());
            }
        });
    }

    private void createlmRecords2(List<Library> libs) {
        for(Library library:libs){


            String address=library.getAddress();
            String contactNo=library.getContactNo();
            String libid=library.getLibid();
            String location=library.getLocation();


            Map<String, String> mRecord = new HashMap<>();
            mRecord.put("address",address );
            mRecord.put("contactNo",contactNo );
            mRecord.put("libid",libid );
            mRecord.put("location",location );


            lmRecords2.add(mRecord);
        }
    }

    private void initLocSpinner(ArrayList<String> lLocation) {
        System.out.println(lLocation);
        lLocation.add(0,"-------------------");
        ArrayAdapter spinnerAdapter = new ArrayAdapter(context, R.layout.view_spinner_row, R.id.tvSpinnerItem, lLocation);

        if(spinnerAdapter!=null){
            spinnerAdapter.setDropDownViewResource(R.layout.view_spinner_drop_down);
        }
        spBranch.setAdapter(spinnerAdapter);
    }


    protected ArrayList<String> retrieveLocationListFrom(ArrayList<Map<String, String>> lmRecords2) {

        ArrayList<String> lLocation=new ArrayList<>();
        for(Map<String, String>record:lmRecords2){
            lLocation.add(record.get("location"));
        }
        return  lLocation;
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