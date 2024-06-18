package com.example.fypapplication.webService;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Methods {
    //if u write("/orders"), last part of base url will be replace:
    // base - >http://192.168.102.123:8090/api/
    // become -> http://192.168.102.123:8090/orders

    @GET("login/{ac}/{pw}") //u can do "http://192.168.102.123:8090/api/orders" overwrite whole base url
    Call<Account> login(@Path("ac") String ac, @Path("pw") String pw);

    //@GET("bookcopiestatus/{barcode}")
    //Call<GetBookCopiesInfoTrans>bookcopiesstatus(@Path("barcode")String barcode);

    @PUT("borrow/{ac}/{barcode}")
    Call<BorrowRetTrans>borrow(@Path("ac")String ac, @Path("barcode")String barcode);

    @PUT("ret/{ac}/{barcode}")
    Call<BorrowRetTrans>returnBook(@Path("ac")String ac, @Path("barcode")String barcode);


    @GET("borrowedbooks/{ac}")
    Call<List<BorrowedBook>>borrowedbooks(@Path("ac")String ac);

    @PUT("renew/{barcodeID}")
    Call<RenewTrans>renew(@Path("barcodeID")String barcodeID);


    @GET("books")
    Call<List<Book>>getAllBook();

    @GET("bookcopies/isbn/{isbn}")
    Call<List<BranchCopies>>getAllBookCopies(@Path("isbn")String isbn);

    @GET("acs/{ac}")
    Call<AccountInfo>getAcInfo(@Path("ac")String ac);

    @GET("readers/{id}")
    Call<ReaderInfo>getReaderInfo(@Path("id")String readerId);

    @PUT("processret/{barcode}")
    Call<ProcessRetTrans>processRet(@Path("barcode")String barcode);

    @POST("book")
    Call<AddBookTrans> createBook(@Body Book book);

    @GET("libs")
    Call<List<Library>>getAllLib();

    @FormUrlEncoded
    @POST("bookcopies")
    Call<AddBookCopiesTrans> createBookCopies(
            @Field("isbn")String isbn,
            @Field("libid")String libid,
            @Field("barcode")String barcode,
            @Field("ac")String ac
    );
    @GET("bookcopies/{barcode}")
    Call<GetBookCopiesInfoTrans>getBookCopiesInfo(@Path("barcode")String barcode);


    @FormUrlEncoded
    @PUT("book")
    Call<UpdateBookTrans> updateBook(@FieldMap Map<String,String> fields);

    @DELETE("bookcopies/{barcode}/{ac}")
    Call<DeleteBookCopiesTrans>delBookCopies(@Path("barcode")String barcode,@Path("ac")String ac);


}
