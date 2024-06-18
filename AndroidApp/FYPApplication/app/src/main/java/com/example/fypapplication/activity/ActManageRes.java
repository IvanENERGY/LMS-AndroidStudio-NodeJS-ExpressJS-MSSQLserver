package com.example.fypapplication.activity;

import static com.example.fypapplication.FYPStatic.context;
import static com.example.fypapplication.FYPStatic.initCurrentContext;
import static com.example.fypapplication.FYPStatic.initToolBar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fypapplication.R;

public class ActManageRes extends AppCompatActivity {
    private Button btnAddBook,btnEditBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_manage_res);
        initCurrentContext(this);
        initToolBar("Manage Resources", this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    finish();
                }
            }
        });
        btnAddBook=findViewById(R.id.btnAddBook);
        btnEditBook=findViewById(R.id.btnEditBook);

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,ActAddBook.class);
                startActivity(i);
            }
        });
        btnEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,ActEditBook.class);
                startActivity(i);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        initCurrentContext(this);

    }
}