var config =require('./dbconfig');
const sql=require ('mssql');

async function login(account){
    try{
        let pool=await sql.connect(config);
        let record = await pool.request()
        .input('Ac',sql.NVarChar,account.Ac)
        .input('Pw',sql.NVarChar,account.Pw)
        .execute("Login")
       
        return record.recordsets[0]; 
    }
    catch (error){
        console.log(error);
    }

}


async function borrow(ac,bookBarCode){
    try{
        let pool=await sql.connect(config);
        let record = await pool.request()
        .input('Ac',sql.NVarChar,ac)
        .input('BarcodeId',sql.NVarChar,bookBarCode)
        .execute("Borrow")
       
        return record.recordsets[0]; 
    }
    catch (error){
        console.log(error);
    }

}
async function returnBook(ac,bookBarCode){
    try{
        let pool=await sql.connect(config);
        let record = await pool.request()
        .input('Ac',sql.NVarChar,ac)
        .input('BarcodeId',sql.NVarChar,bookBarCode)
        .execute("Return")
       
        return record.recordsets[0]; 
    }
    catch (error){
        console.log(error);
    }

}
// async function getBookCopiesStatus(bookBarCode){
//     try{
//         let pool=await sql.connect(config);
//         let record = await pool.request()
//             .input('barcode',sql.VarChar,bookBarCode)
//             .query("select title,status from bookcopies bc inner join Book b on bc.ISBN=b.ISBN where bc.BarcodeID=@barcode")
       
//         return record.recordsets[0]; 
//     }
//     catch (error){
//         console.log(error);
//     }

// }

async function getAllBorrowedBooks(ac){
    try{
        let pool=await sql.connect(config);
        let record = await pool.request()
            .input('ac',sql.VarChar,ac)
            .query("select bc.BarcodeID,b.title,bh.BorrowDate,bh.DueDate,bh.RenewalTimes from BorrowHistory bh inner join BookCopies bc on bh.BarCodeID=bc.BarcodeID inner join Book b on b.ISBN=bc.ISBN inner join Reader r on r.ReaderId=bh.ReaderId where bh.State=1 and r.Ac=@ac")
       
        return record.recordsets[0]; 
    }
    catch (error){
        console.log(error);
    }

}

async function renew(BarcodeID){
    try{
        let pool=await sql.connect(config);
        let record = await pool.request()
            .input('barcodeID',sql.VarChar,BarcodeID)
            .execute("Renew")
       
        return record.recordsets[0]; 
    }
    catch (error){
        console.log(error);
    }

}


async function getAllBook(){
    try{
        let pool=await sql.connect(config);
        let record = await pool.request().query("select Title,Author,Publisher,ISBN,PublishingYear,EditStaffID from Book order by title");
       
        return record.recordsets[0]; 
    }
    catch (error){
        console.log(error);
    }

}
async function getAllLib(){
    try{
        let pool=await sql.connect(config);
        let record = await pool.request().query("select LibID,Location,ContactNo,Address from Library ");
       
        return record.recordsets[0]; 
    }
    catch (error){
        console.log(error);
    }

}

async function getAllBookCopies(isbn){
    try{
        let pool=await sql.connect(config);
        let record = await pool.request()
        .input('isbn',sql.VarChar,isbn)
        .query("select  l.Location,bc.Status from bookcopies bc inner join Library l on bc.LibID=l.LibID where ISBN=@isbn");
       
        return record.recordsets[0]; 
    }
    catch (error){
        console.log(error);
    }

}


async function getAccountInfo(ac){
    try{
        let pool=await sql.connect(config);
        let record = await pool.request()
        .input('ac',sql.VarChar,ac)
        .query(`select StaffId As TypeId,FirstName,LastName,Gender,HKID,DOB,EmailAddress,Address,ContactNo,null As MaxQuota,Ac from Staff  where Ac=@ac
        UNION
        select * from Reader  where Ac=@ac
       `);
       
        return record.recordsets[0]; 
    }
    catch (error){
        console.log(error);
    }

}
async function getReaderInfo(readerId){
    try{
        let pool=await sql.connect(config);
        let record = await pool.request()
        .input('readerid',sql.VarChar,readerId)
        .execute("getReaderInfo");
       
        return record.recordsets[0]; 
    }
    catch (error){
        console.log(error);
    }

}

async function processRetReq(barcode){
    try{
        let pool=await sql.connect(config);
        let record = await pool.request()
        .input('barcodeId',sql.VarChar,barcode)
        .execute("ProcessReturn");
       
        return record.recordsets[0]; 
    }
    catch (error){
        console.log(error);
    }

}
async function addBook(book){
    try{
        let pool=await sql.connect(config);
        let insertBook = await pool.request()
        .input('title',sql.VarChar,book.Title)
        .input('author',sql.VarChar,book.Author)        
        .input('publisher',sql.VarChar,book.Publisher)
        .input('isbn',sql.VarChar,book.ISBN)
        .input('publishingYear',sql.VarChar,book.PublishingYear)
        .input('ac',sql.VarChar,book.EditStaffAc)
        .execute('InsertBook'); //stored procedure

        return insertBook.recordsets[0][0]; 
    }
    catch (error){
        console.log(error);
    }

}
async function addBookCopies(bookCopies){
    try{
        let pool=await sql.connect(config);
        let insertBookCop = await pool.request()

        .input('isbn',sql.VarChar,bookCopies.isbn)
        .input('libid',sql.VarChar,bookCopies.libid)
        .input('barcode',sql.VarChar,bookCopies.barcode)
        .input('ac',sql.VarChar,bookCopies.ac)
        .execute('InsertBookCopies'); //stored procedure

        return insertBookCop.recordsets[0][0]; 
    }
    catch (error){
        console.log(error);
    }

}
async function getBookCopiesInfo(barcode){
    try{
        let pool=await sql.connect(config);
        let getBookCopiesInfo = await pool.request()

        .input('barcode',sql.VarChar,barcode)
        .execute('getBookCopiesInfo'); //stored procedure

        return getBookCopiesInfo.recordsets[0][0]; 
    }
    catch (error){
        console.log(error);
    }

}

async function updateBook(updateReqBody){
    try{
        let pool=await sql.connect(config);
        let getBookCopiesInfo = await pool.request()

        .input('barcodeId',sql.VarChar,updateReqBody.BarcodeID)
        .input('title',sql.VarChar,updateReqBody.Title)
        .input('author',sql.VarChar,updateReqBody.Author)
        .input('publisher',sql.VarChar,updateReqBody.Publisher)
        .input('publishingYear',sql.VarChar,updateReqBody.PublishingYear)
        .input('ac',sql.VarChar,updateReqBody.Ac)
        .input('location',sql.VarChar,updateReqBody.Location)
        .execute('updateBook'); //stored procedure

        return getBookCopiesInfo.recordsets[0][0]; 
    }
    catch (error){
        console.log(error);
    }

}
async function delBook(barcode,ac){
    try{
        let pool=await sql.connect(config);
        let delBook = await pool.request()

        .input('barcodeId',sql.VarChar,barcode)
        .input('ac',sql.VarChar,ac)
        .execute('deleteBook'); //stored procedure

        return delBook.recordsets[0][0]; 
    }
    catch (error){
        console.log(error);
    }

}

module.exports = {
    login:login,
    borrow:borrow,
    returnBook:returnBook,
    //getBookCopiesStatus:getBookCopiesStatus,
    getAllBorrowedBooks:getAllBorrowedBooks,
    renew:renew,
    getAllBook:getAllBook,
    getAllBookCopies:getAllBookCopies,
    getAccountInfo:getAccountInfo,
    getReaderInfo:getReaderInfo,
    processRetReq:processRetReq,
    addBook:addBook,
    getAllLib,getAllLib,
    addBookCopies:addBookCopies,
    getBookCopiesInfo:getBookCopiesInfo,
    updateBook:updateBook,
    delBook:delBook
}