package com.example.fypapplication.activity;

import static org.junit.Assert.*;

import org.junit.Test;

public class ActBookDetailTest {

    @Test
    public void statusNumStringToStatusString() {
       String actual= ActBookDetail.statusNumStringToStatusString("1");
       assertEquals("On-shelf",actual);
    }
    @Test
    public void statusNumStringToStatusString2() {
        String actual= ActBookDetail.statusNumStringToStatusString("2");
        assertEquals("Borrowed-out",actual);
    }
    @Test
    public void statusNumStringToStatusString3() {
        String actual= ActBookDetail.statusNumStringToStatusString("8");
        assertEquals("Deleted",actual);
    }
    @Test
    public void statusNumStringToStatusString4() {
        String actual= ActBookDetail.statusNumStringToStatusString("757");
        assertEquals("",actual);
    }
    @Test
    public void statusNumStringToStatusString5() {
        String actual= ActBookDetail.statusNumStringToStatusString("3");
        assertEquals("On-return-request",actual);
    }
}