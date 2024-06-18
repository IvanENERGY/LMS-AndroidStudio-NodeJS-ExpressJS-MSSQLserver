package com.example.fypapplication.activity;

import static com.example.fypapplication.FYPStatic.context;
import static com.example.fypapplication.FYPStatic.initCurrentContext;
import static com.example.fypapplication.FYPStatic.initToolBar;
import static com.example.fypapplication.FYPStatic.methods;
import static com.example.fypapplication.FYPStatic.sCurrentUserName;
import static com.example.fypapplication.FYPStatic.showErrorMsgDialogOK;
import static com.example.fypapplication.FYPStatic.showInfoMsgDialogOK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.fypapplication.R;
import com.example.fypapplication.webService.BorrowedBook;
import com.example.fypapplication.webService.RenewTrans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActRenewal extends AppCompatActivity {
    private ListView lvBorrowedBooks;
    private Button btnRenewal;
    private List<Map<String, String>> lmRecords = new ArrayList<>(); //borrowedbook
    private int finishedReq=0;

    private String sRenewalTransMsg="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_renewal);
        initCurrentContext(this);
        lvBorrowedBooks=findViewById(R.id.lvBorrowedBooks);
        btnRenewal=findViewById(R.id.btnRenewal);

        getBorrowedBook();



        initToolBar("Renewal", this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    finish();
                }
            }
        });

        btnRenewal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> list=new ArrayList<>();
                for(int i=0;i<lvBorrowedBooks.getChildCount();i++){
                    CheckBox cb=(CheckBox)( lvBorrowedBooks.getChildAt(i).findViewById(R.id.cbRenewal));
                    if(cb.isChecked()){
                        list.add(i);
                    }
                }
                if(list.isEmpty()){
                    showErrorMsgDialogOK(context,"Please select the book you want to renew.");
                    return;
                }


                boolean fiveRenewalExist=false;
                for(int i:list){
                    if( lmRecords.get(i).get("renewalTimes").equals("5")){
                        fiveRenewalExist=true;
                    }

                }
                if(fiveRenewalExist){
                    Toast.makeText(context,"Some books have been renewal 5 times. Please select again.",Toast.LENGTH_SHORT).show();
                }
                else{
                    doRenewal(list);

                }


            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        initCurrentContext(this);

    }

    private void doRenewal(ArrayList<Integer> list) {
        int totalReq=list.size();
        for(int i:list){
            String barcodeID=lmRecords.get(i).get("barcodeID");
            callRenewal(barcodeID,totalReq);

        }


    }

    private void callRenewal(String barcodeID,int totalReq) {
        Call<RenewTrans> call = methods.renew(barcodeID);
        call.enqueue(new Callback<RenewTrans>() {//execute the call and get the response;network op. need to be run in background thread
            @Override
            public void onResponse(Call<RenewTrans> call, Response<RenewTrans> response) {
                RenewTrans renewTrans= response.body();
                if (response.isSuccessful()) {
                    String res;
                    if(renewTrans.getTranState().equals("SUCCESS")){
                        res= String.format("Success Renewal for Book \n%s (%s)\n, newDueDate is : %s\n\n",renewTrans.getTitle(),renewTrans.getBarcodeID(),renewTrans.getNewDueDate());
                    }
                    else{
                        res= String.format("FAIL Renewal for Book %s (%s), ErrorMsg: %s\n",renewTrans.getTitle(),renewTrans.getBarcodeID(),renewTrans.getNewDueDate(),renewTrans.getTranState());
                    }
                    sRenewalTransMsg+=res;
                    System.out.println("finshedreq"+finishedReq);
                    if(totalReq-1==finishedReq){ //last req
                        showInfoMsgDialogOK(context, sRenewalTransMsg, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(context,ActRenewal.class));
                                finish();
                            }
                        });

                    }


                } else {
                    showErrorMsgDialogOK(context, "callRenewal:response not successful\n");
                }
                finishedReq++;
            }

            @Override
            public void onFailure(Call<RenewTrans> call, Throwable t) {
                showErrorMsgDialogOK(context, "callRenewal: failed getting response\n " + t.getMessage());
                finishedReq++;
            }
        });

    }

    private void getBorrowedBook() {


        Call<List<BorrowedBook>> call = methods.borrowedbooks(sCurrentUserName);
        call.enqueue(new Callback<List<BorrowedBook>>() {//execute the call and get the response;network op. need to be run in background thread
            @Override
            public void onResponse(Call<List<BorrowedBook>> call, Response<List<BorrowedBook>> response) {
                List<BorrowedBook> borrowedBooks = response.body();
                if (response.isSuccessful()) {

                    for(BorrowedBook b:borrowedBooks){

                        String barcodeID= b.getBarcodeId();
                        String title=b.getTitle();
                        String dueDate=b.getDueDate();
                        String renewalTimes=b.getRenewalTimes();

                        Map<String, String> mRecord = new HashMap<>();
                        mRecord.put("barcodeID",barcodeID );
                        mRecord.put("title",title);
                        mRecord.put("dueDate",dueDate);
                        mRecord.put("renewalTimes",renewalTimes);

                        lmRecords.add(mRecord);
                    }
                    SimpleAdapter adapter = new SimpleAdapter(context, lmRecords, R.layout.view_borrowedbook_cell_with_renewal, new String[]{"barcodeID","title","dueDate","renewalTimes"}, new int[]{R.id.tvBarcodeID,R.id.tv1,R.id.tvDueDate,R.id.tvRenewalTimes});
                    lvBorrowedBooks.setAdapter(adapter);
                    System.out.println(lvBorrowedBooks.getChildCount());
                    System.out.println(lvBorrowedBooks.getChildAt(0));

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

    private void disableSomeCheckBox() {
        int borrowedBookCnt=lmRecords.size();
        System.out.println(lmRecords);
        for(int i=0;i<borrowedBookCnt;i++){
            if(lmRecords.get(i).get("renewalTimes").equals("0")){
                CheckBox c= (CheckBox) lvBorrowedBooks.getChildAt(i).findViewById(R.id.cbRenewal);
                c.setEnabled(false);
            }
        }
    }
}