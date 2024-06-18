package com.example.fypapplication.webService;

import com.google.gson.annotations.SerializedName;

public class ReaderInfo {
    @SerializedName("tranState")
    private String tranState;
    @SerializedName("ReaderId")
    private String readerId;
    @SerializedName("FirstName")
    private String firstName;

    @SerializedName("LastName")
    private String lastName;


    @SerializedName("Gender")
    private String gender;

    @SerializedName("HKID")
    private String hkid;

    @SerializedName("DOB")
    private String dob;

    @SerializedName("EmailAddress")
    private String emailAddress;

    @SerializedName("Address")
    private String address;

    @SerializedName("ContactNo")
    private String contactNo;

    @SerializedName("MaxQuota")
    private String maxQuota;


    @SerializedName("Ac")
    private String ac;

    public String getTranState() {
        return tranState;
    }

    public String getReaderId() {
        return readerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getHkid() {
        return hkid;
    }

    public String getDob() {

        return dob.substring(0,10);

    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getMaxQuota() {
        return maxQuota;
    }

    public String getAc() {
        return ac;
    }
}
