package com.example.fypapplication.webService;

import static org.junit.Assert.*;

import org.junit.Test;

public class RenewTransTest {

    @Test
    public void getNewDueDate() {
        RenewTrans renewTrans=new RenewTrans("SUCCESS","2023-12-19T00:00:00.000Z","TestTitle","5651443943");
        String act=renewTrans.getNewDueDate();
        assertEquals("2023-12-19",act);
    }
}