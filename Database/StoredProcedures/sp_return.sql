
USE LMSDB;
DROP PROCEDURE if exists [Return]
GO  

CREATE PROCEDURE [Return] 
    @Ac varchar(10),   
	@BarcodeId varchar(10)

AS   
BEGIN
Declare @ReaderId As char(4)
Declare @Err_Msg AS varchar(50)
Declare @DueDate As date	
Declare @BorrowRecordId char(4)
SET @ReaderId=(SELECT ReaderId FROM Reader r WHERE r.Ac=@Ac )

--borrowHistory exist
IF(Select COUNT(1) From BorrowHistory where ReaderId=@ReaderId and BarCodeID=@BarcodeId and State=1)=1
	
	BEGIN
	   (Select @BorrowRecordId = BorrowRecordID, @DueDate = DueDate From BorrowHistory where ReaderId=@ReaderId and BarCodeID=@BarcodeId and State=1)
		--check if bookcopies status is borrowed out
		IF(Select Status From BookCopies where BarCodeID=@BarcodeId)=2 
			--perform update
				BEGIN TRY
					BEGIN TRAN
					Update BookCopies
					SET Status=3 where BarcodeID=@BarcodeId
					Update BorrowHistory
					SET State=8 where ReaderId=@ReaderId and BarCodeID=@BarcodeId and State=1

					COMMIT TRAN
				END TRY
				BEGIN CATCH
					SET @Err_Msg=ERROR_MESSAGE()
					ROLLBACK TRAN
				END CATCH
		ELSE
			SET @Err_Msg='BookCopies Status is not Borrowed out!'
			
	END
ELSE 
	SET @Err_Msg='Cannot find borrowHistory!'

IF (@Err_Msg is null)	
	SELECT 'SUCCESS' AS tranState, @BorrowRecordId AS borrowId ,@DueDate As dueDate
ELSE
	SELECT @Err_Msg AS tranState

END
GO  
--execute [Return] 'ivan','8661995532'

