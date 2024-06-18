
USE LMSDB;
DROP PROCEDURE if exists [updateBook]
GO  

CREATE PROCEDURE [updateBook] 
	@barcodeId varchar(10),
	@title varchar(255),
	@author varchar(255),
	@publisher varchar(255),
	@publishingYear char(4),
	@ac varchar(10),
	@location varchar(255)

AS   
BEGIN
Declare @Err_Msg AS varchar(50)
Declare @editStaffid AS varchar(50)
Declare @isbn AS varchar(13)
Declare @libid AS char(4)

SET @editStaffid=(Select StaffId from Staff where Ac=@ac)
SET  @isbn=(Select b.ISBN from Book b inner join BookCopies bc  on  bc.ISBN=b.isbn where bc.BarcodeID=@barcodeId )
SET @libid=(Select LibID from Library where Location=@location )

--validate barcodeid
if (select 1 from BookCopies where BarcodeID=@barcodeId)=1

	BEGIN TRY
		BEGIN TRAN

		update Book
		set Title=@title,Author=@author,Publisher=@publisher,PublishingYear=@publishingYear,EditStaffID=@editStaffid
		where ISBN=@isbn

		update BookCopies
		set LibID=@libid
		where BarcodeID=@barcodeId

		COMMIT TRAN
	END TRY
	BEGIN CATCH
		SET @Err_Msg=ERROR_MESSAGE()
		ROLLBACK TRAN
	END CATCH
ELSE
	Set @Err_Msg='Invalid Barcode. No update can be made.'
				

IF (@Err_Msg is null)	
	SELECT 'SUCCESS' AS tranState,@barcodeId As barcodeId,@title As title,@author as author,@publisher as publisher,@publishingYear as publishingYear,@editStaffid as editStaffId,@location as location
ELSE
	SELECT @Err_Msg AS tranState


END
GO  
--execute [updateBook] '9868449454','The Far-Distant Oxus2','James Davis2','Penguin Books','2020','jack4','Chai Wan'



