package com.example.fypapplication.activity;

import static com.example.fypapplication.FYPConst.*;
import static com.example.fypapplication.FYPStatic.context;
import static com.example.fypapplication.FYPStatic.currentBookDetailRecord;
import static com.example.fypapplication.FYPStatic.initCurrentContext;
import static com.example.fypapplication.FYPStatic.initToolBar;
import static com.example.fypapplication.FYPStatic.methods;
import static com.example.fypapplication.FYPStatic.showErrorMsgDialogOK;
import static com.example.fypapplication.FYPStatic.showInfoMsgDialogOK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.fypapplication.R;
import com.example.fypapplication.webService.Book;
import com.example.fypapplication.webService.BranchCopies;
import com.example.fypapplication.webService.RenewTrans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActBookDetail extends AppCompatActivity {

    private TextView tvTitle,tvAuthor,tvISBN,tvPublisher,tvPublishingYear;
    private ListView lvBranchCopies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_book_detail);
        initCurrentContext(this);

        tvTitle=findViewById(R.id.tvTitle);
        tvAuthor=findViewById(R.id.tvAuthor);
        tvISBN=findViewById(R.id.tvISBN);
        tvPublisher=findViewById(R.id.tvPublisher);
        tvPublishingYear=findViewById(R.id.tvPublishingYear);
        lvBranchCopies=findViewById(R.id.lvBranchCopies);

        updateBookDetail();
        getBookCopies((currentBookDetailRecord.get("isbn")));
        initToolBar("Book Detail", this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    finish();
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        initCurrentContext(this);

    }

    private void getBookCopies(String isbn) {
        Call<List<BranchCopies>> call = methods.getAllBookCopies(isbn);
        call.enqueue(new Callback<List<BranchCopies>>() {//execute the call and get the response;network op. need to be run in background thread
            @Override
            public void onResponse(Call<List<BranchCopies>> call, Response<List<BranchCopies>> response) {
                List<BranchCopies> branchCopiesList= response.body();
                if (response.isSuccessful()) {
                    populateListView(branchCopiesList);


                } else {
                    showErrorMsgDialogOK(context, "getBookCopies:response not successful\n");
                }

            }

            @Override
            public void onFailure(Call<List<BranchCopies>> call, Throwable t) {
                showErrorMsgDialogOK(context, "getBookCopies: failed getting response\n " + t.getMessage());

            }
        });
    }

    private void populateListView(List<BranchCopies> branchCopiesList) {
        ArrayList<Map<String,String>> lmRecords=new ArrayList<>();
        for(BranchCopies bc:branchCopiesList){


            String location=bc.getLocation();
            String status=bc.getStatus();


            Map<String, String> mRecord = new HashMap<>();
            mRecord.put("location",location );
            mRecord.put("status",statusNumStringToStatusString(status) );


            lmRecords.add(mRecord);
        }
        SimpleAdapter adapter = new SimpleAdapter(context, lmRecords, R.layout.view_branchcopies_cell, new String[]{"location","status"}, new int[]{R.id.tvBranch,R.id.tvStatus});
        lvBranchCopies.setAdapter(adapter);
    }

    protected static String statusNumStringToStatusString(String status) {
        switch (status){
            case  BOOKCOPIES_STATUS_ONSHELF:
                return "On-shelf";

            case BOOKCOPIES_STATUS_BORROWOUT:
                return  "Borrowed-out";
            case BOOKCOPIES_STATUS_DELETED:
                return  "Deleted";

            case BOOKCOPIES_STATUS_ONRETREQ:
                return  "On-return-request";


        }
        return "";
    }

    private void updateBookDetail() {
        tvTitle.setText( (currentBookDetailRecord.get("title")));
        tvAuthor.setText( (currentBookDetailRecord.get("author")));
        tvISBN.setText( (currentBookDetailRecord.get("isbn")));
        tvPublisher.setText( (currentBookDetailRecord.get("publisher")));
        tvPublishingYear.setText( (currentBookDetailRecord.get("publishingYear")));

    }
}