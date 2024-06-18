USE LMSDB;
DROP PROCEDURE if exists InsertBookCopies 
GO  

CREATE PROCEDURE InsertBookCopies  
    @isbn char(13),   
    @libid char(4) ,
	@barcode varchar(10),
	@ac varchar(10)

AS   
BEGIN
Declare @AddRemoveRecordID AS char(4)
Declare @Err_Msg AS varchar(50)
Declare @editStaffid AS varchar(50)
SET @editStaffid=(Select StaffId from Staff where Ac=@ac)
SET @AddRemoveRecordID=(select FORMAT(MAX(AddRemoveRecordID)+1,'0000') from AddAndRemoveHistory)
--check if barcode already exist
IF (Select 1 from BookCopies where BarcodeID=@barcode)=1
	SET @Err_Msg='Barcode already exist'
ELSE
	BEGIN
	--perform insert
		BEGIN TRY
			BEGIN TRAN

			Insert into BookCopies(BarcodeID,Status,ISBN,LibID) 
			values(@barcode,1,@isbn,@libid)

			Insert into AddAndRemoveHistory(AddRemoveRecordID,Date,BarcodeID,StaffID)
			values(@AddRemoveRecordID,(SELECT FORMAT(GETDATE(),'yyyy-MM-dd')),@barcode,@editStaffid)

			COMMIT TRAN
		END TRY
		BEGIN CATCH
			SET @Err_Msg=ERROR_MESSAGE()
			ROLLBACK TRAN
		END CATCH	
	END

	IF (@Err_Msg is null)	
		SELECT 'SUCCESS' AS tranState
	ELSE
		SELECT @Err_Msg AS tranState

	END
GO  
--execute InsertBookCopies '9782380869262','0002','1202125102','jack4' ;