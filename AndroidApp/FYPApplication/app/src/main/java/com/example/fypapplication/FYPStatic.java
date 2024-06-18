package com.example.fypapplication;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fypapplication.webService.Methods;

import java.util.Map;

public class FYPStatic {
    public static Context context;
    public static Methods methods;
    public static String sCurrentUserName;
    public static char cCurrentUserAccountType;

    public static Map<String,String> currentBookDetailRecord;

    public static void showInfoMsgDialogOK(Context context, String Msg,DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Information")
                .setIcon(R.drawable.baseline_info_24)
                .setMessage(Msg)
                .setCancelable(false)
                .setPositiveButton("OK", onClickListener);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }

    public static void showErrorMsgDialogOK(Context context, String Msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Oops")
                .setIcon(R.drawable.baseline_error_24)
                .setMessage(Msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }
    public static void showQuestionDialogYesNo(Context context,String title, String question,DialogInterface.OnClickListener yesListener,DialogInterface.OnClickListener noListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setIcon(R.drawable.baseline_question_mark_24)
                .setMessage(question)
                .setCancelable(false)
                .setPositiveButton("Yes",yesListener)
                .setNegativeButton("No",noListener);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }
    public static void initToolBar(String psTitle,Activity activity,View.OnClickListener onClickListener) {
        ((TextView) (activity.findViewById(R.id.toolbar_title))).setText(psTitle);
        ((ImageView) (activity.findViewById(R.id.toolbar_back))).setOnClickListener(onClickListener);
    }
    public static void initCurrentContext(Activity act) {
        context = act;
    }

}
