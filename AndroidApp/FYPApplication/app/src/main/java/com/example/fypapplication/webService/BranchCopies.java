package com.example.fypapplication.webService;

import com.google.gson.annotations.SerializedName;

public class BranchCopies {
    @SerializedName("Location")
    private String location;

    @SerializedName("Status")
    private String status;

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }
}
