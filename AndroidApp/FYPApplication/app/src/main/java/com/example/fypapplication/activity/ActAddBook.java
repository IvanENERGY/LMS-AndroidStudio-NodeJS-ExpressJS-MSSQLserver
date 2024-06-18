package com.example.fypapplication.activity;

import static com.example.fypapplication.FYPStatic.*;
import static com.example.fypapplication.FYPStatic.initCurrentContext;
import static com.example.fypapplication.FYPStatic.initToolBar;
import static com.example.fypapplication.FYPStatic.methods;

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
import com.example.fypapplication.webService.AddBookCopiesTrans;
import com.example.fypapplication.webService.AddBookTrans;
import com.example.fypapplication.webService.Book;
import com.example.fypapplication.webService.Library;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActAddBook extends AppCompatActivity {

    private EditText etTitle,etAuthor,etIsbn,etPublisher,etPublishingYear;
    private Spinner spTitle,spLocation;
    private Button btnRegNewBook,btnAddCopies;
    private EditText etBarcode;
    private ImageView ivErase, ivScan;
    private final ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            etBarcode.setText(result.getContents());
        }
    });
    private  ArrayList<Map<String,String>> lmRecords=new ArrayList<>(); //allbooks
    private  ArrayList<Map<String,String>> lmRecords2=new ArrayList<>(); //alllibs
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_add_book);
        initCurrentContext(this);
        initToolBar("Add Book", this, new View.OnClickListener() {
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
        spTitle=findViewById(R.id.spBookTitle);
        spLocation=findViewById(R.id.spLocation);
        getAllBook(); //for creating all books records and put it in lmrecord,initspinner
        getAllLib();//for creating all lib records and put it in lmrecord2,initSpinner

        btnRegNewBook=findViewById(R.id.btnRegNewBook);
        btnRegNewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title=capitaliseFirstLetter(etTitle.getText().toString());
                String author=capitaliseFirstLetter(etAuthor.getText().toString());
                String isbn=etIsbn.getText().toString();
                String publisher=etPublisher.getText().toString();
                String publishingYear=etPublishingYear.getText().toString();

                if(title.equals("") || author.equals("")||isbn.equals("")||publisher.equals("")||publishingYear.equals("")){
                    showErrorMsgDialogOK(context,"Please fill in all the fields");
                }
                else if(!isValidYear(publishingYear)){
                    showErrorMsgDialogOK(context,"Please enter valid publishing year.");
                }
                else if (isbn.length()!=13){
                    showErrorMsgDialogOK(context,"Please enter valid isbn.");
                }
                else{
                    Book book=new Book(title,author,publisher,isbn,publishingYear,sCurrentUserName);
                    createBookInDb(book);
                }

            }
        });

        btnAddCopies=findViewById(R.id.btnAddCopies);
        btnAddCopies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedIdxTitles=spTitle.getSelectedItemPosition();
                int selectedIdxLocations=spLocation.getSelectedItemPosition();
                String etBarcodeString=etBarcode.getText().toString();
                if(selectedIdxTitles==0||selectedIdxLocations==0||etBarcodeString.equals("")){
                    showErrorMsgDialogOK(context,"Missing fields found! Please fill in all the fields (title,barcode,location).");
                }
                else{//addcopies
                    String isbn=lmRecords.get(selectedIdxTitles-1).get("isbn");
                    String libid=lmRecords2.get(selectedIdxLocations-1).get("libid");
                    if(etBarcodeString.length()!=10){
                        showErrorMsgDialogOK(context,"The barcode length need to be 10 digit!");
                    }
                    else{
                        addCopies(isbn,libid,etBarcodeString);
                    }
                }




            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        initCurrentContext(this);

    }

    private void addCopies(String isbn, String libid, String barcode) {
        Call<AddBookCopiesTrans> call = methods.createBookCopies(isbn,libid,barcode,sCurrentUserName);
        call.enqueue(new Callback<AddBookCopiesTrans>() {//execute the call and get the response;network op. need to be run in background thread
            @Override
            public void onResponse(Call<AddBookCopiesTrans> call, Response<AddBookCopiesTrans> response) {
                AddBookCopiesTrans addBookCopiesTrans= response.body();
                if (response.isSuccessful()) {
                    if (!addBookCopiesTrans.getTranState() .equals("SUCCESS") ) {
                        showErrorMsgDialogOK(context, "Some error occurs in add book copies transaction.\n" + addBookCopiesTrans.getTranState());
                    }
                    else {
                        showInfoMsgDialogOK(context, "Success.", (dialogInterface, i) -> dialogInterface.dismiss());
                    }


                } else {
                    showErrorMsgDialogOK(context, "addCopies:response not successful\n");
                }

            }

            @Override
            public void onFailure(Call<AddBookCopiesTrans> call, Throwable t) {
                showErrorMsgDialogOK(context, "addCopies: failed getting response\n " + t.getMessage());

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

    protected static ArrayList<String> retrieveTitleListFrom(ArrayList<Map<String, String>> lmRecords) {


        ArrayList<String> lTitles=new ArrayList<>();
        for(Map<String, String>record:lmRecords){
            lTitles.add(record.get("title"));
        }
        return  lTitles;
    }

    private void initTitleSpinner(ArrayList<String>lTitles) {
        System.out.println(lTitles);
        lTitles.add(0,"------Choose a title here------");
        ArrayAdapter spinnerAdapter = new ArrayAdapter(context, R.layout.view_spinner_row, R.id.tvSpinnerItem, lTitles);

        if(spinnerAdapter!=null){
            spinnerAdapter.setDropDownViewResource(R.layout.view_spinner_drop_down);
        }
        spTitle.setAdapter(spinnerAdapter);
    }


    protected static boolean isValidYear(String year) {

        int y=Integer.parseInt(year);
        return y>1900 && y<2023;
    }


    protected static String capitaliseFirstLetter(String s) {


        char[] charArr = s.toCharArray();
        boolean spaceExisted = true;

        for(int i = 0; i < charArr.length; i++) {
            if(Character.isLetter(charArr[i])) {
                if(spaceExisted) {
                    charArr[i] = Character.toUpperCase(charArr[i]);
                    spaceExisted = false;
                }
            }
            else {
                spaceExisted = true;
            }
        }

        return String.valueOf(charArr);
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

    private void initLocSpinner(ArrayList<String> lLocation) {
        System.out.println(lLocation);
        lLocation.add(0,"------Choose a location here------");
        ArrayAdapter spinnerAdapter = new ArrayAdapter(context, R.layout.view_spinner_row, R.id.tvSpinnerItem, lLocation);

        if(spinnerAdapter!=null){
            spinnerAdapter.setDropDownViewResource(R.layout.view_spinner_drop_down);
        }
        spLocation.setAdapter(spinnerAdapter);
    }


    protected static ArrayList<String> retrieveLocationListFrom(ArrayList<Map<String, String>> lmRecords2) {

        ArrayList<String> lLocation=new ArrayList<>();
        for(Map<String, String>record:lmRecords2){
            lLocation.add(record.get("location"));
        }
        return  lLocation;
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

    private void getAllBook() {
        Call<List<Book>> call= methods.getAllBook()  ;
        call.enqueue(new Callback<List<Book>>() {//execute the call and get the response;network op. need to be run in background thread
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                List<Book> books=response.body();
                if (response.isSuccessful()) {
                    createlmRecords(books);
                    initTitleSpinner(retrieveTitleListFrom(lmRecords));

                } else {
                    showErrorMsgDialogOK(context, "getAllBook:response not successful\n");
                }


            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                showErrorMsgDialogOK(context, "getAllBook: failed getting response\n " + t.getMessage());
            }
        });
    }

    private void createlmRecords(List<Book> books) {
        for(Book b:books){


            String title=b.getTitle();
            String author=b.getAuthor();
            String publisher=b.getPublisher();
            String isbn=b.getIsbn();
            String publishingYear=b.getPublishingYear();
            String editStaffID=b.getEditStaffID();

            Map<String, String> mRecord = new HashMap<>();
            mRecord.put("title",title );
            mRecord.put("author",author );
            mRecord.put("publisher",publisher );
            mRecord.put("isbn",isbn );
            mRecord.put("publishingYear",publishingYear );
            mRecord.put("editStaffID",editStaffID );

            lmRecords.add(mRecord);
        }
    }

    private void createBookInDb(Book book) {

        Call<AddBookTrans> call = methods.createBook(book);
        call.enqueue(new Callback<AddBookTrans>() {
            @Override
            public void onResponse(Call<AddBookTrans> call, Response<AddBookTrans> response) {
                AddBookTrans addBookTrans= response.body();
                if (response.isSuccessful()) {
                    if (!addBookTrans.getTranState() .equals("SUCCESS") ) {
                        showErrorMsgDialogOK(context, "Some error occurs in create book transaction.\n" + addBookTrans.getTranState());
                    }
                    else {
                        showInfoMsgDialogOK(context, "Success.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getAllBook(); //update spinner
                            }
                        });
                    }

                } else {
                    showErrorMsgDialogOK(context, "createBook:response not successful\n");
                }

            }

            @Override
            public void onFailure(Call<AddBookTrans> call, Throwable t) {
                showErrorMsgDialogOK(context, "createBook: failed getting response\n " + t.getMessage());

            }
        });


    }
}