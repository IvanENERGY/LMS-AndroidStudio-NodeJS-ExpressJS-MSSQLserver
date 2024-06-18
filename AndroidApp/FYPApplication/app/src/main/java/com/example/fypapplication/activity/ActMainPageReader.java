package com.example.fypapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fypapplication.R;

import static com.example.fypapplication.FYPStatic.*;

public class ActMainPageReader extends AppCompatActivity {
    private Button btnBorrowReturn, btnRenew, btnBrowseCat, btnMyAc; //reader

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main_page_reader);
        initCurrentContext(this);

        initToolBar("Main Screen (Reader)", this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    showQuestionDialogYesNo(context, "Confirmation", "Do you want to log out?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {//nth
                        }
                    });
                }
            }
        });
        btnBorrowReturn = findViewById(R.id.btnBorrowReturn);
        btnBorrowReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,ActBorrowReturn.class);
                startActivity(i);
            }
        });

        btnRenew = findViewById(R.id.btnRenew);
        btnRenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,ActRenewal.class);
                startActivity(i);
            }
        });

        btnBrowseCat = findViewById(R.id.btnBrowseCat);

        btnBrowseCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,ActViewCatalog.class);
                startActivity(i);
            }
        });

        btnMyAc = findViewById(R.id.btnMyAc);
        btnMyAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,ActMyAccount.class);
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