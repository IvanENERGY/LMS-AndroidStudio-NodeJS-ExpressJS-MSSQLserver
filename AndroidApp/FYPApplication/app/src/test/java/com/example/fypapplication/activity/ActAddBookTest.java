package com.example.fypapplication.activity;

import static org.junit.Assert.*;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.manipulation.Ordering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.fypapplication.FYPStatic.*;
import static com.example.fypapplication.FYPStatic.initCurrentContext;
import static com.example.fypapplication.FYPStatic.initToolBar;
import static com.example.fypapplication.FYPStatic.methods;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.fypapplication.R;
import com.example.fypapplication.webService.AddBookCopiesTrans;
import com.example.fypapplication.webService.AddBookTrans;
import com.example.fypapplication.webService.Book;
import com.example.fypapplication.webService.Library;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActAddBookTest {


    @Test
    public void retrieveTitleListFrom() {

        ArrayList<Map<String,String>> lmRecords=new ArrayList<>();

        Map<String,String> record1=new HashMap<>();
        record1.put("title","Title1");
        Map<String,String> record2=new HashMap<>();
        record2.put("title","Title2");
        lmRecords.add(record1);
        lmRecords.add(record2);

        ArrayList<String> actual=ActAddBook.retrieveTitleListFrom(lmRecords);

        ArrayList<String> expected=new ArrayList<>();
        expected.add("Title1");
        expected.add("Title2");

        assertEquals(expected,actual);
    }

    @Test
    public void isValidYear() {
        boolean actual=ActAddBook.isValidYear("2013");
        assertEquals(true,actual);
    }

    @Test
    public void isValidYear2() {
        boolean actual=ActAddBook.isValidYear("1866");
        assertEquals(false,actual);
    }

    @Test
    public void capitaliseFirstLetter() {
        String actual=ActAddBook.capitaliseFirstLetter("some text here");
        assertEquals("Some Text Here",actual);
    }

    @Test
    public void retrieveLocationListFrom() {
        ArrayList<Map<String,String>> lmRecords=new ArrayList<>();

        Map<String,String> record1=new HashMap<>();
        record1.put("location","Location1");
        Map<String,String> record2=new HashMap<>();
        record2.put("location","Location2");
        lmRecords.add(record1);
        lmRecords.add(record2);

        ArrayList<String> actual=ActAddBook.retrieveLocationListFrom(lmRecords);

        ArrayList<String> expected=new ArrayList<>();
        expected.add("Location1");
        expected.add("Location2");

        assertEquals(expected,actual);
    }
}