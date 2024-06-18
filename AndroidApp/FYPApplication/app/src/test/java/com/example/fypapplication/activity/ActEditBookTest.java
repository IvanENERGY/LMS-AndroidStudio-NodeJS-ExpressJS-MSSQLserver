package com.example.fypapplication.activity;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActEditBookTest {

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