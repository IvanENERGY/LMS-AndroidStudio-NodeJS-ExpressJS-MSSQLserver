package com.example.fypapplication.webService;

import com.google.gson.annotations.SerializedName;

public class AccountInfo {
    public AccountInfo(String typeId, String firstName, String lastName, String gender, String hkid, String dob, String emailAddress, String address, String contactNo, String maxQuota, String ac) {
        this.typeId = typeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.hkid = hkid;
        this.dob = dob;
        this.emailAddress = emailAddress;
        this.address = address;
        this.contactNo = contactNo;
        this.maxQuota = maxQuota;
        this.ac = ac;
    }

    @SerializedName("TypeId")
    private String typeId;

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

    public String getMaxQuota() {
        return maxQuota;
    }

    public String getTypeId() {
        return typeId;
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

    public String getAc() {
        return ac;
    }
}
