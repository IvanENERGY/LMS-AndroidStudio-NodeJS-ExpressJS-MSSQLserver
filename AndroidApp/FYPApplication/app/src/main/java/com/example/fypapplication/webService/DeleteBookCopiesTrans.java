package com.example.fypapplication.webService;

import com.google.gson.annotations.SerializedName;

public class DeleteBookCopiesTrans {
    @SerializedName("tranState")
    private String tranState;

    public String getTranState() {
        return tranState;
    }
}
