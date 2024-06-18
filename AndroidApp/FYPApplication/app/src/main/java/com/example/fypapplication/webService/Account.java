package com.example.fypapplication.webService;

import com.google.gson.annotations.SerializedName;

public class Account {

    @SerializedName("Ac")
    private String ac;

    @SerializedName("Pw")
    private String pw;

    @SerializedName("LastLogin")
    private String lastLogin;


    @SerializedName("AccountType")
    private char accountType;
    @SerializedName("Message")
    private String errorMsgLogin;

    public Account(String ac, String pw, String lastLogin) {
        this.ac = ac;
        this.pw = pw;
        this.lastLogin = lastLogin;
    }

    public String getAc() {
        return ac;
    }

    public String getPw() {
        return pw;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public char getAccountType() {return accountType;}
    public String getErrorMsgLogin() {
        return errorMsgLogin;
    }

}
