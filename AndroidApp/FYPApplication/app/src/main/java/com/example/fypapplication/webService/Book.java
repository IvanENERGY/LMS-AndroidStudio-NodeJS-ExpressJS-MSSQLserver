package com.example.fypapplication.webService;

import com.google.gson.annotations.SerializedName;

public class Book {
    @SerializedName("Title")
    private String title;

    @SerializedName("Author")
    private String author;

    @SerializedName("Publisher")
    private String publisher;

    @SerializedName("ISBN")
    private String isbn;

    @SerializedName("PublishingYear")
    private String publishingYear;

    @SerializedName("EditStaffID")
    private String editStaffID;

    @SerializedName("EditStaffAc")
    private String editStaffAc;

    public Book(String title, String author, String publisher, String isbn, String publishingYear,String editStaffAc) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.publishingYear = publishingYear;
        this.editStaffAc=editStaffAc;

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

    public String getIsbn() {
        return isbn;
    }

    public String getPublishingYear() {
        return publishingYear;
    }

    public String getEditStaffID() {
        return editStaffID;
    }
}