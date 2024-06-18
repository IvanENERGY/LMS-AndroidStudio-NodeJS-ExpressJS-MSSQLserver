package com.example.fypapplication.webService;

import com.google.gson.annotations.SerializedName;

public class RenewTrans {
    public RenewTrans(String tranState, String newDueDate, String title, String barcodeID) {
        this.tranState = tranState;
        this.newDueDate = newDueDate;
        this.title = title;
        this.barcodeID = barcodeID;
    }

    @SerializedName("tranState")
    private String tranState;

    @SerializedName("newDueDate")
    private String newDueDate;


    @SerializedName("title")
    private String title;

    @SerializedName("barcodeID")
    private String barcodeID;

    public String getTranState() {
        return tranState;
    }
    public String getNewDueDate() {
        return newDueDate.substring(0,10);
    }

    public String getTitle() {
        return title;
    }

    public String getBarcodeID() {
        return barcodeID;
    }

}
