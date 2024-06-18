package com.example.fypapplication.webService;

import static org.junit.Assert.*;

import org.junit.Test;

public class BorrowRetTransTest {

    @Test
    public void getDueDate() {
        BorrowRetTrans borrowRetTrans=new BorrowRetTrans("SUCCESS","0059","2023-12-19T00:00:00.000Z");
        String act=borrowRetTrans.getDueDate();
        assertEquals("2023-12-19",act);
    }
}