USE LMSDB;
DROP PROCEDURE if exists getBookCopiesInfo
GO  

CREATE PROCEDURE getBookCopiesInfo  

	@barcode varchar(10)

AS   
BEGIN
Declare @Err_Msg AS varchar(50)
Declare @title AS varchar(255)
Declare @isbn AS char(13)
Declare @author AS varchar(255)
Declare @publisher AS varchar(255)
Declare @publishingYear AS char(4)
Declare @location AS varchar(255)
Declare @status AS char(1)
--check if barcode exist
IF (Select 1 from BookCopies where BarcodeID=@barcode)=1
	BEGIN
		Select @title=b.Title,@isbn=b.ISBN,@author=b.Author,@publisher=b.Publisher,@publishingYear=b.PublishingYear, @location=l.location,@status=bc.Status from 
		Book b inner join BookCopies bc on b.ISBN=bc.ISBN
		inner join Library l on l.LibID=bc.LibID
		where @barcode=bc.BarcodeID
		

	END

ELSE
	SET @Err_Msg='Barcode not exist'


	IF (@Err_Msg is null)	
		SELECT 'SUCCESS' AS tranState,@title As Title,@isbn As ISBN,@author As Author,@publisher As Publisher,@publishingYear As PublishingYear, @location As Location,@status As Status
	ELSE
		SELECT @Err_Msg AS tranState

	END
GO  
--execute getBookCopiesInfo '1242524051';
--execute getBookCopiesInfo '1241524051';