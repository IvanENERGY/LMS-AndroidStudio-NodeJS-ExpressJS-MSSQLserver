package com.example.fypapplication.webService;

import com.google.gson.annotations.SerializedName;

public class GetBookCopiesInfoTrans {
    @SerializedName("tranState")
    private String tranState;

    @SerializedName("Title")
    private String title;

    @SerializedName("ISBN")
    private String isbn;

    @SerializedName("Author")
    private String author;

    @SerializedName("Publisher")
    private String publisher;
    @SerializedName("PublishingYear")
    private String publishingYear;
    @SerializedName("Location")
    private String location;

    @SerializedName("Status")
    private String status;

    public String getStatus() {
        return status;
    }

    public String getTranState() {
        return tranState;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
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

    public String getLocation() {
        return location;
    }
}
