package com.example.fypapplication.activity;

import static org.junit.Assert.*;

import org.junit.Test;

public class ActMyAccountTest {

    @Test
    public void charAccountTypeToString() {
        String act=ActMyAccount.charAccountTypeToString('R');
        assertEquals("Reader",act);
    }
    @Test
    public void charAccountTypeToString2() {
        String act=ActMyAccount.charAccountTypeToString('S');
        assertEquals("Staff",act);
    }
}