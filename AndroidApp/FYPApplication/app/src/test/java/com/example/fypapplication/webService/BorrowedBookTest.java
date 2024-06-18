package com.example.fypapplication.webService;

import static org.junit.Assert.*;

import org.junit.Test;

public class BorrowedBookTest {

    @Test
    public void getBorrowDate() {
        BorrowedBook borrowedBook=new BorrowedBook("5415247784", "An Evil Cradling","2023-05-09T00:00:00.000Z","2023-12-19T00:00:00.000Z","3");
        String act= borrowedBook.getBorrowDate();
        assertEquals("2023-05-09",act);
    }

    @Test
    public void getDueDate() {
        BorrowedBook borrowedBook=new BorrowedBook("5415247784", "An Evil Cradling","2023-05-09T00:00:00.000Z","2023-12-19T00:00:00.000Z","3");
        String act= borrowedBook.getDueDate();
        assertEquals("2023-12-19",act);
    }
}