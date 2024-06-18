package com.example.fypapplication.webService;

import static org.junit.Assert.*;

import org.junit.Test;

public class AccountInfoTest {

    @Test
    public void getDob() {
        AccountInfo accountInfo=new AccountInfo("0013","Kan Hei","DING","M","D2042542","1990-12-10T00:00:00.000Z","ivan@yahoo.com","20A, Hiu Wah House, 22 Hiu Jai Road, Kowloon City","96917594","5","ivan");
        String actual =accountInfo.getDob();

        assertEquals("1990-12-10",actual);
    }
}