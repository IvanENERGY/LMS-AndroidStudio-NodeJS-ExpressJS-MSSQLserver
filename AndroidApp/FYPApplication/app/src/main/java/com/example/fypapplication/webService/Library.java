package com.example.fypapplication.webService;

import com.google.gson.annotations.SerializedName;

public class Library {
    @SerializedName("LibID")
    private String libid;

    @SerializedName("Location")
    private String location;

    @SerializedName("ContactNo")
    private String contactNo;

    @SerializedName("Address")
    private String address;

    public String getLibid() {
        return libid;
    }

    public String getLocation() {
        return location;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getAddress() {
        return address;
    }
}
