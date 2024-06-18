
USE LMSDB;
DROP PROCEDURE if exists [deleteBook]
GO  

CREATE PROCEDURE [deleteBook] 
	@barcodeId varchar(10),
	@ac varchar(10)

AS   
BEGIN
Declare @AddRemoveRecordID AS char(4)
Declare @Err_Msg AS varchar(100)
Declare @StatusBookCp AS char(1)
Declare @editStaffid AS varchar(50)
SET @editStaffid=(Select StaffId from Staff where Ac=@ac)
SET @AddRemoveRecordID=(select FORMAT(MAX(AddRemoveRecordID)+1,'0000') from AddAndRemoveHistory)
--validate barcodeid
if (select 1 from BookCopies where BarcodeID=@barcodeId)=1
	BEGIN
		--check status of book copies
		SET @StatusBookCp=(select status from BookCopies where BarcodeID=@barcodeId)
			If @StatusBookCp=2
				SET @Err_Msg='This book is borrowed out. Cannot be deleted'
			ELSE IF @StatusBookCp=3
				SET @Err_Msg='This book is on return request. Cannot be deleted. Finish the return request first.'
			ELSE IF @StatusBookCp=8
				SET @Err_Msg='This book is deleted already. Cannot be deleted again.'
			ELSE
				BEGIN
					BEGIN TRY
						BEGIN TRAN

						update BookCopies
						set Status=8
						where BarcodeID=@barcodeId

						Insert into AddAndRemoveHistory(AddRemoveRecordID,Date,BarcodeID,StaffID)
						values(@AddRemoveRecordID,(SELECT FORMAT(GETDATE(),'yyyy-MM-dd')),@barcodeId,@editStaffid)

						COMMIT TRAN
					END TRY
					BEGIN CATCH
						SET @Err_Msg=ERROR_MESSAGE()
						ROLLBACK TRAN
					END CATCH
				END
	END
ELSE
	Set @Err_Msg='Invalid Barcode. No delete can be made.'
				

IF (@Err_Msg is null)	
	SELECT 'SUCCESS' AS tranState
ELSE
	SELECT @Err_Msg AS tranState


END
GO  
--execute [deleteBook] '6466577788','jack4'



