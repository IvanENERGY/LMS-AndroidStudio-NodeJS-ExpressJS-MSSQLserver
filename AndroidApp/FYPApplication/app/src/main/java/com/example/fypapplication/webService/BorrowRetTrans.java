package com.example.fypapplication.webService;

import com.google.gson.annotations.SerializedName;

public class BorrowRetTrans {
    public BorrowRetTrans(String tranState, String borrowID, String dueDate) {
        this.tranState = tranState;
        this.borrowID = borrowID;
        this.dueDate = dueDate;
    }

    @SerializedName("tranState")
    private String tranState;

    @SerializedName("borrowId")
    private String borrowID;

    @SerializedName("dueDate")
    private String dueDate;

    public String getDueDate() {
        return dueDate.substring(0,10);
    }

    public String getTranState() {
        return tranState;
    }

    public String getBorrowId() {return borrowID;}
}
