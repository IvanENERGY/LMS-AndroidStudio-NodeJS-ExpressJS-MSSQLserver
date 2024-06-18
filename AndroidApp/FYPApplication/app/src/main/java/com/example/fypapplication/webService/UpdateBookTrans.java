package com.example.fypapplication.webService;

import com.google.gson.annotations.SerializedName;

public class UpdateBookTrans {
    @SerializedName("tranState")
    private String tranState;
    @SerializedName("barcodeId")
    private String barcodeId;
    @SerializedName("title")
    private String title;
    @SerializedName("author")
    private String author;
    @SerializedName("publisher")
    private String publisher;
    @SerializedName("publishingYear")
    private String publishingYear;
    @SerializedName("editStaffId")
    private String editStaffId;
    @SerializedName("location")
    private String location;
    public String getLocation() {
        return location;
    }
    public String getTranState() {
        return tranState;
    }

    public String getBarcodeId() {
        return barcodeId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishingYear() {
        return publishingYear;
    }

    public String getEditStaffId() {
        return editStaffId;
    }


}
