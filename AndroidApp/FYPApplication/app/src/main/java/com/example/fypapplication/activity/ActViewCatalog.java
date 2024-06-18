package com.example.fypapplication.activity;

import static com.example.fypapplication.FYPStatic.context;
import static com.example.fypapplication.FYPStatic.currentBookDetailRecord;
import static com.example.fypapplication.FYPStatic.initCurrentContext;
import static com.example.fypapplication.FYPStatic.initToolBar;
import static com.example.fypapplication.FYPStatic.methods;
import static com.example.fypapplication.FYPStatic.showErrorMsgDialogOK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.fypapplication.R;
import com.example.fypapplication.webService.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActViewCatalog extends AppCompatActivity {
    ListView lvBooks;
    private EditText etSearch;
    private  ArrayList<Map<String,String>> lmRecords=new ArrayList<>(); //allbooks
    private List<Map<String,String>> newlmRecords=new ArrayList<>();//booksaftersearch
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_view_catalog);
        initCurrentContext(this);
        etSearch=findViewById(R.id.etSearch);
        lvBooks=findViewById(R.id.lvBooks);
        getAllBook();
        initToolBar("View Catalog", this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    finish();
                }
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateListView(etSearch.getText().toString());
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        initCurrentContext(this);

    }

    private void updateListView(String keyword) {
        newlmRecords=new ArrayList<>();
        for(Map<String,String>record:lmRecords){
            String bookTitle=record.get("title").toUpperCase();
            if(bookTitle.contains(keyword.toUpperCase())) {
                newlmRecords.add(record);
            }
        }
        SimpleAdapter adapter = new SimpleAdapter(context, newlmRecords, R.layout.view_catbook_cell, new String[]{"title","author","isbn","publisher"}, new int[]{R.id.tv1,R.id.tv2,R.id.tvISBN,R.id.tvPublisher});
        lvBooks.setAdapter(adapter);

        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(context,ActBookDetail.class);
                currentBookDetailRecord= newlmRecords.get(i);
                startActivity(intent);

            }
        });
    }

    private void getAllBook() {
        Call<List<Book>> call= methods.getAllBook()  ;
        call.enqueue(new Callback<List<Book>>() {//execute the call and get the response;network op. need to be run in background thread
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                List<Book> books=response.body();
                if (response.isSuccessful()) {
                    populateListView(books);

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

    private void populateListView(List<Book> books) {


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
        SimpleAdapter adapter = new SimpleAdapter(context, lmRecords, R.layout.view_catbook_cell, new String[]{"title","author","isbn","publisher"}, new int[]{R.id.tv1,R.id.tv2,R.id.tvISBN,R.id.tvPublisher});
        lvBooks.setAdapter(adapter);

        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(context,ActBookDetail.class);
                currentBookDetailRecord= lmRecords.get(i);
                startActivity(intent);

            }
        });
    }

    private void setOnClickDetails() {
        System.out.print(lvBooks.getChildCount());
        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.print(i);
            }
        });
    }
}